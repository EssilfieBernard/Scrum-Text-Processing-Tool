package com.project.scrumtextprocessing.regexUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilTest {

    @Test
    void findMatches_WithValidPattern() {
        String result = RegexUtil.findMatches("\\d+", "abc123def456");
        String expected = "Match: 123 at position 3\nMatch: 456 at position 9\n";
        assertEquals(expected, result);
    }

    @Test
    void findMatches_NoMatches() {
        String result = RegexUtil.findMatches("\\d+", "abcdef");
        assertEquals("No matches found", result);
    }

    @Test
    void findMatches_WithEmptyText() {
        String result = RegexUtil.findMatches("\\d+", "");
        assertEquals("No matches found", result);
    }

    @Test
    void replaceText_BasicReplacement() {
        String result = RegexUtil.replaceText("cat", "The cat and another cat", "dog");
        assertEquals("The dog and another dog", result);
    }

    @Test
    void replaceText_NoMatches() {
        String result = RegexUtil.replaceText("cat", "The dog and another dog", "bird");
        assertEquals("The dog and another dog", result);
    }

    @Test
    void replaceText_WithEmptyReplacement() {
        String result = RegexUtil.replaceText("cat", "The cat and another cat", "");
        assertEquals("The  and another ", result);
    }

    @Test
    void isMatch_ValidPattern() {
        assertTrue(RegexUtil.isMatch("\\d+", "123"));
        assertTrue(RegexUtil.isMatch("[a-z]+", "abc"));
    }

    @Test
    void isMatch_InvalidPattern() {
        assertFalse(RegexUtil.isMatch("\\d+", "abc"));
        assertFalse(RegexUtil.isMatch("[a-z]+", "123"));
    }

    @Test
    void isMatch_NullInputs() {
        assertFalse(RegexUtil.isMatch(null, "test"));
        assertFalse(RegexUtil.isMatch("test", null));
        assertFalse(RegexUtil.isMatch(null, null));
    }

    @Test
    void isMatch_EmptyInputs() {
        assertTrue(RegexUtil.isMatch("", ""));
        assertFalse(RegexUtil.isMatch("", "test"));
    }
}