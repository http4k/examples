package actions

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import dev.forkhandles.result4k.Success
import org.http4k.connect.github.GitHubToken
import org.http4k.connect.github.api.GitHub
import org.http4k.connect.github.api.Http
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Moshi.asFormatString
import org.junit.jupiter.api.Test
import java.time.Instant.EPOCH

class GetRepoTest {
    private val input = OrgRepo("http4k", EPOCH)
    private val app = { _: Request -> Response(OK).body(asFormatString(input)) }

    private val gitHub = GitHub.Http({ GitHubToken.of("hello") }, app)

    @Test
    fun `get repo`() {
        assertThat(gitHub(GetRepo("http4k", "http4k-connect")), equalTo(Success(input)))
    }
}
