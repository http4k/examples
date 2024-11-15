package with_connect

import common.Commit
import common.UserDetails

class StubGitHubApi(private val users: Map<String, UserDetails>) : GitHubApi {
    override fun <R : Any> invoke(action: GitHubApiAction<R>): R = when (action) {
        is GetUser -> getUser(action, users) as R
        is GetRepoLatestCommit -> getRepoLatestCommit(action) as R
        else -> throw UnsupportedOperationException()
    }
}

private fun getUser(action: GetUser, users: Map<String, UserDetails>) = users[action.username]
private fun getRepoLatestCommit(action: GetRepoLatestCommit) = Commit(action.owner)
