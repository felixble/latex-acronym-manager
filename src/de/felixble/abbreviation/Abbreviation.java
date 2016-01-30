package de.felixble.abbreviation;

import java.util.Objects;

/**
 * Object represents a abbreviation.
 *
 * Created by Felix on 07.01.2016.
 */
public class Abbreviation {

    /**
     * The abbreviation.
     */
    private String shortStr;

    /**
     * The written-out meaning.
     */
    private String longStr;

    /**
     * Create a new abbreviation from its short and
     * long form.
     *
     * @param shortStr abbreviation
     * @param longStr written-out meaning
     */
    public Abbreviation(String shortStr, String longStr) {
        this.shortStr = shortStr;
        this.longStr = longStr;
    }

    public String getShortStr() {
        return shortStr;
    }

    public String getLongStr() {
        return longStr;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", shortStr, longStr);
    }

    @Override
    public int hashCode() {
        return shortStr.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Abbreviation)
                && Objects.equals(((Abbreviation) obj).shortStr.toUpperCase(), shortStr.toUpperCase());
    }
}
