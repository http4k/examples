package dev.langchain4j.exception

open class NonRetriableException : LangChain4jException {
    constructor(message: String?) : super(message)

    constructor(cause: Throwable) : this(cause.message, cause)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
