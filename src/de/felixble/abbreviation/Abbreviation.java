package de.felixble.abbreviation;

import java.util.Objects;

/**
 * Created by Felix on 07.01.2016.
 */
public class Abbreviation {

    private String shortStr;

    private String longStr;

    public Abbreviation(String shortStr, String longStr) {
        this.shortStr = shortStr;
        this.longStr = longStr;
    }

    public String getShortStr() {
        return shortStr;
    }

    public void setShortStr(String shortStr) {
        this.shortStr = shortStr;
    }

    public String getLongStr() {
        return longStr;
    }

    public void setLongStr(String longStr) {
        this.longStr = longStr;
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
