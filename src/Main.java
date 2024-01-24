import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashMap;

public class Main extends PApplet {
    public static HashMap<String, Window> windows = new HashMap<String, Window>();
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
        windows.put("simulation", new Simulation(25, 25, 800, 400));
        windows.put("controller", new Controller(850, 25, width-(25+850), height-25*2));
        windows.put("console", new Console(25, 450, 800, height-475));
    }

    @Override
    public void settings() {
        size(1280, 720, P3D);
    }

    @Override
    public void keyPressed() {

    }

    @Override
    public void mousePressed() {
        mouse.x = mouseX;
        mouse.y = mouseY;
    }

    @Override
    public void mouseDragged() {
        super.mouseDragged();
        for (Window w : windows.values()) {
            if (w.isOn((int)mouse.x, (int)mouse.y)) {
                w.mouseDragged(mouseX - (int)mouse.x, mouseY - (int)mouse.y);
            }
        }
        mouse.x = mouseX;
        mouse.y = mouseY;
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public static PVector getMouse() {
        return mouse;
    }
}