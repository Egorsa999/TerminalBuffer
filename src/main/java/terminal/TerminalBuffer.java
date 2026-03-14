package terminal;

public class TerminalBuffer {
    Screen screen;
    Attributes attributes;
    Scrollback scrollback;
    Cursor cursor;

    public TerminalBuffer (int width, int height, int scrollbackLimit) {
        this.screen = new Screen(width, height);
        this.attributes = Attributes.DEFAULT;
        this.scrollback = new Scrollback(scrollbackLimit);
        this.cursor = new Cursor(width, height);
    }

    public TerminalBuffer setAttr(Attributes newAttributes) {
        this.attributes = newAttributes;
        return this;
    }

    public char getCharAtPosition(int x, int y) {
        if (x >= 0) {
            return screen.getCharAtPosition(x, y);
        } else {
            return scrollback.getCharAtPosition(x, y);
        }
    }

    public Attributes getAttrAtPosition(int x, int y) {
        if (x >= 0) {
            return screen.getAttrAtPosition(x, y);
        } else {
            return scrollback.getAttrAtPosition(x, y);
        }
    }

    public String getLineAsString(int x) {
        if (x >= 0) {
            return screen.getLineAsString(x);
        } else {
            return scrollback.getLineAsString(x);
        }
    }

    public String getScreenContent() {
        return screen.getContent();
    }

    public String getScreenAndScrollbackContent() {
        return scrollback.getContent() +
                screen.getContent();
    }

    public TerminalBuffer insertEmptyLine() {
        scrollback.insertLineAtBack(screen.eraseLineAtFront());
        screen.insertEmptyLineAtBack();
        return this;
    }

    public TerminalBuffer clearScreen() {
        screen.clearContent();
        return this;
    }

    public TerminalBuffer clearScreenAndScrollback() {
        screen.clearContent();
        scrollback.clearContent();
        return this;
    }

    public TerminalBuffer setCursorPosition(int newX, int newY) {
        cursor.setXAndY(newX, newY);
        return this;
    }

    public int getCursorX() {
        return cursor.getX();
    }

    public int getCursorY() {
        return cursor.getY();
    }

    public TerminalBuffer cursorUp(int step) {
        this.cursor.moveUp(step);
        return this;
    }

    public TerminalBuffer cursorDown(int step) {
        this.cursor.moveDown(step);
        return this;
    }

    public TerminalBuffer cursorRight(int step) {
        this.cursor.moveRight(step);
        return this;
    }

    public TerminalBuffer cursorLeft(int step) {
        this.cursor.moveLeft(step);
        return this;
    }
}
