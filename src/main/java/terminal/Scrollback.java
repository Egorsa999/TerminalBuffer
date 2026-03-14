package terminal;

import java.util.ArrayDeque;
import java.util.Deque;

public class Scrollback {
    int scrollbackLimit;
    Deque<ScreenLine> invisibleScreen;

    public Scrollback(int scrollbackLimit) {
        this.scrollbackLimit = scrollbackLimit;
        invisibleScreen = new ArrayDeque<>();
    }

    public char getCharAtPosition(int x, int y) {
        if (invisibleScreen.size() + x < 0) {
            return '#';
        }
        return DequeUtils.get(invisibleScreen, invisibleScreen.size() + x).getCharAtPosition(y);
    }

    public Attributes getAttrAtPosition(int x, int y) {
        if (invisibleScreen.size() + x < 0) {
            return Attributes.DEFAULT;
        }
        return DequeUtils.get(invisibleScreen, invisibleScreen.size() + x).getAttrAtPosition(y);
    }

    public String getLineAsString(int x) {
        if (invisibleScreen.size() + x < 0) {
            return "ERROR";
        }
        return DequeUtils.get(invisibleScreen, invisibleScreen.size() + x).getAsString();
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
