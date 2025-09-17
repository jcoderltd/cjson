package dev.cjson.schema.types.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record ModelDefinition(
        @NotNull String id,

        String displayName,

        @Schema(description = """
                The name of the provider of the model.
                
                The 'provider' is not standard due to:
                * new providers that can appear at any time and existent providers that can change.
                * different applications using different ways of naming the providers.
                
                We recommend, but don't enforce, the use of the uppercase name of the organization/tool that provides it,
                with underscore as whitespace replacement, for example: "OPEN_AI", "ANTHROPIC", "OLLAMA", "LM_STUDIO".
                
                Applications are free to decide the models they integrate/offer to their users.
                """)
        @NotNull String provider,

        @NotNull String modelName,

        @Schema(description = """
                Optional url to be used to send requests to this model.
                
                The URL is optional as certain integrations with some model providers already add a default URL.
                """)
        String baseUrl,

        @Schema(description = """
                Indicates if the model is disabled. This value is false by default.
                
                Disabled models SHOULD NOT be allowed to be used/selected in the application.
                """)
        Boolean disabled,

        @Schema(description = """
                The name of the environment variable name that contains the API key for this model/integration.
                """)
        String apiKeyEnvironment,

        Map<String, String> parameters,

        @Schema(description = """
                Additional headers to be included in the HTTP requests to the model.
                
                Applications are encouraged, although not enforced, to support environment variable injection into the
                header values and other injection mechanisms that allow for secret passing.
                
                As an example, a "token" header with a "${env:MODEL_TOKEN}" would inject the value of the environment
                variable "MODEL_TOKEN" into the "token" header.
                
                Applications MUST NOT store plain-text credentials here and should use references to secret stores
                or environment variable names.
                
                For secret referencing, we recommend a "${secret:SECRET_NAME}" that is similar to the "env" case, but
                is up to the application to decide where they fetch the secrets from.
                """)
        Map<String, String> headers,

        @Schema(description = """
                Extensions can be used to add custom application-specific data to the model definition.
                
                Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
                """)
        Map<String, Object> extensions
) {
}
