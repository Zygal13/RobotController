import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.HashMap;

public class Main extends PApplet {
    public static HashMap<String, Window> windows = new HashMap<String, Window>();
    public static PVector angle = new PVector(0, 90, 180);
    public static PVector maxAngle = new PVector(360, 90, 180);
    public static PVector arm = new PVector(65, 150, 110);

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
        ortho();
        windows.put("simulation", new Simulation(25, 25, 800, 400));
        windows.put("controller", new Controller(850, 25, width - (25 + 850), height - 25 * 2));
        windows.put("console", new Console(25, 450, 800, height - 475));

        ((Simulation) windows.get("simulation")).loadShapes(this);

        Console.log("Welcome to the robot arm controller!", Console.Type.INFO);
        Console.log("Verion 0.3.0", Console.Type.INFO);
        Console.log("Created by: Zygal", Console.Type.INFO);
        Console.log("Use the sliders to control the arm.", Console.Type.INFO);
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