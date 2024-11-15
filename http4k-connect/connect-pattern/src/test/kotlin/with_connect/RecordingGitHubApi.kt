package with_connect

import org.http4k.client.JavaHttpClient

class RecordingGitHubApi(private val delegate: GitHubApi) : GitHubApi {
    val recorded = mutableListOf<GitHubApiAction<*>>()
    override fun <R : Any> invoke(action: GitHubApiAction<R>): R {
        recorded += action
        return delegate(action)
    }
}

val recording = RecordingGitHubApi(GitHubApi.Http(JavaHttpClient()))
