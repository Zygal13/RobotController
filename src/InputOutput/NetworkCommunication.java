package InputOutput;

import Logic.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkCommunication extends ComInterface {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buffer;

    public NetworkCommunication() {
    }

    @Override
    public boolean connect(String name) {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(name);
            return true;
        } catch (Exception e) {
            Console.log(e.getMessage(), Console.Type.ERROR);
            return false;
        }
    }

    @Override
    public void send(String data) {
        buffer = data.getBytes();
        DatagramPacket p = new DatagramPacket(buffer, buffer.length, address, 10002); //Port 10002
        try {
            socket.send(p);
        } catch (IOException e) {
            Console.log(e.getMessage(), Console.Type.ERROR);
        }

    }

    void close() {
        socket.close();
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
