package network;

import communication.Response;

import java.net.SocketAddress;
import java.util.ArrayList;

public class AddressedResponse {

    private SocketAddress socketAddress;
    private ArrayList<Response> responses;

    public AddressedResponse(SocketAddress socketAddress, ArrayList<Response> responses) {
        this.socketAddress = socketAddress;
        this.responses = responses;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public ArrayList<Response> getResponses() {
        return responses;
    }
}
