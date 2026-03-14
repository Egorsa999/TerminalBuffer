package terminal;

public class TerminalBuffer {
    private final int width;
    private final int height;
    private final Screen screen;
    private Attributes attributes;
    private final Scrollback scrollback;
    private final Cursor cursor;

    public TerminalBuffer (int width, int height, int scrollbackLimit) {
        this.width = width;
        this.height = height;
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

    private TerminalBuffer insertLine(ScreenLine line) {
        scrollback.insertLineAtBack(screen.eraseLineAtFront());
        screen.insertLineAtBack(line);
        cursor.moveUp(1);
        return this;
    }

    public TerminalBuffer insertEmptyLine() {
        scrollback.insertLineAtBack(screen.eraseLineAtFront());
        screen.insertEmptyLineAtBack();
        cursor.moveUp(1);
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

    public TerminalBuffer writeTextOverContent(String text) {
        for (int i = 0; i < text.length(); i++) {
            screen.changeCellAtPosition(new Cell(text.charAt(i), attributes), cursor.getX(), cursor.getY());
            cursor.symbolWrote();
            if (cursor.getX() == height) {
                insertEmptyLine();
            }
        }
        return this;
    }

    public TerminalBuffer insertText(String text) {
        boolean tempLineEmpty = true;
        ScreenLine tempLine = new ScreenLine(width);
        for (int i = 0; i < text.length(); i++) {
            Cell returned = screen.insertCellAtPosition(new Cell(text.charAt(i), attributes), cursor.getX(), cursor.getY());
            if (returned != Cell.DEFAULT) {
                int currentX = cursor.getX();
                do {
                    currentX++;
                    if (currentX == height) {
                        returned = tempLine.insertCellAtPosition(returned, 0);
                        tempLineEmpty = false;
                    } else {
                        returned = screen.insertCellAtPosition(returned, currentX, 0);
                    }
                } while (returned != Cell.DEFAULT);
            }
            cursor.symbolWrote();
            if (cursor.getX() == height) {
                insertLine(tempLine);
                tempLine = new ScreenLine(width);
                tempLineEmpty = true;
            }
        }
        if (!tempLineEmpty) {
            insertLine(tempLine);
        }
        return this;
    }

    public TerminalBuffer fillLineByChar(int x, char symbol) {
        screen.fillLineByCell(x, new Cell(symbol, attributes));
        return this;
    }
}
