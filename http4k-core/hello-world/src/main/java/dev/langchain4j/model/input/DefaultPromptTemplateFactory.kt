package dev.langchain4j.model.input

import dev.langchain4j.internal.Exceptions
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.spi.prompt.PromptTemplateFactory
import java.util.regex.Pattern

internal class DefaultPromptTemplateFactory : PromptTemplateFactory {
    override fun create(input: PromptTemplateFactory.Input?): DefaultTemplate {
        TODO()
//        return DefaultTemplate(input!.template!!)
    }

    internal class DefaultTemplate(template: String) : PromptTemplateFactory.Template {
        private val template: String =
            ValidationUtils.ensureNotBlank(template, "template")
        private val allVariables: Set<String>

        init {
            this.allVariables = extractVariables(template)
        }

        override fun render(variables: Map<String?, Any?>?): String {
            TODO()
//            ensureAllVariablesProvided(variables!!)
//
//            var result = template
//            for ((key, value) in variables) {
//                result = replaceAll(result, key, value)
//            }
//
//            return result
        }

        private fun ensureAllVariablesProvided(providedVariables: Map<String, Any>) {
            for (variable in allVariables) {
                if (!providedVariables.containsKey(variable)) {
                    throw Exceptions.illegalArgument("Value for the variable '%s' is missing", variable)
                }
            }
        }

        companion object {
            /**
             * A regular expression pattern for identifying variable placeholders within double curly braces in a template string.
             * Variables are denoted as `{{variable_name}}` or `{{ variable_name }}`,
             * where spaces around the variable name are allowed.
             *
             *
             * This pattern is used to match and extract variables from a template string for further processing,
             * such as replacing these placeholders with their corresponding values.
             */
            private val VARIABLE_PATTERN: Pattern = Pattern.compile("\\{\\{\\s*(.+?)\\s*\\}\\}")

            private fun extractVariables(template: String): Set<String> {
                val variables: MutableSet<String> = HashSet()
                val matcher = VARIABLE_PATTERN.matcher(template)
                while (matcher.find()) {
                    variables.add(matcher.group(1))
                }
                return variables
            }

            private fun replaceAll(template: String, variable: String, value: Any): String {
                if (value?.toString() == null) {
                    throw Exceptions.illegalArgument("Value for the variable '%s' is null", variable)
                }
                return template.replace(inDoubleCurlyBrackets(variable), value.toString())
            }

            private fun inDoubleCurlyBrackets(variable: String): String {
                return "{{$variable}}"
            }
        }
    }
}
