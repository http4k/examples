package without_connect

import common.Commit
import common.SetHeader
import common.UserDetails
import common.authorFrom
import common.userNameFrom
import common.userOrgsFrom
import org.http4k.client.OkHttp
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters

class GitHubApi(client: HttpHandler) {
    private val http = ClientFilters.SetBaseUriFrom(Uri.of("https://api.github.com"))
        .then(SetHeader("Accept", "application/vnd.github.v3+json"))
        .then(client)

    fun getUser(username: String): UserDetails {
        val response = http(Request(Method.GET, "/users/$username"))
        return UserDetails(userNameFrom(response), userOrgsFrom(response))
    }

    fun getRepoLatestCommit(owner: String, repo: String) = Commit(
        authorFrom(
            http(
                Request(Method.GET, "/repos/$owner/$repo/commits").query("per_page", "1")
            )
        )
    )
}

fun main() {
    val gitHub = GitHubApi(OkHttp())
    val user: UserDetails = gitHub.getUser("octocat")
    println(user)
}
