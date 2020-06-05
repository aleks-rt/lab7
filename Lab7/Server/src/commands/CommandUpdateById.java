package commands;

import communication.Response;
import elements.HumanBeing;

public class CommandUpdateById extends Command {

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getManual() {
        return "Обновить значение элемента коллекции по полю \"id\". Параметры: id {element}.";
}

    @Override
    public Response execute() {
        try {
            return new Response(getName(), context.humanList.updateById((Integer) arguments[0].getValue(), (HumanBeing) arguments[1].getValue(), context.handlerDatabase.isExistingUser(login, password)));
        }
        catch (Exception e) {
            return new Response(getName(), "Вы не прошли авторизацию.");
        }
    }
}
