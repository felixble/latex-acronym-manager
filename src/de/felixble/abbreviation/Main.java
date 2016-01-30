package de.felixble.abbreviation;

import de.felixble.abbreviation.latex.AbbrTexReader;
import de.felixble.abbreviation.ui.Dialog;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String path;

        if (args.length > 0) {
            path = args[0];
        } else {
            Scanner in = new Scanner(System.in);
            System.out.print("Path to text file: ");
            path = in.nextLine().trim();
        }

        AbbrManager abbrManager = new AbbrManager();

        AbbrTexReader texReader = new AbbrTexReader(path, abbrManager);
        texReader.readFile();

        Dialog dialog = new Dialog(abbrManager, path);
        dialog.run();
    }
}
