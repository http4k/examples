package dev.langchain4j.data.message

import dev.langchain4j.internal.Exceptions
import dev.langchain4j.internal.Utils
import java.util.Arrays
import java.util.Objects

/**
 * Represents a message from a user, typically an end user of the application.
 * <br></br>
 * Depending on the supported modalities (text, image, audio, video, etc.) of the model,
 * user messages can contain either a single text (a `String`) or multiple [Content]s,
 * which can be either [TextContent], [ImageContent], [AudioContent],
 * [VideoContent], or [PdfFileContent].
 * <br></br>
 * Optionally, user message can contain a [.name] of the user.
 * Be aware that not all models support names in `UserMessage`.
 */
class UserMessage : ChatMessage {
    private val name: String?
    private val contents: List<Content>

    /**
     * Creates a [UserMessage] from a text.
     *
     * @param text the text.
     */
    constructor(text: String?) : this(TextContent.Companion.from(text))

    /**
     * Creates a [UserMessage] from a name and a text.
     *
     * @param name the name.
     * @param text the text.
     */
    constructor(name: String?, text: String?) : this(name, TextContent.Companion.from(text))

    /**
     * Creates a [UserMessage] from one or multiple [Content]s.
     * <br></br>
     * Will have a `null` name.
     *
     * @param contents the contents.
     */
    constructor(vararg contents: Content?) : this(Arrays.asList<Content>(*contents))

    /**
     * Creates a [UserMessage] from a name and one or multiple [Content]s.
     *
     * @param name     the name.
     * @param contents the contents.
     */
    constructor(name: String?, vararg contents: Content?) : this(name, Arrays.asList<Content>(*contents))

    /**
     * Creates a [UserMessage] from a list of [Content]s.
     * <br></br>
     * Will have a `null` name.
     *
     * @param contents the contents.
     */
    constructor(contents: List<Content>) {
        this.name = null
        this.contents = Utils.copy(contents!!)
    }

    /**
     * Creates a [UserMessage] from a name and a list of [Content]s.
     *
     * @param name     the name.
     * @param contents the contents.
     */
    constructor(name: String?, contents: List<Content>?) {
        this.name = name
        this.contents = Utils.copy(contents!!)
    }

    /**
     * The name of the user.
     *
     * @return the name, or `null` if not set.
     */
    fun name(): String? {
        return name
    }

    /**
     * The [Content]s of the message.
     *
     * @return the contents.
     */
    fun contents(): List<Content> {
        return contents
    }

    /**
     * Returns text from a single [TextContent].
     * Use this accessor only if you are certain that the message contains only a single text.
     * If the message contains multiple [Content]s, or if the only [Content] is not a [TextContent],
     * a [RuntimeException] is thrown.
     *
     * @return a single text.
     * @see .hasSingleText
     */
    fun singleText(): String? {
        if (hasSingleText()) {
            return (contents[0] as TextContent).text()
        } else {
            throw Exceptions.runtime("Expecting single text content, but got: $contents")
        }
    }

    /**
     * Whether this message contains a single [TextContent].
     *
     * @return `true` if this message contains a single [TextContent], `false` otherwise.
     */
    fun hasSingleText(): Boolean {
        return contents.size == 1 && contents[0] is TextContent
    }

    override fun type(): ChatMessageType {
        return ChatMessageType.USER
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserMessage
        return this.name == that.name
                && this.contents == that.contents
    }

    override fun hashCode(): Int {
        return Objects.hash(name, contents)
    }

    override fun toString(): String {
        return "UserMessage {" +
                " name = " + Utils.quoted(name) +
                " contents = " + contents +
                " }"
    }

    class Builder {
        private var name: String? = null
        private var contents: MutableList<Content>? = null

        fun name(name: String?): Builder {
            this.name = name
            return this
        }

        fun contents(contents: MutableList<Content>?): Builder {
            this.contents = contents
            return this
        }

        fun addContent(content: Content): Builder {
            if (this.contents == null) {
                this.contents = ArrayList()
            }
            contents!!.add(content)
            return this
        }

        fun build(): UserMessage {
            return UserMessage(name, contents)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        /**
         * Create a [UserMessage] from a text.
         *
         * @param text the text.
         * @return the [UserMessage].
         */
        @JvmStatic
        fun from(text: String?): UserMessage {
            return UserMessage(text)
        }

        /**
         * Create a [UserMessage] from a name and a text.
         *
         * @param name the name.
         * @param text the text.
         * @return the [UserMessage].
         */
        fun from(name: String?, text: String?): UserMessage {
            return UserMessage(name, text)
        }

        /**
         * Create a [UserMessage] from contents.
         *
         * @param contents the contents.
         * @return the [UserMessage].
         */
        fun from(vararg contents: Content?): UserMessage {
            return UserMessage(*contents)
        }

        /**
         * Create a [UserMessage] from a name and contents.
         *
         * @param name     the name.
         * @param contents the contents.
         * @return the [UserMessage].
         */
        fun from(name: String?, vararg contents: Content?): UserMessage {
            return UserMessage(name, *contents)
        }

        /**
         * Create a [UserMessage] from contents.
         *
         * @param contents the contents.
         * @return the [UserMessage].
         */
        fun from(contents: List<Content>): UserMessage {
            return UserMessage(contents)
        }

        /**
         * Create a [UserMessage] from a name and contents.
         *
         * @param name     the name.
         * @param contents the contents.
         * @return the [UserMessage].
         */
        fun from(name: String?, contents: List<Content>?): UserMessage {
            return UserMessage(name, contents)
        }

        /**
         * Create a [UserMessage] from a text.
         *
         * @param text the text.
         * @return the [UserMessage].
         */
        @JvmStatic
        fun userMessage(text: String?): UserMessage {
            return from(text)
        }

        /**
         * Create a [UserMessage] from a name and a text.
         *
         * @param name the name.
         * @param text the text.
         * @return the [UserMessage].
         */
        @JvmStatic
        fun userMessage(name: String?, text: String?): UserMessage {
            return from(name, text)
        }

        /**
         * Create a [UserMessage] from contents.
         *
         * @param contents the contents.
         * @return the [UserMessage].
         */
        @JvmStatic
        fun userMessage(vararg contents: Content?): UserMessage {
            return from(*contents)
        }

        /**
         * Create a [UserMessage] from a name and contents.
         *
         * @param name     the name.
         * @param contents the contents.
         * @return the [UserMessage].
         */
        @JvmStatic
        fun userMessage(name: String?, vararg contents: Content?): UserMessage {
            return from(name, *contents)
        }

        /**
         * Create a [UserMessage] from contents.
         *
         * @param contents the contents.
         * @return the [UserMessage].
         */
        fun userMessage(contents: List<Content>): UserMessage {
            return from(contents)
        }

        /**
         * Create a [UserMessage] from a name and contents.
         *
         * @param name     the name.
         * @param contents the contents.
         * @return the [UserMessage].
         */
        fun userMessage(name: String?, contents: List<Content>?): UserMessage {
            return from(name, contents)
        }
    }
}
