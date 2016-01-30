package de.felixble.abbreviation.ui;

import de.felixble.abbreviation.AbbrManager;
import de.felixble.abbreviation.Abbreviation;
import de.felixble.abbreviation.latex.AbbrTextWriter;
import de.felixble.abbreviation.latex.PushToTeXstudio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * User - program interface.
 *
 * Created by Felix on 07.01.2016.
 */
public class Dialog extends BasicUserDialog {

    private PushToTeXstudio pushToTeXstudio;

    private AbbrManager abbrManager;

    private String filePath;

    public Dialog(AbbrManager abbrManager, String filePath) {
        this.abbrManager = abbrManager;
        this.filePath = filePath;
    }

    @SuppressWarnings("unused")
    @Command(cmd = "add", usage = "short long [-e]", defaultVal = "\"\"")
    public void addAbbreviation(String shortStr, String longStr, String export) throws IOException, InterruptedException {
        abbrManager.add(new Abbreviation(shortStr, longStr));
        if (export.equals("-e")) {
            exportToTexStudio(shortStr, "\"\"");
        }
    }

    @SuppressWarnings("unused")
    @Command(cmd = "rm", usage = "short")
    public void removeAbbreviation(String shortStr) {
        abbrManager.remove(shortStr);
    }

    @SuppressWarnings("unused")
    @Command(cmd = "list")
    public void listAbbreviations() {
        List<Abbreviation> abbrs = abbrManager.getAbbreviations();
        for (Abbreviation abbr : abbrs) {
            System.out.println(abbr);
        }
    }

    @SuppressWarnings("unused")
    @Command(cmd = "get")
    public void getAbbreviation(String shortStr) {
        Abbreviation abbreviation = abbrManager.getAbbreviation(shortStr);
        if (null != abbreviation) {
            System.out.println(abbreviation);
        }
    }

    @SuppressWarnings("unused")
    @Command(cmd = "longest")
    public void getLongestAbbreviation() {
        System.out.println(abbrManager.getLongestAbbreviation());
    }

    @SuppressWarnings("unused")
    @Command
    public void make() throws FileNotFoundException, UnsupportedEncodingException {
        AbbrTextWriter writer = new AbbrTextWriter(filePath, abbrManager);
        writer.writeFile();
    }

    @SuppressWarnings("unused")
    @Command(cmd = "export", usage = "abbreviation, [-p]", defaultVal = "\"\"")
    public void exportToTexStudio(String abbr, String plural) throws IOException, InterruptedException {
        if (null == pushToTeXstudio) {
            pushToTeXstudio = new PushToTeXstudio();
        }

        String cmd = "\\" + ((plural.equals("-p")) ? "acs" : "ac");

        int result = pushToTeXstudio.pushCommand(cmd, abbr);
        System.out.println("Result: " + result);
    }

}
