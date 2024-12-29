package com.project.scrumtextprocessing.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {
    private DataManager dataManager;
    private TextEntry textEntry;

    @BeforeEach
    void setUp() {
        dataManager = new DataManager();
        textEntry = dataManager.createEntry(
                "original text",
                "processed text",
                "test pattern"
        );
    }


    @Test
    void createEntry() {
        TextEntry entry = dataManager.createEntry("test text", "processed", "[a-z]+");
        assertNotNull(entry);
        assertNotNull(entry.getId());
        assertEquals("test text", entry.getOriginalText());
        assertEquals("processed", entry.getProcessedText());
        assertEquals("[a-z]+", entry.getRegexPattern());
    }

    @Test
    void getEntryById() {
        TextEntry retrieved = dataManager.getEntryById(textEntry.getId());

        assertNotNull(retrieved);
        assertEquals(textEntry.getId(), retrieved.getId());
        assertEquals(textEntry.getOriginalText(), retrieved.getOriginalText());
        assertEquals(textEntry.getProcessedText(), retrieved.getProcessedText());
    }

    @Test
    void getAllEntries() {
        dataManager.createEntry("text2", "processed2", "pattern2");
        List<TextEntry> entries = dataManager.getAllEntries();

        assertNotNull(entries);
        assertEquals(2, entries.size());
    }

    @Test
    void searchByPattern() {
        dataManager.createEntry("another text", "processed text", "test pattern");
        List<TextEntry> matches = dataManager.searchByPattern("test pattern");

        assertNotNull(matches);
        assertEquals(2, matches.size());
    }

    @Test
    void updateEntry() {
        var updated = dataManager.updateEntry(textEntry.getId(), "new proccessed text");

        assertTrue(updated);
        assertEquals("new proccessed text", dataManager.getEntryById(textEntry.getId()).getProcessedText());
    }

    @Test
    void deleteEntry() {
        boolean deleted = dataManager.deleteEntry(textEntry.getId());

        assertTrue(deleted);
        assertNull(dataManager.getEntryById(textEntry.getId()));
        assertTrue(dataManager.getAllEntries().isEmpty());
    }
}