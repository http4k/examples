package dev.langchain4j.store.embedding.filter.comparison

import dev.langchain4j.data.document.Metadata
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Objects

class IsGreaterThanOrEqualTo(key: String, comparisonValue: Comparable<*>?) : Filter {
    private val key: String = key!!
    private val comparisonValue = comparisonValue!!!!

    fun key(): String {
        return key
    }

    fun comparisonValue(): Comparable<*> {
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
        TypeChecker.ensureTypesAreCompatible(actualValue!!, comparisonValue, key)

        if (actualValue is Number) {
            return NumberComparator.compareAsBigDecimals(actualValue, comparisonValue) >= 0
        }

        return TODO()
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is IsGreaterThanOrEqualTo) return false

        return this.key == o.key
                && this.comparisonValue == o.comparisonValue
    }

    override fun hashCode(): Int {
        return Objects.hash(key, comparisonValue)
    }

    override fun toString(): String {
        return "IsGreaterThanOrEqualTo(key=" + this.key + ", comparisonValue=" + this.comparisonValue + ")"
    }
}
