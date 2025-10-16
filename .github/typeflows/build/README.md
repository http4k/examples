# Build Examples (build.yml)

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    push(["📤 push<br/>branches(only: 1)"])
    pullrequest(["🔀 pull_request<br/>(*), branches(only: 1)"])
    subgraph buildyml["Build Examples"]
        buildyml_discovermodules["discover_modules<br/>🐧 ubuntu-latest<br/>📤 Outputs: modules"]
        buildyml_build["build<br/>🐧 ubuntu-latest<br/>📊 Matrix: module (1 runs)"]
        buildyml_discovermodules --> buildyml_build
    end
    push --> buildyml_discovermodules
    pullrequest --> buildyml_discovermodules
```

## Job: discover_modules

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `discover_modules` | 🐧 ubuntu-latest | - | 📤 1 outputs |

### Steps

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    step1["Step 1: Checkout"]
    style step1 fill:#f8f9fa,stroke:#495057
    action1["🎬 actions<br/>checkout"]
    style action1 fill:#e1f5fe,stroke:#0277bd
    step1 -.-> action1
    step2["Step 2: Find all build.gradle.kts files and extract module paths<br/>💻 bash"]
    style step2 fill:#f3e5f5,stroke:#7b1fa2
    step1 --> step2
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs




## Job: build

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `build` | 🐧 ubuntu-latest | `discover_modules` | 🔄 matrix |

### Steps

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    step1["Step 1: Checkout"]
    style step1 fill:#f8f9fa,stroke:#495057
    action1["🎬 actions<br/>checkout"]
    style action1 fill:#e1f5fe,stroke:#0277bd
    step1 -.-> action1
    step2["Step 2: Setup Java"]
    style step2 fill:#f8f9fa,stroke:#495057
    action2["🎬 actions<br/>setup-java<br/><br/>📝 Inputs:<br/>• java-version: 21<br/>• distribution: temurin"]
    style action2 fill:#e1f5fe,stroke:#0277bd
    step2 -.-> action2
    step1 --> step2
    step3["Step 3: Build and test module<br/>💻 bash"]
    style step3 fill:#f3e5f5,stroke:#7b1fa2
    step2 --> step3
    step4["Step 4: Tag automerge branch<br/>🔐 if: github.event_name == 'pull_request'"]
    style step4 fill:#f8f9fa,stroke:#495057
    action4["🎬 TimonVS<br/>pr-labeler-action<br/><br/>📝 Inputs:<br/>• configuration-path: .github/pr-labeler.yml"]
    style action4 fill:#e1f5fe,stroke:#0277bd
    step4 -.-> action4
    step3 --> step4
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs