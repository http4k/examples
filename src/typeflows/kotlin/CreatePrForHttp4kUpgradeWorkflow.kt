import io.typeflows.github.workflow.Job
import io.typeflows.github.workflow.RunsOn
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.step.UseAction
import io.typeflows.github.workflow.step.marketplace.Checkout
import io.typeflows.github.workflow.trigger.RepositoryDispatch
import io.typeflows.util.Builder

class CreatePrForHttp4kUpgradeWorkflow : Builder<Workflow> {
    override fun build() = Workflow("create_pr_for_http4k_upgrade") {
        displayName = "Create PR for http4k upgrade"

        on += RepositoryDispatch("http4k-release")

        jobs += Job("create-pr-for-http4k-upgrade", RunsOn.of("ubuntu-latest")) {
            steps += Checkout()
            steps += UseAction("technote-space/create-pr-action@v2") {
                name = "Update release"
                with += mapOf(
                    "EXECUTE_COMMANDS" to $$"./upgrade_http4k.sh ${{ github.event.client_payload.version }}",
                    "COMMIT_MESSAGE" to $$"Auto-upgrade to ${{ github.event.client_payload.version }}",
                    "COMMIT_NAME" to $$"Auto-upgrade to ${{ github.event.client_payload.version }}",
                    "PR_BRANCH_PREFIX" to "auto/",
                    "PR_BRANCH_NAME" to $$"${{ github.event.client_payload.version }}",
                    "PR_TITLE" to $$"Auto-upgrade to ${{ github.event.client_payload.version }}",
                    "GITHUB_TOKEN" to $$"${{ secrets.ORG_PUBLIC_REPO_RELEASE_TRIGGERING }}"
                )
            }
        }
    }
}
