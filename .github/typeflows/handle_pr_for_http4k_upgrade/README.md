# Handle PR for http4k upgrade (handle_pr_for_http4k_upgrade.yml)

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    pullrequest(["🔀 pull_request<br/>(labeled)"])
    subgraph handleprforhttp4kupgradeyml["Handle PR for http4k upgrade"]
        handleprforhttp4kupgradeyml_handleprforhttp4kupgrade["handle-pr-for-http4k-upgrade<br/>🐧 ubuntu-latest"]
    end
    pullrequest --> handleprforhttp4kupgradeyml_handleprforhttp4kupgrade
```

## Job: handle-pr-for-http4k-upgrade

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `handle-pr-for-http4k-upgrade` | 🐧 ubuntu-latest | - | - |

### Steps

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    step1["Step 1: Automatically Merge"]
    style step1 fill:#f8f9fa,stroke:#495057
    action1["🎬 plm9606<br/>automerge_actions<br/><br/>📝 Inputs:<br/>• label-name: automerge<br/>• reviewers-number: 0<br/>• merge-method: squash<br/>• auto-delete: true<br/>• github-token: ${{ secrets.ORG_PUBLIC_REPO_RE..."]
    style action1 fill:#e1f5fe,stroke:#0277bd
    step1 -.-> action1
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs