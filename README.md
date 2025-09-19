# CJSON: Conversation JSON

CJSON (Conversation JSON) is an open standard for portable conversation data across AI tools/applications. It defines three JSON Schema specifications that enable producers/consumers to exchange conversations, model definitions, and tool definitions in a vendor‑neutral way.

- Conversation schema (aka cjson)
- Models schema (aka cjson-models)
- Toolsets schema (aka cjson-toolsets)

This repository contains the Java source models, a generator that produces versioned JSON Schemas, early tests/examples, and the documentation site.


## Modules

- cjson-schema-0
  - Java POJOs for schema version 0.x.y
  - Generated JSON Schemas live under cjson-schema-0/schemas/0
    - Conversation: schemas/0/conversation/cjson-0.1.0.schema.json
    - Models: schemas/0/models/cjson-models-0.1.0.schema.json
    - Toolsets: schemas/0/toolsets/cjson-toolsets-0.1.0.schema.json
- cjson-schema-generator
  - Utilities to generate JSON Schemas from the Java models
- cjson-tests
  - Early examples/tests consuming the generated schemas
- docs/cjson-dev
  - Antora documentation (specification, guides, landing page)


## Canonical Schema URLs and Versioning

Schemas are published with stable canonical IDs and SemVer:

- Conversation: https://cjson.dev/schema/0/conversation/cjson-0.1.0.schema.json
- Models: https://cjson.dev/schema/0/models/cjson-models.0.1.0.schema.json
- Toolsets: https://cjson.dev/schema/0/toolsets/cjson-toolsets.0.1.0.schema.json

General structure:

```
https://cjson.dev/schema/<MAJOR_VERSION>/<MODEL_TYPE>/<SPEC_NAME>-<VERSION>.schema.json
```

Versioning policy:
- Backward-compatible additions → minor version bump
- Breaking changes → major version bump
- Small non‑breaking fixes → patch version


## CJSON Examples

Check the Getting Started guide in the documentation page.

## Security and Privacy Notes

- Do not serialize API keys or secrets into any CJSON documents.
- Prefer environment variables or secret stores and reference them in configuration fields (e.g., "${env:NAME}", "${secret:NAME}").
- When exporting data, avoid including PII and warn users about risks.


## Documentation

The documentation site is built with Antora in docs/cjson-dev and includes:
- Home: docs/cjson-dev/modules/ROOT/pages/index.adoc
- Specification: docs/cjson-dev/modules/spec/pages/index.adoc
- Guide: docs/cjson-dev/modules/guides/pages/getting-started.adoc

You can build and publish the site with the Antora playbook (antora-playbook.yml).


## Contributing

We welcome ideas, feedback, and contributions. See CONTRIBUTE.md for guidelines. Changes to the specification should be proposed as CJSON Improvement Proposals (CIPs).


## Quick Start for Developers of this repository

If you're planning to contribute to this repo, this section is for you!

Prerequisites: Java 24+.

Build everything:

- Windows (PowerShell): `./gradlew.bat build`
- Linux/macOS: `./gradlew build`

This compiles modules and produces schemas under cjson-schema-0/schemas/0.

Run tests/examples:

- `./gradlew.bat :cjson-tests:test` (or `./gradlew :cjson-tests:test`)


## License

Apache License 2.0 — see LICENSE.
