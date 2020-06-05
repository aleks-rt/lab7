package commands;

import communication.Argument;

import java.util.ArrayList;

public class CommandExit extends Command {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getManual() {
        return "Завершить клиентское приложение.";
    }

    @Override
    public String executeWithValidArguments(ArrayList<Argument> arguments) {
        System.exit(1);
        return "";
    }
}
