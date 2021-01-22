package dev.rakeshmistry.server;

public class Command {
    public Commands command;
    public String value;

    public Command(Commands command, String value) {
        this.command = command;
        this.value = value;
    }

    public static Command parse(String command) {
        String parsedCommand  = command.substring(0, command.indexOf(" "));
        String parsedValue = command.substring(command.indexOf(" ")+1);
        return new Command(Commands.valueOf(parsedCommand), parsedValue);
    }
}
