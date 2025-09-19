CJSON: Conversation JSON (File intended for LLM consumption)

Purpose
- Provide a vendor-neutral JSON representation for conversations between users and LLMs, plus auxiliary specs for model and tool definitions.
- Enable import/export across apps, long-term data ownership, and tooling interoperability.

Specs in this repo (v0.1.0)
- Conversation (aka cjson)
  - Canonical URL ($id): https://cjson.dev/schema/0/conversation/cjson-0.1.0.schema.json
  - Required fields: id, schemaUrl
  - mediaType default: application/vnd.cjson+json
  - Messages can be:
    - TextMessage (messageType = "text", with a single text content)
    - CompositeMessage (messageType = "composite", with contentBlocks)
  - Content blocks include: text, thinking, toolCall, toolApproval, toolResult
- Models (aka cjson-models)
  - $id: https://cjson.dev/schema/0/models/cjson-models.0.1.0.schema.json
  - Required fields: schemaUrl; each modelDefinition requires id, modelName, provider
  - Secrets SHOULD NOT be embedded; use placeholders like ${env:VAR} or ${secret:NAME}
- Toolsets (aka cjson-toolsets)
  - $id: https://cjson.dev/schema/0/toolsets/cjson-toolsets.0.1.0.schema.json
  - Required fields: schema (top-level), and for each toolset: id, kind; each tool requires name

Minimal Conversation
{
  "id": "af9b2b96-204d-41cd-8f35-d25483514996",
  "mediaType": "application/vnd.cjson+json",
  "schemaUrl": "https://cjson.dev/schema/0/conversation/cjson-0.1.0.schema.json"
}

Two-Message Conversation (user → assistant/composite)
{
  "id": "b8bf083e-6e2c-4e20-a300-eef3c867042f",
  "conversationTitle": "Example Conversation",
  "mediaType": "application/vnd.cjson+json",
  "schemaUrl": "https://cjson.dev/schema/0/conversation/cjson-0.1.0.schema.json",
  "messages": [
    {
      "id": "b52fb6eb-36e2-4cc4-a5d6-383bebf04c9d",
      "content": "Can you help me explain this schema?",
      "role": "user",
      "messageType": "text"
    },
    {
      "id": "0eec30fd-f5ad-4cde-94e9-b29db4553cbe",
      "contentBlocks": [
        {
          "id": "284ef440-bf4e-4548-923e-37bb78e52c93",
          "createdAt": "2025-09-18 20:20:14.502",
          "text": "CJSON is an open standard for portable conversation data",
          "blockType": "text"
        }
      ],
      "role": "assistant",
      "messageType": "composite"
    }
  ]
}

Key Invariants and Hints
- Always include the appropriate schemaUrl and validate against the matching JSON Schema before emitting.
- Prefer UUIDs/ULIDs for ids (not enforced by the schema but recommended).
- TextMessage is a simplified CompositeMessage with a single TextBlock.
- For streaming, blocks may be flagged with isStreaming and updated over time; order is typically creation order.
- System/developer prompts should use Conversation.systemMessage rather than separate system messages.
- Tool execution
  - Assistant requests a tool via a ToolCallBlock
  - Approval (if required) is represented by a ToolApprovalBlock
  - Results are represented by a ToolResultBlock and can be fed back to the model

Security and Privacy
- Do not serialize API keys/tokens in any of the specs.
- Use ${env:VAR} or ${secret:NAME} placeholders in model/toolset config.
- Avoid PII in exported data; warn users when exporting private conversations.

Versioning
- SemVer with canonical IDs; non-breaking → minor, breaking → major, fixes → patch.
- Extension mechanism: vendors may add namespaced fields like "com.company:feature": { ... } without altering core semantics.

Where to Look in the Repo
- Generated schemas:
  - cjson-schema-0/schemas/0/conversation/cjson-0.1.0.schema.json
  - cjson-schema-0/schemas/0/models/cjson-models-0.1.0.schema.json
  - cjson-schema-0/schemas/0/toolsets/cjson-toolsets-0.1.0.schema.json
- Spec and guides (Antora): `docs/cjson-dev/modules/*/pages/*.adoc`

What to Output When Asked About CJSON
- Provide concise examples that validate against the specified version
- Include schemaUrl and mediaType
- Use proper roles (user, assistant, tool) and block types (text, toolCall, toolApproval, toolResult, thinking)
- If models/toolsets are involved, include only non-sensitive configuration and reference secrets via placeholders

Common Mistakes to Avoid
- Missing or incorrect schemaUrl
- Embedding secrets directly in JSON
- Emitting system messages as separate entries instead of Conversation.systemMessage
- Forgetting required fields (e.g., id, messageType, blockType, tool names/ids)
