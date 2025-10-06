# Create PR for http4k upgrade (create_pr_for_http4k_upgrade.yml)

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    repositorydispatch(["🔔 repository_dispatch<br/>(http4k-release)"])
    subgraph createprforhttp4kupgradeyml["Create PR for http4k upgrade"]
        createprforhttp4kupgradeyml_createprforhttp4kupgrade["create-pr-for-http4k-upgrade<br/>🐧 ubuntu-latest"]
    end
    repositorydispatch --> createprforhttp4kupgradeyml_createprforhttp4kupgrade
```

## Job: create-pr-for-http4k-upgrade

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `create-pr-for-http4k-upgrade` | 🐧 ubuntu-latest | - | - |

### Steps

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    step1["Step 1: Checkout"]
    style step1 fill:#f8f9fa,stroke:#495057
    action1["🎬 actions<br/>checkout"]
    style action1 fill:#e1f5fe,stroke:#0277bd
    step1 -.-> action1
    step2["Step 2: Update release"]
    style step2 fill:#f8f9fa,stroke:#495057
    action2["🎬 technote-space<br/>create-pr-action<br/><br/>📝 Inputs:<br/>• EXECUTE_COMMANDS: ./upgrade_http4k.sh ${{ github...<br/>• COMMIT_MESSAGE: Auto-upgrade to ${{ github.eve...<br/>• COMMIT_NAME: Auto-upgrade to ${{ github.eve...<br/>• PR_BRANCH_PREFIX: auto/<br/>• PR_BRANCH_NAME: ${{ github.event.client_payloa...<br/>• PR_TITLE: Auto-upgrade to ${{ github.eve...<br/>• GITHUB_TOKEN: ${{ secrets.ORG_PUBLIC_REPO_RE..."]
    style action2 fill:#e1f5fe,stroke:#0277bd
    step2 -.-> action2
    step1 --> step2
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs