package dev.langchain4j.exception

class ModelNotFoundException : NonRetriableException {
    constructor(message: String?) : super(message)

    constructor(cause: Throwable) : this(cause.message, cause)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
