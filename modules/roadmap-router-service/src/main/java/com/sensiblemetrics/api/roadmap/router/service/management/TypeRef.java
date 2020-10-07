package com.sensiblemetrics.api.roadmap.router.service.management;

import lombok.Data;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Used to specify generic type information. Example usage: <code>
 * TypeRef&lt;List&lt;Item&gt;&gt; type = new TypeRef&lt;List&lt;Item&gt;&gt;(){};
 * <p>
 * List&lt;Item&gt; serialized = sut.map(incoming, type);
 * </code>
 *
 * @param <T> the type of the instance to be wrapped
 */
@Data
public class TypeRef<T> implements Comparable<TypeRef<T>> {
    protected final Type type;

    public TypeRef() {
        this.type = getGenericType(this.getClass());
    }

    public static Type getGenericType(final Class<?> typeClass) {
        final Type superclass = typeClass.getGenericSuperclass();
        if (superclass instanceof Class<?>) {
            throw new IllegalArgumentException("No type parameter provided");
        }
        final ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }

    /**
     * Prevent construction of a reference without type information.
     */
    @Override
    public int compareTo(@Nullable final TypeRef<T> o) {
        return 0;
    }
}
