package de.felixble.abbreviation.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Base class for a command like user dialog.
 * <br>
 * This class can be extended by specific implementation of the command methods.
 * Each command method must have the {@link Command} annotation.
 *
 * Created by Felix on 05.01.2016.
 */
public class BasicUserDialog {

    private static final String CMD_EXIT = "exit";
    private static final String CMD_USAGE_1 = "?";
    private static final String CMD_USAGE_2 = "help";

    private class CmdInfo {
        private final Method method;
        private String usage;
        private String[] defaultValues = null;

        public CmdInfo(Method method) {
            this.method = method;
        }
    }

    private HashMap<String, CmdInfo> commands = new HashMap<>();

    private boolean goon = true;

    public BasicUserDialog() {
        init();
    }

    private void init() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            addCommand(method);
        }
    }

    public void run() {
        while (goon) {
            promptForCommandAndExecute();
        }
    }

    private void addCommand(Method method) {
        String cmd = method.getName();
        CmdInfo info = new CmdInfo(method);
        String usage;

        Command cmdAnnotation = method.getAnnotation(Command.class);
        if (null != cmdAnnotation) {
            if (cmdAnnotation.cmd() != null && !cmdAnnotation.cmd().isEmpty()) {
                cmd = cmdAnnotation.cmd();
            }

            if (cmdAnnotation.defaultVal().length != 0) {
                info.defaultValues = cmdAnnotation.defaultVal();
            }

            if (!cmdAnnotation.usage().isEmpty()) {
                usage = " " + cmdAnnotation.usage();
            } else {
                usage = "";
                // generate usage by parameters -> arg0 arg1 ...
                Parameter[] params = method.getParameters();
                for (Parameter param : params) {
                    usage += " " + param.getName();
                }
            }

            if (usage.isEmpty()) {
                info.usage = cmd;
            } else {
                info.usage = cmd + ":" + usage;
            }

            commands.put(cmd, info);
        }
    }

    /**
     * <p>Prompts the user to input a command with its parameters.
     * The command will be executed by the next newline token.</p>
     * <p>Format of the user input: &lt;command&gt; [parameters...]</p>
     * <p>
     *     Command and parameters are divided by whitespace.<br>
     *     Parameters with newline may be quoted by quotation marks.
     * </p>
     */
    private void promptForCommandAndExecute() {

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        // prompt user for input
        System.out.println("Enter command: ");

        CommandParser parser = new CommandParser();

        boolean inputParsed = false;
        while (!inputParsed) {
            //  prompt for the user's name
            String commandWithParams =  scanner.nextLine();

            inputParsed = parser.parse(commandWithParams);
        }

        CmdInfo command = commands.get(parser.getCommand());

        if (!executeSpecialCommand(parser.getCommand())) {
            executeCommand(command, parser.getArgs());
        }

    }

    private boolean executeSpecialCommand(String command) {
        switch (command) {
            case CMD_EXIT:
                goon = false;
                onExitDialog();
                return true;

            case CMD_USAGE_1:
            case CMD_USAGE_2:
                for (CmdInfo cmdInfo : commands.values()) {
                    System.out.println(cmdInfo.usage);
                }
                System.out.println(CMD_USAGE_2 + ": print usage");
                System.out.println(CMD_USAGE_1 + ": print usage");
                System.out.println(CMD_EXIT + ": exit program");
                return true;

            default:
                return false;
        }
    }

    private void executeCommand(CmdInfo command, String[] args) {
        try {
            if (null == command) {
                throw new IllegalArgumentException("Invalid command");
            }

            Method method = command.method;
            List<Object> methodArgs = new LinkedList<>();

            Parameter[] params = method.getParameters();
            List<String> allArgs = appendDefaultValues(command, args, method.getParameterCount());
            assertArgCount(params, allArgs);
            int i = 0;
            for (Parameter param : params) {
                Class<?> type = param.getType();

                if(type.equals(Integer.class) || type.equals(int.class)) {
                    methodArgs.add(Integer.valueOf(allArgs.get(i)));
                } else if(type.equals(Long.class) || type.equals(long.class)) {
                    methodArgs.add(Long.valueOf(allArgs.get(i)));
                } else if(type.equals(boolean.class)) {
                    methodArgs.add("true".equals(allArgs.get(i)));
                } else {
                    methodArgs.add(allArgs.get(i));
                }

                i++;
            }
            Object result = method.invoke(this, methodArgs.toArray());
            if (null != result) {
                System.out.println(result);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (null != e.getCause()) {
                System.out.println("ERR: " + e.getCause().getMessage());
                System.out.println(command.usage);
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("ERR: " + e.getMessage());
            if (null != command) {
                System.out.println(command.usage);
            }
        }
    }

    private List<String> appendDefaultValues(CmdInfo command, String[] args, int paramCount) {
        List<String> allArgs = new LinkedList<>();
        Collections.addAll(allArgs, args);

        if (null != command.defaultValues) {
            int missingParams = paramCount - args.length;

            int start = command.defaultValues.length - missingParams;
            if (start >= 0) {
                allArgs.addAll(Arrays.asList(command.defaultValues).subList(start, command.defaultValues.length));
            }
        }

        return allArgs;
    }

    protected void onExitDialog() {}

    private void assertArgCount(Parameter[] params, List<String> args) {
        if (params.length != args.size()) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
    }

}
