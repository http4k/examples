package dev.langchain4j.model.moderation

import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Represents moderation status.
 */
class Moderation {
    private val flagged: Boolean
    private val flaggedText: String?

    /**
     * Construct a Moderation object that is not flagged.
     */
    constructor() {
        this.flagged = false
        this.flaggedText = null
    }

    /**
     * Construct a Moderation object that is flagged.
     *
     * @param flaggedText the text that was flagged.
     */
    constructor(flaggedText: String?) {
        this.flagged = true
        this.flaggedText = flaggedText
    }

    /**
     * Returns true if the text was flagged.
     * @return true if the text was flagged, false otherwise.
     */
    fun flagged(): Boolean {
        return flagged
    }

    /**
     * Returns the text that was flagged.
     * @return the text that was flagged, or null if the text was not flagged.
     */
    fun flaggedText(): String? {
        return flaggedText
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Moderation
        return this.flagged == that.flagged
                && this.flaggedText == that.flaggedText
    }

    override fun hashCode(): Int {
        return Objects.hash(flagged, flaggedText)
    }

    override fun toString(): String {
        return "Moderation {" +
                " flagged = " + flagged +
                ", flaggedText = " + Utils.quoted(flaggedText) +
                " }"
    }

    companion object {
        /**
         * Constructs a Moderation object that is flagged.
         * @param flaggedText the text that was flagged.
         * @return a Moderation object.
         */
        fun flagged(flaggedText: String?): Moderation {
            return Moderation(flaggedText)
        }

        /**
         * Constructs a Moderation object that is not flagged.
         * @return a Moderation object.
         */
        fun notFlagged(): Moderation {
            return Moderation()
        }
    }
}
