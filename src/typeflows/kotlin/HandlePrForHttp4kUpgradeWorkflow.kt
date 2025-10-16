import io.typeflows.github.workflow.Job
import io.typeflows.github.workflow.PullRequestType
import io.typeflows.github.workflow.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.step.UseAction
import io.typeflows.github.workflow.trigger.PullRequest
import io.typeflows.util.Builder

class HandlePrForHttp4kUpgradeWorkflow : Builder<Workflow> {
    override fun build() = Workflow("handle_pr_for_http4k_upgrade") {
        displayName = "Handle PR for http4k upgrade"

        on += PullRequest {
            types += PullRequestType.Labeled
        }

        jobs += Job("handle-pr-for-http4k-upgrade", UBUNTU_LATEST) {
            steps += UseAction("plm9606/automerge_actions@1.2.2") {
                name = "Automatically Merge"
                with += mapOf(
                    "label-name" to "automerge",
                    "reviewers-number" to "0",
                    "merge-method" to "squash",
                    "auto-delete" to "true",
                    "github-token" to $$"${{ secrets.ORG_PUBLIC_REPO_RELEASE_TRIGGERING }}"
                )
            }
        }
    }
}
