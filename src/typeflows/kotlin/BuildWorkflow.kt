import io.typeflows.github.workflow.Job
import io.typeflows.github.workflow.Output
import io.typeflows.github.workflow.RunsOn
import io.typeflows.github.workflow.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflow.Strategy
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.step.RunCommand
import io.typeflows.github.workflow.step.UseAction
import io.typeflows.github.workflow.step.marketplace.Checkout
import io.typeflows.github.workflow.step.marketplace.JavaDistribution.Temurin
import io.typeflows.github.workflow.step.marketplace.JavaVersion.V21
import io.typeflows.github.workflow.step.marketplace.SetupJava
import io.typeflows.github.workflow.trigger.Branches
import io.typeflows.github.workflow.trigger.PullRequest
import io.typeflows.github.workflow.trigger.Push
import io.typeflows.util.Builder

class BuildWorkflow : Builder<Workflow> {
    override fun build() = Workflow("build") {
        displayName = "Build Examples"

        on += Push {
            branches = Branches.Only("*")
        }
        on += PullRequest {
            branches = Branches.Only("*")
        }

        val discover = Job("discover_modules", RunsOn.of("ubuntu-latest")) {

            steps += Checkout()
            val runCommand = RunCommand(
                $$"""
                  # Find all build.gradle.kts files except the root one
                  MODULES=$(find . -type f -name "build.gradle.kts" -not -path "./build.gradle.kts" | sed -e 's|^./||' -e 's|/build.gradle.kts||' | sed -e 's|/|-|g' | awk '{print "\":" $0 "\""}' | tr '\n' ',' | sed 's/,$//')
                  echo "modules=[${MODULES}]" >> $GITHUB_OUTPUT
                  echo "Found modules: [$MODULES]"
                """.trimIndent(),
            ) {
                name = "Find all build.gradle.kts files and extract module paths"
                id = "set-modules"
            }
            steps += runCommand

            outputs += Output.string("modules", $$"""${{ steps.set-modules.outputs.modules }}""")

        }
        jobs += discover

        jobs += Job("build", UBUNTU_LATEST) {
            needs += discover

            strategy = Strategy(mapOf("module" to listOf($$"""${{ fromJson(needs.discover_modules.outputs.modules) }}"""))) {
                this.failFast = false
            }

            steps += Checkout()
            steps += SetupJava(Temurin, V21)
            steps += RunCommand($$"./gradlew ${{ matrix.project }}:build") {
                name = "Build and test module"
            }
            steps += UseAction("TimonVS/pr-labeler-action@v5.0.0") {
                name = "Tag automerge branch"
                with += mapOf("configuration-path" to ".github/pr-labeler.yml")
                env += "GITHUB_TOKEN" to $$"${{ secrets.ORG_PUBLIC_REPO_RELEASE_TRIGGERING }}"
            }
        }
    }
}
