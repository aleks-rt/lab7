package commands;

import application.Context;
import communication.Request;
import communication.Response;

import java.util.HashMap;
import java.util.LinkedList;

public class HandlerCommands {

    private Context context;
    private int historySize;

    private HashMap<String, Command> commands;
    private LinkedList<String> lastCommands;

    public HandlerCommands(Context context, int historySize) {
        this.context = context;
        this.historySize = historySize;
        commands = new HashMap<>();
        lastCommands = new LinkedList<>();
    }

    public HandlerCommands setCommand(Command command) {
        command.setContext(context);
        commands.put(command.getName(), command);
        return this;
    }

    public Response executeCommand(Request request) {
        if (lastCommands.size() > historySize) {
            lastCommands.removeFirst();
        }
        lastCommands.addLast(request.getName());
        if((request.getLogin() == null || request.getPassword() == null) && !request.getName().equals("login") && !request.getName().equals("registration")) {
            return new Response("", "Выполните вход при помощи команды login");
        }
        if(request.getLogin() == null || request.getPassword() == null) {
            commands.get(request.getName()).setParameters(request.getArguments());
        }
        else {
            commands.get(request.getName()).setParameters(request.getArguments()).setLogin(request.getLogin()).setPassword(request.getPassword());
        }
        return commands.get(request.getName()).execute();
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public String getHistory() {
        return lastCommands.toString();
    }

}
