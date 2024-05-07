import processing.serial.*;

public class Comunication {
    static Serial port;
    static Main main;

    public static void create(Main main) {
        Comunication.main = main;
    }

    public static boolean connect(String portName) {
        try {
            port = new Serial(Comunication.main, portName, 9600);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String[] getPorts() {
        return Serial.list();
    }

    public static void send(String data) {
        port.write(data);
    }
}
