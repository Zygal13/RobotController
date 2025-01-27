package InputOutput;

import Logic.Main;
import processing.serial.Serial;

public class SerialCommunication extends ComInterface {
    static Serial port;

    public SerialCommunication() {
    }

    public boolean connect(String portName) {
        try {
            port = new Serial(Communication.main, portName, 9600);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void send(String data) {
        try {
            port.write(data);
        } catch (Exception e) {
            Console.log("No Port open", Console.Type.ERROR);
        }
    }

    public boolean isConnected() {
        return port != null;
    }

    @Override
    void close() {
        port.stop();
    }
}
