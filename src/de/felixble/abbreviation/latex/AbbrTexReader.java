package de.felixble.abbreviation.latex;

import de.felixble.abbreviation.AbbrManager;
import de.felixble.abbreviation.Abbreviation;

import java.io.*;
import java.util.Objects;

/**
 * Created by Felix on 07.01.2016.
 */
public class AbbrTexReader {

    public static final String ENCODING = "ISO-8859-1";

    private static final String ACRO_LINE_START = "\\acro{";
    private String path;

    private AbbrManager abbrManager;

    public AbbrTexReader(String path, AbbrManager abbrManager) {
        this.path = path;
        this.abbrManager = abbrManager;
    }

    public void readFile() throws IOException {
        File file = new File(path);
        if (!file.exists()) return;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.length() >= ACRO_LINE_START.length() && Objects.equals(line.substring(0, ACRO_LINE_START.length()), ACRO_LINE_START)) {
                readAcroLine(line);
            }
        }

    }

    private void readAcroLine(String line) {
        AbbrLineParser parser = new AbbrLineParser(line);
        abbrManager.add(new Abbreviation(parser.getShortStr(), parser.getLongStr()));
    }
}
