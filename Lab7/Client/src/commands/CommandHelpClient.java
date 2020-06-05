package commands;

import communication.Argument;

import java.util.ArrayList;
import java.util.Map;

public class CommandHelpClient extends Command {

    @Override
    public String getName() {
        return "help_client";
    }

    @Override
    public String getManual() {
        return "Вывести справку по всем командам клиента.";
    }

    @Override
    public String executeWithValidArguments(ArrayList<Argument> arguments) {
        String result = getName() + ":\n";
        for (Map.Entry<String, Command> command : context.handlerCommands.getClientCommands().entrySet()){
            result = result + command.getKey() + ": " + command.getValue().getManual() + "\n";
        }
        return result;
    }
}
