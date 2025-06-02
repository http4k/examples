package dev.langchain4j.store.embedding.filter.logical

import dev.langchain4j.data.document.Metadata
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Objects

class Not(expression: Filter) : Filter {
    private val expression: Filter =
        expression!!

    fun expression(): Filter {
        return expression
    }

    override fun test(`object`: Any): Boolean {
        if (`object` !is Metadata) {
            return false
        }
        return !expression.test(`object`)
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is Not) return false
        return this.expression == o.expression
    }

    override fun hashCode(): Int {
        return Objects.hash(expression)
    }

    override fun toString(): String {
        return "Not(expression=" + this.expression + ")"
    }
}
