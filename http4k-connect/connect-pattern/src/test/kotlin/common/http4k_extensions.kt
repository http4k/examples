package common

import org.http4k.core.Filter
import org.http4k.core.Response

fun SetHeader(name: String, value: String): Filter = Filter { next ->
    {
        next(it.header(name, value))
    }
}


fun authorFrom(response: Response) = "bob"
fun userNameFrom(response: Response) = """.*"login":"(.+?)".*""".toRegex().matchEntire(response.bodyString())!!.groupValues[1]
fun userOrgsFrom(response: Response) = listOf<String>()
