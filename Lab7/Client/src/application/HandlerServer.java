package application;

import communication.Request;
import communication.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class HandlerServer {
    public final int SIZE_BUFFER = 100000;

    private SocketAddress socketAddress;
    private DatagramChannel datagramChannel;

    public void connect(String host, int port) throws IOException {
        socketAddress = new InetSocketAddress(host, port);
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.connect(socketAddress);
    }

    protected void send(byte[] bytes) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        datagramChannel.send(byteBuffer, socketAddress);
    }

    public void sendRequests(ArrayList<Request> requests) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(requests);
        objectOutputStream.flush();
        objectOutputStream.close();
        send(byteArrayOutputStream.toByteArray());
    }

    protected byte[] receive(int delay) throws IOException {
        SocketAddress earlySocketAddress = socketAddress;
        long startTime = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE_BUFFER);
        do {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= delay) {
                socketAddress = earlySocketAddress;
                throw new SocketTimeoutException();
            }
            socketAddress = datagramChannel.receive(byteBuffer);
        } while (socketAddress == null);
        byteBuffer.flip();
        byte[] data = new byte[byteBuffer.limit()];
        byteBuffer.get(data, 0, byteBuffer.limit());
        return data;
    }

    public ArrayList<Response> receiveResponse(int delay) throws IOException, ClassNotFoundException {
        byte[] bytes = receive(delay);
        if (bytes.length == 0) {
            return new ArrayList<>();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ArrayList<Response> responses = (ArrayList<Response>) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return responses;
    }

}
