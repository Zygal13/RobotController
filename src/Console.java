import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class Console extends Window {
    enum Type {
        INFO,
        WARNING,
        ERROR
    }

    private static ArrayList<String> lines = new ArrayList<String>();
    private static ArrayList<Type> type = new ArrayList<Type>();
    private int offset = 0;
    private String input = "";

    public Console(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void draw(PGraphics g) {
        super.draw(g);
        g.fill(255);
        g.textSize(19);
        g.textAlign(g.LEFT, g.BOTTOM);
        for (int i = lines.size() - 1 - offset; i >= Math.max(0, lines.size() - 9 - offset); i--) {
            switch (type.get(i)) {
                case INFO -> g.fill(255);
                case WARNING -> g.fill(255, 255, 0);
                case ERROR -> g.fill(255, 0, 0);
            }
            g.text(lines.get(i), 10, h - 10 - (lines.size() - (i + offset)) * 19);
        }
        g.fill(255);
        g.text(">", 10, h - 10);
        g.text(input, 20, h - 10);
    }

    /**
     * prints the message to the console
     *
     * @param s string to print
     * @param t type of message, INFO, WARNING, ERROR
     */
    public static void log(String s, Type t) {
        lines.add("[" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond() + "] " + s);
        type.add(t);
    }

    @Override
    public void mouseWheel(float e) {
        super.mouseWheel(e);
        offset -= e;
        if (offset > lines.size() - 9) offset = lines.size() - 9;
        if (offset < 0) offset = 0;
    }

    @Override
    public void keyPressed(char key, int keyCode) {
        super.keyPressed(key, keyCode);
        if (keyCode == PConstants.BACKSPACE && input.length() > 0)
            input = input.substring(0, input.length() - 1);
        else if (keyCode == PConstants.DELETE) input = "";
        else if (input.length() < 85 && (Character.isDigit(key) || Character.isLetter(key) || key == ' ')) input += key;
        if (keyCode == PConstants.ENTER) {
            input = input.toLowerCase();

            switch (input.split(" ")[0]) {
                case "h", "help" -> {
                    log("help: shows this message", Type.INFO);
                    log("clear: clears the console", Type.INFO);
                    log("exit: exits the program", Type.INFO);
                    log("showTrace <true/false>: shows or hides the trace", Type.INFO);
                    log("trace <true/false>: enables or dissables the trace", Type.INFO);
                    log("forceTrace <true/false>: forces the trace to be on or off", Type.INFO);
                    log("clearTrace: clears the trace", Type.INFO);
                    log("showGrid <true/false>: shows or hides the grid", Type.INFO);
                    log("showArm <true/false>: shows or hides the arm", Type.INFO);
                    log("maxSpeed <speed>: sets the max speed of the arm", Type.INFO);
                    log("speed <speed>: sets the speed of the arm as a percentage", Type.INFO);
                    log("p <x> <y> <z>: sets the target position of the arm", Type.INFO);
                    log("a <x> <y> <z>: sets the target angle of the arm (Sim only)", Type.INFO);
                    log("head <x> <y> <z>: sets the offset of the head", Type.INFO);
                    log("devices: lists all the available devices", Type.INFO);
                    log("connect <name>: connects to the device", Type.INFO);
                }
                case "clear" -> {
                    lines.clear();
                    type.clear();
                }
                case "exit" -> System.exit(0);
                case "cleartrace" -> {
                    Simulation.trace.clear();
                    log("Trace cleared", Type.INFO);
                }
                case "p" -> {
                    String[] split = input.split(" ");
                    float x = Float.parseFloat(split[1]);
                    float y = Float.parseFloat(split[2]);
                    float z = Float.parseFloat(split[3]);
                    Controller.setTarget(new PVector(x, y, z));
                    log("Target set to " + x + " " + y + " " + z, Type.INFO);
                }
                case "a" -> {
                    String[] split = input.split(" ");
                    float x = Float.parseFloat(split[1]);
                    float y = Float.parseFloat(split[2]);
                    float z = Float.parseFloat(split[3]);
                    Main.angle.x = x;
                    Main.angle.y = y;
                    Main.angle.z = z;
                    Controller.deg2pos();
                    log("Angle manually set to " + x + " " + y + " " + z, Type.INFO);
                }
                default -> command(input);
            }
            input = "";
        }
    }

    public static void command(String line) {
        String[] split = line.split(" ");
        try {
            switch (split[0]) {
                case "maxspeed" -> {
                    Commands.maxSpeed = Float.parseFloat(split[1]);
                    Console.log("Max speed set to " + Commands.maxSpeed, Type.INFO);
                }
                case "speed" -> {
                    Commands.speed = Float.parseFloat(split[1]) / 100f;
                    Console.log("Speed set to " + Commands.speed * 100 + "%", Type.INFO);
                }
                case "trace" -> {
                    Commands.trace = Boolean.parseBoolean(split[1]);
                    Console.log("Trace set to " + Commands.trace, Type.INFO);
                }
                case "forcetrace" -> {
                    Commands.forceTrace = Boolean.parseBoolean(split[1]);
                    Console.log("Force trace set to " + Commands.forceTrace, Type.INFO);
                }
                case "showtrace" -> {
                    Commands.showTrace = Boolean.parseBoolean(split[1]);
                    Console.log("Show trace set to " + Commands.showTrace, Type.INFO);
                }
                case "showgrid" -> {
                    Commands.showGrid = Boolean.parseBoolean(split[1]);
                    Console.log("Show grid set to " + Commands.showGrid, Type.INFO);
                }
                case "showarm" -> {
                    Commands.showArm = Boolean.parseBoolean(split[1]);
                    Console.log("Show arm set to " + Commands.showArm, Type.INFO);
                }
                case "head" -> {
                    float x = Float.parseFloat(split[1]);
                    float y = Float.parseFloat(split[2]);
                    float z = Float.parseFloat(split[3]);
                    Commands.headOffset = new PVector(x, y, z);
                    Console.log("Head offset set to " + Commands.headOffset, Type.INFO);
                }
                case "devices" -> {
                    String[] ports = Comunication.getPorts();
                    Console.log(ports.length + " devices found", Type.INFO);
                    for (String port : ports) {
                        Console.log(port, Type.INFO);
                    }
                }
                case "connect" -> {
                    Comunication.connect(split[1]);
                }
                default -> Console.log("Unknown command \"" + split[0] + "\"", Type.ERROR);
            }
        } catch (Exception e) {
            Console.log("Invalid command \"" + line + "\"", Console.Type.ERROR);
        }
    }
}
