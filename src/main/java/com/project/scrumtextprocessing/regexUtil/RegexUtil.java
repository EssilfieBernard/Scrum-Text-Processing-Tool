package com.project.scrumtextprocessing.regexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *Utility class for performing regular expression operations such as pattern matching,
 * text replacement, and finding matches in a given input string.
 */
public class RegexUtil {

    /**
     *  Finds all matches of the given regular expression in the input text and returns details about each match.
     *
     * @param pattern  Finds all matches of the given regular expression in the input text and returns details about each match.
     * @param text the input text to search in
     * @return a string containing all matches and their positions, or "No matches found" if no match is found
     */
    public static String findMatches(String pattern, String text){
        StringBuilder results = new StringBuilder();
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);

        while (matcher.find()){
            results.append("Match: ").append(matcher.group()).append(" at position ").append(matcher.start()).append("\n");
        }
        return !results.isEmpty() ? results.toString() : "No matches found";
    }


    /**
     * Replaces all occurrences of the given regular expression in the input text with the specified replacement text.
     *
     * @param pattern the regular expression to search for
     * @param text the input text where replacements will be applied
     * @param replacement the replacement text to use for matches
     * @return the input text with all matches replaced by the replacement text
     */
    public static String replaceText(String pattern, String text, String replacement){
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);
        return matcher.replaceAll(replacement);
    }

    // for finding match
    public static boolean isMatch(String pattern, String text)
    {
        if(pattern == null || text == null){
            return false;
        }
        return Pattern.matches(pattern, text);
    }

}
