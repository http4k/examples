package dev.langchain4j.data.document

import dev.langchain4j.exception.LangChain4jException

class BlankDocumentException : LangChain4jException("The document is blank")
