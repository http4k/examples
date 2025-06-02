package dev.langchain4j.store.embedding.filter.comparison

import dev.langchain4j.data.document.Metadata
import dev.langchain4j.internal.Exceptions
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Objects

/**
 * A filter that checks if the value of a metadata key contains a specific string.
 * The value of the metadata key must be a string.
 */
class ContainsString(key: String, comparisonValue: String) : Filter {
    private val key: String = ValidationUtils.ensureNotBlank(key, "key")
    private val comparisonValue: String = ValidationUtils.ensureNotNull(
        comparisonValue,
        "comparisonValue with key '$key'"
    )

    fun key(): String {
        return key
    }

    fun comparisonValue(): String {
        return comparisonValue
    }

    override fun test(`object`: Any): Boolean {
        if (`object` !is Metadata) {
            return false
        }

        if (!`object`.containsKey(key)) {
            return false
        }

        val actualValue = `object`.toMap()[key]

        if (actualValue is String) {
            return actualValue.contains(comparisonValue)
        }

        throw Exceptions.illegalArgument(
            "Type mismatch: actual value of metadata key \"%s\" (%s) has type %s, "
                    + "while it is expected to be a string",
            key, actualValue, actualValue!!.javaClass.name
        )
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is ContainsString) return false

        return this.key == o.key && this.comparisonValue == o.comparisonValue
    }

    override fun hashCode(): Int {
        return Objects.hash(key, comparisonValue)
    }

    override fun toString(): String {
        return "ContainsString(key=" + this.key + ", comparisonValue=" + this.comparisonValue + ")"
    }
}
