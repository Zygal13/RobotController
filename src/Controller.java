import processing.core.PGraphics;
import processing.core.PVector;

public class Controller extends Window {
    private PVector angle;
    private PVector position;

    public Controller(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.angle = Main.angle;
        position = new PVector(5, 0, 0);
    }

    public void mouseDragged(int mx, int my) {
        super.mouseDragged(mx, my);
        if (my > y + 50 && my < y + 225) {
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
        } else if (my > y + 225 && my < y + 400) {
            //position

            if (Math.sqrt(Math.pow((50 + (w - 75) * (position.x / 260f)) - (mx - this.x), 2) + Math.pow(50 - (my - this.y - 250), 2)) < 25) {
                position.x = ((mx - this.x) - 50) * (260f / (w - 75));
                position.x = Math.round(Math.max(-260, Math.min(260f, position.x)) * 100) / 100f;
            } else if (Math.sqrt(Math.pow((50 + (w - 75) * (position.y / 10f)) - (mx - this.x), 2) + Math.pow(100 - (my - this.y - 250), 2)) < 25) {
                position.y = ((mx - this.x) - 50) * (260f / (w - 75));
                position.y = Math.round(Math.max(-260, Math.min(260f, position.y)) * 100) / 100f;
            } else if (Math.sqrt(Math.pow((50 + (w - 75) * (position.z / 325f)) - (mx - this.x), 2) + Math.pow(150 - (my - this.y - 250), 2)) < 25) {
                position.z = ((mx - this.x) - 50) * (325f / (w - 75));
                position.z = Math.round(Math.max(-65, Math.min(325f, position.z)) * 100) / 100f;
            } else if (my > y + 400 && my < y + h) {
                //text
            }
        }
    }

    public void draw(PGraphics g) {
        super.draw(g);
        g.stroke(200, 200, 200);
        g.line(0, 50, w, 50);
        g.line(10, 250, w - 20, 250);
        g.line(10, 450, w - 20, 450);
        //g.line(10, 575, w-20, 575);

        g.textSize(20);
        //angles
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

        //position
        g.translate(0, 200);
        g.text("Position", 10, 0);
        g.stroke(255);
        g.line(50, 65, 50 + (w - 75) * (position.x / 10f), 65);
        g.line(50, 115, 50 + (w - 75) * (position.y / 10f), 115);
        g.line(50, 165, 50 + (w - 75) * (position.z / 10f), 165);

        g.stroke(200);
        g.line(50 + (w - 75) * (position.x / 10f), 65, (w - 25), 65);
        g.line(50 + (w - 75) * (position.y / 10f), 115, (w - 25), 115);
        g.line(50 + (w - 75) * (position.z / 10f), 165, (w - 25), 165);
        g.text("x: " + position.x, 10, 30);
        g.text("y: " + position.y, 10, 80);
        g.text("z: " + position.z, 10, 130);

        g.fill(255);
        g.circle(50 + (w - 75) * (position.x / 10f), 65, 10);
        g.circle(50 + (w - 75) * (position.y / 10f), 115, 10);
        g.circle(50 + (w - 75) * (position.z / 10f), 165, 10);

        //Input
        g.translate(0, 200);
    }

    public void deg2pos() {
        float r = (float) Math.round((150f*Math.cos(Math.toRadians(Main.angle.y)) + 110f*Math.cos(Math.toRadians(Main.angle.y - Main.angle.z)))*100f)/100f;
        float x = (float) Math.round(r*Math.cos(Math.toRadians(Main.angle.x))*100f)/100f;
        float y = (float) Math.round(r*Math.sin(Math.toRadians(Main.angle.x))*100f)/100f;
        float z = (float) Math.round((65 + 150*Math.sin(Math.toRadians(Main.angle.y)) - 110*Math.sin(Math.toRadians(Main.angle.z - Main.angle.y)))*100f)/100f;
        position = new PVector(x, y, z);
    }

}
