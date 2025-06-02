package dev.langchain4j.agent.tool

import dev.langchain4j.agent.tool.ToolExecutionRequest
import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Represents an LLM-generated request to execute a tool.
 */
class ToolExecutionRequest private constructor(builder: Builder) {
    private val id: String?
    private val name: String?
    private val arguments: String?

    /**
     * Creates a [ToolExecutionRequest] from a [Builder].
     * @param builder the builder.
     */
    init {
        this.id = builder.id
        this.name = builder.name
        this.arguments = builder.arguments
    }

    /**
     * Returns the id of the tool.
     * @return the id of the tool.
     */
    fun id(): String? {
        return id
    }

    /**
     * Returns the name of the tool.
     * @return the name of the tool.
     */
    fun name(): String? {
        return name
    }

    /**
     * Returns the arguments of the tool.
     * @return the arguments of the tool.
     */
    fun arguments(): String? {
        return arguments
    }

    override fun equals(another: Any?): Boolean {
        if (this === another) return true
        return another is ToolExecutionRequest
                && equalTo(another)
    }

    private fun equalTo(another: ToolExecutionRequest): Boolean {
        return id == another.id
                && name == another.name
                && arguments == another.arguments
    }

    override fun hashCode(): Int {
        var h = 5381
        h += (h shl 5) + Objects.hashCode(id)
        h += (h shl 5) + Objects.hashCode(name)
        h += (h shl 5) + Objects.hashCode(arguments)
        return h
    }

    override fun toString(): String {
        return ("ToolExecutionRequest {"
                + " id = " + Utils.quoted(id)
                + ", name = " + Utils.quoted(name)
                + ", arguments = " + Utils.quoted(arguments)
                + " }")
    }

    /**
     * `ToolExecutionRequest` builder static inner class.
     */
    class Builder
    /**
     * Creates a builder for `ToolExecutionRequest`.
     */
    {
        var id: String? = null
        var name: String? = null
        var arguments: String? = null

        /**
         * Sets the `id`.
         * @param id the `id`
         * @return the `Builder`
         */
        fun id(id: String?): Builder {
            this.id = id
            return this
        }

        /**
         * Sets the `name`.
         * @param name the `name`
         * @return the `Builder`
         */
        fun name(name: String?): Builder {
            this.name = name
            return this
        }

        /**
         * Sets the `arguments`.
         * @param arguments the `arguments`
         * @return the `Builder`
         */
        fun arguments(arguments: String?): Builder {
            this.arguments = arguments
            return this
        }

        /**
         * Returns a `ToolExecutionRequest` built from the parameters previously set.
         * @return a `ToolExecutionRequest`
         */
        fun build(): ToolExecutionRequest {
            return ToolExecutionRequest(this)
        }
    }

    companion object {
        /**
         * Creates builder to build [ToolExecutionRequest].
         * @return created builder
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}
