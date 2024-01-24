import processing.core.PGraphics;
import processing.core.PVector;

public class Controller extends Window {
    private PVector angle;
    private PVector position;

    public Controller(int x, int y, int w, int h) {
        super(x, y, w, h);
        angle = new PVector(135, 180, 90);
        position = new PVector(5, 0, 0);
    }

    public void mouseDragged(int dmx, int dmy) {
        super.mouseDragged(dmx, dmy);
        PVector m = Main.getMouse();
        if(m.y > y + 50 && m.y < y + 225) {
            //angle
            if(Math.sqrt(Math.pow((angle.x + (w-75)*(angle.x/180f)) - m.x, 2) + Math.pow(50 - m.y, 2)) < 50) {
                angle.x += ;
            }
        } else if (m.y > y + 225 && m.y < y + 400) {
            //position
        } else if (m.y > y + 400 && m.y < y + h) {
            //text
        }
    }

    public void draw(PGraphics g) {
        super.draw(g);
        g.stroke(200, 200, 200);
        g.line(0, 50, w, 50);
        g.line(10, 225, w-20, 225);
        g.line(10, 400, w-20, 400);
        //g.line(10, 575, w-20, 575);

        g.textSize(20);
        //angles
        g.translate(0, 50);
        g.text("Angles", 10, 0);
        g.strokeWeight(3);
        g.stroke(255);
        g.line(50, 50, 50 + (w-75)*(angle.x/180f), 50);
        g.line(50, 100, 50 + (w-75)*(angle.y/180f), 100);
        g.line(50, 150, 50 + (w-75)*(angle.z/180f), 150);

        g.stroke(200);
        g.line(50 + (w-75)*(angle.x/180f), 50, (w-25), 50);
        g.line(50 + (w-75)*(angle.y/180f), 100, (w-25), 100);
        g.line(50 + (w-75)*(angle.z/180f), 150, (w-25), 150);
        g.text("1: " + angle.x, 10, 20);
        g.text("2: " + angle.y, 10, 70);
        g.text("3: " + angle.z, 10, 120);

        //position
        g.translate(0, 175);
        g.text("Position", 10, 0);
        g.stroke(255);
        g.line(50, 50, 50 + (w-75)*(position.x/10f), 50); // todo change 10f to actual max
        g.line(50, 100, 50 + (w-75)*(position.y/10f), 100);
        g.line(50, 150, 50 + (w-75)*(position.z/10f), 150);
        g.text("x: " + position.x, 10, 20);
        g.text("y: " + position.y, 10, 70);
        g.text("z: " + position.z, 10, 120);

        g.stroke(200);
        g.line(50 + (w-75)*(position.x/10f), 50, (w-25), 50);
        g.line(50 + (w-75)*(position.y/10f), 100, (w-25), 100);
        g.line(50 + (w-75)*(position.z/10f), 150, (w-25), 150);

        //Input
        g.translate(0, 175);
    }

}
