package dev.langchain4j.internal

import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Collections
import java.util.HexFormat
import java.util.Objects
import java.util.UUID
import java.util.function.Supplier

/**
 * Utility methods.
 */
object Utils {
    /**
     * Returns the given value if it is not `null`, otherwise returns the given default value.
     * @param value The value to return if it is not `null`.
     * @param defaultValue The value to return if the value is `null`.
     * @return the given value if it is not `null`, otherwise returns the given default value.
     * @param <T> The type of the value.
    </T> */
    fun <T> getOrDefault(value: T?, defaultValue: T): T {
        return value ?: defaultValue
    }

    /**
     * Returns the given list if it is not `null` and not empty, otherwise returns the given default list.
     *
     * @param list        The list to return if it is not `null` and not empty.
     * @param defaultList The list to return if the list is `null` or empty.
     * @param <T>         The type of the value.
     * @return the given list if it is not `null` and not empty, otherwise returns the given default list.
    </T> */
    fun <T> getOrDefault(list: List<T>?, defaultList: List<T>): List<T> {
        return if (isNullOrEmpty(list)) defaultList else list!!
    }

    /**
     * Returns the given map if it is not `null` and not empty, otherwise returns the given default map.
     *
     * @param map        The map to return if it is not `null` and not empty.
     * @param defaultMap The map to return if the map is `null` or empty.
     * @return the given map if it is not `null` and not empty, otherwise returns the given default map.
     */
    fun <K, V> getOrDefault(map: Map<K, V>?, defaultMap: Map<K, V>): Map<K, V> {
        return if (isNullOrEmpty(map)) defaultMap else map!!
    }

    /**
     * Is the given string `null` or blank?
     * @param string The string to check.
     * @return true if the string is `null` or blank.
     */
    fun isNullOrBlank(string: String?): Boolean {
        return string == null || string.trim { it <= ' ' }.isEmpty()
    }

    /**
     * Is the given string `null` or empty ("")?
     * @param string The string to check.
     * @return true if the string is `null` or empty.
     */
    fun isNullOrEmpty(string: String?): Boolean {
        return string == null || string.isEmpty()
    }

    /**
     * Is the given string not `null` and not blank?
     * @param string The string to check.
     * @return true if there's something in the string.
     */
    fun isNotNullOrBlank(string: String?): Boolean {
        return !isNullOrBlank(string)
    }

    /**
     * Is the given string not `null` and not empty ("")?
     * @param string The string to check.
     * @return true if the given string is not `null` and not empty ("")?
     */
    fun isNotNullOrEmpty(string: String?): Boolean {
        return !isNullOrEmpty(string)
    }

    /**
     * Are all the given strings not `null` and not blank?
     * @param strings The strings to check.
     * @return `true` if every string is non-`null` and non-empty.
     */
    fun areNotNullOrBlank(vararg strings: String?): Boolean {
        if (strings == null || strings.size == 0) {
            return false
        }

        for (string in strings) {
            if (isNullOrBlank(string)) {
                return false
            }
        }

        return true
    }

    /**
     * Is the collection `null` or empty?
     * @param collection The collection to check.
     * @return `true` if the collection is `null` or [Collection.isEmpty], otherwise `false`.
     */
    fun isNullOrEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    /**
     * Is the iterable object `null` or empty?
     * @param iterable The iterable object to check.
     * @return `true` if the iterable object is `null` or there are no objects to iterate over, otherwise `false`.
     */
    fun isNullOrEmpty(iterable: Iterable<*>?): Boolean {
        return iterable == null || !iterable.iterator().hasNext()
    }

    /**
     * Is the map object `null` or empty?
     * @param map The iterable object to check.
     * @return `true` if the map object is `null` or empty map, otherwise `false`.
     */
    fun isNullOrEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }

    /**
     * Returns a string consisting of the given string repeated `times` times.
     *
     * @param string The string to repeat.
     * @param times  The number of times to repeat the string.
     * @return A string consisting of the given string repeated `times` times.
     */
    fun repeat(string: String?, times: Int): String {
        val sb = StringBuilder()
        for (i in 0..<times) {
            sb.append(string)
        }
        return sb.toString()
    }

    /**
     * Returns a random UUID.
     * @return a UUID.
     */
    fun randomUUID(): String {
        return UUID.randomUUID().toString()
    }

    private val sha256Instance: MessageDigest
        /**
         * Internal method to get a SHA-256 instance of [MessageDigest].
         * @return a [MessageDigest].
         */
        get() {
            try {
                return MessageDigest.getInstance("SHA-256")
            } catch (e: NoSuchAlgorithmException) {
                throw IllegalArgumentException(e)
            }
        }

    /**
     * Generates a UUID from a hash of the given input string.
     * @param input The input string.
     * @return A UUID.
     */
    @JvmStatic
    fun generateUUIDFrom(input: String): String {
        val hashBytes = sha256Instance.digest(input.toByteArray(StandardCharsets.UTF_8))
        val hexFormat = HexFormat.of().formatHex(hashBytes)
        return UUID.nameUUIDFromBytes(hexFormat.toByteArray(StandardCharsets.UTF_8)).toString()
    }

    /**
     * Appends a trailing '/' if the provided URL does not end with '/'
     *
     * @param url URL to check for trailing '/'
     * @return Same URL if it already ends with '/' or a new URL with '/' appended
     */
    fun ensureTrailingForwardSlash(url: String): String {
        return if (url.endsWith("/")) url else "$url/"
    }

    /**
     * Returns the given object's `toString()` surrounded by quotes.
     *
     *
     * If the given object is `null`, the string `"null"` is returned.
     *
     * @param object The object to quote.
     * @return The given object surrounded by quotes.
     */
    fun quoted(`object`: Any?): String {
        if (`object` == null) {
            return "null"
        }
        return "\"" + `object` + "\""
    }

    /**
     * Returns the first `numberOfChars` characters of the given string.
     * If the string is shorter than `numberOfChars`, the whole string is returned.
     *
     * @param string        The string to get the first characters from.
     * @param numberOfChars The number of characters to return.
     * @return The first `numberOfChars` characters of the given string.
     */
    fun firstChars(string: String?, numberOfChars: Int): String? {
        if (string == null) {
            return null
        }
        return if (string.length > numberOfChars) string.substring(0, numberOfChars) else string
    }

    /**
     * Reads the content as bytes from the given URL as a GET request for HTTP/HTTPS resources,
     * and from files stored on the local filesystem.
     *
     * @param url The URL to read from.
     * @return The content as bytes.
     * @throws RuntimeException if the request fails.
     */
    fun readBytes(url: String): ByteArray {
        try {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                // Handle URLs
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val outputStream = ByteArrayOutputStream()

                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while ((inputStream.read(buffer).also { bytesRead = it }) != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }

                    return outputStream.toByteArray()
                } else {
                    throw RuntimeException("Error while reading: $responseCode")
                }
            } else {
                // Handle files
                return Files.readAllBytes(Path.of(URI(url)))
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * Returns an (unmodifiable) copy of the provided set.
     * Returns `null` if the provided set is `null`.
     *
     * @param set The set to copy.
     * @param <T>  Generic type of the set.
     * @return The copy of the provided set.
    </T> */
    fun <T> copyIfNotNull(set: Set<T>?): Set<T>? {
        if (set == null) {
            return null
        }

        return Collections.unmodifiableSet(set)
    }

    /**
     * Returns an (unmodifiable) copy of the provided set.
     * Returns an empty set if the provided set is `null`.
     *
     * @param set The set to copy.
     * @param <T>  Generic type of the set.
     * @return The copy of the provided set or an empty set.
    </T> */
    fun <T> copy(set: Set<T>?): Set<T> {
        if (set == null) {
            return setOf()
        }

        return Collections.unmodifiableSet(set)
    }

    /**
     * Returns an (unmodifiable) copy of the provided list.
     * Returns `null` if the provided list is `null`.
     *
     * @param list The list to copy.
     * @param <T>  Generic type of the list.
     * @return The copy of the provided list.
    </T> */
    fun <T> copyIfNotNull(list: List<T>?): List<T>? {
        if (list == null) {
            return null
        }

        return Collections.unmodifiableList(list)
    }

    /**
     * Returns an (unmodifiable) copy of the provided list.
     * Returns an empty list if the provided list is `null`.
     *
     * @param list The list to copy.
     * @param <T>  Generic type of the list.
     * @return The copy of the provided list or an empty list.
    </T> */
    fun <T> copy(list: List<T>?): List<T> {
        if (list == null) {
            return listOf()
        }

        return Collections.unmodifiableList(list)
    }

    /**
     * Returns an (unmodifiable) copy of the provided map.
     * Returns `null` if the provided map is `null`.
     *
     * @param map The map to copy.
     * @return The copy of the provided map.
     */
    fun <K, V> copyIfNotNull(map: Map<K, V>?): Map<K, V>? {
        if (map == null) {
            return null
        }

        return Collections.unmodifiableMap(map)
    }

    /**
     * Returns an (unmodifiable) copy of the provided map.
     * Returns an empty map if the provided map is `null`.
     *
     * @param map The map to copy.
     * @return The copy of the provided map or an empty map.
     */
    fun <K, V> copy(map: Map<K, V>?): Map<K, V> {
        if (map == null) {
            return java.util.Map.of()
        }

        return Collections.unmodifiableMap(map)
    }

    fun toStringValueMap(map: Map<String, Any?>?): Map<String, String>? {
        if (map == null) {
            return null
        }

        val stringValueMap: MutableMap<String, String> = HashMap()
        for (key in map.keys) {
            val value = map[key]
            val stringValue = Objects.toString(value, null)
            stringValueMap[key] = stringValue
        }
        return stringValueMap
    }
}
