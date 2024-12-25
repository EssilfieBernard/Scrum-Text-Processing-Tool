package com.project.scrumtextprocessing.controller;

import com.project.scrumtextprocessing.regexUtil.RegexUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class TextProcessingController {




        @FXML
        private Button bttn_clear; // button use to clear all input and output fields

        @FXML
        private Button bttn_match;  // button use to check if a text matches a regular expression

        @FXML
        private Button bttn_replace; // button use to replace text based on regex pattern

        @FXML
        private Button bttn_search; // button use to search for matches in the text based on a regex pattern

        @FXML
        private TextArea txtA_input_text; //text field for user input

        @FXML
        private TextArea txtA_result;  // text field for displaying results

        @FXML
        private TextField txt_regular_expres; // text field for regex pattern input

        @FXML
        private TextField txt_replace_text;  // text field for entering replacement text





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

            if(pattern.isEmpty() || text.isEmpty())
                JOptionPane.showMessageDialog(null, "Either the pattern or text field is empty");

            boolean is_match = RegexUtil.isMatch(pattern, text);
            String result = is_match ? "The text matches the pattern" : "The text does not match the pattern";
            txtA_result.setText(result);
            txtA_result.setDisable(true);


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

            if(textToReplace.isEmpty() || text.isEmpty() || pattern.isEmpty())
                JOptionPane.showMessageDialog(null, "Text fields cannot be empty");
            String matches = RegexUtil.replaceText(pattern, text, textToReplace);
            txtA_result.setText(matches);
            txtA_result.setDisable(true);
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
            if(pattern.isEmpty() || text.isEmpty())
                JOptionPane.showMessageDialog(null, "Either the pattern or text field is empty");
            RegexUtil RegexUtils = null;
            String matches = RegexUtils.findMatches(pattern, text);
            txtA_result.setText(matches);
            txtA_result.setDisable(true);

        }

    }

