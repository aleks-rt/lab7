package commands;

import communication.Response;

public class CommandPrintUniqueCar extends Command {
    @Override
    public String getName() {
        return "print_unique_car";
    }

    @Override
    public String getManual() {
        return "Вывести уникальные значения поля \"car\" всех элементов в коллекции.";
    }

    @Override
    public Response execute() {
        try {
            if(context.handlerDatabase.isExistingUser(login, password) == -1) {
                throw new Exception();
            }
            else {
                return new Response(getName(), context.humanList.printUniqueCar());
            }
        } catch (Exception e) {
            return new Response(getName(), "Вы не прошли авторизацию.");
        }
    }
}
