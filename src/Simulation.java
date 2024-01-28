import processing.core.*;

import java.util.ArrayList;

public class Simulation extends Window {
    PVector angle = new PVector();
    PShape base;
    PShape shoulder;
    PShape uArm;
    PShape lArm;
    static ArrayList<PVector> trace = new ArrayList<PVector>();

    public Simulation(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void loadShapes(PApplet g) {
        base = g.loadShape("Models/Base.obj");
        shoulder = g.loadShape("Models/Shoulder.obj");
        uArm = g.loadShape("Models/UpperArm.obj");
        lArm = g.loadShape("Models/LowerArm.obj");
    }

    public void draw(PGraphics g) {
        g.directionalLight(255, 255, 255, 0, 0, 1);
        g.translate(0, 0, -1000);
        super.draw(g);
        // arrows
        g.pushMatrix();
        g.translate(this.w - 100, 100, 100);
        g.rotateX(-this.angle.x);
        g.rotateY(this.angle.y);
        g.stroke(255, 0, 0);
        g.line(0, 0, 0, 50, 0, 0);
        g.stroke(0, 0, 255);
        g.line(0, 0, 0, 0, -50, 0);
        g.stroke(0, 255, 0);
        g.line(0, 0, 0, 0, 0, 50);
        g.popMatrix();

        if (Commands.showArm) drawArm(g);
        if (Commands.showGrid) drawGrid(g);
        if (Commands.showTrace) drawTrace(g);
        g.directionalLight(255, 255, 255, 0, 0, -1);

        g.ambient(255);
        g.translate(0, 0, 850);
        g.noStroke();
        g.fill(64);
        g.rect(0, h, w, h + 100);
        g.rect(0, 0, w, -100);
        g.strokeWeight(5);
        g.stroke(200);
        g.line(0, h, w, h);
        g.line(0, 0, w, 0);
    }

    private void drawGrid(PGraphics g) {
        g.pushMatrix();
        g.translate(w / 2f, h * 0.75f, 500);
        g.rotateX((float) (Math.PI / 2f - this.angle.x));
        g.rotateZ(-this.angle.y);
        g.noFill();
        g.stroke(100);
        g.strokeWeight(1);
        int num = 8;
        for (int i = 1; i <= num; i++) {
            float n = 2f / (num + 1) * i - 1;
            float v = (float) (260f * Math.sin(Math.acos(n)));
            g.line(260f * n, v, 0, 260f * n, -v, 0);
            g.line(v, 260f * n, 0, -v, 260f * n, 0);
        }
        g.directionalLight(255, 255, 255, 0, 0, -1);
        g.fill(255);
        g.textSize(50);
        g.textAlign(PConstants.CENTER, PConstants.CENTER);
        g.text("Y", 0, -260, 0);
        g.translate(260, 0, 0);
        g.rotateZ((float) (Math.PI / 2f));
        g.text("X", 0, 0, 0);
        g.directionalLight(255, 255, 255, 0, 0, 1);
        g.popMatrix();
    }

    private void drawArm(PGraphics g) {
        g.pushMatrix();
        g.translate(w / 2f, h * 0.75f, 500);
        g.scale(1, -1, 1);
        g.rotateX(this.angle.x);
        g.rotateY(this.angle.y);
        g.translate(0, 22.5f, 0);
        g.shape(base);
        g.translate(0, Main.arm.x - 25, 0);
        g.rotateY((float) Math.toRadians(Main.angle.x));
        g.shape(shoulder);
        g.translate(0, 2.5f, -11.25f);
        g.rotateZ((float) (Math.toRadians(Main.angle.y) - Math.PI / 2f));
        g.translate(0, Main.arm.y / 2f, 0);
        g.shape(uArm);
        g.translate(0, Main.arm.y / 2f, 22.5f);
        g.rotateZ((float) (Math.PI - Math.toRadians(Main.angle.z)));
        g.translate(0, -Main.arm.z / 2f, 0);
        g.shape(lArm);
        g.popMatrix();
    }

    public void drawTrace(PGraphics g) {
        g.pushMatrix();
        g.translate(w / 2f, h * 0.75f, 500);
        g.scale(1, -1, 1);
        g.rotateX(this.angle.x);
        g.rotateY(this.angle.y);
        g.translate(0, 22.5f, 0);
        g.stroke(0, 255, 0);
        for (int i = 0; i < trace.size(); i++) {
            g.point(trace.get(i).x, trace.get(i).z - 20, -trace.get(i).y);
            //g.line(trace.get(i - 1).x, trace.get(i - 1).z - 20, -trace.get(i - 1).y, trace.get(i).x, trace.get(i).z - 20, -trace.get(i).y);
        }
        g.popMatrix();
    }

    @Override
    public void mouseDragged(int mx, int my) {
        super.mouseDragged(mx, my);
        float dmx = mx - Main.getMouse().x;
        float dmy = my - Main.getMouse().y;
        this.angle.x += dmy * 0.01;
        this.angle.y += dmx * 0.01;
        this.angle.x = (float) Math.max(-Math.PI / 2f, Math.min(Math.PI / 2f, this.angle.x));
    }

    public static void addTrace(PVector v) {
        if (Commands.trace || Commands.forceTrace) trace.add(v.copy());
    }
}
