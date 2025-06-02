package dev.langchain4j.store.embedding.filter

import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.filter.comparison.IsEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsGreaterThan
import dev.langchain4j.store.embedding.filter.comparison.IsGreaterThanOrEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsLessThan
import dev.langchain4j.store.embedding.filter.comparison.IsLessThanOrEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsNotEqualTo
import dev.langchain4j.store.embedding.filter.comparison.IsNotIn
import dev.langchain4j.store.embedding.filter.logical.And
import dev.langchain4j.store.embedding.filter.logical.Not
import dev.langchain4j.store.embedding.filter.logical.Or

/**
 * This class represents a filter that can be applied during search in an [EmbeddingStore].
 * <br></br>
 * Many [EmbeddingStore]s support a feature called metadata filtering. A `Filter` can be used for this.
 * <br></br>
 * A `Filter` object can represent simple (e.g. `type = 'documentation'`)
 * and composite (e.g. `type = 'documentation' AND year > 2020`) filter expressions in
 * an [EmbeddingStore]-agnostic way.
 * <br></br>
 * Each [EmbeddingStore] implementation that supports metadata filtering is mapping [Filter]
 * into it's native filter expression.
 *
 * @see IsEqualTo
 *
 * @see IsNotEqualTo
 *
 * @see IsGreaterThan
 *
 * @see IsGreaterThanOrEqualTo
 *
 * @see IsLessThan
 *
 * @see IsLessThanOrEqualTo
 *
 * @see IsIn
 *
 * @see IsNotIn
 *
 * @see ContainsString
 *
 * @see And
 *
 * @see Not
 *
 * @see Or
 */
interface Filter {
    /**
     * Tests if a given object satisfies this [Filter].
     *
     * @param object An object to test.
     * @return `true` if a given object satisfies this [Filter], `false` otherwise.
     */
    fun test(`object`: Any): Boolean

    fun and(filter: Filter): Filter {
        return and(this, filter)
    }

    fun or(filter: Filter): Filter {
        return or(this, filter)
    }

    companion object {
        fun and(left: Filter, right: Filter): Filter {
            return And(left, right)
        }

        fun or(left: Filter, right: Filter): Filter {
            return Or(left, right)
        }

        fun not(expression: Filter): Filter {
            return Not(expression)
        }
    }
}
