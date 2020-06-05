package arguments;

import arguments.builders.ArgumentBuilder;
import arguments.exceptions.CheckerException;
import arguments.exceptions.InvalidAmountArgumentException;
import arguments.exceptions.TypeException;
import arguments.valid.ValidArgument;
import communication.Argument;

import java.util.ArrayList;

public class TemplateServerCommand {

    private String name;
    private ArrayList<ValidArgument> validArguments;
    private ArrayList<ArgumentBuilder> buildArguments;

    public TemplateServerCommand(String name) {
        this.name = name;
        this.validArguments = new ArrayList<>();
        this.buildArguments = new ArrayList<>();
    }

    public TemplateServerCommand setValidArgument(ValidArgument validArgument) {
        this.validArguments.add(validArgument);
        return this;
    }

    public TemplateServerCommand setBuildArgument(ArgumentBuilder buildArgument) {
        this.buildArguments.add(buildArgument);
        return this;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Argument> getArguments(ArrayList<String> data) throws InvalidAmountArgumentException, CheckerException, TypeException {
        if(data.size() != validArguments.size()) {
            throw new  InvalidAmountArgumentException();
        }

        ArrayList<Argument> arguments = new ArrayList<>();

        for (int i = 0; i < data.size(); ++i) {
            arguments.add(new Argument(validArguments.get(i).get(data.get(i))));
        }

        for (ArgumentBuilder builder : buildArguments) {
            arguments.add(new Argument(builder.build()));

        }
        return arguments;
    }
}
