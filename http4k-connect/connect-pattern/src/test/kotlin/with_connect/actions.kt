package with_connect

import common.Commit
import common.UserDetails
import common.authorFrom
import common.userNameFrom
import common.userOrgsFrom
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response

// interface
interface GitHubApiAction<R> {
    fun toRequest(): Request
    fun fromResponse(response: Response): R
}

// action/response
data class GetUser(val username: String) : GitHubApiAction<UserDetails> {
    override fun toRequest() = Request(Method.GET, "/users/$username")
    override fun fromResponse(response: Response) = UserDetails(userNameFrom(response), userOrgsFrom(response))
}
data class GetRepoLatestCommit(val owner: String, val repo: String) : GitHubApiAction<Commit> {
    override fun toRequest() = Request(Method.GET, "/repos/$owner/$repo/commits").query("per_page", "1")
    override fun fromResponse(response: Response) = Commit(authorFrom(response))
}

