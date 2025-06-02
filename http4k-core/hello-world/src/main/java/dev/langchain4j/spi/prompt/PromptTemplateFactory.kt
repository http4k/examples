package dev.langchain4j.spi.prompt

/**
 * A factory for creating prompt templates.
 */
interface PromptTemplateFactory {
    /**
     * Interface for input for the factory.
     */
    interface Input {
        /**
         * Get the template string.
         * @return the template string.
         */
        val template: String?

        val name: String
            /**
             * Get the name of the template.
             * @return the name of the template.
             */
            get() = "template"
    }

    /**
     * Interface for a prompt template.
     */
    interface Template {
        /**
         * Render the template.
         * @param variables the variables to use.
         * @return the rendered template.
         */
        fun render(variables: Map<String?, Any?>?): String?
    }

    /**
     * Create a new prompt template.
     * @param input the input to the factory.
     * @return the prompt template.
     */
    fun create(input: Input?): Template?
}
