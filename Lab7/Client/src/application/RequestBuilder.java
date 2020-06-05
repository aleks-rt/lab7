package application;

import arguments.TemplateServerCommand;
import arguments.exceptions.CheckerException;
import arguments.exceptions.InvalidAmountArgumentException;
import arguments.exceptions.TypeException;
import communication.Argument;
import communication.Request;

import java.util.ArrayList;
import java.util.HashMap;


public class RequestBuilder {

    private Context context;
    private HashMap<String, TemplateServerCommand> serverCommands;

    public RequestBuilder(Context context) {
        this.context = context;
        serverCommands = new HashMap<>();
    }

    public RequestBuilder addCommand(TemplateServerCommand templateServerCommand) {
        context.commands.put(templateServerCommand.getName(), CommandType.SERVER);
        serverCommands.put(templateServerCommand.getName(), templateServerCommand);
        return this;
    }

    public Request getRequest(ArrayList<String> data) throws InvalidAmountArgumentException, TypeException, CheckerException {
        String nameCommand = data.remove(0);
        ArrayList<Argument> arguments = serverCommands.get(nameCommand).getArguments(data);
        Argument[] arrayArguments = new Argument[arguments.size()];
        arguments.toArray(arrayArguments);
        return new Request(nameCommand, arrayArguments).setLogin(context.login).setPassword(context.password);
    }
}
