package dev.langchain4j.store.embedding.filter.comparison

import dev.langchain4j.data.document.Metadata
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Objects
import java.util.UUID

class IsNotEqualTo(key: String, comparisonValue: Any) : Filter {
    private val key: String = ValidationUtils.ensureNotBlank(key, "key")
    private val comparisonValue: Any = ValidationUtils.ensureNotNull(
        comparisonValue,
        "comparisonValue with key '$key'"
    )

    fun key(): String {
        return key
    }

    fun comparisonValue(): Any {
        return comparisonValue
    }

    override fun test(`object`: Any): Boolean {
        if (`object` !is Metadata) {
            return false
        }

        if (!`object`.containsKey(key)) {
            return true
        }

        val actualValue = `object`.toMap()[key]
        TypeChecker.ensureTypesAreCompatible(actualValue!!, comparisonValue, key)

        if (actualValue is Number) {
            return NumberComparator.compareAsBigDecimals(actualValue, comparisonValue) != 0
        }

        if (comparisonValue is UUID && actualValue is String) {
            return actualValue != comparisonValue.toString()
        }

        return actualValue != comparisonValue
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is IsNotEqualTo) return false

        return this.key == o.key
                && this.comparisonValue == o.comparisonValue
    }

    override fun hashCode(): Int {
        return Objects.hash(key, comparisonValue)
    }

    override fun toString(): String {
        return "IsNotEqualTo(key=" + this.key + ", comparisonValue=" + this.comparisonValue + ")"
    }
}
