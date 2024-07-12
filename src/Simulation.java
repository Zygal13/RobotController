import processing.core.*;

import java.util.ArrayList;

public class Simulation extends Window {
    PVector CameraAngle = new PVector();
    PShape base;
    PShape shoulder;
    PShape uArm;
    PShape lArm;
    static ArrayList<PVector> trace = new ArrayList<PVector>();

    public Simulation(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void loadShapes(PApplet g) {
        shoulder = g.loadShape("Models/Shoulder.obj");
        uArm = g.loadShape("Models/UpperArm.obj");
        lArm = g.loadShape("Models/LowerArm.obj");

        shoulder.scale(1000f);
        uArm.scale(1000f);
        lArm.scale(1000f);
    }

    public void draw(PGraphics g) {
        g.directionalLight(255, 255, 255, 0, 0, 1);
        g.translate(0, 0, -1000);
        super.draw(g);
        // arrows
        g.pushMatrix();
        g.translate(this.w - 100, 100, 100);
        g.rotateX(-this.CameraAngle.x);
        g.rotateY(this.CameraAngle.y);
        g.stroke(255, 0, 0);
        g.line(0, 0, 0, 50, 0, 0); // x
        g.stroke(0, 0, 255);
        g.line(0, 0, 0, 0, -50, 0); // z
        g.stroke(0, 255, 0);
        g.line(0, 0, 0, 0, 0, -50); // y
        g.popMatrix();

        if (Commands.showArm) drawArm(g);
        if (Commands.showGrid) drawGrid(g);
        if (Commands.showTrace) drawTrace(g);

        g.noLights();
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
        g.rotateX((float) (Math.PI / 2f - this.CameraAngle.x));
        g.rotateZ(-this.CameraAngle.y);
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
        //camera stuff
        g.translate(w / 2f, h * 0.75f, 500);
        g.scale(1, -1, 1);
        g.rotateX(this.CameraAngle.x);
        g.rotateY(this.CameraAngle.y);
        //base
        g.rotateY((float) Math.toRadians(Main.angle.x + 90));
        g.translate(0, Main.BASE_HEIGHT, 0);
        g.shape(shoulder);
        g.translate(0, 40.5f, 0);
        //upper arm
        g.rotateX((float) Math.toRadians(Main.angle.y));
        g.shape(uArm);
        g.translate(0, Main.UPPER_ARM, 0);
        //lower arm
        g.rotateX((float) Math.toRadians(180f-Main.angle.z));
        g.shape(lArm);
        g.translate(0, Main.LOWER_ARM, 0);
        g.popMatrix();
    }

    public void drawTrace(PGraphics g) {
        g.pushMatrix();
        g.translate(w / 2f, h * 0.75f, 500);
        g.scale(1, -1, 1);
        g.rotateX(this.CameraAngle.x);
        g.rotateY(this.CameraAngle.y);
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
        this.CameraAngle.x += dmy * 0.01;
        this.CameraAngle.y += dmx * 0.01;
        this.CameraAngle.x = (float) Math.max(-Math.PI / 2f, Math.min(Math.PI / 2f, this.CameraAngle.x));
    }

    public static void addTrace(PVector v) {
        if (Commands.trace || Commands.forceTrace) trace.add(v.copy());
    }
}
