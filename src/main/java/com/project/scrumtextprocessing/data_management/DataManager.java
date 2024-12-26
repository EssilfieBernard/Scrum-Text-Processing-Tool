package com.project.scrumtextprocessing.data_management;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataManager {
    private Map<String, TextEntry> entriesMap;
    private Set<String> uniquePatterns;
    private List<TextEntry> entriesList;
    private static final String SAVE_FILE_PATH = "text_entries.json";
    private Gson gson;

    public DataManager() {
        this.entriesMap = new HashMap<>();
        this.uniquePatterns = new HashSet<>();
        this.entriesList = new ArrayList<>();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(SAVE_FILE_PATH)) {
            JsonObject data = new JsonObject();
            data.add("entries", gson.toJsonTree(entriesList));
            data.add("patterns", gson.toJsonTree(uniquePatterns));
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        File file = new File(SAVE_FILE_PATH);
        if (!file.exists())
            return;
        try(FileReader reader = new FileReader(SAVE_FILE_PATH)) {
            JsonObject data = gson.fromJson(reader, JsonObject.class);

            //Clear current data
            entriesList.clear();
            entriesMap.clear();
            uniquePatterns.clear();

            // Load entries
            var listType = new TypeToken<ArrayList<TextEntry>>(){}.getType();
            List<TextEntry> loadedEntries = gson.fromJson(data.get("entries"), listType);
            for (TextEntry entry : loadedEntries) {
                entriesList.add(entry);
                entriesMap.put(entry.getId(), entry);
            }

            // Load patterns
            var setType = new TypeToken<HashSet<String>>(){}.getType();
            uniquePatterns = gson.fromJson(data.get("patterns"), setType);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create operation
    public TextEntry createEntry(String originalText, String processedText, String regexPattern) {
        TextEntry entry = new TextEntry(originalText, processedText, regexPattern);
        entriesMap.put(entry.getId(), entry);
        uniquePatterns.add(regexPattern);
        entriesList.add(entry);
        return entry;
    }

    // Read operations
    public TextEntry getEntryById(String id) {
        return entriesMap.get(id);
    }

    public List<TextEntry> getAllEntries() {
        return new ArrayList<>(entriesList);
    }

    public List<TextEntry> searchByPattern(String pattern) {
        return entriesList.stream()
                .filter(entry -> entry.getRegexPattern().equals(pattern))
                .collect(Collectors.toList());
    }

    // Update operation
    public boolean updateEntry(String id, String processedText) {
        TextEntry entry = entriesMap.get(id);
        if (entry != null) {
            entry.setProcessedText(processedText);
            return true;
        }
        return false;
    }

    // Delete operation
    public boolean deleteEntry(String id) {
        TextEntry entry = entriesMap.remove(id);
        if (entry != null) {
            entriesList.remove(entry);
            // Only remove pattern if no other entries use it
            if (entriesList.stream().noneMatch(e -> e.getRegexPattern().equals(entry.getRegexPattern()))) {
                uniquePatterns.remove(entry.getRegexPattern());
            }
            return true;
        }
        return false;
    }
}
