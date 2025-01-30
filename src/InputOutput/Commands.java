package InputOutput;

import Graphics.Simulation;
import Logic.Controller;
import Logic.Main;
import processing.core.PVector;

import static InputOutput.Console.log;

public class Commands {
    public static float maxSpeed = 5f;
    public static float speed = 1.0f;
    public static boolean trace = true;
    public static boolean forceTrace = false;
    public static boolean showTrace = true;
    public static boolean showGrid = true;
    public static boolean showArm = true;
    public static PVector headOffset = new PVector(0, 0, 0);

    /**
     * Run a command
     *
     * @param input
     */
    public static void run(String input) {
        String[] split = input.toLowerCase().split(" ");

        try {
            switch (split[0]) {
                case "h", "help" -> {
                    log("help: shows this message", Console.Type.INFO);
                    log("clear: clears the console", Console.Type.INFO);
                    log("exit: exits the program", Console.Type.INFO);
                    log("showTrace <true/false>: shows or hides the trace", Console.Type.INFO);
                    log("trace <true/false>: enables or dissables the trace", Console.Type.INFO);
                    log("forceTrace <true/false>: forces the trace to be on or off", Console.Type.INFO);
                    log("clearTrace: clears the trace", Console.Type.INFO);
                    log("showGrid <true/false>: shows or hides the grid", Console.Type.INFO);
                    log("showArm <true/false>: shows or hides the arm", Console.Type.INFO);
                    log("maxSpeed <speed>: sets the max speed of the arm", Console.Type.INFO);
                    log("speed <speed>: sets the speed of the arm as a percentage", Console.Type.INFO);
                    log("p <x> <y> <z>: sets the target position of the arm", Console.Type.INFO);
                    log("a <x> <y> <z>: sets the target angle of the arm (Sim only)", Console.Type.INFO);
                    log("head <x> <y> <z>: sets the offset of the head", Console.Type.INFO);
                    log("devices: lists all the available devices", Console.Type.INFO);
                    log("connect <name>: connects to a port or IP address", Console.Type.INFO);
                    log("close: closes the connection", Console.Type.INFO);
                    log("test: tests if the connection is open", Console.Type.INFO);
                    log("send <message>: sends a message to the connected device", Console.Type.INFO);
                }
                case "clear" -> {
                    Console.clear();
                }
                case "exit" -> System.exit(0);
                case "cleartrace" -> {
                    Simulation.trace.clear();
                    log("Trace cleared", Console.Type.INFO);
                }
                case "p" -> {
                    float x = Float.parseFloat(split[1]);
                    float y = Float.parseFloat(split[2]);
                    float z = Float.parseFloat(split[3]);
                    Controller.setTarget(new PVector(x, y, z));
                    log("Target set to " + x + " " + y + " " + z, Console.Type.INFO);
                }
                case "a" -> {
                    float x = Float.parseFloat(split[1]);
                    float y = Float.parseFloat(split[2]);
                    float z = Float.parseFloat(split[3]);
                    Main.angle.x = x;
                    Main.angle.y = y;
                    Main.angle.z = z;
                    Controller.deg2pos();
                    log("Angle manually set to " + x + " " + y + " " + z, Console.Type.INFO);
                }
                case "maxspeed" -> {
                    Commands.maxSpeed = Float.parseFloat(split[1]);
                    log("Max speed set to " + Commands.maxSpeed, Console.Type.INFO);
                }
                case "speed" -> {
                    Commands.speed = Float.parseFloat(split[1]) / 100f;
                    log("Speed set to " + Commands.speed * 100 + "%", Console.Type.INFO);
                }
                case "trace" -> {
                    Commands.trace = Boolean.parseBoolean(split[1]);
                    log("Trace set to " + Commands.trace, Console.Type.INFO);
                }
                case "forcetrace" -> {
                    Commands.forceTrace = Boolean.parseBoolean(split[1]);
                    log("Force trace set to " + Commands.forceTrace, Console.Type.INFO);
                }
                case "showtrace" -> {
                    Commands.showTrace = Boolean.parseBoolean(split[1]);
                    log("Show trace set to " + Commands.showTrace, Console.Type.INFO);
                }
                case "showgrid" -> {
                    Commands.showGrid = Boolean.parseBoolean(split[1]);
                    log("Show grid set to " + Commands.showGrid, Console.Type.INFO);
                }
                case "showarm" -> {
                    Commands.showArm = Boolean.parseBoolean(split[1]);
                    log("Show arm set to " + Commands.showArm, Console.Type.INFO);
                }
                case "head" -> {
                    float x = Float.parseFloat(split[1]);
                    float y = Float.parseFloat(split[2]);
                    float z = Float.parseFloat(split[3]);
                    Commands.headOffset = new PVector(x, y, z);
                    log("Head offset set to " + Commands.headOffset, Console.Type.INFO);
                }
                case "devices" -> {
                    String[] ports = Communication.getPorts();
                    log(ports.length + " devices found", Console.Type.INFO);
                    for (String port : ports) {
                        log(port, Console.Type.INFO);
                    }
                }
                case "connect" -> {
                    if (!Communication.connect(split[1])) {
                        log("Failed to connect", Console.Type.ERROR);
                    } else {
                        log("Connected", Console.Type.INFO);
                    }
                }
                case "close" -> {
                    Communication.close();
                    log("Connection closed", Console.Type.INFO);
                }
                case "test" -> { // Test if the connection is open
                    log("connected: " + Communication.isConnected(), Console.Type.INFO);
                }
                case "send" -> { // Send a message to the connected device
                    Communication.send(input.substring(5));
                }
                default -> Console.log("Unknown command \"" + split[0] + "\"", Console.Type.ERROR);
            }
        } catch (Exception e) {
            log("Error, invalid command", Console.Type.ERROR);
        }
    }
}
