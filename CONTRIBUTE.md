# Contributing to CJSON

Thanks for your interest in improving CJSON! This document summarizes how to propose changes, report issues, and submit pull requests. Contributions to both the code and the specification are welcome.


## Code of Conduct

Be respectful and constructive. Assume good intent and focus discussions on technical merits.


## Ways to Contribute

- Report bugs or gaps in the spec/schemas
- Propose features via CJSON Improvement Proposals (CIPs)
- Improve documentation, guides, and examples
- Add tests that validate or exercise the schemas

In many cases, for proposals that don't impact the core fields, we recommend the use of an extension first to have working examples of the change being applied.
This helps clarify the use cases and also helps to see how it should work which makes the decision process and discussion a lot easier.

## CJSON Improvement Proposals (CIPs)

Changes to the specification or schemas should start as a CIP:

1. Open an issue describing the problem, motivation, and proposed change
2. Provide examples and discuss compatibility
3. Reach consensus
4. Implement the change (Java model + regenerated schemas + docs) and reference the CIP in the PR

Non‑breaking additions should target a minor version bump. Breaking changes require a major version bump; consider introducing extensions first to validate the idea in practice.


## Development Environment

- Java 17+
- Gradle

Build all modules:

- Windows (PowerShell): `./gradlew.bat build`
- Linux/macOS: `./gradlew build`

Run tests/examples:

- `./gradlew.bat :cjson-tests:test`

Documentation is under `docs/cjson-dev` (Antora). The Antora playbook file is at `antora-playbook.yml`.


## Project Structure

- `cjson-schema-0`: Java models for spec version 0.x.y; generated JSON Schemas in `cjson-schema-0/schemas/0`
- `cjson-schema-generator`: schema generation utilities
- `cjson-tests`: tests/examples
- `docs/cjson-dev`: documentation site (spec, guides)


## Making Changes

1. Fork and create a feature branch
2. If you modify the Java models, regenerate schemas by running the build (schemas are committed under `cjson-schema-0/schemas/0`) - this will be automated in a future PR
3. Update docs and examples as needed
4. Ensure tests pass; add new tests when appropriate
5. Open a PR with a clear description, rationale, and links to related issues/CIPs

Checklist:
- [ ] Code compiles and tests pass (`./gradlew[.bat] build`)
- [ ] Schemas regenerated and committed if models changed
- [ ] Documentation updated (spec/guides/examples)
- [ ] Backward compatibility considered; versioning updated if required


## Style and Practices

- Keep PRs small and focused
- Prefer explicit names and clear descriptions
- Avoid leaking secrets; never put credentials in schemas or docs
- Follow SemVer for schema changes
- Prefer extensions for experimentation before changing the core spec


## Reporting Security Issues

Do not open public issues for sensitive security problems. Please contact the maintainers directly.


## License

By contributing, you agree that your contributions will be licensed under the Apache License 2.0.
