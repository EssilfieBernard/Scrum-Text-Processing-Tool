package com.project.scrumtextprocessing.data_management;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class TextEntry {
    private String id;
    private String originalText;
    private String processedText;
    private String regexPattern;
    private Date timestamp;

    public TextEntry(String originalText, String processedText, String regexPattern) {
        this.id = UUID.randomUUID().toString();
        this.originalText = originalText;
        this.processedText = processedText;
        this.regexPattern = regexPattern;
        this.timestamp = new Date();
    }


    public String getId() { return id; }
    public String getOriginalText() { return originalText; }
    public String getProcessedText() { return processedText; }
    public String getRegexPattern() { return regexPattern; }
    public Date getTimestamp() { return timestamp; }

    public void setProcessedText(String processedText) {
        this.processedText = processedText;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextEntry textEntry = (TextEntry) o;
        return Objects.equals(id, textEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
