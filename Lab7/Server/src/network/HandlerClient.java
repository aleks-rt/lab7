package network;

import communication.Request;
import communication.Response;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class HandlerClient {
    public final int SIZE_BUFFER = 100000;

    private SocketAddress socketAddress;
    private DatagramSocket datagramSocket;

    public void bind(int port) throws SocketException {
        socketAddress = new InetSocketAddress(port);
        datagramSocket = new DatagramSocket(socketAddress);
    }

    private void send(byte[] bytes, SocketAddress address) throws IOException {
        datagramSocket.send(new DatagramPacket(bytes, bytes.length, address));
    }

    public void sendResponses(ArrayList<Response> responses, SocketAddress address) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(responses);
        objectOutputStream.flush();
        objectOutputStream.close();
        send(byteArrayOutputStream.toByteArray(), address);
    }

    public void sendAddressedResponse(AddressedResponse response) throws IOException {
        sendResponses(response.getResponses(), response.getSocketAddress());
    }

    private byte[] receive(int delay) {
        byte[] bytes = new byte[SIZE_BUFFER];
        try {
            if(delay != 0) {
                datagramSocket.setSoTimeout(delay);
            }
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            datagramSocket.receive(datagramPacket);
            socketAddress = datagramPacket.getSocketAddress();
        } catch (IOException e) {
            return new byte[0];
        }
        return bytes;
    }

    public ArrayList<Request> receiveRequests() throws IOException, ClassNotFoundException {
        return receiveRequests(0);
    }

    public ArrayList<Request> receiveRequests(int delay) throws IOException, ClassNotFoundException {
        byte[] bytes = receive(delay);
        if(bytes.length == 0) {
            return new ArrayList<>();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ArrayList<Request> requests = (ArrayList<Request>) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return requests;
    }

    public AddressedRequest receiveAddressedRequest() throws IOException, ClassNotFoundException {
        AddressedRequest addressedRequest = new AddressedRequest(receiveRequests());
        addressedRequest.setSocketAddress(socketAddress);
        return addressedRequest;
    }
}