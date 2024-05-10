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
            Console.log(e.getMessage(), Console.Type.ERROR);
            return false;
        }
    }

    public static String[] getPorts() {
        return Serial.list();
    }


    public static boolean send(String data) {
        if (port != null) { //if the arm doesn't respond, try adding a delay here
            port.write(data + 'x');
            System.out.println(data);
            return true;
        } else {
            return false;
        }
    }
}
