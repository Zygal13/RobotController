package InputOutput;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WifiCom implements Runnable {
    Thread thread;
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buffer;
    private boolean connected = false;

    public boolean connect(String name) {
        try {
            buffer = new byte[0];
            socket = new DatagramSocket();
            socket.setSoTimeout(2000);
            address = InetAddress.getByName(name);
            connected = true;
            Thread thread = new Thread(this);
            thread.start();
            return true;
        } catch (Exception e) {
            Console.log(e.getMessage(), Console.Type.ERROR);
            connected = false;
            return false;
        }
    }

    public void send(String data) {
        buffer = data.getBytes();
    }

    public void close() {
        connected = false;
        socket.close();
    }

    public boolean isConnected() {
        return socket != null && !socket.isClosed() && socket.isConnected();
    }

    @Override
    public void run() {
        while (connected) {
            if (buffer.length > 0) {
                DatagramPacket p = new DatagramPacket(buffer, buffer.length, address, 10002); //Port 10002
                try {
                    socket.send(p);
                } catch (IOException e) {
                    Console.log(e.getMessage(), Console.Type.ERROR);
                    try {
                        thread.wait(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
