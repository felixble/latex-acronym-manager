package de.felixble.abbreviation.latex;

import de.felixble.abbreviation.AbbrManager;
import de.felixble.abbreviation.Abbreviation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Felix on 07.01.2016.
 */
public class AbbrTextWriter {

    public static final String HEADER =
            "\\svnid{$Id$}\n" +
            "%*******************************************************\n" +
            "% Abkürzungsverzeichnis\n" +
            "%*******************************************************\n" +
            "\\chapter*{Abkürzungsverzeichnis}\n" +
            "\\addcontentsline{toc}{chapter}{Abkürzungsverzeichnis}\t\n" +
            "\t%Hier alle benötigten Abkürzungen einfügen\n";

    public static final String BEGIN =
            "\t\\begin{acronym}[%s] %% LONGEST ACRONYM HERE FOR CORRECT SPACING\n";

    private static final String ABBR_STRING = "\t\t\\acro{%s}{%s}\n";
    private static final String END = "\t\\end{acronym}\n";

    private String path;
    private AbbrManager abbrManager;

    public AbbrTextWriter(String path, AbbrManager abbrManager) {
        this.path = path;
        this.abbrManager = abbrManager;
    }

    public void writeFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path, AbbrTexReader.ENCODING);

        writer.write(HEADER);
        writer.write(String.format(BEGIN, abbrManager.getLongestAbbreviation().getShortStr()));

        List<Abbreviation> abbrs = abbrManager.getAbbreviations();
        for (Abbreviation abbr : abbrs) {
            writer.write(
                    String.format(ABBR_STRING, abbr.getShortStr(), abbr.getLongStr())
            );
        }

        writer.write(END);


        writer.close();
    }
}
