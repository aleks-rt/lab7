package network;

import communication.Request;

import java.net.SocketAddress;
import java.util.ArrayList;

public class AddressedRequest {
    private SocketAddress socketAddress;
    private ArrayList<Request> requests;

    public AddressedRequest(ArrayList<Request> requests) {
        this.requests = requests;
    }


    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}
