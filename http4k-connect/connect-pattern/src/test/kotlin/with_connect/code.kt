package with_connect

import common.Commit
import common.SetHeader
import common.UserDetails
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom

interface GitHubApi {
    operator fun <R : Any> invoke(action: GitHubApiAction<R>): R

    companion object
}

// adapter
fun GitHubApi.Companion.Http(client: HttpHandler) = object : GitHubApi {
    private val http = SetBaseUriFrom(Uri.of("https://api.github.com"))
        .then(SetHeader("Accept", "application/vnd.github.v3+json"))
        .then(client)

    override fun <R : Any> invoke(action: GitHubApiAction<R>) = action.fromResponse(http(action.toRequest()))
}

// extension function - these give a nicer API
fun GitHubApi.getUser(username: String) = invoke(GetUser(username))
fun GitHubApi.getRepoLatestCommit(owner: String, repo: String): Commit = invoke(GetRepoLatestCommit(owner, repo))

// composite function
fun GitHubApi.getLatestUser(org: String, repo: String): UserDetails {
    val commit = getRepoLatestCommit(org, repo)
    return getUser(commit.author)
}
