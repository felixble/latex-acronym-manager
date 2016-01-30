package de.felixble.abbreviation;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Manages all abbreviations.
 *
 * Created by Felix on 07.01.2016.
 */
public class AbbrManager {

    private LinkedList<Abbreviation> abbreviations = new LinkedList<>();

    /**
     * Add a new abbreviation.
     *
     * @param abbr new abbreviation
     */
    public void add(Abbreviation abbr) {
        if (abbreviations.contains(abbr)) {
            int i = abbreviations.indexOf(abbr);
            abbreviations.remove(i);
        }
        abbreviations.add(abbr);
    }

    /**
     * Returns the abbreviation represented by the given short form.
     *
     * TODO: there could be multiple ones represented by the same string.
     *
     * @param shortString
     * @return abbreviation represented by the given short form.
     */
    public @Nullable Abbreviation getAbbreviation(String shortString) {
        int index = abbreviations.indexOf(new Abbreviation(shortString, ""));
        if (-1 == index) return null;
        return abbreviations.get(index);
    }

    /**
     * Returns all abbreviations alphabetically ordered.
     *
     * @return all abbreviations.
     */
    public List<Abbreviation> getAbbreviations() {
        sort();
        return Collections.unmodifiableList(abbreviations);
    }

    /**
     * Returns the longest abbreviation depending on the font family.
     *
     * @return the longest abbreviation.
     */
    public @Nullable Abbreviation getLongestAbbreviation() {
        int max = 0;
        Abbreviation longestAbbr = null;
        for (Abbreviation abbr : abbreviations) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // TODO: maybe we should offer a possibility to change the font family.
            Font font = ge.getAllFonts()[0];
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            FontMetrics fm = img.getGraphics().getFontMetrics(font);
            int width = fm.stringWidth(abbr.getShortStr());

            if (width > max) {
                max = width;
                longestAbbr = abbr;
            }
        }

        return longestAbbr;
    }

    public void sort() {
        abbreviations.sort((o1, o2) -> o1.getShortStr().compareTo(o2.getShortStr()));
    }

    /**
     * Removes the abbreviation identified by the given short string.
     *
     * TODO: there could be multiple ones represented by the same string.
     *
     * @param shortStr short string of the abbreviation which should be deleted.
     */
    public void remove(String shortStr) {
        abbreviations.remove(abbreviations.indexOf(new Abbreviation(shortStr, "")));
    }
}
