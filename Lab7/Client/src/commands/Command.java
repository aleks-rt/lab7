package commands;

import application.Context;
import arguments.exceptions.CheckerException;
import arguments.exceptions.InvalidAmountArgumentException;
import arguments.exceptions.TypeException;
import arguments.valid.ValidArgument;
import communication.Argument;

import java.util.ArrayList;

public abstract class Command {

    protected Context context;
    protected ArrayList<ValidArgument> validArguments;

    public Command() {
        validArguments = new ArrayList<>();
    }

    public Command setValidArgument(ValidArgument validArgument) {
        this.validArguments.add(validArgument);
        return this;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract String getName();

    public abstract String getManual();

    public ArrayList<Argument> getArguments(ArrayList<String> data) throws InvalidAmountArgumentException, CheckerException, TypeException {
        if(data.size() != validArguments.size()) {
            throw new InvalidAmountArgumentException();
        }

        ArrayList<Argument> arguments = new ArrayList<>();

        for (int i = 0; i < data.size(); ++i) {
            arguments.add(new Argument(validArguments.get(i).get(data.get(i))));
        }
        return arguments;
    }

    public String execute(ArrayList<String> data) throws Exception {
        return executeWithValidArguments(getArguments(data));
    }

    public abstract String executeWithValidArguments(ArrayList<Argument> arguments) ;
}
