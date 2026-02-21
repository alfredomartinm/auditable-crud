# auditable-crud

## Automation & Standards

This repository adopts shared standards from [`alfredomartinm/repo-standards@v1`](https://github.com/alfredomartinm/repo-standards).

### CI

Every push to `master` and every pull request runs the reusable Maven CI workflow (`reusable-ci-maven.yml@v1`) using **Java 21**.

### Dependabot

Dependency updates are managed automatically:

- **Schedule:** weekly.
- **Scope:** direct and indirect Maven dependencies.
- **Grouping:** minor and patch updates are grouped into a single PR to reduce noise.
- **Major updates** each get their own PR. Risk notes / breaking-change explanations should be added to those PRs before merging.

### Housekeeping

A housekeeping workflow (`reusable-housekeeping-springboot-maven.yml@v1`) checks for missing standard files (`.editorconfig`, PR template, etc.) and can apply safe fixes automatically.

| Trigger | Mode | Behaviour |
|---|---|---|
| Monthly schedule (`cron`) | `dry-run` | Generates a report artifact – no repo changes. |
| `workflow_dispatch` → `dry-run` | `dry-run` | Same as above; useful for ad-hoc checks. |
| `workflow_dispatch` → `apply` | `apply` | Applies safe fixes and opens a housekeeping PR. |

To run housekeeping manually, go to **Actions → Housekeeping → Run workflow** and choose the desired mode.

