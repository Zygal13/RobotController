package InputOutput;

import Logic.Main;
import processing.serial.*;

public class Communication {
    static Main main;
    private static ComInterface com; // Communication through Serial or Network

    public static void create(Main main) {
        Communication.main = main;
    }

    public static boolean connect(String name) {
        if (name.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$")) { // Check if name is an ip adress
            Console.log("Connecting wirelessly to " + name, Console.Type.INFO);
            com = new NetworkCommunication();
            return com.connect(name);
        } else if (name.toUpperCase().matches("COM([0-9]?[0-9])")) { // Check if name is a COM port
            Console.log("Connecting to port " + name, Console.Type.INFO);
            com = new SerialCommunication();
            return com.connect(name);
        } else {
            Console.log("Invalid Connection", Console.Type.ERROR);
            return false;
        }
    }

    /**
     * Only for serial communication, checks all available ports
     * @return array of available ports
     */
    public static String[] getPorts() {
        return Serial.list();
    }

    public static void send(String data) {
        com.send(data);
    }

    /**
     * Checks if the connection is open, both serial and network
     * @return true if connected
     */
    public static boolean isConnected() {
        return com != null && com.isConnected();
    }

    /**
     * Closes the connection
     */
    public static void close() {
        if(com == null) return;
        com.close();
        com = null;
    }
}
