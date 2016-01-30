package de.felixble.abbreviation;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Felix on 07.01.2016.
 */
public class AbbrManager {

    private LinkedList<Abbreviation> abbreviations = new LinkedList<>();

    public void add(Abbreviation abbr) {
        if (abbreviations.contains(abbr)) {
            int i = abbreviations.indexOf(abbr);
            abbreviations.remove(i);
        }
        abbreviations.add(abbr);
    }

    public @Nullable Abbreviation getAbbreviation(String shortString) {
        int index = abbreviations.indexOf(new Abbreviation(shortString, ""));
        if (-1 == index) return null;
        return abbreviations.get(index);
    }

    public List<Abbreviation> getAbbreviations() {
        sort();
        return Collections.unmodifiableList(abbreviations);
    }

    public @Nullable Abbreviation getLongestAbbreviation() {
        int max = 0;
        Abbreviation longestAbbr = null;
        for (Abbreviation abbr : abbreviations) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = ge.getAllFonts()[0];
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            FontMetrics fm = img.getGraphics().getFontMetrics(font);
            int width = fm.stringWidth(abbr.getShortStr());

            //if (abbr.getShortStr().length() > max) {
            //    max = abbr.getShortStr().length();
            if (width > max) {
                max = width;
                longestAbbr = abbr;
            }
        }

        return longestAbbr;
    }

    public void sort() {
        abbreviations.sort((o1, o2) -> o1.getShortStr().compareTo(o2.getShortStr()));
        /*abbreviations.sort(new Comparator<Abbreviation>() {
            @Override
            public int compare(Abbreviation o1, Abbreviation o2) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font font = ge.getAllFonts()[0];
                BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                FontMetrics fm = img.getGraphics().getFontMetrics(font);
                int width = fm.stringWidth("Your string");
                return fm.stringWidth(o2.getShortStr()) - fm.stringWidth(o1.getShortStr());
            }
        });*/
    }

    public void remove(String shortStr) {
        abbreviations.remove(abbreviations.indexOf(new Abbreviation(shortStr, "")));
    }
}
