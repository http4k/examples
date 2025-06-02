package dev.langchain4j.data.document

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.internal.Exceptions
import java.util.Objects
import java.util.UUID

/**
 * Represents metadata of a [Document] or a [TextSegment].
 * <br></br>
 * For a [Document], the metadata could store information such as the source, creation date,
 * owner, or any other relevant details.
 * <br></br>
 * For a [TextSegment], in addition to metadata inherited from a [Document], it can also include
 * segment-specific information, such as the page number, the position of the segment within the document, chapter, etc.
 * <br></br>
 * The metadata is stored as a key-value map, where the key is a [String] and the value can be one of:
 * [String], [UUID], [Integer], [Long], [Float], [Double].
 * If you require additional types, please [open an issue](https://github.com/langchain4j/langchain4j/issues/new/choose).
 * <br></br>
 * `null` values are not permitted.
 */
class Metadata {
    private val metadata: MutableMap<String?, Any?>

    /**
     * Construct a Metadata object with an empty map of key-value pairs.
     */
    constructor() {
        this.metadata = HashMap()
    }

    /**
     * Constructs a Metadata object from a map of key-value pairs.
     *
     * @param metadata the map of key-value pairs; must not be `null`. `null` values are not permitted.
     * Supported value types: [String], [Integer], [Long], [Float], [Double]
     */
    constructor(metadata: Map<String?, *>) {
        validate(metadata)
        this.metadata = HashMap(metadata)
    }

    /**
     * Returns the `String` value associated with the given key.
     *
     * @param key the key
     * @return the `String` value associated with the given key, or `null` if the key is not present.
     * @throws RuntimeException if the value is not of type String
     */
    fun getString(key: String?): String? {
        if (!containsKey(key)) {
            return null
        }

        val value = metadata[key]
        if (value is String) {
            return value
        }

        throw Exceptions.runtime(
            "Metadata entry with the key '%s' has a value of '%s' and type '%s'. "
                    + "It cannot be returned as a String.",
            key, value, value!!.javaClass.name
        )
    }

    /**
     * Returns the `UUID` value associated with the given key.
     *
     * @param key the key
     * @return the `UUID` value associated with the given key, or `null` if the key is not present.
     * @throws RuntimeException if the value is not of type String
     */
    fun getUUID(key: String?): UUID? {
        if (!containsKey(key)) {
            return null
        }

        val value = metadata[key]
        if (value is UUID) {
            return value
        }
        if (value is String) {
            return UUID.fromString(value)
        }

        throw Exceptions.runtime(
            "Metadata entry with the key '%s' has a value of '%s' and type '%s'. "
                    + "It cannot be returned as a UUID.",
            key, value, value!!.javaClass.name
        )
    }

    /**
     * Returns the `Integer` value associated with the given key.
     * <br></br>
     * Some [EmbeddingStore] implementations (still) store `Metadata` values as `String`s.
     * In this case, the `String` value will be parsed into an `Integer` when this method is called.
     * <br></br>
     * Some [EmbeddingStore] implementations store `Metadata` key-value pairs as JSON.
     * In this case, type information is lost when serializing to JSON and then deserializing back from JSON.
     * JSON libraries can, for example, serialize an `Integer` and then deserialize it as a `Long`.
     * Or serialize a `Float` and then deserialize it as a `Double`, and so on.
     * In such cases, the actual value will be cast to an `Integer` when this method is called.
     *
     * @param key the key
     * @return the [Integer] value associated with the given key, or `null` if the key is not present.
     * @throws RuntimeException if the value is not [Number]
     */
    fun getInteger(key: String?): Int? {
        if (!containsKey(key)) {
            return null
        }

        val value = metadata[key]
        if (value is String) {
            return value.toString().toInt()
        } else if (value is Number) {
            return value.toInt()
        }

        throw Exceptions.runtime(
            "Metadata entry with the key '%s' has a value of '%s' and type '%s'. "
                    + "It cannot be returned as an Integer.",
            key, value, value!!.javaClass.name
        )
    }

    /**
     * Returns the `Long` value associated with the given key.
     * <br></br>
     * Some [EmbeddingStore] implementations (still) store `Metadata` values as `String`s.
     * In this case, the `String` value will be parsed into an `Long` when this method is called.
     * <br></br>
     * Some [EmbeddingStore] implementations store `Metadata` key-value pairs as JSON.
     * In this case, type information is lost when serializing to JSON and then deserializing back from JSON.
     * JSON libraries can, for example, serialize an `Integer` and then deserialize it as a `Long`.
     * Or serialize a `Float` and then deserialize it as a `Double`, and so on.
     * In such cases, the actual value will be cast to a `Long` when this method is called.
     *
     * @param key the key
     * @return the `Long` value associated with the given key, or `null` if the key is not present.
     * @throws RuntimeException if the value is not [Number]
     */
    fun getLong(key: String?): Long? {
        if (!containsKey(key)) {
            return null
        }

        val value = metadata[key]
        if (value is String) {
            return value.toString().toLong()
        } else if (value is Number) {
            return value.toLong()
        }

        throw Exceptions.runtime(
            "Metadata entry with the key '%s' has a value of '%s' and type '%s'. "
                    + "It cannot be returned as a Long.",
            key, value, value!!.javaClass.name
        )
    }

    /**
     * Returns the `Float` value associated with the given key.
     * <br></br>
     * Some [EmbeddingStore] implementations (still) store `Metadata` values as `String`s.
     * In this case, the `String` value will be parsed into a `Float` when this method is called.
     * <br></br>
     * Some [EmbeddingStore] implementations store `Metadata` key-value pairs as JSON.
     * In this case, type information is lost when serializing to JSON and then deserializing back from JSON.
     * JSON libraries can, for example, serialize an `Integer` and then deserialize it as a `Long`.
     * Or serialize a `Float` and then deserialize it as a `Double`, and so on.
     * In such cases, the actual value will be cast to a `Float` when this method is called.
     *
     * @param key the key
     * @return the `Float` value associated with the given key, or `null` if the key is not present.
     * @throws RuntimeException if the value is not [Number]
     */
    fun getFloat(key: String?): Float? {
        if (!containsKey(key)) {
            return null
        }

        val value = metadata[key]
        if (value is String) {
            return value.toFloat()
        } else if (value is Number) {
            return value.toFloat()
        }

        throw Exceptions.runtime(
            "Metadata entry with the key '%s' has a value of '%s' and type '%s'. "
                    + "It cannot be returned as a Float.",
            key, value, value!!.javaClass.name
        )
    }

    /**
     * Returns the `Double` value associated with the given key.
     * <br></br>
     * Some [EmbeddingStore] implementations (still) store `Metadata` values as `String`s.
     * In this case, the `String` value will be parsed into a `Double` when this method is called.
     * <br></br>
     * Some [EmbeddingStore] implementations store `Metadata` key-value pairs as JSON.
     * In this case, type information is lost when serializing to JSON and then deserializing back from JSON.
     * JSON libraries can, for example, serialize an `Integer` and then deserialize it as a `Long`.
     * Or serialize a `Float` and then deserialize it as a `Double`, and so on.
     * In such cases, the actual value will be cast to a `Double` when this method is called.
     *
     * @param key the key
     * @return the `Double` value associated with the given key, or `null` if the key is not present.
     * @throws RuntimeException if the value is not [Number]
     */
    fun getDouble(key: String?): Double? {
        if (!containsKey(key)) {
            return null
        }

        val value = metadata[key]
        if (value is String) {
            return value.toString().toDouble()
        } else if (value is Number) {
            return value.toDouble()
        }

        throw Exceptions.runtime(
            "Metadata entry with the key '%s' has a value of '%s' and type '%s'. "
                    + "It cannot be returned as a Double.",
            key, value, value!!.javaClass.name
        )
    }

    /**
     * Check whether this `Metadata` contains a given key.
     *
     * @param key the key
     * @return `true` if this metadata contains a given key; `false` otherwise.
     */
    fun containsKey(key: String?): Boolean {
        return metadata.containsKey(key)
    }

    /**
     * Adds a key-value pair to the metadata.
     *
     * @param key   the key
     * @param value the value
     * @return `this`
     */
    fun put(key: String, value: String): Metadata {
        validate(key, value)
        metadata[key] = value
        return this
    }

    /**
     * Adds a key-value pair to the metadata.
     *
     * @param key   the key
     * @param value the value
     * @return `this`
     */
    fun put(key: String, value: UUID): Metadata {
        validate(key, value)
        metadata[key] = value
        return this
    }

    /**
     * Adds a key-value pair to the metadata.
     *
     * @param key   the key
     * @param value the value
     * @return `this`
     */
    fun put(key: String, value: Int): Metadata {
        validate(key, value)
        metadata[key] = value
        return this
    }

    /**
     * Adds a key-value pair to the metadata.
     *
     * @param key   the key
     * @param value the value
     * @return `this`
     */
    fun put(key: String, value: Long): Metadata {
        validate(key, value)
        metadata[key] = value
        return this
    }

    /**
     * Adds a key-value pair to the metadata.
     *
     * @param key   the key
     * @param value the value
     * @return `this`
     */
    fun put(key: String, value: Float): Metadata {
        validate(key, value)
        metadata[key] = value
        return this
    }

    /**
     * Adds a key-value pair to the metadata.
     *
     * @param key   the key
     * @param value the value
     * @return `this`
     */
    fun put(key: String, value: Double): Metadata {
        validate(key, value)
        metadata[key] = value
        return this
    }

    fun putAll(metadata: Map<String?, Any?>): Metadata {
        validate(metadata)
        this.metadata.putAll(metadata)
        return this
    }

    /**
     * Removes the given key from the metadata.
     *
     * @param key the key
     * @return `this`
     */
    fun remove(key: String?): Metadata {
        metadata.remove(key)
        return this
    }

    /**
     * Copies the metadata.
     *
     * @return a copy of this Metadata object.
     */
    fun copy(): Metadata {
        return Metadata(metadata)
    }

    /**
     * Get a copy of the metadata as a map of key-value pairs.
     *
     * @return the metadata as a map of key-value pairs.
     */
    fun toMap(): Map<String?, Any?> {
        return HashMap(metadata)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Metadata
        return this.metadata == that.metadata
    }

    override fun hashCode(): Int {
        return Objects.hash(metadata)
    }

    override fun toString(): String {
        return "Metadata { metadata = $metadata }"
    }

    /**
     * Merges the current Metadata object with another Metadata object.
     * The two Metadata objects must not have any common keys.
     *
     * @param another The Metadata object to be merged with the current Metadata object.
     * @return A new Metadata object that contains all key-value pairs from both Metadata objects.
     * @throws IllegalArgumentException if there are common keys between the two Metadata objects.
     */
    fun merge(another: Metadata?): Metadata {
        if (another == null || another.metadata.isEmpty()) {
            return this.copy()
        }
        val thisMap = this.toMap()
        val anotherMap = another.toMap()
        val commonKeys = HashSet(thisMap.keys)
        commonKeys.retainAll(anotherMap.keys)
        if (!commonKeys.isEmpty()) {
            throw Exceptions.illegalArgument("Metadata keys are not unique. Common keys: %s", commonKeys)
        }
        val mergedMap = HashMap(thisMap)
        mergedMap.putAll(anotherMap)
        return from(mergedMap)
    }

    companion object {
        private val SUPPORTED_VALUE_TYPES: MutableSet<Class<*>?> = LinkedHashSet()

        init {
            SUPPORTED_VALUE_TYPES.add(String::class.java)

            SUPPORTED_VALUE_TYPES.add(UUID::class.java)

            SUPPORTED_VALUE_TYPES.add(Int::class.javaPrimitiveType)
            SUPPORTED_VALUE_TYPES.add(Int::class.java)

            SUPPORTED_VALUE_TYPES.add(Long::class.javaPrimitiveType)
            SUPPORTED_VALUE_TYPES.add(Long::class.java)

            SUPPORTED_VALUE_TYPES.add(Float::class.javaPrimitiveType)
            SUPPORTED_VALUE_TYPES.add(Float::class.java)

            SUPPORTED_VALUE_TYPES.add(Double::class.javaPrimitiveType)
            SUPPORTED_VALUE_TYPES.add(Double::class.java)
        }

        private fun validate(metadata: Map<String?, *>) {
            metadata!!.forEach { (key: String?, value: Any?) ->
                validate(
                    key!!,
                    value!!
                )
                if (!SUPPORTED_VALUE_TYPES.contains(value.javaClass)) {
                    throw Exceptions.illegalArgument(
                        "The metadata key '%s' has the value '%s', which is of the unsupported type '%s'. "
                            + "Currently, the supported types are: %s",
                        key, value, value.javaClass.name, SUPPORTED_VALUE_TYPES
                    )
                }
            }
        }

        private fun validate(key: String, value: Any) {
        }

        /**
         * Constructs a Metadata object from a single key-value pair.
         *
         * @param key   the key
         * @param value the value
         * @return a Metadata object
         */
        fun from(key: String, value: String): Metadata {
            return Metadata().put(key, value)
        }

        /**
         * Constructs a Metadata object from a map of key-value pairs.
         *
         * @param metadata the map of key-value pairs
         * @return a Metadata object
         */
        fun from(metadata: Map<String?, *>): Metadata {
            return Metadata(metadata)
        }

        /**
         * Constructs a Metadata object from a single key-value pair.
         *
         * @param key   the key
         * @param value the value
         * @return a Metadata object
         */
        fun metadata(key: String, value: String): Metadata {
            return from(key, value)
        }
    }
}
