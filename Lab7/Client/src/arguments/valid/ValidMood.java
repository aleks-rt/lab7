package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.TypeException;
import elements.Mood;

public class ValidMood extends ValidArgument<Mood> {

    public ValidMood(Checker ... checkers) {
        super(checkers);
    }

    @Override
    public Mood parse(String argument) throws TypeException {
        try {
            result = Mood.valueOf(argument.toUpperCase());
            return result;
        } catch (IllegalArgumentException e) {
            throw new TypeException("Введенные данные не подходят под перечень!");
        } catch (NullPointerException e) {
            throw new TypeException("Данный enum пуст.");
        }
    }
}
