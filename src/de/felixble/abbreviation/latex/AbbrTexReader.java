package de.felixble.abbreviation.latex;

import de.felixble.abbreviation.AbbrManager;
import de.felixble.abbreviation.Abbreviation;

import java.io.*;
import java.util.Objects;

/**
 * Class to read the abbreviation tex-file.
 *
 * This reader does only support abbreviation files where
 * each abbreviation is declared in one line.
 *
 * TODO: support more than one abbreviation declaration in a line.
 *
 * Created by Felix on 07.01.2016.
 */
public class AbbrTexReader {

    /**
     * File encoding
     * TODO: Settings to change the encoding.
     */
    public static final String ENCODING = "ISO-8859-1";

    /**
     * Constant string to find the beginning of a declaration.
     */
    private static final String ACRO_LINE_START = "\\acro{";

    /**
     * Path to the encoding file.
     */
    private String path;

    /**
     * Manager to add the parsed abbreviations.
     */
    private AbbrManager abbrManager;

    /**
     * Constructor to create a new AbbrTexReader.
     *
     * @param path Path to the abbreviation tex-file.
     * @param abbrManager Manager to add the abbreviations.
     */
    public AbbrTexReader(String path, AbbrManager abbrManager) {
        this.path = path;
        this.abbrManager = abbrManager;
    }

    /**
     * Reads the tex-file and adds all abbreviations.
     *
     * @throws IOException
     */
    public void readFile() throws IOException {
        File file = new File(path);
        if (!file.exists()) return;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            // read the line iff it starts like a abbreviation declaration
            if (line.length() >= ACRO_LINE_START.length() && Objects.equals(line.substring(0, ACRO_LINE_START.length()), ACRO_LINE_START)) {
                readAcroLine(line);
            }
        }

    }

    /**
     * Reads a abbreviation declaration line.
     *
     * @param line the line which represents a abbreviation declaration.
     */
    private void readAcroLine(String line) {
        AbbrLineParser parser = new AbbrLineParser(line);
        abbrManager.add(new Abbreviation(parser.getShortStr(), parser.getLongStr()));
    }
}
