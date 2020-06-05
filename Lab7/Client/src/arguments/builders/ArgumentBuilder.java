package arguments.builders;

import application.HandlerInput;
import arguments.valid.ValidArgument;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ArgumentBuilder<T> {

    protected HandlerInput handlerInput;
    protected ArrayList<ValidArgument> arguments;
    protected HashMap<ValidArgument, String> descriptions;

    public ArgumentBuilder(HandlerInput handlerInput) {
        this.handlerInput = handlerInput;
        arguments = new ArrayList<>();
        descriptions = new HashMap<>();
        setArguments();
    }

    protected abstract void setArguments();

    protected void addArgument(ValidArgument argument, String description) {
        arguments.add(argument);
        descriptions.put(argument, description);
    }

    private void clearArguments() {
        arguments.clear();
        descriptions.clear();
    }

    public T build() {
        T value = building();
        clearArguments();
        setArguments();
        return value;
    }

    public abstract T building();
}
