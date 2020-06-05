package commands;

import communication.Response;

public class CommandLogin extends Command {
    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getManual() {
        return "Произвести авторизацию пользователя. Параметры: login password";
    }

    @Override
    public Response execute() {
        try {
            context.handlerDatabase.isExistingUser((String) arguments[0].getValue(), (String) arguments[1].getValue());
            return new Response(getName(),  (String) arguments[0].getValue() + " " + (String) arguments[1].getValue());
        }
        catch (Exception e) {
            return new Response(getName(), "Вы не прошли авторизацию.");
        }
    }
}
