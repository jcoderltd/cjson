package dev.cjson.examples.schema0;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.networknt.schema.*;
import dev.cjson.models.conversation.*;
import dev.cjson.models.conversation.CompositeMessage.MessageRole;
import dev.cjson.models.models.ModelDefinition;
import dev.cjson.models.models.Models;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Example {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void main(String[] args) throws Exception {
        var models = createExampleModelDefinition();

        var model = models.getModelDefinitions().get(0);
        var conversation = createExampleConversation(model);

        var versionToUse = "0.1.0-SNAPSHOT";
        validate(conversation, "conversation/cjson-" + versionToUse + ".schema.json");
        validate(models, "models/cjson-models-" + versionToUse + ".schema.json");
        validate(createEmptyConversation(model), "conversation/cjson-" + versionToUse + ".schema.json");
        validate(createMinimalEmptyConversation(), "conversation/cjson-" + versionToUse + ".schema.json");

        System.out.println("Validation successful");
    }

    private static <T> void validate(T object, String schemaPath) throws Exception {
        var schema = loadSchema(schemaPath);
        var json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        System.out.println("JSON:\n" + json);
        var validationErrors = schema.validate(objectMapper.readTree(json));
        if (!validationErrors.isEmpty()) {
            throw new RuntimeException("Validation failed: " + validationErrors);
        }
    }

    private static Models createExampleModelDefinition() {
        var model = new ModelDefinition()
                .withId(newStringUUID())
                .withProvider("OPEN_AI")
                .withModelName("gpt-5-mini")
                .withApiKeyEnvironment("OPEN_AI_KEY");
        return new Models().withModelDefinitions(List.of(model));
    }

    private static Conversation createEmptyConversation(ModelDefinition model) {
        var conversation = new Conversation();
        conversation.withId(newStringUUID()).withModelId(model.getId()).withConversationTitle("Example Conversation");
        conversation.withSystemMessage("""
                You are an expert in helping solve problems.""");
        var messages = new ArrayList<Message>();
        conversation.withMessages(messages);
        return conversation;
    }

    private static Conversation createMinimalEmptyConversation() {
        var conversation = new Conversation();
        conversation.withId(newStringUUID());
        return conversation;
    }

    private static Conversation createExampleConversation(ModelDefinition model) {
        var conversation = new Conversation();
        conversation.withId(newStringUUID()).withModelId(model.getId()).withConversationTitle("Example Conversation");
        conversation.withSystemMessage("""
                You are an expert in helping solve problems.""");
        var messages = new ArrayList<Message>();
        conversation.withMessages(messages);

        var userMessage = new TextMessage().withId(newStringUUID()).withRole(MessageRole.USER);
        userMessage.withContent("""
                Help me explain this schema!!!""");
        messages.add(userMessage);

        var assistantMessage = new CompositeMessage().withId(newStringUUID()).withRole(MessageRole.ASSISTANT);
        assistantMessage.withContentBlocks(List.of(
                new TextBlock().withId(newStringUUID())
                        .withCreatedAt(LocalDateTime.now())
                        .withText("""
                                You're in a bit of a pickle, but you'll get there!!!""")
        ));
        messages.add(assistantMessage);
        return conversation;
    }

    private static String newStringUUID() {
        return UUID.randomUUID().toString();
    }

    private static JsonSchema loadSchema(String resourcePath) {
        var workingDir = Paths.get("").toAbsolutePath().toString().replace('\\', '/');
        var schemasDir = "file:///" + workingDir + "/cjson-schema-0/schemas/0/";

        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012, builder ->
                builder.schemaMappers(schemaMappers -> schemaMappers.mapPrefix("https://schema.cjson.dev/", schemasDir))
        );

        SchemaValidatorsConfig.Builder builder = SchemaValidatorsConfig.builder();
        SchemaValidatorsConfig config = builder.build();

        return jsonSchemaFactory.getSchema(SchemaLocation.of("https://schema.cjson.dev/" + resourcePath), config);
    }
}
