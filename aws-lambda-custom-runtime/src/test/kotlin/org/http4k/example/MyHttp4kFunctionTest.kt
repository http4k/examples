package org.http4k.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.lang.reflect.Proxy

class MyHttp4kFunctionTest {

    @Test
    fun `Testing the entire function`() {
        val output = ByteArrayOutputStream()
        MyHttp4kFunction().handleRequest(
            """
                {
                  "body": "eyJ0ZXN0IjoiYm9keSJ9",
                  "resource": "/{proxy+}",
                  "path": "/echo/hello",
                  "httpMethod": "GET",
                  "isBase64Encoded": true,
                  "queryStringParameters": {
                  },
                  "multiValueQueryStringParameters": {
                  },
                  "pathParameters": {
                  },
                  "stageVariables": {
                  },
                  "headers": {
                  },
                  "multiValueHeaders": {
                  },
                  "requestContext": {
                    "accountId": "123456789012",
                    "resourceId": "123456",
                    "stage": "prod",
                    "requestId": "c6af9ac6-7b61-11e6-9a41-93e8deadbeef",
                    "requestTime": "09/Apr/2015:12:34:56 +0000",
                    "requestTimeEpoch": 1428582896000
                  }
                }
            """.trimIndent().byteInputStream(), output, proxy()
        )
        assertThat(String(output.toByteArray()), equalTo("""{"statusCode":200,"headers":{},"body":"aGVsbG8=","isBase64Encoded":true}"""))
    }

    @Test
    fun `testing just the handler`() {
        assertThat(http4kApp(Request(Method.GET, "/echo/hello")), hasStatus(OK).and(hasBody("hello")))
    }
}

inline fun <reified T> proxy(): T = Proxy.newProxyInstance(
    T::class.java.classLoader,
    arrayOf(T::class.java)
) { _, _, _ -> TODO("not implemented") } as T
