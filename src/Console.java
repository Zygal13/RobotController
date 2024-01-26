import processing.core.PGraphics;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Console extends Window {
    enum Type {
        INFO,
        WARNING,
        ERROR
    }
    private static ArrayList<String> lines = new ArrayList<String>();
    private static ArrayList<Type> type = new ArrayList<Type>();
    private int offset = 0;

    public Console(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void draw(PGraphics g) {
        super.draw(g);
        g.fill(255);
        g.textSize(19);
        g.textAlign(g.LEFT, g.BOTTOM);
        for (int i = lines.size() - 1 - offset; i >= Math.max(0, lines.size() - 10 - offset); i--) {
            switch (type.get(i)) {
                case INFO:
                    g.fill(255);
                    break;
                case WARNING:
                    g.fill(255, 255, 0);
                    break;
                case ERROR:
                    g.fill(255, 0, 0);
                    break;
            }
            g.text(lines.get(i), 10, h - 10 - (lines.size() - (i + offset) - 1) * 19);
        }
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
        if (offset < 0) offset = 0;
        if (offset > lines.size()-10) offset = lines.size()-10;
    }
}
