package com.sensiblemetrics.api.roadmap.commons.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@UtilityClass
public class PropertyUtils {
    /**
     * Throw exception on loading failure flag
     */
    private static final boolean THROW_ON_LOAD_FAILURE = true;
    /**
     * Load resource as bundle flag
     */
    private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
    /**
     * Default resource suffix
     */
    private static final String SUFFIX = ".properties";

    /**
     * Property {@link Map}
     */
    private static final Properties PROPERTY_MAP = loadProperties("config.properties");

    /**
     * Looks up a resource named 'name' in the classpath. The resource must map
     * to a file with .properties extention. The name is assumed to be absolute
     * and can use either "/" or "." for package segment separation with an
     * optional leading "/" and optional ".properties" suffix.
     *
     * @param name   classpath resource name [may not be null]
     * @param loader classloader through which to load the resource [null
     *               is equivalent to the application loader]
     * @return resource converted to java.util.Properties [may be null if the
     * resource was not found and THROW_ON_LOAD_FAILURE is false]
     * @throws IllegalArgumentException if the resource was not found and
     *                                  THROW_ON_LOAD_FAILURE is true
     */
    private static Properties loadProperties(String name, ClassLoader loader) {
        if (name == null) {
            throw new IllegalArgumentException("null input: name");
        }
        if (name.startsWith("/"))
            name = name.substring(1);

        if (name.endsWith(SUFFIX)) {
            name = name.substring(0, name.length() - SUFFIX.length());
        }
        Properties result = null;
        InputStream in = null;
        try {
            if (loader == null) {
                loader = ClassLoader.getSystemClassLoader();
            }

            if (LOAD_AS_RESOURCE_BUNDLE) {
                name = name.replace('/', '.');
                // throws MissingResourceException on lookup failures:
                final ResourceBundle rb = ResourceBundle.getBundle(name, Locale.getDefault(), loader);
                result = new Properties();
                for (final Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements(); ) {
                    final String key = keys.nextElement();
                    final String value = rb.getString(key);
                    result.put(key, value);
                }
            } else {
                name = name.replace('.', '/');
                if (!name.endsWith(SUFFIX)) {
                    name = name.concat(SUFFIX);
                }
                // returns null on lookup failures:
                in = loader.getResourceAsStream(name);
                if (in != null) {
                    result = new Properties();
                    result.load(in);
                }
            }
        } catch (Exception e) {
            result = null;
        } finally {
            if (in != null) try {
                in.close();
            } catch (Throwable ignore) {
            }
        }

        if (THROW_ON_LOAD_FAILURE && (result == null)) {
            throw new IllegalArgumentException(
                "could not load [" + name + "]" +
                    " as " + (LOAD_AS_RESOURCE_BUNDLE
                    ? "a resource bundle"
                    : "a classloader resource")
            );
        }
        return result;
    }

    /**
     * A convenience overload of {@link #loadProperties(String, ClassLoader)}
     * that uses the current thread's context classloader.
     */
    public static Properties loadProperties(final String name) {
        return loadProperties(name, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Returns a defensive copy of the string object which is
     * contained in the HashMap which associates to the name key
     */
    public static String getProperty(final String propertyName) {
        return PROPERTY_MAP.getProperty(propertyName.toUpperCase(), EMPTY);
    }

    /**
     * Returns binary flag whether input {@link T} value is positive/negative
     *
     * @param value initial input {@link T} to validate
     * @param <T>   type of configurable number
     * @return true - if input value is positive or zero, false - otherwise
     */
    public static <T extends Number> boolean isPositive(final T value) {
        return value.doubleValue() >= 0d;
    }

    /**
     * Returns parsed {@link Optional} {@link String} value
     *
     * @return {@link Optional} {@link String} value
     */
    public static Optional<String> getPropertyValueAsString(final String value) {
        return validateAndCompute(value, v -> v);
    }

    /**
     * Returns parsed {@link Optional} {@link Long} value
     *
     * @return {@link Optional} {@link Long} value
     */
    public static Optional<Long> getPropertyValueAsLong(final String value) {
        return validateAndCompute(value, Long::valueOf);
    }

    /**
     * Returns parsed {@link Optional} {@link Integer} value
     *
     * @return {@link Optional} {@link Integer} value
     */
    public static Optional<Integer> getPropertyValueAsInt(final String value) {
        return validateAndCompute(value, Integer::valueOf);
    }

    /**
     * Returns {@link Optional} of computed input {@link String} property value by {@link Function} validator
     *
     * @param value     initial input {@link String} property value to compute
     * @param validator initial input {@link Function} validator
     * @param <T>       type of computed value
     * @return computed {@link T} property value
     */
    private static <T> Optional<T> validateAndCompute(final String value,
                                                      final Function<String, T> validator) {
        try {
            return Optional.ofNullable(value)
                .map(StringUtils::trimToNull)
                .map(validator);
        } catch (Exception ex) {
            log.error("Cannot validate input value: {}", value, ex);
            return Optional.empty();
        }
    }
}
