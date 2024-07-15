package com.example.indexBuild;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataUtils {
	
    // Indexes Saving Directory
    private static final String JSON_FILE_PATH = "src/main/resources/Indexes.json";
    
    public static void updateJSONFile(String indexName, String jsonString, String action) throws Exception {
        File jsonFile = new File(JSON_FILE_PATH);
        ObjectNode rootNode;
        ObjectMapper mapper = new ObjectMapper();

        // Read the Existing Values, if any
        if (jsonFile.exists() && jsonFile.length() > 0) {
            try (InputStream inputStream = Files.newInputStream(Paths.get(JSON_FILE_PATH))) {
                JsonNode root = mapper.readTree(inputStream);
                if (root instanceof ObjectNode) {
                    rootNode = (ObjectNode) root;
                } else {
                    rootNode = mapper.createObjectNode();
                }
            }
        } else {
            rootNode = mapper.createObjectNode();
        }

        // Update the JSON file according to the key specified
        if (action.equals("create")) {
            JsonNode indexNode = mapper.readTree(jsonString);
            rootNode.set(indexName, indexNode);
        }
        if (action.equals("delete")) {
            rootNode.remove(indexName);
        }

        // Save to JSON with indentation
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(jsonFile, rootNode);
    }
    
    // class to convert from String to OffsetDatetime
    public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String dateString = p.getText();
            return OffsetDateTime.parse(dateString, formatter);
        }
    }
	
}