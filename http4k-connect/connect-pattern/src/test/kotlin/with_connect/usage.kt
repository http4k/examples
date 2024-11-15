package with_connect

import common.UserDetails
import org.http4k.client.OkHttp

fun main() {
    val gitHub: GitHubApi = GitHubApi.Http(OkHttp())
    val user: UserDetails = gitHub.getUser("octocat")
    val latestUser: UserDetails = gitHub.getLatestUser("http4k", "http4k-connect")
}
