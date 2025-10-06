

# Toolsets



<table>
<tbody>
<tr><th>$id</th><td>https://schema.cjson.dev/0/toolsets/cjson-toolsets-0.1.0-SNAPSHOT.schema.json</td></tr>
<tr><th>$schema</th><td>https://json-schema.org/draft/2020-12/schema</td></tr>
</tbody>
</table>

## Properties

<table class="jssd-properties-table"><thead><tr><th colspan="2">Name</th><th>Type</th></tr></thead><tbody><tr><td colspan="2"><a href="#mediatype">mediaType</a></td><td>String</td></tr><tr><td colspan="2"><a href="#schema">schema</a></td><td>String</td></tr><tr><td colspan="2"><a href="#toolsets">toolsets</a></td><td>Array</td></tr></tbody></table>



<hr />


## mediaType


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    <tr>
      <th>Required</th>
      <td colspan="2">No</td>
    </tr>
    <tr>
      <th>Default</th>
      <td colspan="2">application/vnd.cjson-toolsets+json</td>
    </tr>
    
  </tbody>
</table>




## schema


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    <tr>
      <th>Required</th>
      <td colspan="2">Yes</td>
    </tr>
    <tr>
      <th>Default</th>
      <td colspan="2">https://schema.cjson.dev/0/toolsets/cjson-toolsets-0.1.0-SNAPSHOT.schema.json</td>
    </tr>
    
  </tbody>
</table>




## toolsets


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">Array</td></tr>
    <tr>
      <th>Required</th>
      <td colspan="2">No</td>
    </tr>
    
  </tbody>
</table>



### toolsets.extensions


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Extensions can be used to add custom application-specific data to the toolset definition.

Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>




### toolsets.headers


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Additional headers to be included in the HTTP requests for &quot;http&quot; defined tools (mainly HTTP MCP servers).

Applications are encouraged, although not enforced, to support environment variable injection into the
header values.

As an example, a &quot;token&quot; header with a &quot;${env:MODEL_TOKEN}&quot; would inject the value of the environment
variable &quot;MODEL_TOKEN&quot; into the &quot;token&quot; header.

Applications MUST NOT store plain-text credentials here and should use references to secret stores
or environment variable names.

For secret referencing, we recommend a &quot;${secret:SECRET_NAME}&quot; that is similar to the &quot;env&quot; case, but
is up to the application to decide where they fetch the secrets from.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>




### toolsets.id


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>




### toolsets.kind


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">The kind of toolset represented by this definition.</td>
    </tr>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    <tr>
      <th>Enum</th>
      <td colspan="2"><ul><li>builtin</li><li>mcp</li><li>uri</li></ul></td>
    </tr>
  </tbody>
</table>




### toolsets.server


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Optional server connection details for MCP/remote toolsets.

Applications are encouraged, although not enforced, to support environment variable injection into the
server values.

As an example, a &quot;token&quot; header with a &quot;${env:MODEL_TOKEN}&quot; would inject the value of the environment
variable &quot;MODEL_TOKEN&quot; into the &quot;token&quot; value.

Applications MUST NOT store plain-text credentials here and should use references to secret stores
or environment variable names.

For secret referencing, we recommend a &quot;${secret:SECRET_NAME}&quot; that is similar to the &quot;env&quot; case, but
is up to the application to decide where they fetch the secrets from.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>




### toolsets.tools


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">The tools that are exposed/defined by this toolset.

It is the responsibility of the application to fetch the list of tools from the respective provider
(e.g. MCP server) and to list them here.

Part of the intention with this is to allow the user(s) to define the permissions of each tool individually.

For example, for an MCP Server that provides &quot;file system access tools&quot;, a user might want to allow all
file reads inside a folder without requiring approval, but might want to require approval for any deletions.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Array</td></tr>
    
  </tbody>
</table>



### toolsets.tools.argsSchema


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">JSON Schema (or schema-like) describing args; free-form object.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>




### toolsets.tools.enabled


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Whether this tool is enabled (overrides toolset.defaults.enabled).</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Boolean</td></tr>
    <tr>
      <th>Default</th>
      <td colspan="2">true</td>
    </tr>
    
  </tbody>
</table>




### toolsets.tools.examples


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Optional example payloads/usages.</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Array</td></tr>
    
  </tbody>
</table>




### toolsets.tools.extensions


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Extensions can be used to add custom application-specific data to the tool definition.

Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>




### toolsets.tools.name


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Unique identifier for this tool.

This is the name that applications MUST send to the model as part of the tool definitions.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>




### toolsets.tools.requiresApproval


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Whether this command requires approval (overrides toolset.defaults.requiresApproval).</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Boolean</td></tr>
    <tr>
      <th>Default</th>
      <td colspan="2">false</td>
    </tr>
    
  </tbody>
</table>




### toolsets.tools.summary


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">A human-readable summary of this tool.</td>
    </tr>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>





### toolsets.toolsetDefaults


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Default flags that apply to all tools unless overridden at the tool level.</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>



### toolsets.toolsetDefaults.enabled


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Whether tools are enabled by default.</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Boolean</td></tr>
    <tr>
      <th>Default</th>
      <td colspan="2">true</td>
    </tr>
    
  </tbody>
</table>




### toolsets.toolsetDefaults.requiresApproval


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Whether tools require approval by default.</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Boolean</td></tr>
    <tr>
      <th>Default</th>
      <td colspan="2">false</td>
    </tr>
    
  </tbody>
</table>





### toolsets.version


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>










<hr />

## Schema
```
{
    "$schema": "https://json-schema.org/draft/2020-12/schema",
    "type": "object",
    "properties": {
        "mediaType": {
            "type": "string",
            "default": "application/vnd.cjson-toolsets+json"
        },
        "schema": {
            "type": "string",
            "default": "https://schema.cjson.dev/0/toolsets/cjson-toolsets-0.1.0-SNAPSHOT.schema.json"
        },
        "toolsets": {
            "type": "array",
            "items": {
                "type": "object",
                "properties": {
                    "extensions": {
                        "type": "object",
                        "additionalProperties": true,
                        "description": "Extensions can be used to add custom application-specific data to the toolset definition.\n\nExtensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.\n",
                        "existingJavaType": "java.util.Map<java.lang.String, java.lang.Object>"
                    },
                    "headers": {
                        "type": "object",
                        "additionalProperties": true,
                        "description": "Additional headers to be included in the HTTP requests for \"http\" defined tools (mainly HTTP MCP servers).\n\nApplications are encouraged, although not enforced, to support environment variable injection into the\nheader values.\n\nAs an example, a \"token\" header with a \"${env:MODEL_TOKEN}\" would inject the value of the environment\nvariable \"MODEL_TOKEN\" into the \"token\" header.\n\nApplications MUST NOT store plain-text credentials here and should use references to secret stores\nor environment variable names.\n\nFor secret referencing, we recommend a \"${secret:SECRET_NAME}\" that is similar to the \"env\" case, but\nis up to the application to decide where they fetch the secrets from.\n",
                        "existingJavaType": "java.util.Map<java.lang.String, java.lang.String>"
                    },
                    "id": {
                        "type": "string"
                    },
                    "kind": {
                        "type": "string",
                        "enum": [
                            "builtin",
                            "mcp",
                            "uri"
                        ],
                        "description": "The kind of toolset represented by this definition."
                    },
                    "server": {
                        "type": "object",
                        "additionalProperties": true,
                        "description": "Optional server connection details for MCP/remote toolsets.\n\nApplications are encouraged, although not enforced, to support environment variable injection into the\nserver values.\n\nAs an example, a \"token\" header with a \"${env:MODEL_TOKEN}\" would inject the value of the environment\nvariable \"MODEL_TOKEN\" into the \"token\" value.\n\nApplications MUST NOT store plain-text credentials here and should use references to secret stores\nor environment variable names.\n\nFor secret referencing, we recommend a \"${secret:SECRET_NAME}\" that is similar to the \"env\" case, but\nis up to the application to decide where they fetch the secrets from.\n",
                        "existingJavaType": "java.util.Map<java.lang.String, java.lang.Object>"
                    },
                    "tools": {
                        "description": "The tools that are exposed/defined by this toolset.\n\nIt is the responsibility of the application to fetch the list of tools from the respective provider\n(e.g. MCP server) and to list them here.\n\nPart of the intention with this is to allow the user(s) to define the permissions of each tool individually.\n\nFor example, for an MCP Server that provides \"file system access tools\", a user might want to allow all\nfile reads inside a folder without requiring approval, but might want to require approval for any deletions.\n",
                        "type": "array",
                        "items": {
                            "type": "object",
                            "properties": {
                                "argsSchema": {
                                    "type": "object",
                                    "additionalProperties": true,
                                    "description": "JSON Schema (or schema-like) describing args; free-form object.\n",
                                    "existingJavaType": "java.util.Map<java.lang.String, java.lang.Object>"
                                },
                                "enabled": {
                                    "type": "boolean",
                                    "description": "Whether this tool is enabled (overrides toolset.defaults.enabled).",
                                    "default": "true"
                                },
                                "examples": {
                                    "description": "Optional example payloads/usages.",
                                    "type": "array",
                                    "items": {
                                        "type": "object",
                                        "additionalProperties": true,
                                        "existingJavaType": "java.util.Map<java.lang.String, java.lang.Object>"
                                    }
                                },
                                "extensions": {
                                    "type": "object",
                                    "additionalProperties": true,
                                    "description": "Extensions can be used to add custom application-specific data to the tool definition.\n\nExtensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.\n",
                                    "existingJavaType": "java.util.Map<java.lang.String, java.lang.Object>"
                                },
                                "name": {
                                    "type": "string",
                                    "description": "Unique identifier for this tool.\n\nThis is the name that applications MUST send to the model as part of the tool definitions.\n"
                                },
                                "requiresApproval": {
                                    "type": "boolean",
                                    "description": "Whether this command requires approval (overrides toolset.defaults.requiresApproval).",
                                    "default": "false"
                                },
                                "summary": {
                                    "type": "string",
                                    "description": "A human-readable summary of this tool."
                                }
                            },
                            "required": [
                                "name"
                            ],
                            "description": "A tool that is exposed/defined by this toolset."
                        }
                    },
                    "toolsetDefaults": {
                        "type": "object",
                        "properties": {
                            "enabled": {
                                "type": "boolean",
                                "description": "Whether tools are enabled by default.",
                                "default": "true"
                            },
                            "requiresApproval": {
                                "type": "boolean",
                                "description": "Whether tools require approval by default.",
                                "default": "false"
                            }
                        },
                        "description": "Default flags that apply to all tools unless overridden at the tool level."
                    },
                    "version": {
                        "type": "string"
                    }
                },
                "required": [
                    "id",
                    "kind"
                ]
            }
        }
    },
    "required": [
        "schema"
    ],
    "$id": "https://schema.cjson.dev/0/toolsets/cjson-toolsets-0.1.0-SNAPSHOT.schema.json",
    "title": "Toolsets"
}
```


