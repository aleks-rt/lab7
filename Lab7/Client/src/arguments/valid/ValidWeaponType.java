package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.TypeException;
import elements.WeaponType;

public class ValidWeaponType extends ValidArgument<WeaponType> {

    public ValidWeaponType(Checker ... checkers) {
        super(checkers);
    }

    @Override
    public WeaponType parse(String argument) throws TypeException {
        try {
            result = WeaponType.valueOf(argument.toUpperCase());
            return result;
        } catch (IllegalArgumentException e) {
            throw new TypeException("Введенные данные не подходят под перечень!");
        } catch (NullPointerException e) {
            throw new TypeException("Данный enum пуст.");
        }
    }
}
