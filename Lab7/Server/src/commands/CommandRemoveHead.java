package commands;

import communication.Response;

public class CommandRemoveHead extends Command {

    @Override
    public String getName() {
        return "remove_head";
    }

    @Override
    public String getManual() {
        return "Вывести первый элемент коллекции и удалить его.";
    }

    @Override
    public Response execute() {
        try {
            return new Response(getName(), context.humanList.removeHead(context.handlerDatabase.isExistingUser(login, password)));
        }
        catch (Exception e) {
            return new Response(getName(), "Вы не прошли авторизацию.");
        }
    }

}
