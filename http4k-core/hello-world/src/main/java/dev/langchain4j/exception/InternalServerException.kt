package dev.langchain4j.exception

class InternalServerException : RetriableException {
    constructor(message: String?) : super(message)

    constructor(cause: Throwable) : this(cause.message, cause)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
