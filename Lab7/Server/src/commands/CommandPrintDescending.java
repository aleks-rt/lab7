package commands;

import communication.Response;

public class CommandPrintDescending extends Command {

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getManual() {
        return "Вывести элементы коллекции в порядке убфвания";
    }

    @Override
    public Response execute() {
        try {
            if(context.handlerDatabase.isExistingUser(login, password) == -1) {
                throw new Exception();
            }
            else {
                return new Response(getName(), context.humanList.printDescending());
            }
        } catch (Exception e) {
            return new Response(getName(), "Вы не прошли авторизацию.");
        }
    }
}
