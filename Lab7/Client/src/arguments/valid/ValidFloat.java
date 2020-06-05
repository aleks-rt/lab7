package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.TypeException;

public class ValidFloat extends ValidArgument<Float> {

    public ValidFloat(Checker ... checkers) {
        super(checkers);
    }

    @Override
    public Float parse(String argument) throws TypeException {
        try {
            result = Float.parseFloat(argument);
            return result;
        } catch (Exception e) {
            throw new TypeException();
        }
    }
}
