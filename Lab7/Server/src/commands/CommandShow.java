package commands;

import communication.Response;

public class CommandShow extends Command {

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getManual() {
        return "Вывести все элементы коллекции в строковом представлении.";
    }

    @Override
    public Response execute() {
        try {
            if(context.handlerDatabase.isExistingUser(login, password) == -1) {
                throw new Exception();
            }
            else {
                return new Response(getName(), context.humanList.getAlphabet());
            }
        } catch (Exception e) {
            return new Response(getName(), "Вы не прошли авторизацию.");
        }
    }
}
