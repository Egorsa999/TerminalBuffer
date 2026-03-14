package terminal;

import java.util.ArrayDeque;
import java.util.Deque;

public class Screen {
    int width;
    int height;
    Deque<ScreenLine> screen;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        screen = new ArrayDeque<>();

        for (int i = 0; i < height; i++) {
            this.screen.addLast(new ScreenLine(width));
        }
    }

    public char getCharAtPosition(int x, int y) {
        if (x >= height || y >= width || y < 0) {
            return '#';
        } else {
            return DequeUtils.get(screen, x).getCharAtPosition(y);
        }
    }

    public Attributes getAttrAtPosition(int x, int y) {
        if (x >= height || y >= width || y < 0) {
            return Attributes.DEFAULT;
        } else {
            return DequeUtils.get(screen, x).getAttrAtPosition(y);
        }
    }

    public String getLineAsString(int x) {
        if (x >= height) {
            return "ERROR";
        } else {
            return DequeUtils.get(screen, x).getAsString();
        }
    }

    public String getContent() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < height; i++) {
            result.append(this.getLineAsString(i));
            result.append('\n');
        }
        return result.toString();
    }
}
