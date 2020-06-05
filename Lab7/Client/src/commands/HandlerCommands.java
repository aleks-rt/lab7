package commands;

import application.CommandType;
import application.Context;
import java.util.ArrayList;
import java.util.HashMap;

public class HandlerCommands {

    private Context context;
    private HashMap<String, Command> clientCommands;

    public HandlerCommands(Context context) {
        this.context = context;
        clientCommands = new HashMap<>();
    }

    public HandlerCommands addCommand(Command command) {
        command.setContext(context);
        context.commands.put(command.getName(), CommandType.CLIENT);
        clientCommands.put(command.getName(), command);
        return this;
    }

    public HashMap<String, Command> getClientCommands() {
        return clientCommands;
    }

    public String executeCommand(ArrayList<String> data) throws Exception {
        return clientCommands.get(data.remove(0)).execute(data);
    }
}
