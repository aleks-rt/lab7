package commands;

import communication.Response;

public class CommandHistory extends Command {
    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getManual() {
        return "Вывести последние 7 команд сервера (без аргументов).";
    }

    @Override
    public Response execute() {
        try {
            if(context.handlerDatabase.isExistingUser(login, password) == -1) {
                throw new Exception();
            }
            else {
                return new Response(getName(), context.handlerCommands.getHistory());
            }
        } catch (Exception e) {
            return new Response(getName(), "Вы не прошли авторизацию.");
        }
    }
}
