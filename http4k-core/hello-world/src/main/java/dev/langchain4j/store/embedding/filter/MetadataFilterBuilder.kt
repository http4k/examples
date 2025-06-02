package dev.langchain4j.store.embedding.filter

import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.store.embedding.filter.comparison.ContainsString
import dev.langchain4j.store.embedding.filter.comparison.IsEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsGreaterThan
import dev.langchain4j.store.embedding.filter.comparison.IsGreaterThanOrEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsIn
import dev.langchain4j.store.embedding.filter.comparison.IsLessThan
import dev.langchain4j.store.embedding.filter.comparison.IsLessThanOrEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsNotEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsNotIn
import java.util.Arrays
import java.util.UUID
import java.util.stream.Collectors

/**
 * A helper class for building a [Filter] for [Metadata] key.
 */
class MetadataFilterBuilder(key: String?) {
    private val key: String = ValidationUtils.ensureNotBlank(key, "key")

    // containsString
    fun containsString(value: String): Filter {
        return ContainsString(key, value)
    }

    // isEqualTo
    fun isEqualTo(value: String): Filter {
        return IsEqualTo(key, value)
    }

    fun isEqualTo(value: UUID): Filter {
        return IsEqualTo(key, value)
    }

    fun isEqualTo(value: Int): Filter {
        return IsEqualTo(key, value)
    }

    fun isEqualTo(value: Long): Filter {
        return IsEqualTo(key, value)
    }

    fun isEqualTo(value: Float): Filter {
        return IsEqualTo(key, value)
    }

    fun isEqualTo(value: Double): Filter {
        return IsEqualTo(key, value)
    }

    // isNotEqualTo
    fun isNotEqualTo(value: String): Filter {
        return IsNotEqualTo(key, value)
    }

    fun isNotEqualTo(value: UUID): Filter {
        return IsNotEqualTo(key, value)
    }

    fun isNotEqualTo(value: Int): Filter {
        return IsNotEqualTo(key, value)
    }

    fun isNotEqualTo(value: Long): Filter {
        return IsNotEqualTo(key, value)
    }

    fun isNotEqualTo(value: Float): Filter {
        return IsNotEqualTo(key, value)
    }

    fun isNotEqualTo(value: Double): Filter {
        return IsNotEqualTo(key, value)
    }

    // isGreaterThan
    fun isGreaterThan(value: String): Filter {
        return IsGreaterThan(key, value)
    }

    fun isGreaterThan(value: Int): Filter {
        return IsGreaterThan(key, value)
    }

    fun isGreaterThan(value: Long): Filter {
        return IsGreaterThan(key, value)
    }

    fun isGreaterThan(value: Float): Filter {
        return IsGreaterThan(key, value)
    }

    fun isGreaterThan(value: Double): Filter {
        return IsGreaterThan(key, value)
    }

    // isGreaterThanOrEqualTo
    fun isGreaterThanOrEqualTo(value: String): Filter {
        return IsGreaterThanOrEqualTo(key, value)
    }

    fun isGreaterThanOrEqualTo(value: Int): Filter {
        return IsGreaterThanOrEqualTo(key, value)
    }

    fun isGreaterThanOrEqualTo(value: Long): Filter {
        return IsGreaterThanOrEqualTo(key, value)
    }

    fun isGreaterThanOrEqualTo(value: Float): Filter {
        return IsGreaterThanOrEqualTo(key, value)
    }

    fun isGreaterThanOrEqualTo(value: Double): Filter {
        return IsGreaterThanOrEqualTo(key, value)
    }

    // isLessThan
    fun isLessThan(value: String): Filter {
        return IsLessThan(key, value)
    }

    fun isLessThan(value: Int): Filter {
        return IsLessThan(key, value)
    }

    fun isLessThan(value: Long): Filter {
        return IsLessThan(key, value)
    }

    fun isLessThan(value: Float): Filter {
        return IsLessThan(key, value)
    }

    fun isLessThan(value: Double): Filter {
        return IsLessThan(key, value)
    }

    // isLessThanOrEqualTo
    fun isLessThanOrEqualTo(value: String): Filter {
        return IsLessThanOrEqualTo(key, value)
    }

    fun isLessThanOrEqualTo(value: Int): Filter {
        return IsLessThanOrEqualTo(key, value)
    }

    fun isLessThanOrEqualTo(value: Long): Filter {
        return IsLessThanOrEqualTo(key, value)
    }

    fun isLessThanOrEqualTo(value: Float): Filter {
        return IsLessThanOrEqualTo(key, value)
    }

    fun isLessThanOrEqualTo(value: Double): Filter {
        return IsLessThanOrEqualTo(key, value)
    }

    // isBetween
    fun isBetween(fromValue: String, toValue: String): Filter {
        return isGreaterThanOrEqualTo(fromValue).and(isLessThanOrEqualTo(toValue))
    }

    fun isBetween(fromValue: Int, toValue: Int): Filter {
        return isGreaterThanOrEqualTo(fromValue).and(isLessThanOrEqualTo(toValue))
    }

    fun isBetween(fromValue: Long, toValue: Long): Filter {
        return isGreaterThanOrEqualTo(fromValue).and(isLessThanOrEqualTo(toValue))
    }

    fun isBetween(fromValue: Float, toValue: Float): Filter {
        return isGreaterThanOrEqualTo(fromValue).and(isLessThanOrEqualTo(toValue))
    }

    fun isBetween(fromValue: Double, toValue: Double): Filter {
        return isGreaterThanOrEqualTo(fromValue).and(isLessThanOrEqualTo(toValue))
    }

    // isIn
    fun isIn(vararg values: String?): Filter {
        return IsIn(key, Arrays.asList(*values))
    }

    fun isIn(vararg values: UUID?): Filter {
        return IsIn(key, Arrays.asList(*values))
    }

    fun isIn(vararg values: Int): Filter {
        return IsIn(key, Arrays.stream(values).boxed().collect(Collectors.toList()))
    }

    fun isIn(vararg values: Long): Filter {
        return IsIn(key, Arrays.stream(values).boxed().collect(Collectors.toList()))
    }

    fun isIn(vararg values: Float): Filter {
        val valuesList: MutableList<Float> = ArrayList()
        for (value in values) {
            valuesList.add(value)
        }
        return IsIn(key, valuesList)
    }

    fun isIn(vararg values: Double): Filter {
        return IsIn(key, Arrays.stream(values).boxed().collect(Collectors.toList()))
    }

    fun isIn(values: Collection<*>): Filter {
        return IsIn(key, values)
    }

    // isNotIn
    fun isNotIn(vararg values: String?): Filter {
        return IsNotIn(key, Arrays.asList(*values))
    }

    fun isNotIn(vararg values: UUID?): Filter {
        return IsNotIn(key, Arrays.asList(*values))
    }

    fun isNotIn(vararg values: Int): Filter {
        return IsNotIn(key, Arrays.stream(values).boxed().collect(Collectors.toList()))
    }

    fun isNotIn(vararg values: Long): Filter {
        return IsNotIn(key, Arrays.stream(values).boxed().collect(Collectors.toList()))
    }

    fun isNotIn(vararg values: Float): Filter {
        val valuesList: MutableList<Float> = ArrayList()
        for (value in values) {
            valuesList.add(value)
        }
        return IsNotIn(key, valuesList)
    }

    fun isNotIn(vararg values: Double): Filter {
        return IsNotIn(key, Arrays.stream(values).boxed().collect(Collectors.toList()))
    }

    fun isNotIn(values: Collection<*>): Filter {
        return IsNotIn(key, values)
    }

    companion object {
        fun metadataKey(key: String?): MetadataFilterBuilder {
            return MetadataFilterBuilder(key)
        }
    }
}
