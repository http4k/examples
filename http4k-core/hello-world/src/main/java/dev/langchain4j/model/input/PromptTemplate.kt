package dev.langchain4j.model.input

import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.spi.ServiceHelper
import dev.langchain4j.spi.prompt.PromptTemplateFactory
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Collections

/**
 * Represents a template of a prompt that can be reused multiple times.
 * A template typically contains one or more variables (placeholders) defined as {{variable_name}} that are
 * replaced with actual values to produce a Prompt.
 * Special variables {{current_date}}, {{current_time}}, and {{current_date_time}} are automatically
 * filled with LocalDate.now(), LocalTime.now(), and LocalDateTime.now() respectively.
 */
class PromptTemplate internal constructor(template: String?, clock: Clock) {
    private val templateString: String =
        ValidationUtils.ensureNotBlank(template, "template")
    private val template: PromptTemplateFactory.Template
    private val clock: Clock

    /**
     * Create a new PromptTemplate.
     *
     *
     * The `Clock` will be the system clock.
     *
     * @param template the template string of the prompt.
     */
    constructor(template: String?) : this(template, Clock.systemDefaultZone())

    /**
     * Create a new PromptTemplate.
     *
     * @param template the template string of the prompt.
     * @param clock    the clock to use for the special variables.
     */
    init {
        this.template = FACTORY.create { template }
        this.clock = ValidationUtils.ensureNotNull(clock, "clock")
    }

    /**
     * @return A prompt template string.
     */
    fun template(): String {
        return templateString
    }

    /**
     * Applies a value to a template containing a single variable. The single variable should have the name {{it}}.
     *
     * @param value The value that will be injected in place of the {{it}} placeholder in the template.
     * @return A Prompt object where the {{it}} placeholder in the template has been replaced by the provided value.
     */
    fun apply(value: Any): Prompt {
        return apply(Collections.singletonMap("it", value))
    }

    /**
     * Applies multiple values to a template containing multiple variables.
     *
     * @param variables A map of variable names to values that will be injected in place of the corresponding placeholders in the template.
     * @return A Prompt object where the placeholders in the template have been replaced by the provided values.
     */
    fun apply(variables: Map<String, Any>): Prompt {
        ValidationUtils.ensureNotNull(variables, "variables")
        return Prompt.Companion.from(template.render(injectDateTimeVariables(variables)))
    }

    /**
     * Injects the special variables {{current_date}}, {{current_time}}, and {{current_date_time}} into the given map.
     *
     * @param variables the map to inject the variables into.
     * @return a copy of the map with the variables injected.
     */
    private fun injectDateTimeVariables(variables: Map<String, Any>): Map<String, Any> {
        val variablesCopy: MutableMap<String, Any> = HashMap(variables)
        variablesCopy[CURRENT_DATE] = LocalDate.now(clock)
        variablesCopy[CURRENT_TIME] = LocalTime.now(clock)
        variablesCopy[CURRENT_DATE_TIME] = LocalDateTime.now(clock)
        return variablesCopy
    }

    companion object {
        private val FACTORY = factory()

        private fun factory(): PromptTemplateFactory {
            for (factory in ServiceHelper.loadFactories(
                PromptTemplateFactory::class.java
            )) {
                return factory
            }
            return DefaultPromptTemplateFactory()
        }

        const val CURRENT_DATE: String = "current_date"
        const val CURRENT_TIME: String = "current_time"
        const val CURRENT_DATE_TIME: String = "current_date_time"

        /**
         * Create a new PromptTemplate.
         *
         * @param template the template string of the prompt.
         * @return the PromptTemplate.
         */
        fun from(template: String?): PromptTemplate {
            return PromptTemplate(template)
        }
    }
}
