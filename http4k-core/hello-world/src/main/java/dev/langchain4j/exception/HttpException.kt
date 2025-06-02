package dev.langchain4j.exception

class HttpException(private val statusCode: Int, message: String?) : LangChain4jException(message) {
    fun statusCode(): Int {
        return statusCode
    }
}
