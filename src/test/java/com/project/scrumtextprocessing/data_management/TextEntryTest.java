package com.project.scrumtextprocessing.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TextEntryTest {
    private TextEntry textEntry;
    private final String TEST_TEXT = "test text";
    private final String TEST_PROCESSED = "processed text";
    private final String TEST_PATTERN = "test pattern";

    @BeforeEach
    void setUp() {
        textEntry = new TextEntry(TEST_TEXT, TEST_PROCESSED, TEST_PATTERN);
    }

    @Test
    void constructor_ShouldInitializeAllFields() {
        assertNotNull(textEntry.getId());
        assertEquals(TEST_TEXT, textEntry.getOriginalText());
        assertEquals(TEST_PROCESSED, textEntry.getProcessedText());
        assertEquals(TEST_PATTERN, textEntry.getRegexPattern());
        assertNotNull(textEntry.getTimestamp());
    }

    @Test
    void getId_ShouldReturnCorrectId() {
        String id = "test-id";
        textEntry.setId(id);
        assertEquals(id, textEntry.getId());
    }

    @Test
    void getOriginalText_ShouldReturnCorrectText() {
        assertEquals(TEST_TEXT, textEntry.getOriginalText());
    }

    @Test
    void getProcessedText_ShouldReturnCorrectText() {
        assertEquals(TEST_PROCESSED, textEntry.getProcessedText());
    }

    @Test
    void getRegexPattern_ShouldReturnCorrectPattern() {
        assertEquals(TEST_PATTERN, textEntry.getRegexPattern());
    }

    @Test
    void getTimestamp_ShouldReturnCorrectTimestamp() {
        Date now = new Date();
        textEntry.setTimestamp(now);
        assertEquals(now, textEntry.getTimestamp());
    }

    @Test
    void setProcessedText_ShouldUpdateProcessedText() {
        String newText = "new processed text";
        textEntry.setProcessedText(newText);
        assertEquals(newText, textEntry.getProcessedText());
    }

    @Test
    void setId_ShouldUpdateId() {
        String newId = "new-id";
        textEntry.setId(newId);
        assertEquals(newId, textEntry.getId());
    }

    @Test
    void setOriginalText_ShouldUpdateOriginalText() {
        String newText = "new original text";
        textEntry.setOriginalText(newText);
        assertEquals(newText, textEntry.getOriginalText());
    }

    @Test
    void setRegexPattern_ShouldUpdatePattern() {
        String newPattern = "new pattern";
        textEntry.setRegexPattern(newPattern);
        assertEquals(newPattern, textEntry.getRegexPattern());
    }

    @Test
    void setTimestamp_ShouldUpdateTimestamp() {
        Date newDate = new Date();
        textEntry.setTimestamp(newDate);
        assertEquals(newDate, textEntry.getTimestamp());
    }

    @Test
    void equals_ShouldReturnTrueForSameId() {
        TextEntry other = new TextEntry();
        other.setId(textEntry.getId());
        assertEquals(textEntry, other);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentId() {
        TextEntry other = new TextEntry();
        other.setId("different-id");
        assertNotEquals(textEntry, other);
    }

    @Test
    void hashCode_ShouldBeConsistentWithEquals() {
        TextEntry other = new TextEntry();
        other.setId(textEntry.getId());
        assertEquals(textEntry.hashCode(), other.hashCode());
    }
}