import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Controller extends Window {
    File file;
    private static PVector angle;
    private static Float hAngle;
    private static PVector position;
    private static PVector target;
    private static PVector l_pos;
    private static float lerp = 1.0f;
    private int selected = 0;
    private String[] text = {"100", "100", "100", "100"};

    private Slider[] sliders = new Slider[6];

    public Controller(int x, int y, int w, int h) {
        super(x, y, w, h);
        angle = Main.angle;
        hAngle = Main.hAngle;
        position = new PVector(0, 0, 105);
        sliders[0] = new Slider(x + 50, y + 50 + 65, w - 75, Main.minAngle[0], Main.maxAngle[0], 0.5f); //angle 1
        sliders[1] = new Slider(x + 50, y + 50 + 115, w - 75, Main.minAngle[1], Main.maxAngle[1], 0.333f); //angle 2
        sliders[2] = new Slider(x + 50, y + 50 + 165, w - 75, Main.minAngle[2], Main.maxAngle[2], 0.5f); //angle 3
        //TODO check for real values
        sliders[3] = new Slider(x + 50, y + 250 + 65, w - 75, 0, 294.812f, 0.5f); //position x
        sliders[4] = new Slider(x + 50, y + 250 + 115, w - 75, -294.812f, 294.812f, 0.5f); //position y
        sliders[5] = new Slider(x + 50, y + 250 + 165, w - 75, 0, 294.812f, 0.5f); //position z
    }

    public void update() {
        PVector lAngle = angle.copy();
        if (lerp != 1.0f) {
            position.x = PApplet.lerp(l_pos.x, target.x, lerp);
            position.y = PApplet.lerp(l_pos.y, target.y, lerp);
            position.z = PApplet.lerp(l_pos.z, target.z, lerp);
            float d = PVector.dist(l_pos, target);
            lerp = (float) Math.min(1.0, lerp + (Commands.maxSpeed / d) * Commands.speed);
            pos2deg();
            Simulation.addTrace(position);
        }
        if (file != null && lerp == 1.0f) {
            target = file.getLine();
            if (target == null) file = null;
            else {
                l_pos = position.copy();
                lerp = 0.0f;
            }
        }


        //if (lAngle.normalize().dot(angle.copy().normalize()) < 0.9998f) {

        float a3 = Main.angle.z - Main.angle.y;
        a3 = Math.max(Math.min(a3, Main.maxAngle[3]), Main.minAngle[3]);

        Comunication.send(
                "a" + PApplet.map(angle.x, Main.minAngle[0], Main.maxAngle[0], Main.minSignal[0], Main.maxSignal[0]) +
                        "b" + PApplet.map(angle.y, Main.minAngle[1], Main.maxAngle[1], Main.minSignal[1], Main.maxSignal[1]) +
                        "c" + PApplet.map(angle.z, Main.minAngle[2], Main.maxAngle[2], Main.minSignal[2], Main.maxSignal[2]) +
                        "d" + PApplet.map(a3, Main.minAngle[3], Main.maxAngle[3], Main.minSignal[3], Main.maxSignal[3]));

        // }
    }

    public void mouseDragged(int mx, int my) {
        super.mouseDragged(mx, my);
        for (Slider s : sliders) {
            s.mouseDragged(mx, my);
        }
        if (my > y + 50 && my < y + 250) {
            //angle
            angle.x = sliders[0].getValue();
            angle.y = sliders[1].getValue();
            angle.z = sliders[2].getValue();
            deg2pos();
            Simulation.addTrace(position);
            sliders[3].setValue(position.x);
            sliders[4].setValue(position.y);
            sliders[5].setValue(position.z);
        } else if (my > y + 250 && my < y + 450) {
            //position
            position.x = sliders[3].getValue();
            position.y = sliders[4].getValue();
            position.z = sliders[5].getValue();
            pos2deg();
            Simulation.addTrace(position);
            sliders[0].setValue(angle.x);
            sliders[1].setValue(angle.y);
            sliders[2].setValue(angle.z);
        }
    }

    public void mousePressed(int mx, int my) {
        super.mousePressed(mx, my);
        if (my > y + 450 && my < y + h) {
            if (my > y + 450 + 50 && my < y + 450 + 75 && mx > x + 25 && mx < x + (w - 125) / 3f - 25 + 25) {
                selected = 0;
                text[0] = "";
            } else if (my > y + 450 + 50 && my < y + 450 + 75 && mx > x + (w - 125) / 3f + 25 && mx < x + (w - 125) / 3f * 2 - 25 + 25) {
                selected = 1;
                text[1] = "";
            } else if (my > y + 450 + 50 && my < y + 450 + 75 && mx > x + (w - 125) / 3f * 2 + 25 && mx < x + (w - 125) / 3f * 3 - 25 + 25) {
                selected = 2;
                text[2] = "";
            } else if (my > y + 450 + 50 && my < y + 450 + 75 && mx > x + (w - 125) / 3f * 3 + 25 && mx < x + (w - 125) / 3f * 4 - 25 + 25) {
                selected = 3;
                text[3] = "";
            } else if (my > y + 450 + 100 && my < y + 450 + 125 && mx > x + 25 && mx < x + (w - 125) / 3f) { //lerp button
                try {
                    float tx = Float.parseFloat(text[0]);
                    float ty = Float.parseFloat(text[1]);
                    float tz = Float.parseFloat(text[2]);
                    float ts = Float.parseFloat(text[3]);
                    target = new PVector(tx, ty, tz);
                    l_pos = position.copy();
                    Commands.speed = Math.max(0, Math.min(100, ts)) / 100f;
                    lerp = 0;
                } catch (NumberFormatException e) {
                    Console.log("Invalid input", Console.Type.ERROR);
                }
            } else if (my > y + 450 + 100 && my < y + 450 + 125 && mx > x + (w - 125) / 3f - 25 && mx < x + 2 * (w - 125) / 3f) {
                file = new File();
            }
        }
    }

    public void keyPressed(char key, int keyCode) {
        super.keyPressed(key, keyCode);
        if (selected <= 3 && selected >= 0) {
            if (keyCode == PConstants.BACKSPACE && text[selected].length() > 0)
                text[selected] = text[selected].substring(0, text[selected].length() - 1);
            else if (keyCode == PConstants.DELETE) text[selected] = "";
            else if (text[selected].length() < 6) text[selected] += key;
        }
    }

    public void draw(PGraphics g) {
        super.draw(g);
        update();
        g.stroke(200, 200, 200);
        g.line(0, 50, w, 50);
        g.line(10, 250, w - 20, 250);
        g.line(10, 450, w - 20, 450);

        for (Slider s : sliders) {
            s.draw(g, x, y);
        }

        g.textSize(20);
        //<editor-fold desc="Angles">
        g.translate(0, 50);
        g.text("Angles", 10, 0);
        g.text("1: " + angle.x, 10, 30);
        g.text("2: " + angle.y, 10, 80);
        g.text("3: " + angle.z, 10, 130);
        //</editor-fold>

        //<editor-fold desc="Position">
        g.translate(0, 200);
        g.text("Position", 10, 0);
        g.text("x: " + position.x, 10, 30);
        g.text("y: " + position.y, 10, 80);
        g.text("z: " + position.z, 10, 130);
        //</editor-fold>

        //<editor-fold desc="Input">
        g.translate(0, 200);
        g.text("Input", 10, 0);
        g.fill(0);
        g.stroke(selected == 0 ? 255 : 200);
        g.rect(25, 50, (w - 125) / 3f - 25, 25);
        g.stroke(selected == 1 ? 255 : 200);
        g.rect(25 + (w - 125) / 3f, 50, (w - 125) / 3f - 25, 25);
        g.stroke(selected == 2 ? 255 : 200);
        g.rect(25 + 2 * (w - 125) / 3f, 50, (w - 125) / 3f - 25, 25);
        g.stroke(selected == 3 ? 255 : 200);
        g.rect(w - 100, 50, 75, 25);
        g.fill(255);
        g.textAlign(g.LEFT, g.BOTTOM);
        g.text("x", 25, 50);
        g.text("y", 25 + (w - 125) / 3f, 50);
        g.text("z", 25 + 2 * (w - 125) / 3f, 50);
        g.text("speed", w - 100, 50);
        g.textAlign(g.LEFT, g.TOP);
        g.text(text[0], 25, 50);
        g.text(text[1], 25 + (w - 125) / 3f, 50);
        g.text(text[2], 25 + 2 * (w - 125) / 3f, 50);
        g.text(text[3] + "%", w - 100, 50);
        //buttons
        g.stroke(200);
        g.fill(50, 168, 82);
        g.rect(25, 100, (w - 125) / 3f - 25, 25);
        g.fill(255);
        g.textAlign(g.CENTER, g.TOP);
        g.text("Lerp", 25 + ((w - 125) / 3f - 25) / 2f, 100);
        g.fill(127);
        g.rect(25 + (w - 125) / 3f, 100, (w - 125) / 3f - 25, 25);
        g.fill(255);
        g.text("File", 25 + (w - 125) / 3f + ((w - 125) / 3f - 25) / 2f, 100);
        //</editor-fold>
    }

    public void deg2pos() {
        float Rxy = (float) (Main.UPPER_ARM * Math.sin(Math.toRadians(angle.y)) + Main.LOWER_ARM * Math.sin(Math.toRadians(angle.z - angle.y)));
        float x = (float) (Rxy * Math.sin(Math.toRadians(angle.x)) + Commands.headOffset.x * Math.sin(Math.toRadians(angle.x)) + Commands.headOffset.y * Math.cos(Math.toRadians(angle.x)));
        float y = (float) (Rxy * Math.cos(Math.toRadians(angle.x)) + Commands.headOffset.y * Math.cos(Math.toRadians(angle.x)) + Commands.headOffset.x * Math.sin(Math.toRadians(angle.x)));
        float z = (float) (Main.UPPER_ARM * Math.cos(Math.toRadians(angle.y)) + Main.LOWER_ARM * Math.cos(Math.toRadians(angle.z - angle.y)) + Commands.headOffset.z);
        position = new PVector(x, y, z);
    }

    public void pos2deg() {
        float Rxy = (float) Math.sqrt(Math.pow(position.x, 2) + Math.pow(position.y, 2) - Math.pow(Commands.headOffset.y, 2)) - Commands.headOffset.x;
        float R = (float) Math.sqrt(Math.pow(Rxy, 2) + Math.pow(position.z - Commands.headOffset.z, 2));
        if (R > 0.1) {
            float a0 = (float) Math.toDegrees(Math.acos((Math.pow(Rxy, 2) + Math.pow(position.x, 2) + Math.pow(position.y, 2) - Math.pow(Commands.headOffset.x, 2) - Math.pow(Commands.headOffset.y, 2)) / (2 * Rxy * Math.sqrt(Math.pow(position.x, 2) + Math.pow(position.y, 2)))) + Math.acos(position.x / (Math.sqrt(Math.pow(position.x, 2) + Math.pow(position.y, 2)))));
            float a1 = 90f - (float) Math.toDegrees(Math.asin((position.z - Commands.headOffset.z) / R) + Math.acos((Math.pow(Main.UPPER_ARM, 2) + Math.pow(R, 2) - Math.pow(Main.LOWER_ARM, 2)) / (2 * Main.UPPER_ARM * R)));
            float a2 = (float) Math.toDegrees(Math.acos((Math.pow(Main.UPPER_ARM, 2) + Math.pow(Main.LOWER_ARM, 2) - Math.pow(R, 2)) / (2 * Main.UPPER_ARM * Main.LOWER_ARM)));
            a0 = Math.max(Math.min(a0, Main.maxAngle[0]), Main.minAngle[0]);
            a1 = Math.max(Math.min(a1, Main.maxAngle[1]), Main.minAngle[1]);
            a2 = Math.max(Math.min(a2, Main.maxAngle[2]), Main.minAngle[2]);
            float a3 = 135f - a2 + a1;
            a3 = Math.max(Math.min(a3, Main.maxAngle[3]), Main.minAngle[3]);
            angle = new PVector(a0, a1, a3);
            hAngle = a3;
        } else {
            angle = new PVector(0, 0, 0);
            hAngle = 0f;
        }
    }

    public static void setTarget(PVector target) {
        Controller.target = target;
        Controller.l_pos = Controller.position.copy();
        Controller.lerp = 0;
    }

    private static class Slider {
        private final float max;
        private final float min;
        private float value;
        private final float x;
        private final float y;
        private final float l;

        /**
         * create a new slider
         *
         * @param x     x position
         * @param y     y position
         * @param l     length
         * @param min   minimum value
         * @param max   maximum value
         * @param value initial value (between 0 and 1)
         */
        public Slider(float x, float y, float l, float min, float max, float value) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.min = min;
            this.max = max;
            this.value = value;
        }

        public void draw(PGraphics g, float offsetX, float offsetY) {
            g.translate(-offsetX, -offsetY);
            g.stroke(255);
            g.line(x, y, x + l * value, y);
            g.circle(x + l * value, y, 10);
            g.stroke(200);
            g.line(x + l * value, y, x + l, y);
            g.translate(offsetX, offsetY);
        }

        public void mouseDragged(float mouseX, float mouseY) {
            if (Math.sqrt(Math.pow(mouseX - x - l * value, 2) + Math.pow(mouseY - y, 2)) < 25) {
                value = (mouseX - x) / l;
                value = Math.max(Math.min(value, 1), 0);
            }
        }

        public float getValue() {
            return min + (max - min) * value;
        }

        public void setValue(float value) {
            this.value = (value - min) / (max - min);
            this.value = Math.max(Math.min(this.value, 1), 0);
        }
    }
}
