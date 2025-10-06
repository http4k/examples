import io.typeflows.github.DotGitHub
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.github.visualisation.WorkflowVisualisations
import io.typeflows.github.workflow.Cron
import io.typeflows.github.workflow.step.RunCommand
import io.typeflows.util.Builder
import org.http4k.typeflows.Http4kProjectStandards
import org.http4k.typeflows.UpdateGradleProjectDependencies

class Typeflows : Builder<TypeflowsGitHubRepo> {
    override fun build() = TypeflowsGitHubRepo {
        dotGithub = DotGitHub {
            workflows += CreatePrForHttp4kUpgradeWorkflow()
            workflows += HandlePrForHttp4kUpgradeWorkflow()
            workflows += UpdateGradleProjectDependencies("update-dependencies", Cron.of("0 08 * * 1"), RunCommand("ls"))

            files += WorkflowVisualisations(workflows)
        }

        files += Http4kProjectStandards()
    }
}
