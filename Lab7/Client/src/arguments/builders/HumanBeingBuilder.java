package arguments.builders;

import application.HandlerInput;
import arguments.exceptions.CheckerException;
import arguments.exceptions.InvalidAmountArgumentException;
import arguments.exceptions.TypeException;
import arguments.valid.*;
import elements.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HumanBeingBuilder extends ArgumentBuilder<HumanBeing> {
    public HumanBeingBuilder(HandlerInput handlerInput) {
        super(handlerInput);
    }

    @Override
    protected void setArguments() {
        addArgument(new ValidString(), "Введите значение поля \"name\" одним словом.");
        addArgument(new ValidFloat(), "Введите значение поля X для \"coordinates\" X - дробное, его надо записать через ТОЧКУ!");
        addArgument(new ValidFloat(), "Введите значение поля Y для \"coordinates\" Y - дробное, его надо записать через ТОЧКУ!");
        addArgument(new ValidBoolean(), "Введите значение поля \"realHero\". Принимаемые значения: true, false.");
        addArgument(new ValidBoolean(), "Введите значение поля \"hasToothpick\". Принимаемые значения: true, false.");
        addArgument(new ValidFloat(), "Введите значение поля \"impactSpeed\". Если число дробное, то его надо записать через ТОЧКУ!");
        addArgument(new ValidWeaponType(), "Введите значение поля \"weaponType\". Принимаемые значения:\n" + Arrays.toString(WeaponType.values()));
        addArgument(new ValidMood(), "Введите значение поля \"mood\". Принимаемые значения:\n" + Arrays.toString(Mood.values()));
        addArgument(new ValidString(), "Введите значение поля \"name\" одним словом для объекта Car.");
    }

    @Override
    public HumanBeing building() {
        int index = 0;
        while (index < arguments.size()) {
            try {
                if (arguments.get(index).getResult() == null) {
                    if (handlerInput.isSIN())
                        System.out.println(descriptions.get(arguments.get(index)));
                    ArrayList<String> data = handlerInput.getData();
                    if (data.size() != 1) {
                        throw new InvalidAmountArgumentException();
                    }
                    arguments.get(index).get(data.get(0));
                    index++;
                }
            } catch (IOException e) {
                System.out.println("Ошибка ввода!");
            } catch (InvalidAmountArgumentException e) {
                if (handlerInput.isSIN())
                    System.out.println("Неверное количество аргументов!");
            } catch (TypeException e) {
                if (handlerInput.isSIN()) {
                    if (e.getMessage() == null) {
                        System.out.println("Тип введенных данных не соотвествует с указанному!");
                    } else {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (CheckerException e) {
                if (handlerInput.isSIN()) {
                    if (e.getMessage() == null) {
                        System.out.println("Введенные данные не подходят по ограничениям!");
                    } else {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return new HumanBeing((String) arguments.get(0).getResult(), new Coordinates((Float) arguments.get(1).getResult(),
                (Float) arguments.get(2).getResult()), (Boolean) arguments.get(3).getResult(), (Boolean) arguments.get(4).getResult(),
                (Float) arguments.get(5).getResult(), (WeaponType) arguments.get(6).getResult(), (Mood) arguments.get(7).getResult(),
                new Car((String) arguments.get(8).getResult()));
    }
}
