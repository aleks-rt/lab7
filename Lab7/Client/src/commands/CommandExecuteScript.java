package commands;

import communication.Argument;

import java.util.ArrayList;

public class CommandExecuteScript extends Command {
    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getManual() {
        return "Считать и выполнить скрипт из указанного файла. Параметры: file_name.";
    }

    @Override
    public String executeWithValidArguments(ArrayList<Argument> arguments) {
        try {
            context.handlerInput.pushFileBufferedStream((String) arguments.get(0).getValue());
        } catch (Exception e) {
            return getName() + ": " + (String) arguments.get(0).getValue() + " - " + e.getMessage();
        }
        return "";
    }
}
