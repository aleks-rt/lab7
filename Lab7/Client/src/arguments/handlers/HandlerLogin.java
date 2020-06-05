package arguments.handlers;

import application.Context;
import communication.Response;

import java.util.StringTokenizer;

public class HandlerLogin implements HandlerRequst {

    @Override
    public void processing(Context context, Response response) {
        StringTokenizer stringTokenizer= new StringTokenizer(response.getResultComand());
        if(stringTokenizer.countTokens() == 2) {
            context.login = stringTokenizer.nextToken();
            context.password = stringTokenizer.nextToken();
            System.out.println(response.getNameCommand() + ": вход выполнен!");
            System.out.println("Введите \"help\" для просмотра перечня команд сервера.\n" +
                    "Введите \"help_client\" для просмотра перечня команд клиента.\n" +
                    "Вводите команды:");
        }
        else {
            System.out.println(response.getNameCommand() + ": " + response.getResultComand());
        }
    }
}
