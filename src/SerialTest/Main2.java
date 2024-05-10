package SerialTest;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.serial.Serial;

public class Main2 extends PApplet {
    private String input = "";
    private Serial myPort;

    @Override
    public void draw() {
        background(255);
        fill(0);
        textSize(32);
        text(input, 10, 30);
        line(0, 45, width, 45);
        for (int i = 0; i < Serial.list().length; i++) {
            text(Serial.list()[i], 10, 80 + i * 30);
        }

        while (myPort != null && myPort.available() > 0) {
            int inByte =  myPort.read();
            println(inByte);
        }
    }

    @Override
    public void mousePressed() {
        int index = (mouseY - 80) / 30;
        if(index >= 0 && index < Serial.list().length) {
            try {
                myPort = new Serial(this, Serial.list()[index], 9600);
                System.out.println("Connected to " + Serial.list()[index]);
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void keyPressed() {
        if (keyCode == PConstants.BACKSPACE && input.length() > 0)
            input = input.substring(0, input.length() - 1);
        else if (keyCode == PConstants.DELETE) input = "";
        else if (input.length() < 85 && (Character.isDigit(key) || Character.isLetter(key) || key == ' ' || key == '-')) input += key;
        if (keyCode == PConstants.ENTER) {
            input.strip();
            System.out.println((int) input.charAt(0));
            if (myPort != null) myPort.write(input.charAt(0));
        }
    }

    @Override
    public void setup() {
        printArray(Serial.list());
    }

    @Override
    public void settings() {
        size(1280, 720);
    }

    public static void main(String[] args) {
        PApplet.main("SerialTest.Main2");
    }
}
