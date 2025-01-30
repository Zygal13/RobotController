package InputOutput;

import Logic.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class NetworkCommunication extends ComInterface {

    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buffer;

    @Override
    boolean connect(String name) {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(name);
            socket.setSoTimeout(2000);
            return true;
        } catch (Exception e) {
            Console.log(e.getMessage(), Console.Type.ERROR);
            return false;
        }
    }

    @Override
    void send(String data) {
        System.out.println("Sending message: " + data);
        data = data.strip();
        buffer = data.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 10002);
        try {
            socket.send(packet);
        } catch (IOException e) {
            Console.log("send failed: " + e.getMessage(), Console.Type.ERROR);
        }
    }

    @Override
    boolean isConnected() {
        return socket != null && !socket.isClosed();
    }

    @Override
    void close() {
        Console.log("Disconnecting...", Console.Type.INFO);
        socket.close();
        Console.log("Disconnected", Console.Type.INFO);
    }

    /*private WifiCom wifiCom;

    public NetworkCommunication() {
        wifiCom = new WifiCom();
    }

    @Override
    public boolean connect(String name) {
        return wifiCom.connect(name);
    }

    @Override
    public void send(String data) {
        wifiCom.send(data);
    }

    void close() {
        wifiCom.close();
    }

    @Override
    public boolean isConnected() {
        return wifiCom.isConnected();
    }*/
}
