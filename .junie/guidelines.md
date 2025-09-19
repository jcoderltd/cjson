# Junie Guidelines for the CJSON Repository

This file documents practical guidelines for Junie (the autonomous programmer) when working on this repository.


## Core Principles

- Prefer minimal, targeted changes that satisfy the issue description
- Keep users informed with an explicit UPDATE → PLAN → NEXT_STEP cycle
- Do not leak secrets; never add credentials to code, schemas, or docs
- Maintain backwards compatibility of schemas unless a major bump is explicitly intended
- Use extensions to validate ideas before changing core spec fields


## Workflow Expectations

1. Read the issue and repository context thoroughly
2. Plan steps concisely; mark progress with ✓, *, ! as you execute
3. Locate relevant files using search and file structure tools (avoid redundant scans)
4. Reproduce issues when applicable; write small scripts/tests as needed
5. Implement the smallest viable change; keep edits scoped
6. If models change, rebuild to regenerate schemas; commit regenerated files
7. Update docs/examples when behavior or public surface changes
8. Summarize changes and validations before submitting


## Building and Regenerating Schemas

- Build command (Windows): `./gradlew.bat build`
- Build command (Unix): `./gradlew build`
- Generated schemas reside under `cjson-schema-0/schemas/0`
- If touching POJOs in `cjson-schema-0/src/main/java`, ensure the corresponding schemas are updated by re-running the build and committing the changes


## Documentation

- Docs are in `docs/cjson-dev` (Antora). Pages:
  - ROOT landing page
  - spec/pages/index.adoc (normative draft)
  - guides/pages/getting-started.adoc (examples)
- Keep README.md and CONTRIBUTE.md aligned with the spec
- Reference canonical schema URLs as declared by `$id` fields


## Versioning

- SemVer for schemas:
  - Backward-compatible additions → minor
  - Breaking changes → major
  - Non-breaking fixes → patch
- Ensure `$id`, defaults, and required fields are consistent with Java models


## Security and Privacy

- Never commit secrets; use placeholders like `${env:VAR}` or `${secret:NAME}` in examples
- Avoid PII in example data; if necessary, anonymize
- Remind users to exclude private data when exporting (or confirm explicitly)


## Pull Requests and Commits

- Small, reviewable PRs
- Clear commit messages referencing issues/CIPs when relevant
- Include tests when adding or changing behavior
- Update docs alongside code changes


## Common Pitfalls

- Forgetting to regenerate/commit schemas after model changes
- Inconsistent defaults between models and docs
- Introducing breaking changes without bumping major version
- Overly broad refactors unrelated to the issue


## Useful Paths

- Conversation schema: `cjson-schema-0/schemas/0/conversation/cjson-0.1.0.schema.json`
- Models schema: `cjson-schema-0/schemas/0/models/cjson-models-0.1.0.schema.json`
- Toolsets schema: `cjson-schema-0/schemas/0/toolsets/cjson-toolsets-0.1.0.schema.json`
- Spec: `docs/cjson-dev/modules/spec/pages/index.adoc`
- Getting Started: `docs/cjson-dev/modules/guides/pages/getting-started.adoc`


## When in Doubt

- Ask for clarification via the issue channel
- Prefer adding a small doc note with assumptions made
- Keep the change reversible and well-scoped
