package com.sensiblemetrics.api.roadmap.router.service.mapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sensiblemetrics.api.roadmap.commons.exception.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.cast;
import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.streamOf;
import static java.lang.String.format;

/**
 * {@link ObjectMapper} utilities implementation
 */
@Slf4j
public final class DelegatedObjectMapper {
    private final ObjectMapper objectMapper;

    /**
     * Default delegated {@link ObjectMapper} constructor
     *
     * @param objectMapper - initial input {@link ObjectMapper} to operate by
     */
    public DelegatedObjectMapper(final ObjectMapper objectMapper) {
        Validate.notNull(objectMapper, "Object mapper should not be null");
        this.objectMapper = objectMapper;
    }

    /**
     * Returns serialized {@link String} by input source {@link T} and view {@link Class}
     *
     * @param <T>    type of object to be converted from
     * @param <V>    type of object to be mapped by
     * @param source - initial input source to be mapped from {@link T}
     * @return serialized {@link String} representation of input {@link T} object
     */
    public <T, V> String toJsonInternal(final T source) {
        return this.toJson(source, ModelView.Internal.class, false);
    }

    /**
     * Returns serialized {@link String} by input source {@link T} and view {@link Class}
     *
     * @param <T>      type of object to be converted from
     * @param <V>      type of object to be mapped by
     * @param source   - initial input source to be mapped from {@link T}
     * @param failFast - initial input {@code boolean} fail fast flag
     * @return serialized {@link String} representation of input {@link T} object
     */
    public <T, V> String toJsonInternal(final T source, final boolean failFast) {
        return this.toJson(source, ModelView.Internal.class, failFast);
    }

    /**
     * Returns serialized {@link String} by input source {@link T} and view {@link Class}
     *
     * @param <T>    type of object to be converted from
     * @param <V>    type of object to be mapped by
     * @param source - initial input source to be mapped from {@link T}
     * @return serialized {@link String} representation of input {@link T} object
     */
    public <T, V> String toJsonExternal(final T source) {
        return this.toJson(source, ModelView.External.class, false);
    }

    /**
     * Returns serialized {@link String} by input source {@link T} and view {@link Class}
     *
     * @param <T>      type of object to be converted from
     * @param <V>      type of object to be mapped by
     * @param source   - initial input source to be mapped from {@link T}
     * @param failFast - initial input {@code boolean} fail fast flag
     * @return serialized {@link String} representation of input {@link T} object
     */
    public <T, V> String toJsonExternal(final T source, final boolean failFast) {
        return this.toJson(source, ModelView.External.class, failFast);
    }

    /**
     * Returns converted {@link D} by input source {@link S} and destination target {@link Class}
     *
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <S>         type of object to be converted from
     * @param <D>         type of objects in result list
     * @param source      - initial input object to be mapped {@link S}
     * @param targetClass - initial input class to map by {@link Class}
     * @return mapped object of {@link D} type
     */
    public <S, D> D map(final S source, final Class<? extends D> targetClass) {
        return this.objectMapper.convertValue(source, targetClass);
    }

    /**
     * Returns converted {@link D} by input source {@link String} and destination target {@link JavaType}
     *
     * @param <S>        type of object to be converted from
     * @param <D>        type of object to be converted to
     * @param source     - initial input source to be mapped from {@link S}
     * @param targetType - initial java type to be mapped to {@link JavaType}
     * @return mapped object with {@link D} type
     */
    public <S, D> D map(final S source, final JavaType targetType) {
        return this.objectMapper.convertValue(source, targetType);
    }

    /**
     * Returns converted {@link List} of {@link D} items by input {@link Collection} of {@link S} items and {@link Class}
     *
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <S>         type of object to be converted from
     * @param <D>         type of objects in result list
     * @param source      - initial input collection of objects {@link S} to be mapped {@link Collection}
     * @param targetClass - initial input class to map by {@link Class}
     * @return {@link List} of mapped objects of {@link D} type
     */
    public <S, D> List<D> toList(final Collection<S> source,
                                 final Class<? extends D> targetClass) {
        return streamOf(source)
            .map(entity -> this.map(entity, targetClass))
            .collect(Collectors.toList());
    }

    /**
     * Returns deserialized {@link D} value by input source {@link String} and target {@link Type}
     *
     * @param <D>    type of object to be converted to
     * @param source - initial input {@link String} to deserialize by
     * @param type   - initial input target {@link Type}
     * @return deserialized {@link D} value
     */
    public <D> D map(final String source, final Type type) {
        try {
            return this.objectMapper.readValue(source, this.objectMapper.getTypeFactory().constructType(type));
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, type.getTypeName()), e);
        }
    }

    /**
     * Returns deserialized {@link D} value by input source {@link String} and destination target {@link D}
     *
     * @param <D>         type of object to be converted to
     * @param <V>         type of object to be mapped by
     * @param source      - initial input source to be mapped from {@link String}
     * @param targetClass - initial input class to convert to {@link Class}
     * @param viewClass   - initial input view class to converted by {@link Class}
     * @return mapped object with {@link D} type
     */
    public <D, V> D map(final String source, final Class<? extends D> targetClass, final Class<? extends V> viewClass) {
        try {
            return this.objectMapper.readerWithView(viewClass).forType(targetClass).readValue(source);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetClass.getSimpleName()), e);
        }
    }

    /**
     * Returns deserialized {@link D} value by input source {@link String} and destination target {@link D}
     *
     * @param <D>         type of object to be converted to
     * @param source      - initial input source to be mapped from {@link String}
     * @param targetClass - initial input class to convert to {@link Class}
     * @return mapped object with {@link D} type
     */
    public <D> D map(final String source, final Class<? extends D> targetClass) {
        try {
            return this.objectMapper.reader().forType(targetClass).readValue(source);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetClass.getSimpleName()), e);
        }
    }

    /**
     * Returns deserialized {@link D} value by input source {@link byte} array and destination target {@link TypeReference}
     *
     * @param <D>        type of object to be converted to
     * @param source     - initial input source to be mapped from
     * @param targetType - initial input destination target {@link TypeReference}
     * @return mapped object with {@link D} type
     */
    public <D> D map(final byte[] source, final TypeReference<D> targetType) {
        try {
            return this.objectMapper.readValue(source, targetType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", Arrays.toString(source), targetType.getType().getTypeName()), e);
        }
    }

    /**
     * Returns deserialized {@link D} value by input source {@link byte} array and destination target {@link TypeReference}
     *
     * @param <D>         type of object to be converted to
     * @param inputStream - initial input source to be mapped from
     * @param targetType  - initial input destination target {@link TypeReference}
     * @return mapped object with {@link D} type
     */
    public <D> D map(final InputStream inputStream, final Class<D> targetType) {
        try {
            return this.objectMapper.readValue(inputStream, targetType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source to type: {%s}", targetType), e);
        }
    }

    /**
     * Returns deserialized {@link D} value by input source {@link String} and destination target {@link TypeReference}
     *
     * @param <D>        type of object to be converted to
     * @param source     - initial input source to be mapped from
     * @param targetType - initial input destination target {@link TypeReference}
     * @return mapped object with {@link D} type
     */
    public <D> D map(final String source, final TypeReference<D> targetType) {
        try {
            return this.objectMapper.readValue(source, targetType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetType.getType().getTypeName()), e);
        }
    }

    /**
     * Returns deserialized {@link JsonNode} by input {@link String} source
     *
     * @param source - initial input {@link String} source to deserialize from
     * @return deserialized {@link JsonNode}
     */
    public JsonNode toJsonNode(final String source) {
        try {
            return this.objectMapper.readTree(source);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, JsonNode.class.getSimpleName()), e);
        }
    }

    public <T> T readConfiguration(final JsonNode jsonNode, final Class<T> clazz) {
        try {
            return this.objectMapper.treeToValue(jsonNode, clazz);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", jsonNode, JsonNode.class.getSimpleName()), e);
        }
    }

    /**
     * Returns deserialized {@link JsonNode} by input {@link Object} source
     *
     * @param source - initial input {@link Object} source to deserialize from
     * @return deserialized {@link JsonNode}
     */
    public JsonNode toJsonNode(final Object source) {
        try {
            return this.objectMapper.readTree(toString(source));
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, JsonNode.class.getSimpleName()), e);
        }
    }

    /**
     * Returns deserialized {@link T} value by input {@link JsonNode} source
     *
     * @param <T>    type of configurable result item
     * @param source - initial input {@link JsonNode} source to deserialize from
     * @return deserialized {@link T} value
     */
    public <T> T map(final JsonNode source, final TypeReference<T> targetType) {
        try {
            return this.objectMapper.readValue(source.toString(), targetType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetType.getType().getTypeName()), e);
        }
    }

    /**
     * Returns deserialized {@link T} value by input {@link Object} source
     *
     * @param <T>    type of configurable result item
     * @param source - initial input {@link Object} source to deserialize from
     * @return deserialized {@link T} value
     */
    public <T> T map(final Object source, final TypeReference<T> targetType) {
        try {
            return this.objectMapper.readValue(toString(source), targetType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetType.getType().getTypeName()), e);
        }
    }

    /**
     * Returns deserialized {@link T} value by input {@link InputStream} source
     *
     * @param source     - initial input {@link InputStream} source to deserialize from
     * @param targetType - initial input {@link TypeReference}
     * @return deserialized {@link T} value
     */
    public <T> T map(final InputStream source, final TypeReference<T> targetType) {
        try {
            return this.objectMapper.readValue(source, targetType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetType.getType().getTypeName()), e);
        }
    }

    /**
     * Serializes {@link T} value by input {@link OutputStream} source
     *
     * @param source     - initial input {@link OutputStream} source to serialize by
     * @param targetType - initial input {@link TypeReference}
     */
    public <T> void map(final OutputStream source, final TypeReference<T> targetType) {
        try {
            this.objectMapper.writeValue(source, targetType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetType.getType().getTypeName()), e);
        }
    }

    /**
     * Returns deserialized {@link List} of {@link D} items by input {@link String} source
     *
     * @param <D>    type of element to be converted to
     * @param source - initial input {@link String} source to map from
     * @return mapped object {@link List} with {@link D} type
     */
    public <D> List<D> toList(final String source) {
        return this.toList(source.getBytes());
    }

    /**
     * Returns deserialized {@link List} of {@link D} items by input {@link byte} array source
     *
     * @param <D>    type of element to be converted to
     * @param source - initial input {@link byte} array source to map from
     * @return mapped object {@link List} with {@link D} type
     */
    public <D> List<D> toList(final byte[] source) {
        try {
            return this.objectMapper.readValue(source, new TypeReference<List<D>>() {
            });
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", Arrays.toString(source), List.class.getSimpleName()), e);
        }
    }

    /**
     * Returns deserialized {@link Map} collection by input {@link String} source
     *
     * @param <K>    type of key element
     * @param <V>    type of value element
     * @param source - initial input {@link String} source to map from
     * @return mapped object {@link Map} with {@link K} key type, {@link V} value type
     */
    public <K, V> Map<K, V> toMap(final String source) {
        return this.toMap(source.getBytes());
    }

    /**
     * Returns deserialized {@link Map} collection by input {@link byte} array source
     *
     * @param <K>    type of key element
     * @param <V>    type of value element
     * @param source - initial input {@link byte} array source to map from
     * @return mapped object {@link Map} with {@link K} key type, {@link V} value type
     */
    public <K, V> Map<K, V> toMap(final byte[] source) {
        try {
            return this.objectMapper.readValue(source, new TypeReference<Map<K, V>>() {
            });
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", Arrays.toString(source), Map.class.getSimpleName()), e);
        }
    }

    /**
     * Returns deserialized {@link List} of {@link D} items by input {@link String} source and destination target {@link Class}
     *
     * @param <D>         type of key element
     * @param source      - initial input source to be mapped from
     * @param targetClass - initial input element {@link Class}
     * @return mapped object {@link List} with {@link D} items
     */
    @SuppressWarnings("unchecked")
    public <D> List<D> toList(final String source, final Class<? extends D> targetClass) {
        return this.toCollection(source, List.class, targetClass);
    }

    /**
     * Returns deserialized {@link List} of {@link D} items by input {@link byte} array source and destination target {@link Class}
     *
     * @param <D>         type of key element
     * @param source      - initial input source to be mapped from
     * @param targetClass - initial input element {@link Class}
     * @return mapped object {@link List} with {@link D} items
     */
    @SuppressWarnings("unchecked")
    public <D> List<D> toList(final byte[] source, final Class<? extends D> targetClass) {
        return this.toCollection(source, List.class, targetClass);
    }

    /**
     * Returns deserialized {@link Set} of {@link D} items by input {@link String} source and destination target {@link Class}
     *
     * @param <D>         type of key element
     * @param source      - initial input source to be mapped from
     * @param targetClass - initial input element {@link Class}
     * @return mapped object {@link Set} with {@link D} values
     */
    @SuppressWarnings("unchecked")
    public <D> Set<D> toSet(final String source, final Class<? extends D> targetClass) {
        return this.toCollection(source, Set.class, targetClass);
    }

    /**
     * Returns deserialized {@link Set} of {@link D} items by input {@link byte} array source and destination target {@link Class}
     *
     * @param <D>         type of key element
     * @param source      - initial input source to be mapped from
     * @param targetClass - initial input element {@link Class}
     * @return mapped object {@link Set} with {@link D} values
     */
    @SuppressWarnings("unchecked")
    public <D> Set<D> toSet(final byte[] source, final Class<? extends D> targetClass) {
        return this.toCollection(source, Set.class, targetClass);
    }

    /**
     * Returns deserialized {@link S} by input {@link String} source and destination target {@link Class}
     *
     * @param <T>             type of collection element
     * @param <S>             type of collection
     * @param source          - initial input source to be mapped from
     * @param collectionClass - initial input collection {@link Class}
     * @param elementClass    - initial input element {@link Class}
     * @return mapped object {@link S} collection of {@link T} values
     */
    public <T, S extends Collection<T>> S toCollection(final String source,
                                                       final Class<? extends S> collectionClass,
                                                       final Class<? extends T> elementClass) {
        return this.toCollection(source.getBytes(), collectionClass, elementClass);
    }

    /**
     * Returns deserialized {@link S} by input {@link String} source and destination target {@link Class}
     *
     * @param <T>             type of collection element
     * @param <S>             type of collection
     * @param source          - initial input source to be mapped from
     * @param collectionClass - initial input collection {@link Class}
     * @param elementClass    - initial input element {@link Class}
     * @return mapped object {@link S} collection of {@link T} values
     */
    public <T, S extends Collection<T>> S toCollection(final byte[] source,
                                                       final Class<? extends S> collectionClass,
                                                       final Class<? extends T> elementClass) {
        final CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(collectionClass, elementClass);
        try {
            return this.objectMapper.readerFor(collectionType).readValue(source);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", Arrays.toString(source), collectionClass.getSimpleName()), e);
        }
    }

    /**
     * Returns serialized {@link String} by input source {@link T} and view {@link Class}
     *
     * @param <T>       type of object to be converted from
     * @param <V>       type of object to be mapped by
     * @param source    - initial input source to be mapped from {@link T}
     * @param viewClass - initial input view class to converted by {@link Class}
     * @return serialized {@link String} representation of input object
     */
    public <T, V> String toJson(final T source, final Class<? extends V> viewClass) {
        try {
            return this.objectMapper.writerWithView(viewClass).writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, viewClass), e);
        }
    }

    /**
     * Returns serialized formatted {@link String} by input source {@link T} and view {@link Class}
     *
     * @param <T>       type of object to be converted from
     * @param <V>       type of object to be mapped by
     * @param source    - initial input source to be mapped from {@link T}
     * @param viewClass - initial input view class to converted by {@link Class}
     * @return string representation of input object
     */
    public <T, V> String toFormatString(final T source, final Class<? extends V> viewClass) {
        try {
            return this.objectMapper.writerWithView(viewClass).withDefaultPrettyPrinter().writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, viewClass), e);
        }
    }

    /**
     * Returns serialized {@link String} by input {@link Object} source
     *
     * @param source - initial input {@link Object} source to serialize
     * @return serialized {@link String}
     */
    public String toString(final Object source) {
        try {
            return this.objectMapper.writer().writeValueAsString(source);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, String.class.getSimpleName()), e);
        }
    }

    /**
     * Returns serialized {@link byte} array by input {@link Object} source
     *
     * @param source - initial input {@link Object} source to serialize
     * @return serialized {@link byte} array
     */
    public byte[] toByteArray(final Object source) {
        try {
            return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(source);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, String.class.getSimpleName()), e);
        }
    }

    /**
     * Returns serialized formatted {@link String} by input {@link Object} source
     *
     * @param source - initial input {@link Object} source to serialize
     * @return serialized formatted {@link String}
     */
    public String toPrettyString(final Object source) {
        try {
            return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(source);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, String.class.getSimpleName()), e);
        }
    }

    /**
     * Returns serialized value {@link String} from input parameters
     *
     * @param <T>         type of object to be converted to
     * @param source      - initial input {@link T} value to be serialized
     * @param datePattern - initial input date pattern
     * @return serialized value {@link T} as string
     * @throws JsonProcessingException - if cannot process input value
     */
    public <T> String toDateString(final T source, final String datePattern) throws JsonProcessingException {
        Validate.notBlank(datePattern, "Date pattern should not be null or empty");

        final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        this.objectMapper.setDateFormat(dateFormat);
        return this.objectMapper.writeValueAsString(source);
    }

    public String parse(final Object source) {
        final StringWriter writer = new StringWriter();
        try {
            final JsonGenerator generator = this.objectMapper.getFactory().createGenerator(writer);
            this.objectMapper.writeValue(generator, source);
            writer.flush();
            writer.close();
            generator.close();
            return writer.getBuffer().toString();
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, String.class.getSimpleName()), e);
        }
    }

    public <T> T parse(final InputStream source,
                       final Class<T> targetClass,
                       final String encoding) {
        try {
            return this.objectMapper.reader().forType(targetClass).readValue(new InputStreamReader(source, encoding));
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetClass.getSimpleName()), e);
        }
    }

    public <T> List<T> fromFile(final String filename) {
        Validate.notNull(filename, "File name should not be null");
        try {
            return this.objectMapper.readValue(new File(filename), new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", filename, List.class.getSimpleName()), e);
        }
    }

    public <T> void toFile(final List<T> source, final String filename) {
        Validate.notNull(filename, "File name should not be null");
        try {
            this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), source);
        } catch (Exception e) {
            throw new InvalidFormatException(format("Cannot write input source: {%s} to file", filename), e);
        }
    }

    private <T> List<T> jsonToListType(final String source, final Class<T> targetClass) {
        try {
            return this.objectMapper.readValue(source, this.objectMapper.getTypeFactory().constructCollectionType(List.class, targetClass));
        } catch (Exception e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetClass.getSimpleName()), e);
        }
    }

    public String serialize(final List<Map<String, Object>> source) {
        if (Objects.isNull(source)) {
            return null;
        }
        try {
            if (source.isEmpty()) {
                return this.objectMapper.writeValueAsString(new HashMap<>());
            } else {
                if (source.size() == 1) {
                    return this.objectMapper.writeValueAsString(source.get(0));
                }
                return this.objectMapper.writeValueAsString(source);
            }
        } catch (Exception e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, String.class.getSimpleName()), e);
        }
    }

    public <T> T toJson(final List<Map<String, Object>> source, final Class<T> targetClass) {
        if (Objects.isNull(source)) {
            return null;
        }
        try {
            if (source.isEmpty()) {
                return this.objectMapper.convertValue(new HashMap<>(), targetClass);
            } else {
                if (source.size() == 1) {
                    return this.objectMapper.convertValue(source.get(0), targetClass);
                }
                return this.objectMapper.convertValue(source, targetClass);
            }
        } catch (Exception e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetClass.getSimpleName()), e);
        }
    }

    /**
     * JSON Deserialization of the given json string.
     *
     * @param json       The json parser for reading json to deserialize
     * @param classArray The class of the array of objects to deserialize into
     * @return The deserialized list of objects
     */
    public <T> List<T> deserializeArray(final String json, final Class<T[]> classArray) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return Arrays.asList(this.objectMapper.readValue(json, classArray));
    }

    /**
     * Deserialize the given File to Java object.
     *
     * @param file       The File path
     * @param returnType The type to deserialize inot
     * @return The deserialized Java object
     */
    public <T> T deserialize(final File file, final TypeRef<?> returnType) {
        final JavaType javaType = this.objectMapper.constructType(returnType.getType());
        try {
            return this.objectMapper.readValue(file, javaType);
        } catch (IOException e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", file.getName(), returnType.getType().getTypeName()), e);
        }
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param source     The JSON string
     * @param returnType The type to deserialize inot
     * @return The deserialized Java object
     */
    public <T> T deserialize(final String source, final TypeRef<?> returnType) {
        final JavaType javaType = this.objectMapper.constructType(returnType.getType());
        try {
            return this.objectMapper.readValue(source, javaType);
        } catch (IOException e) {
            if (returnType.getType().equals(String.class)) {
                return cast(source);
            }
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, returnType.getType().getTypeName()), e);
        }
    }

    public <T> T toJson(final List<Map<String, Object>> source, final TypeRef<T> targetType) {
        if (Objects.isNull(source)) {
            return null;
        }
        final JavaType type = this.objectMapper.getTypeFactory().constructType(targetType.getType());
        try {
            if (source.isEmpty()) {
                return this.objectMapper.convertValue(new HashMap<>(), type);
            } else {
                if (source.size() == 1) {
                    return this.objectMapper.convertValue(source.get(0), type);
                }
                return this.objectMapper.convertValue(source, type);
            }
        } catch (Exception e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, targetType.getType().getTypeName()), e);
        }
    }

    /**
     * Returns serialized {@link String} by input source {@link T} and view {@link Class}
     *
     * @param <T>       type of object to be converted from
     * @param <V>       type of object to be mapped by
     * @param source    - initial input source to be mapped from {@link T}
     * @param viewClass - initial input view class to converted by {@link Class}
     * @param failFast  - initial input {@code boolean} flag on fail-fast processing
     * @return serialized {@link String} representation of input {@link T} object
     */
    public <T, V> String toJson(final T source, final Class<? extends V> viewClass, final boolean failFast) {
        try {
            return this.objectMapper.writerWithView(viewClass).writeValueAsString(source);
        } catch (JsonProcessingException e) {
            log.error("Cannot serialize value = {} by viewClass = {}, message = {}", source, viewClass, e.getMessage());
            if (failFast) {
                throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, viewClass.getSimpleName()), e);
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(final Object o) {
        return this.objectMapper.convertValue(o, Map.class);
    }

    /**
     * {@link JsonFactory} implementation
     */
    public static class Factory extends JsonFactory {
        private final PrettyPrinter prettyPrinter;

        /**
         * Default factory constructor
         */
        public Factory() {
            this(PrettyPrinter.INSTANCE);
        }

        /**
         * Default factory constructor by input {@link PrettyPrinter} instance
         *
         * @param prettyPrinter initial input {@link PrettyPrinter} instance to operate by
         */
        public Factory(final PrettyPrinter prettyPrinter) {
            this.prettyPrinter = prettyPrinter;
        }

        /**
         * {@inheritDoc}
         *
         * @see JsonFactory
         */
        @Override
        protected JsonGenerator _createGenerator(final Writer out, final IOContext ctxt) throws IOException {
            return super._createGenerator(out, ctxt).setPrettyPrinter(this.prettyPrinter);
        }
    }

    /**
     * {@link DefaultPrettyPrinter} instance
     */
    public static class PrettyPrinter extends DefaultPrettyPrinter {
        /**
         * Default explicit serialVersionUID for interoperability
         */
        private static final long serialVersionUID = 3193620927877099452L;

        /**
         * Default {@link PrettyPrinter} instance
         */
        public static final PrettyPrinter INSTANCE = new PrettyPrinter();

        /**
         * Default {@link PrettyPrinter} constructor
         */
        public PrettyPrinter() {
            this._arrayIndenter = new DefaultIndenter();
        }
    }

    public Object fileToObject(final String jsonFile,
                               final Class<?> objClass) throws IOException {
        return this.objectMapper.readValue(new File(jsonFile), objClass);
    }

    public <T> List<T> jsonToListObject(final String json,
                                        final Class<T> objClass) throws IOException {
        return this.objectMapper.readValue(
            json,
            this.objectMapper.getTypeFactory().constructCollectionType(List.class, objClass)
        );
    }

    public <T> List<T> fileToListObject(final InputStream jsonFile, final Class<T> typeClass) throws IOException {
        return this.objectMapper.readValue(
            jsonFile,
            this.objectMapper.getTypeFactory().constructCollectionType(List.class, typeClass)
        );
    }

    public <T> List<T> fileToListObject(final String jsonFile, final Class<T> typeClass) throws IOException {
        return this.objectMapper.readValue(
            new File(jsonFile),
            this.objectMapper.getTypeFactory().constructCollectionType(List.class, typeClass)
        );
    }

    public <S, D, P> D convertMessageToRequest(final S source,
                                               final Class<D> destClass,
                                               final Class<P> paramsClass) {
        try {
            final JavaType requestType = this.objectMapper.getTypeFactory().constructParametricType(destClass, paramsClass);
            return this.objectMapper.convertValue(source, requestType);
        } catch (Exception e) {
            throw new InvalidFormatException(format("Cannot convert input source: {%s} to type: {%s}", source, destClass.getTypeName()), e);
        }
    }

    public <S, D, P> D convertMessageToResponse(final S message,
                                                final Class<D> destClass,
                                                final Class<P> resultClass) {
        final JavaType responseType = this.objectMapper.getTypeFactory().constructParametricType(destClass, resultClass);
        return this.objectMapper.convertValue(message, responseType);
    }
}
