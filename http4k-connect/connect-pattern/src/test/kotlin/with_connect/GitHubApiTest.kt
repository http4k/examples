package with_connect

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import common.UserDetails
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class GitHubApiTest {
    @Test
    fun `get user details`() {
        val githubApi = mockk<GitHubApi>()
        val userDetails = UserDetails("bob", listOf("http4k"))
        every { githubApi(any<GetUser>()) } returns userDetails

        assertThat(githubApi.getUser("bob"), equalTo(userDetails))
    }
}
