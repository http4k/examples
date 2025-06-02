package dev.langchain4j.store.embedding.filter.comparison

import java.math.BigDecimal

internal object NumberComparator {
    fun compareAsBigDecimals(actualNumber: Any, comparisonNumber: Any): Int {
        return BigDecimal(actualNumber.toString()).compareTo(BigDecimal(comparisonNumber.toString()))
    }

    fun containsAsBigDecimals(actualNumber: Any, comparisonNumbers: Collection<*>): Boolean {
        val actualNumberAsBigDecimal = toBigDecimal(actualNumber)
        return TODO()
//        return comparisonNumbers.stream()
//            .map { obj: NumberComparator, actualNumber: Any -> toBigDecimal(actualNumber) }
//            .anyMatch { comparisonNumberAsBigDecimal: BigDecimal ->
//                comparisonNumberAsBigDecimal.compareTo(
//                    actualNumberAsBigDecimal
//                ) == 0
//            }
    }

    private fun toBigDecimal(actualNumber: Any): BigDecimal {
        if (actualNumber is Int) {
            return BigDecimal.valueOf(actualNumber.toLong())
        } else if (actualNumber is Long) {
            return BigDecimal.valueOf(actualNumber)
        } else if (actualNumber is Float) {
            return BigDecimal.valueOf(actualNumber.toDouble())
        } else if (actualNumber is Double) {
            return BigDecimal.valueOf(actualNumber)
        }

        throw IllegalArgumentException("Unsupported type: " + actualNumber.javaClass.name)
    }
}
