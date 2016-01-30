package de.felixble.abbreviation.latex;


/**
 * Created by Felix on 07.01.2016.
 */
public class AbbrLineParser {

    private static int STATUS_IDLE = 0;
    private static int STATUS_READ = 1;

    private String line;
    private char currentChar;
    private String currentWord = "";

    private String shortStr = null;
    private String longStr = null;

    private int status = STATUS_IDLE;

    public AbbrLineParser(String line) {
        this.line = line;
        parseLine();
    }

    private void parseLine() {
        for (int i = 0; i < line.length(); i++) {
            currentChar = line.charAt(i);
            parseToken();
        }
    }

    private void parseToken() {
        switch (currentChar) {
            case '{':
                status = STATUS_READ;
                break;
            case '}':
                status = STATUS_IDLE;
                addWord(currentWord);
                //quotationOpen = !quotationOpen;
                break;
            default:
                if (status != STATUS_IDLE) {
                    currentWord += currentChar;
                }
                break;
        }
    }

    private void addWord(String word) {
        if (word.isEmpty()) {
            return;
        }

        if (null == shortStr) {
            shortStr = word;
        } else {
            longStr = word;
        }
        currentWord = "";
    }

    public String getShortStr() {
        return shortStr;
    }

    public String getLongStr() {
        return longStr;
    }
}
