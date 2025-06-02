package dev.langchain4j.store.embedding.filter.comparison

import dev.langchain4j.internal.Exceptions
import java.util.UUID

internal object TypeChecker {
    fun ensureTypesAreCompatible(actualValue: Any, comparisonValue: Any, key: String?) {
        if (actualValue is Number && comparisonValue is Number) {
            return
        }

        if (actualValue is String && comparisonValue is UUID) {
            return
        }

        if (actualValue.javaClass != comparisonValue.javaClass) {
            throw Exceptions.illegalArgument(
                "Type mismatch: actual value of metadata key \"%s\" (%s) has type %s, " +
                        "while comparison value (%s) has type %s",
                key,
                actualValue,
                actualValue.javaClass.name,
                comparisonValue,
                comparisonValue.javaClass.name
            )
        }
    }
}
