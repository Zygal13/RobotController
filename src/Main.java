import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.HashMap;

public class Main extends PApplet {
    public static HashMap<String, Window> windows = new HashMap<String, Window>();
    public static PVector angle = new PVector(0, 0, 0);
    public static Float hAngle = 0f;
    public static float[] maxAngle = {180, 90, 140, 135};
    public static float[] minAngle = {0, -45, 0, 0};
    public static int[] maxSignal = {250, 195, 195, 195};
    public static int[] minSignal = {5, 5, 8, 15};

    public static final float BASE_HEIGHT = 67.5f;
    public static final float UPPER_ARM = 195.25f;
    public static final float LOWER_ARM = 130f;
    public static final float HAND = 35f;

    private static PVector mouse = new PVector();

    @Override
    public void draw() {
        background(64);
        for (Window w : windows.values()) {
            pushMatrix();
            w.draw(g);
            popMatrix();
        }
    }

    @Override
    public void setup() {
        Comunication.create(this);
        ortho();
        windows.put("simulation", new Simulation(25, 25, 800, 400));
        windows.put("controller", new Controller(850, 25, width - (25 + 850), height - 25 * 2));
        windows.put("console", new Console(25, 450, 800, height - 475));

        ((Simulation) windows.get("simulation")).loadShapes(this);

        Console.log("Welcome to the robot arm controller!", Console.Type.INFO);
        Console.log("Verion 0.5.0", Console.Type.INFO);
        Console.log("Created by: Zygal", Console.Type.INFO);
        Console.log("Use the sliders to control the arm or load a .txt file", Console.Type.INFO);
        Console.log("Origin point is on hand servo, use \"head <x> <y> <z>\" to sett offset", Console.Type.INFO);
        Console.log("<h> to list commands.", Console.Type.INFO);
        Console.log("No device detected.", Console.Type.WARNING);
    }

    @Override
    public void settings() {
        size(1280, 720, P3D);
    }

    @Override
    public void keyPressed() {
        for (Window w : windows.values()) {
            if (w.isOn((int) mouse.x, (int) mouse.y))
                w.keyPressed(key, keyCode);
        }
    }

    @Override
    public void mousePressed() {
        mouse.x = mouseX;
        mouse.y = mouseY;
        for (Window w : windows.values()) {
            if (w.isOn((int) mouse.x, (int) mouse.y)) {
                w.mousePressed(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseDragged() {
        super.mouseDragged();
        for (Window w : windows.values()) {
            if (w.isOn((int) mouse.x, (int) mouse.y)) {
                w.mouseDragged(mouseX, mouseY);
            }
        }
        mouse.x = mouseX;
        mouse.y = mouseY;
    }

    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        for (Window w : windows.values()) {
            if (w.isOn((int) mouse.x, (int) mouse.y)) {
                w.mouseWheel(e);
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public static PVector getMouse() {
        return mouse;
    }
}