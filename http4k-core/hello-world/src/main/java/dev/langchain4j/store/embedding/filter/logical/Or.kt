package dev.langchain4j.store.embedding.filter.logical

import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Objects

class Or(left: Filter, right: Filter) :
    Filter {
    private val left: Filter =
        ValidationUtils.ensureNotNull(
            left,
            "left"
        )
    private val right: Filter =
        ValidationUtils.ensureNotNull(
            right,
            "right"
        )

    fun left(): Filter {
        return left
    }

    fun right(): Filter {
        return right
    }

    override fun test(`object`: Any): Boolean {
        return left().test(`object`) || right().test(`object`)
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is Or) return false
        return this.left == o.left && this.right == o.right
    }

    override fun hashCode(): Int {
        return Objects.hash(left, right)
    }

    override fun toString(): String {
        return "Or(left=" + this.left + ", right=" + this.right + ")"
    }
}
