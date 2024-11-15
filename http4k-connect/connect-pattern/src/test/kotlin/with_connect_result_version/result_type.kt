package with_connect_result_version

import common.UserDetails
import common.userNameFrom
import common.userOrgsFrom
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response

interface GitHubApiAction<R> {
    fun toRequest(): Request
    fun fromResponse(response: Response): Result<R, Exception>
}

data class GetUser(val username: String) : GitHubApiAction<UserDetails> {
    override fun toRequest() = Request(Method.GET, "/users/$username")
    override fun fromResponse(response: Response) = when {
        response.status.successful -> Success(UserDetails(userNameFrom(response), userOrgsFrom(response)))
        else -> Failure(RuntimeException("API returned: " + response.status))
    }
}
