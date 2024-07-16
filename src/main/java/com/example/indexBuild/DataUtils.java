package com.example.indexBuild;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import Objects.Index;

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
    

    public static void createIndexSeries(ArrayList<Index> indexes) {
        // Date Formatter for offsetDatetime.now()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateString = OffsetDateTime.now().format(formatter);
        // New JSON Map
        Map<String, Map<String, Float>> newData = new HashMap<String, Map<String, Float>>();;
        Map<String, Float> singleIndexMap = new HashMap<String, Float>();
        for (Index index : indexes) {
            singleIndexMap.put(index.getIndexName(), index.getIndexLevel());
        }
        newData.put(dateString, singleIndexMap);

        // JSON file path
        Path path = Paths.get("src/main/resources/IndexSeries.json");
        try {
            // Read JSON File (if exists)
            Map<String, Map<String, Float>> existingData = new HashMap<String, Map<String, Float>>();
            if (Files.exists(path)) {
                ObjectMapper mapper = new ObjectMapper();
                existingData = mapper.readValue(new File(path.toString()), new TypeReference<Map<String, Map<String, Float>>>() {});
            }

            // Add the new data
            existingData.putAll(newData);
            // Convert to JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String jsonString = mapper.writeValueAsString(existingData);

            // Create Directories
            Files.createDirectories(path.getParent());
            Files.write(path, jsonString.getBytes());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}