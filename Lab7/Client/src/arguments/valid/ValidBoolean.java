package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.TypeException;

public class ValidBoolean extends ValidArgument<Boolean> {

    public ValidBoolean(Checker... checkers) {
        super(checkers);
    }

    @Override
    protected Boolean parse(String argument) throws TypeException {

        if (!argument.toLowerCase().equals("false") && !argument.toLowerCase().equals("true"))
            throw new TypeException("Введенные данные не подходят под перечень!");
        result = Boolean.parseBoolean(argument.toLowerCase());
        return result;
    }
}
