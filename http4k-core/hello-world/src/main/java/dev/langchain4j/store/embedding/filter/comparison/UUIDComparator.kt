package dev.langchain4j.store.embedding.filter.comparison

import java.util.UUID

internal object UUIDComparator {
    fun containsAsUUID(actualUUID: Any, comparisonUUIDs: Collection<*>): Boolean {
        val uuid = toUUID(actualUUID)
        return TODO()
//        return comparisonUUIDs.stream()
//            .map { obj: UUIDComparator, actualUUID: Any -> toUUID(actualUUID) }
//            .anyMatch { comparisonUUID: UUID -> comparisonUUID.compareTo(uuid) == 0 }
    }

    private fun toUUID(actualUUID: Any): UUID {
        if (actualUUID is String) {
            return UUID.fromString(actualUUID.toString())
        } else if (actualUUID is UUID) {
            return actualUUID
        }

        throw IllegalArgumentException("Unsupported type: " + actualUUID.javaClass.name)
    }
}
