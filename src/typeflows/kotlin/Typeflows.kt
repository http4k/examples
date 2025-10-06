import io.typeflows.github.DotGitHub
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.util.Builder
import org.http4k.typeflows.Http4kProjectStandards

class Typeflows : Builder<TypeflowsGitHubRepo> {
    override fun build() = TypeflowsGitHubRepo {
        dotGithub = DotGitHub {
            workflows += CreatePrForHttp4kUpgradeWorkflow()
            workflows += HandlePrForHttp4kUpgradeWorkflow()
        }

        files += Http4kProjectStandards()
    }
}
