package com.project.scrumtextprocessing.controller;

import com.project.scrumtextprocessing.data_management.DataManager;
import com.project.scrumtextprocessing.data_management.TextEntry;
import com.project.scrumtextprocessing.regexUtil.RegexUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TextProcessingController implements Initializable {
    private ObservableList<TextEntry> historyEntries;
    private DataManager dataManager;

    @FXML private ListView<TextEntry> listView_history;

    @FXML private TextArea txtA_input_text; //text field for user input

    @FXML private TextArea txtA_result;  // text field for displaying results

    @FXML private TextField txt_regular_expres; // text field for regex pattern input

    @FXML private TextField txt_replace_text;  // text field for entering replacement text

    /**
     * handles the routing to data management
     * @param event the action event triggered by the Data Management
     */


    /**
     * Clears all input and output fields. Disables the results text area after clearing
     * @param event the action event triggered by the "Clear button"
     */
    @FXML
    void handleClear(ActionEvent event) {
        txt_replace_text.clear();
        txtA_result.clear();
        txt_regular_expres.clear();
        txtA_input_text.clear();
        txtA_result.setDisable(true);
    }


    /**
     * checks if the input text matches the provided regular expression
     * @param event the action event triggered by the "Match" button
     *  it also checks if any text field is empty
     */
    @FXML
    void handleMatchPattern(ActionEvent event) throws IOException{
        String pattern = txt_regular_expres.getText();
        String text = txtA_input_text.getText();
        if (pattern.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter both regex pattern and input text.");
            return;
        }

        boolean is_match = RegexUtil.isMatch(pattern, text);
        String result = is_match ? "The text matches the pattern" : "The text does not match the pattern";
        txtA_result.setText(result);
        txtA_result.setDisable(true);
        saveOperation("MATCH", result);

    }

    /**
     * Replaces occurrences of texts that match the regular expression with the specified replaced text
     * @param event the action event triggered by the "Replace" button
     * it also checks if any text field is empty
     */
    @FXML
    void handleReplace(ActionEvent event) {
        String textToReplace = txt_replace_text.getText();
        String text = txtA_input_text.getText();
        String pattern = txt_regular_expres.getText();

        if (textToReplace.isEmpty() || pattern.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter regex pattern, replacement text and input text.");
            return;
        }

        String matches = RegexUtil.replaceText(pattern, text, textToReplace);
        txtA_result.setText(matches);
        txtA_result.setDisable(true);
        saveOperation("REPLACE", matches);
    }


    /**
     * Searches for matches in the input text based on the provided regular expression
     * @param event the action event is triggered by the "Search" button
     * it also checks if any text field is empty
     */
    @FXML
    void handleSearch(ActionEvent event) {
        String pattern = txt_regular_expres.getText();
        String text = txtA_input_text.getText();
        if (pattern.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter both regex pattern and input text.");
            return;
        }

        String matches = RegexUtil.findMatches(pattern, text);
        txtA_result.setText(matches);
        txtA_result.setDisable(true);
        saveOperation("SEARCH", matches);

    }

    @FXML
    public void handleDeleteHistory(ActionEvent actionEvent) {
        TextEntry selectedEntry = listView_history.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            // Remove from data manager
            dataManager.deleteEntry(selectedEntry.getId());
            // Remove from observable list
            historyEntries.remove(selectedEntry);
            // Save changes to file
            dataManager.saveToFile();
            // Clear the text fields
            txtA_input_text.clear();
            txt_regular_expres.clear();
            txtA_result.clear();
        } else {
            JOptionPane.showMessageDialog(null, "Please select an entry to delete.");
        }
    }

    private void saveOperation(String operationType, String result) {
        TextEntry entry = dataManager.createEntry(
                txtA_input_text.getText(),
                result,
                txt_regular_expres.getText()
        );
        historyEntries.addFirst(entry); // Add to start of list
        dataManager.saveToFile();
    }

    private String truncateText(String text) {
        return text.length() > 50 ? text.substring(0, 50) + "..." : text;
    }

    private void loadHistoryEntry(TextEntry entry) {
        txtA_input_text.setText(entry.getOriginalText());
        txt_regular_expres.setText(entry.getRegexPattern());
        txtA_result.setText(entry.getProcessedText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataManager = new DataManager();
        historyEntries = FXCollections.observableArrayList();

        dataManager.loadFromFile();
        historyEntries.setAll(dataManager.getAllEntries());

        // Set up history list view
        listView_history.setItems(historyEntries);
        listView_history.setCellFactory(lv -> new ListCell<TextEntry>() {
            @Override
            protected void updateItem(TextEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText("Pattern: " + entry.getRegexPattern() +
                            "\nOriginal: " + truncateText(entry.getOriginalText()) +
                            "\nProcessed: " + truncateText(entry.getProcessedText()));
                }
            }
        });

        // Add selection listener to history list view
        listView_history.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        loadHistoryEntry(newVal);
                    }
                });
    }


}