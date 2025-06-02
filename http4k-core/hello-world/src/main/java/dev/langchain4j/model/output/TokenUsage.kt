package dev.langchain4j.model.output

import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Represents the token usage of a response.
 */
class TokenUsage
/**
 * Creates a new [TokenUsage] instance with all fields set to null.
 */ @JvmOverloads constructor(
    private val inputTokenCount: Int? = null,
    private val outputTokenCount: Int? = null,
    private val totalTokenCount: Int? = sum(
        inputTokenCount,
        outputTokenCount
    )
) {
    /**
     * Creates a new [TokenUsage] instance with the given input, output and total token counts.
     *
     * @param inputTokenCount  The input token count, or null if unknown.
     * @param outputTokenCount The output token count, or null if unknown.
     * @param totalTokenCount  The total token count, or null if unknown.
     */
    /**
     * Creates a new [TokenUsage] instance with the given input and output token counts.
     *
     * @param inputTokenCount  The input token count, or null if unknown.
     * @param outputTokenCount The output token count, or null if unknown.
     */
    /**
     * Creates a new [TokenUsage] instance with the given input token count.
     *
     * @param inputTokenCount The input token count.
     */

    /**
     * Returns the input token count, or null if unknown.
     *
     * @return the input token count, or null if unknown.
     */
    fun inputTokenCount(): Int? {
        return inputTokenCount
    }

    /**
     * Returns the output token count, or null if unknown.
     *
     * @return the output token count, or null if unknown.
     */
    fun outputTokenCount(): Int? {
        return outputTokenCount
    }

    /**
     * Returns the total token count, or null if unknown.
     *
     * @return the total token count, or null if unknown.
     */
    fun totalTokenCount(): Int? {
        return totalTokenCount
    }

    /**
     * Adds the token usage of two responses together.
     *
     *
     * Fields which are null in both responses will be null in the result.
     *
     * @param that The token usage to add to this one.
     * @return a new [TokenUsage] instance with the token usage of both responses added together.
     */
    fun add(that: TokenUsage?): TokenUsage {
        if (that == null) {
            return this
        }

        if (that.javaClass != TokenUsage::class.java) {
            // when adding TokenUsage ("this") and one of TokenUsage's subclasses ("that"),
            // we want to call "add" on the subclass to preserve extra information present in the subclass
            return that.add(this)
        }

        return TokenUsage(
            sum(this.inputTokenCount, that.inputTokenCount),
            sum(this.outputTokenCount, that.outputTokenCount),
            sum(this.totalTokenCount, that.totalTokenCount)
        )
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as TokenUsage
        return this.inputTokenCount == that.inputTokenCount
                && this.outputTokenCount == that.outputTokenCount
                && this.totalTokenCount == that.totalTokenCount
    }

    override fun hashCode(): Int {
        return Objects.hash(inputTokenCount, outputTokenCount, totalTokenCount)
    }

    override fun toString(): String {
        return "TokenUsage {" +
                " inputTokenCount = " + inputTokenCount +
                ", outputTokenCount = " + outputTokenCount +
                ", totalTokenCount = " + totalTokenCount +
                " }"
    }

    companion object {
        /**
         * Adds two token usages.
         * <br></br>
         * If one of the token usages is null, the other is returned without changes.
         * <br></br>
         * Fields which are null in both responses will be null in the result.
         *
         * @param first  The first token usage to add.
         * @param second The second token usage to add.
         * @return a new [TokenUsage] instance with the sum of token usages.
         */
        fun sum(first: TokenUsage?, second: TokenUsage?): TokenUsage? {
            return if (first == null) {
                second
            } else if (second == null) {
                first
            } else {
                first.add(second)
            }
        }

        /**
         * Sum two integers, returning null if both are null.
         *
         * @param first  The first integer, or null.
         * @param second The second integer, or null.
         * @return the sum of the two integers, or null if both are null.
         */
        protected fun sum(first: Int?, second: Int?): Int? {
            if (first == null && second == null) {
                return null
            }

            return first!! + second!!
        }
    }
}
