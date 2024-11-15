package actions

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import org.http4k.connect.RemoteFailure
import org.http4k.connect.github.api.GitHubAction
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.format.Moshi
import java.time.Instant

data class GetRepo(val owner: String, val repo: String) : GitHubAction<OrgRepo> {
    override fun toRequest() = Request(GET, "/repos/$owner/$repo")
    override fun toResult(response: Response) = with(response) {
        when {
            status.successful -> Success(Moshi.asA<OrgRepo>(bodyString()))
            else -> Failure(RemoteFailure(toRequest().method, toRequest().uri, status, bodyString()))
        }
    }
}

data class OrgRepo(val name: String, val created_at: Instant)
