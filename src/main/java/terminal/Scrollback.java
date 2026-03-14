package terminal;

import java.util.ArrayDeque;
import java.util.Deque;

public class Scrollback {
    private final int scrollbackLimit;
    private final Deque<ScreenLine> invisibleScreen;

    public Scrollback(int scrollbackLimit) {
        this.scrollbackLimit = scrollbackLimit;
        invisibleScreen = new ArrayDeque<>();
    }

    public char getCharAtPosition(int col, int row) {
        if (invisibleScreen.size() + row < 0) {
            return '#';
        }
        return DequeUtils.get(invisibleScreen, invisibleScreen.size() + row).getCharAtPosition(col);
    }

    public Attributes getAttrAtPosition(int col, int row) {
        if (invisibleScreen.size() + row < 0) {
            return Attributes.DEFAULT;
        }
        return DequeUtils.get(invisibleScreen, invisibleScreen.size() + row).getAttrAtPosition(col);
    }

    public String getLineAsString(int row) {
        if (invisibleScreen.size() + row < 0) {
            return "ERROR";
        }
        return DequeUtils.get(invisibleScreen, invisibleScreen.size() + row).getAsString();
    }

    public String getContent() {
        StringBuilder result = new StringBuilder();
        for (ScreenLine obj : invisibleScreen) {
            result.append(obj.getAsString());
            result.append('\n');
        }
        return result.toString();
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
