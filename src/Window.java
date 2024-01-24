import processing.core.PGraphics;

import java.security.PublicKey;

public class Window {
    protected final int x, y, w, h;


    public Window(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(PGraphics g) {
        g.translate(x, y);
        g.fill(0);
        g.stroke(200);
        g.strokeWeight(5);
        g.rect(0, 0, w, h);

        g.fill(255);
        g.textAlign(g.LEFT, g.TOP);
        g.textSize(25);
        g.text("" + this.getClass().getSimpleName() + ":" , 10, 10);
    }

    public void mousePressed(int mx, int my) {

    }

    public void mouseDragged(int dmx, int dmy) {

    }

    public void keyPressed(char key, int keyCode) {

    }

    /**
     * @param mx mouse x
     * @param my mouse y
     * @return true if the mouse is on the window
     */
    public boolean isOn(int mx, int my) {
        return mx > x && mx < x + w && my > y && my < y + h;
    }
}
