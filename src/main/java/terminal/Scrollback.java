package terminal;

import java.util.ArrayDeque;
import java.util.Deque;

public class Scrollback {
    private final int scrollbackLimit;
    private final RingBuffer invisibleScreen;

    public Scrollback(int scrollbackLimit) {
        this.scrollbackLimit = scrollbackLimit;
        invisibleScreen = new RingBuffer(scrollbackLimit);
    }

    public char getCharAtPosition(int col, int row) {
        if (invisibleScreen.size() + row < 0) {
            return '#';
        }
        return invisibleScreen.getAtPosition(row).getCharAtPosition(col);
    }

    public Attributes getAttrAtPosition(int col, int row) {
        if (invisibleScreen.size() + row < 0) {
            return Attributes.DEFAULT;
        }
        return invisibleScreen.getAtPosition(row).getAttrAtPosition(col);
    }

    public String getLineAsString(int row) {
        if (invisibleScreen.size() + row < 0) {
            return "ERROR";
        }
        return invisibleScreen.getAtPosition(row).getAsString();
    }

    public String getContent() {
        return invisibleScreen.getContent();
    }

    public Scrollback insertLineAtBack(ScreenLine currentLine) {
        if (invisibleScreen.size() == scrollbackLimit) {
            invisibleScreen.removeFirst();
        }
        invisibleScreen.addLast(currentLine);
        return this;
    }

    public Scrollback clearContent() {
        invisibleScreen.clear();
        return this;
    }
}
