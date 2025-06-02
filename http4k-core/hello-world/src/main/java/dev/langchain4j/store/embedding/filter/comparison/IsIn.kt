package dev.langchain4j.store.embedding.filter.comparison

import dev.langchain4j.data.document.Metadata
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Collections
import java.util.Objects
import java.util.UUID

class IsIn(key: String, comparisonValues: Collection<*>) : Filter {
    private val key: String = key!!
    private val comparisonValues: Collection<*>

    init {
        val copy: Set<*> = HashSet(
            comparisonValues!!
        )
        comparisonValues.forEach { value: Any? ->
        }
        this.comparisonValues = Collections.unmodifiableSet(copy)
    }

    fun key(): String {
        return key
    }

    fun comparisonValues(): Collection<*> {
        return comparisonValues
    }

    override fun test(`object`: Any): Boolean {
        if (`object` !is Metadata) {
            return false
        }

        if (!`object`.containsKey(key)) {
            return false
        }

        val actualValue = `object`.toMap()[key]
        TypeChecker.ensureTypesAreCompatible(
            actualValue!!,
            comparisonValues.iterator().next()!!, key
        )

        if (comparisonValues.iterator().next() is Number) {
            return NumberComparator.containsAsBigDecimals(actualValue, comparisonValues)
        }
        if (comparisonValues.iterator().next() is UUID) {
            return UUIDComparator.containsAsUUID(actualValue, comparisonValues)
        }

        return comparisonValues.contains(actualValue)
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is IsIn) return false

        return this.key == o.key
                && this.comparisonValues == o.comparisonValues
    }

    override fun hashCode(): Int {
        return Objects.hash(key, comparisonValues)
    }


    override fun toString(): String {
        return "IsIn(key=" + this.key + ", comparisonValues=" + this.comparisonValues + ")"
    }
}
