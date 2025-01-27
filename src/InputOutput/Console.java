package InputOutput;

import Graphics.Window;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Console extends Window {

    public enum Type {
        INFO,
        WARNING,
        ERROR
    }

    private static final ArrayList<String> lines = new ArrayList<String>(); // lines of text of message
    private static final ArrayList<Type> type = new ArrayList<Type>(); // type of message
    private int offset = 0;
    private String input = "";

    public Console(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void draw(PGraphics g) {
        super.draw(g);
        g.fill(255);
        g.textSize(19);
        g.textAlign(g.LEFT, g.BOTTOM);
        for (int i = lines.size() - 1 - offset; i >= Math.max(0, lines.size() - 9 - offset); i--) {
            switch (type.get(i)) {
                case INFO -> g.fill(255);
                case WARNING -> g.fill(255, 255, 0);
                case ERROR -> g.fill(255, 0, 0);
            }
            g.text(lines.get(i), 10, h - 10 - (lines.size() - (i + offset)) * 19);
        }
        g.fill(255);
        g.text(">", 10, h - 10);
        g.text(input, 20, h - 10);
    }

    /**
     * prints the message to the console
     * @param s string to print
     * @param t type of message, INFO, WARNING, ERROR
     */
    public static void log(String s, Type t) {
        lines.add("[" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond() + "] " + s);
        type.add(t);
    }

    @Override
    public void mouseWheel(float e) {
        super.mouseWheel(e);
        offset -= e;
        if (offset > lines.size() - 9) offset = lines.size() - 9;
        if (offset < 0) offset = 0;
    }

    @Override
    public void keyPressed(char key, int keyCode) {
        super.keyPressed(key, keyCode);
        if (keyCode == PConstants.BACKSPACE && input.length() > 0) // remove last character
            input = input.substring(0, input.length() - 1); // remove last character
        else if (keyCode == PConstants.DELETE) input = ""; // clear input
        else if (input.length() < 85 && (Character.isDigit(key) || Character.isLetter(key) || key == ' ' || key == '.')) input += key; // add character
        if (keyCode == PConstants.ENTER) {
            Commands.run(input);
            input = "";
        }
    }

    public static void clear() {
        lines.clear();
        type.clear();
    }
}
