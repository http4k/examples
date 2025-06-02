package dev.langchain4j.agent.tool

import dev.langchain4j.agent.tool.ToolSpecification
import dev.langchain4j.internal.Utils
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import java.util.Objects

/**
 * Describes a tool that language model can execute.
 *
 *
 * Can be generated automatically from methods annotated with [Tool] using [ToolSpecifications] helper.
 */
class ToolSpecification private constructor(builder: Builder) {
    private val name: String?
    private val description: String?
    private val parameters: JsonObjectSchema?

    /**
     * Creates a [ToolSpecification] from a [Builder].
     *
     * @param builder the builder.
     */
    init {
        this.name = builder.name
        this.description = builder.description
        this.parameters = builder.parameters
    }

    /**
     * Returns the name of the tool.
     *
     * @return the name of the tool.
     */
    fun name(): String? {
        return name
    }

    /**
     * Returns the description of the tool.
     *
     * @return the description of the tool.
     */
    fun description(): String? {
        return description
    }

    /**
     * Returns the parameters of the tool.
     */
    fun parameters(): JsonObjectSchema? {
        return parameters
    }

    override fun equals(another: Any?): Boolean {
        if (this === another) return true
        return another is ToolSpecification
                && equalTo(another)
    }

    private fun equalTo(another: ToolSpecification): Boolean {
        return name == another.name
                && description == another.description
                && parameters == another.parameters
    }

    override fun hashCode(): Int {
        var h = 5381
        h += (h shl 5) + Objects.hashCode(name)
        h += (h shl 5) + Objects.hashCode(description)
        h += (h shl 5) + Objects.hashCode(parameters)
        return h
    }

    override fun toString(): String {
        return ("ToolSpecification {"
                + " name = " + Utils.quoted(name)
                + ", description = " + Utils.quoted(description)
                + ", parameters = " + parameters
                + " }")
    }

    /**
     * `ToolSpecification` builder static inner class.
     */
    class Builder
    /**
     * Creates a [Builder].
     */
    {
        var name: String? = null
        var description: String? = null
        var parameters: JsonObjectSchema? = null

        /**
         * Sets the `name`.
         *
         * @param name the `name`
         * @return `this`
         */
        fun name(name: String?): Builder {
            this.name = name
            return this
        }

        /**
         * Sets the `description`.
         *
         * @param description the `description`
         * @return `this`
         */
        fun description(description: String?): Builder {
            this.description = description
            return this
        }

        /**
         * Sets the `parameters`.
         *
         * @param parameters the `parameters`
         * @return `this`
         */
        fun parameters(parameters: JsonObjectSchema?): Builder {
            this.parameters = parameters
            return this
        }

        /**
         * Returns a `ToolSpecification` built from the parameters previously set.
         *
         * @return a `ToolSpecification` built with parameters of this `ToolSpecification.Builder`
         */
        fun build(): ToolSpecification {
            return ToolSpecification(this)
        }
    }

    companion object {
        /**
         * Creates builder to build [ToolSpecification].
         *
         * @return created builder
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}
