import processing.core.PGraphics;
import processing.core.PVector;

public class Simulation extends Window {
    PVector angle = new PVector();

    public Simulation(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void draw(PGraphics g) {
        super.draw(g);

        // arrows
        g.pushMatrix();
        g.translate(this.w-100, 150, 100);
        g.rotateX(angle.x);
        g.rotateY(angle.y);
        g.stroke(255, 0, 0);
        g.line(0, 0, 0, 50, 0, 0);
        g.stroke(0, 0, 255);
        g.line(0, 0, 0, 0, -50, 0);
        g.stroke(0, 255, 0);
        g.line(0, 0, 0, 0, 0, 50);
        g.popMatrix();
    }

    @Override
    public void mouseDragged(int dmx, int dmy) {
        super.mouseDragged(dmx, dmy);
        angle.x += dmy * 0.01;
        angle.y -= dmx * 0.01;
    }
}
