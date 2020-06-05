package application;

import network.AddressedResponse;
import communication.Response;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class Transmitter implements Runnable {

    private Context context;

    public Transmitter(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            if (!context.queueAddressedResponse.isEmpty()) {
                AddressedResponse response = context.queueAddressedResponse.poll();
                try {
                    context.handlerClient.sendAddressedResponse(response);
                } catch (SocketException e) {
                    ArrayList<Response> error = new ArrayList<>();
                    error.add(new Response("Ошибка", "слишком много запросов, перезагрузите клиент."));
                    try {
                        context.handlerClient.sendAddressedResponse(new AddressedResponse(response.getSocketAddress(), error));
                    } catch (IOException e1) {
                        context.logger.warn("Произошла ошибка при отправке ответа: " + e1.getMessage());
                    }
                } catch (IOException e) {
                    context.logger.warn("Произошла ошибка при отправке ответа: " + e.getMessage());
                }
            }
        } catch (Exception e) {

        }
    }
}
