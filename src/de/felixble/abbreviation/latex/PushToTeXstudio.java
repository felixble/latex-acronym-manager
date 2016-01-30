package de.felixble.abbreviation.latex;


import java.io.IOException;

/**
 * Created by Felix on 09.01.2016.
 */
public class PushToTeXstudio {

    private String programmPath;

    public PushToTeXstudio() {
        generateProgrammPath();
    }

    private void generateProgrammPath() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            String progFiles = System.getenv("ProgramFiles(x86)");
            if(progFiles == null) {
                progFiles = System.getenv("ProgramFiles");
            }

            programmPath = progFiles + "\\texstudio\\texstudio.exe";
        } else {
            programmPath = "texstudio";
        }
    }

    public int pushCommand(String command, String cmdArg) throws IOException, InterruptedException {
        String[] cmd = new String[] {
                programmPath,
                "--insert-cite",
                command + "{" + cmdArg + "}"
        };
        Process process = Runtime.getRuntime().exec(cmd);
        return process.waitFor();

    }

}
