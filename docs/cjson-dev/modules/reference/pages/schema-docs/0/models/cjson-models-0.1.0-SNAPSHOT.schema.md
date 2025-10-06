

# Models

<p>A list of model definitions defined/enabled by the user.
</p>

<table>
<tbody>
<tr><th>$id</th><td>https://schema.cjson.dev/0/models/cjson-models-0.1.0-SNAPSHOT.schema.json</td></tr>
<tr><th>$schema</th><td>https://json-schema.org/draft/2020-12/schema</td></tr>
</tbody>
</table>

## Properties

<table class="jssd-properties-table"><thead><tr><th colspan="2">Name</th><th>Type</th></tr></thead><tbody><tr><td colspan="2"><a href="#mediatype">mediaType</a></td><td>String</td></tr><tr><td colspan="2"><a href="#modeldefinitions">modelDefinitions</a></td><td>Array</td></tr><tr><td colspan="2"><a href="#schemaurl">schemaUrl</a></td><td>String</td></tr></tbody></table>



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
      <td colspan="2">application/vnd.cjson-models+json</td>
    </tr>
    
  </tbody>
</table>




## modelDefinitions


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">Array</td></tr>
    <tr>
      <th>Required</th>
      <td colspan="2">No</td>
    </tr>
    
  </tbody>
</table>



### modelDefinitions.apiKeyEnvironment


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">The name of the environment variable name that contains the API key for this model/integration.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>




### modelDefinitions.baseUrl


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Optional url to be used to send requests to this model.

The URL is optional as certain integrations with some model providers already add a default URL.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>




### modelDefinitions.disabled


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Indicates if the model is disabled. This value is false by default.

Disabled models SHOULD NOT be allowed to be used/selected in the application.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Boolean</td></tr>
    
  </tbody>
</table>




### modelDefinitions.displayName


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>




### modelDefinitions.extensions


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Extensions can be used to add custom application-specific data to the model definition.

Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>




### modelDefinitions.headers


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">Additional headers to be included in the HTTP requests to the model.

Applications are encouraged, although not enforced, to support environment variable injection into the
header values and other injection mechanisms that allow for secret passing.

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




### modelDefinitions.id


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>




### modelDefinitions.modelName


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>




### modelDefinitions.parameters


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">Object</td></tr>
    
  </tbody>
</table>




### modelDefinitions.provider


<table class="jssd-property-table">
  <tbody>
    <tr>
      <th>Description</th>
      <td colspan="2">The name of the provider of the model.

The &#x27;provider&#x27; is not standard due to:
* new providers that can appear at any time and existent providers that can change.
* different applications using different ways of naming the providers.

We recommend, but don&#x27;t enforce, the use of the uppercase name of the organization/tool that provides it,
with underscore as whitespace replacement, for example: &quot;OPEN_AI&quot;, &quot;ANTHROPIC&quot;, &quot;OLLAMA&quot;, &quot;LM_STUDIO&quot;.

Applications are free to decide the models they integrate/offer to their users.
</td>
    </tr>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    
  </tbody>
</table>





## schemaUrl


<table class="jssd-property-table">
  <tbody>
    <tr><th>Type</th><td colspan="2">String</td></tr>
    <tr>
      <th>Required</th>
      <td colspan="2">Yes</td>
    </tr>
    <tr>
      <th>Default</th>
      <td colspan="2">https://schema.cjson.dev/0/models/cjson-models-0.1.0-SNAPSHOT.schema.json</td>
    </tr>
    
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
            "default": "application/vnd.cjson-models+json"
        },
        "modelDefinitions": {
            "type": "array",
            "items": {
                "type": "object",
                "properties": {
                    "apiKeyEnvironment": {
                        "type": "string",
                        "description": "The name of the environment variable name that contains the API key for this model/integration.\n"
                    },
                    "baseUrl": {
                        "type": "string",
                        "description": "Optional url to be used to send requests to this model.\n\nThe URL is optional as certain integrations with some model providers already add a default URL.\n"
                    },
                    "disabled": {
                        "type": "boolean",
                        "description": "Indicates if the model is disabled. This value is false by default.\n\nDisabled models SHOULD NOT be allowed to be used/selected in the application.\n"
                    },
                    "displayName": {
                        "type": "string"
                    },
                    "extensions": {
                        "type": "object",
                        "additionalProperties": true,
                        "description": "Extensions can be used to add custom application-specific data to the model definition.\n\nExtensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.\n",
                        "existingJavaType": "java.util.Map<java.lang.String, java.lang.Object>"
                    },
                    "headers": {
                        "type": "object",
                        "additionalProperties": true,
                        "description": "Additional headers to be included in the HTTP requests to the model.\n\nApplications are encouraged, although not enforced, to support environment variable injection into the\nheader values and other injection mechanisms that allow for secret passing.\n\nAs an example, a \"token\" header with a \"${env:MODEL_TOKEN}\" would inject the value of the environment\nvariable \"MODEL_TOKEN\" into the \"token\" header.\n\nApplications MUST NOT store plain-text credentials here and should use references to secret stores\nor environment variable names.\n\nFor secret referencing, we recommend a \"${secret:SECRET_NAME}\" that is similar to the \"env\" case, but\nis up to the application to decide where they fetch the secrets from.\n",
                        "existingJavaType": "java.util.Map<java.lang.String, java.lang.String>"
                    },
                    "id": {
                        "type": "string"
                    },
                    "modelName": {
                        "type": "string"
                    },
                    "parameters": {
                        "type": "object",
                        "additionalProperties": true,
                        "existingJavaType": "java.util.Map<java.lang.String, java.lang.String>"
                    },
                    "provider": {
                        "type": "string",
                        "description": "The name of the provider of the model.\n\nThe 'provider' is not standard due to:\n* new providers that can appear at any time and existent providers that can change.\n* different applications using different ways of naming the providers.\n\nWe recommend, but don't enforce, the use of the uppercase name of the organization/tool that provides it,\nwith underscore as whitespace replacement, for example: \"OPEN_AI\", \"ANTHROPIC\", \"OLLAMA\", \"LM_STUDIO\".\n\nApplications are free to decide the models they integrate/offer to their users.\n"
                    }
                },
                "required": [
                    "id",
                    "modelName",
                    "provider"
                ]
            }
        },
        "schemaUrl": {
            "type": "string",
            "default": "https://schema.cjson.dev/0/models/cjson-models-0.1.0-SNAPSHOT.schema.json"
        }
    },
    "required": [
        "schemaUrl"
    ],
    "$id": "https://schema.cjson.dev/0/models/cjson-models-0.1.0-SNAPSHOT.schema.json",
    "title": "Models",
    "description": "A list of model definitions defined/enabled by the user.\n"
}
```


