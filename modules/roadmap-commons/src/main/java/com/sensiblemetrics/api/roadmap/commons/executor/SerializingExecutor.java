package com.sensiblemetrics.api.roadmap.commons.executor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Executor ensuring that all {@link Runnable} tasks submitted are executed in order
 * using the provided {@link Executor}, and serially such that no two will ever be
 * running at the same time.
 */
@Slf4j
public final class SerializingExecutor implements Executor, Runnable {

    private static final AtomicHelper atomicHelper = getAtomicHelper();

    private static AtomicHelper getAtomicHelper() {
        AtomicHelper helper;
        try {
            helper = new FieldUpdaterAtomicHelper(AtomicIntegerFieldUpdater.newUpdater(SerializingExecutor.class, "runState"));
        } catch (Throwable t) {
            log.error("FieldUpdaterAtomicHelper failed", t);
            helper = new SynchronizedAtomicHelper();
        }
        return helper;
    }

    private static final int STOPPED = 0;
    private static final int RUNNING = -1;

    /**
     * Underlying executor that all submitted Runnable objects are run on.
     */
    private final Executor executor;

    /**
     * A list of Runnables to be run in order.
     */
    private final Queue<Runnable> runQueue = new ConcurrentLinkedQueue<>();

    private volatile int runState = STOPPED;

    /**
     * Creates a SerializingExecutor, running tasks using {@code executor}.
     *
     * @param executor Executor in which tasks should be run. Must not be null.
     */
    public SerializingExecutor(final Executor executor) {
        Validate.notNull(executor, "Executor should not be null");
        this.executor = executor;
    }

    /**
     * Runs the given runnable strictly after all Runnables that were submitted
     * before it, and using the {@code executor} passed to the constructor.     .
     */
    @Override
    public void execute(final Runnable runnable) {
        this.runQueue.add(checkNotNull(runnable, "'r' must not be null."));
        this.schedule(runnable);
    }

    private void schedule(@Nullable final Runnable removable) {
        if (atomicHelper.runStateCompareAndSet(this, STOPPED, RUNNING)) {
            boolean success = false;
            try {
                executor.execute(this);
                success = true;
            } finally {
                if (!success) {
                    if (removable != null) {
                        this.runQueue.remove(removable);
                    }
                    atomicHelper.runStateSet(this, STOPPED);
                }
            }
        }
    }

    @Override
    public void run() {
        Runnable r;
        try {
            while ((r = this.runQueue.poll()) != null) {
                try {
                    r.run();
                } catch (RuntimeException e) {
                    // Log it and keep going.
                    log.error("Exception while executing runnable " + r, e);
                }
            }
        } finally {
            atomicHelper.runStateSet(this, STOPPED);
        }
        if (!this.runQueue.isEmpty()) {
            // we didn't enqueue anything but someone else did.
            this.schedule(null);
        }
    }

    private abstract static class AtomicHelper {
        public abstract boolean runStateCompareAndSet(final SerializingExecutor obj,
                                                      final int expect,
                                                      final int update);

        public abstract void runStateSet(final SerializingExecutor obj,
                                         final int newValue);
    }

    private static final class FieldUpdaterAtomicHelper extends AtomicHelper {
        private final AtomicIntegerFieldUpdater<SerializingExecutor> runStateUpdater;

        private FieldUpdaterAtomicHelper(final AtomicIntegerFieldUpdater<SerializingExecutor> runStateUpdater) {
            this.runStateUpdater = runStateUpdater;
        }

        @Override
        public boolean runStateCompareAndSet(final SerializingExecutor obj,
                                             final int expect,
                                             final int update) {
            return this.runStateUpdater.compareAndSet(obj, expect, update);
        }

        @Override
        public void runStateSet(final SerializingExecutor obj, final int newValue) {
            this.runStateUpdater.set(obj, newValue);
        }
    }

    private static final class SynchronizedAtomicHelper extends AtomicHelper {
        @Override
        public boolean runStateCompareAndSet(final SerializingExecutor obj,
                                             final int expect,
                                             final int update) {
            synchronized (obj) {
                if (obj.runState == expect) {
                    obj.runState = update;
                    return true;
                }
                return false;
            }
        }

        @Override
        public void runStateSet(final SerializingExecutor obj,
                                final int newValue) {
            synchronized (obj) {
                obj.runState = newValue;
            }
        }
    }
}
