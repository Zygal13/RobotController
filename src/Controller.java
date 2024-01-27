import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Controller extends Window {
    File file;
    private PVector angle;
    private PVector position;
    private PVector target;
    private PVector l_pos;
    private float speed = 1f;
    private float lerp = 1.0f;
    private int selected = 0;
    private String[] text = {"100", "100", "100", "100"};

    public Controller(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.angle = Main.angle;
        position = new PVector(0, 0, 105);
    }

    public void update(){
        if (lerp != 1.0f) {
            position.x = PApplet.lerp(l_pos.x, target.x, lerp);
            position.y = PApplet.lerp(l_pos.y, target.y, lerp);
            position.z = PApplet.lerp(l_pos.z, target.z, lerp);
            float d = PVector.dist(l_pos, target);
            lerp = (float) Math.min(1.0, lerp + (Main.MAX_SPEED / d)*speed);
            pos2deg();
            Simulation.addTrace(position);
        }
        if (file != null && lerp == 1.0f) {
            target = file.getLine();
            Console.log("Target: " + target, Console.Type.WARNING);
            if (target == null) file = null;
            else {
                l_pos = position.copy();
                lerp = 0.0f;
            }
        }
    }

    public void mouseDragged(int mx, int my) {
        super.mouseDragged(mx, my);
        if (my > y + 50 && my < y + 250) {
            //angle

            if (Math.sqrt(Math.pow((50 + (w - 75) * (angle.x / Main.maxAngle.x)) - (mx - this.x), 2) + Math.pow(50 - (my - this.y - 50), 2)) < 25) {
                angle.x = ((mx - this.x) - 50) * (Main.maxAngle.x / (w - 75));
                angle.x = Math.round(Math.max(0, Math.min(Main.maxAngle.x, angle.x)));
            } else if (Math.sqrt(Math.pow((50 + (w - 75) * (angle.y / Main.maxAngle.y)) - (mx - this.x), 2) + Math.pow(100 - (my - this.y - 50), 2)) < 25) {
                angle.y = ((mx - this.x) - 50) * (Main.maxAngle.y / (w - 75));
                angle.y = Math.round(Math.max(0, Math.min(Main.maxAngle.y, angle.y)));
            } else if (Math.sqrt(Math.pow((50 + (w - 75) * (angle.z / Main.maxAngle.z)) - (mx - this.x), 2) + Math.pow(150 - (my - this.y - 50), 2)) < 25) {
                angle.z = ((mx - this.x) - 50) * (Main.maxAngle.z / (w - 75));
                angle.z = Math.round(Math.max(0, Math.min(Main.maxAngle.z, angle.z)));
            }
            deg2pos();
            Simulation.addTrace(position);
        } else if (my > y + 250 && my < y + 450) {
            //position
            if (Math.sqrt(Math.pow((50 + (w - 75) * ((position.x + 260f) / 520f)) - (mx - this.x), 2) + Math.pow(65 - (my - this.y - 250), 2)) < 25) {
                position.x = (((mx - this.x - 50f) / (w - 75f)) * 520f) - 260f;
                position.x = Math.round(Math.max(-260f, Math.min(260f, position.x)) * 100) / 100f;
            } else if (Math.sqrt(Math.pow((50 + (w - 75) * ((position.y + 260f) / 520f)) - (mx - this.x), 2) + Math.pow(115 - (my - this.y - 250), 2)) < 25) {
                position.y = (((mx - this.x - 50f) / (w - 75f)) * 520f) - 260f;
                position.y = Math.round(Math.max(-260, Math.min(260f, position.y)) * 100) / 100f;
            } else if (Math.sqrt(Math.pow((50 + (w - 75) * (position.z / 325f)) - (mx - this.x), 2) + Math.pow(165 - (my - this.y - 250), 2)) < 25) {
                position.z = ((mx - this.x) - 50) * (325f / (w - 75));
                position.z = Math.round(Math.max(0, Math.min(325f, position.z)) * 100) / 100f;
            }
            pos2deg();
            Simulation.addTrace(position);
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
            } else if (my > y + 450 + 100 && my < y + 450 + 125 && mx > x + 25 && mx < x + (w - 125) / 3f){ //lerp button
                try {
                    float tx = Float.parseFloat(text[0]);
                    float ty = Float.parseFloat(text[1]);
                    float tz = Float.parseFloat(text[2]);
                    float ts = Float.parseFloat(text[3]);
                    target = new PVector(tx, ty, tz);
                    l_pos = position.copy();
                    speed = Math.max(0, Math.min(100, ts))/100f;
                    lerp = 0;
                } catch (NumberFormatException e) {
                    Console.log("Invalid input", Console.Type.ERROR);
                }
            } else if (my > y + 450 + 100 && my < y + 450 + 125 && mx > x + (w - 125) / 3f - 25 && mx < x + 2 * (w - 125) / 3f){
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

        g.textSize(20);
        //<editor-fold desc="Angles">
        g.translate(0, 50);
        g.text("Angles", 10, 0);
        g.strokeWeight(3);
        g.stroke(255);
        g.line(50, 65, 50 + (w - 75) * (angle.x / Main.maxAngle.x), 65);
        g.line(50, 115, 50 + (w - 75) * (angle.y / Main.maxAngle.y), 115);
        g.line(50, 165, 50 + (w - 75) * (angle.z / Main.maxAngle.z), 165);

        g.stroke(200);
        g.line(50 + (w - 75) * (angle.x / Main.maxAngle.x), 65, (w - 25), 65);
        g.line(50 + (w - 75) * (angle.y / Main.maxAngle.y), 115, (w - 25), 115);
        g.line(50 + (w - 75) * (angle.z / Main.maxAngle.z), 165, (w - 25), 165);
        g.text("1: " + angle.x, 10, 30);
        g.text("2: " + angle.y, 10, 80);
        g.text("3: " + angle.z, 10, 130);

        g.fill(255);
        g.circle(50 + (w - 75) * (angle.x / Main.maxAngle.x), 65, 10);
        g.circle(50 + (w - 75) * (angle.y / Main.maxAngle.y), 115, 10);
        g.circle(50 + (w - 75) * (angle.z / Main.maxAngle.z), 165, 10);
        //</editor-fold>

        //<editor-fold desc="Position">
        g.translate(0, 200);
        g.text("Position", 10, 0);
        g.stroke(255);
        g.line(50, 65, 50 + (w - 75) * ((position.x + 260f) / 520f), 65);
        g.line(50, 115, 50 + (w - 75) * ((position.y + 260f) / 520f), 115);
        g.line(50, 165, 50 + (w - 75) * (position.z / 325f), 165);

        g.stroke(200);
        g.line(50 + (w - 75) * ((position.x + 260f) / 520f), 65, (w - 25), 65);
        g.line(50 + (w - 75) * ((position.y + 260f) / 520f), 115, (w - 25), 115);
        g.line(50 + (w - 75) * (position.z / 325f), 165, (w - 25), 165);
        g.text("x: " + position.x, 10, 30);
        g.text("y: " + position.y, 10, 80);
        g.text("z: " + position.z, 10, 130);

        g.fill(255);
        g.circle(50 + (w - 75) * ((position.x + 260f) / 520f), 65, 10);
        g.circle(50 + (w - 75) * ((position.y + 260f) / 520f), 115, 10);
        g.circle(50 + (w - 75) * (position.z / 325f), 165, 10);
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
        float r = (float) Math.round((150f * Math.cos(Math.toRadians(Main.angle.y)) + 110f * Math.cos(Math.toRadians(Main.angle.y - Main.angle.z))) * 100f) / 100f;
        float x = (float) Math.round(r * Math.cos(Math.toRadians(Main.angle.x)) * 100f) / 100f;
        float y = (float) Math.round(r * Math.sin(Math.toRadians(Main.angle.x)) * 100f) / 100f;
        float z = (float) Math.round((65 + 150 * Math.sin(Math.toRadians(Main.angle.y)) - 110 * Math.sin(Math.toRadians(Main.angle.z - Main.angle.y))) * 100f) / 100f;
        position = new PVector(x, y, z);
    }

    public void pos2deg() { //todo a3 works, only needs limits
        float R = (float) Math.sqrt(Math.pow(position.x, 2) + Math.pow(position.y, 2) + Math.pow(position.z - (Main.arm.x), 2));
        if (R > Main.arm.y + Main.arm.z || R < Math.abs(Main.arm.y - Main.arm.z)) {
            Console.log("Position out of range " + R, Console.Type.ERROR);
            return;
        }
        float a3 = (float) Math.toDegrees(Math.PI - Math.acos((Math.pow(Main.arm.y, 2) + Math.pow(Main.arm.z, 2) - Math.pow(R, 2)) / (2 * Main.arm.y * Main.arm.z)));
        float a2 = (float) Math.toDegrees(Math.acos((Math.pow(R, 2) + Math.pow(Main.arm.y, 2) - Math.pow(Main.arm.z, 2)) / (2 * R * Main.arm.y)) + Math.asin((position.z - Main.arm.x) / R));
        float a1 = (float) Math.toDegrees(Math.atan2(position.y, position.x));
        angle.x = a1;
        angle.y = a2;
        angle.z = a3;
    }

}
