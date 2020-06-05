package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.TypeException;

public class ValidInteger extends ValidArgument<Integer> {

    public ValidInteger(Checker... checkers) {
        super(checkers);
    }

    @Override
    public Integer parse(String argument) throws TypeException {
        try {
            result = Integer.parseInt(argument);
            return result;
        } catch (Exception e) {
            throw new TypeException();
        }
    }
}
