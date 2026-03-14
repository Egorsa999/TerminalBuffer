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

    public char getCharAtPosition(int col, int row) {
        if (row >= 0) {
            return screen.getCharAtPosition(col, row);
        } else {
            return scrollback.getCharAtPosition(col, row);
        }
    }

    public Attributes getAttrAtPosition(int col, int row) {
        if (row >= 0) {
            return screen.getAttrAtPosition(col, row);
        } else {
            return scrollback.getAttrAtPosition(col, row);
        }
    }

    public String getLineAsString(int row) {
        if (row >= 0) {
            return screen.getLineAsString(row);
        } else {
            return scrollback.getLineAsString(row);
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

    public TerminalBuffer setCursorPosition(int newCol, int newRow) {
        cursor.setColAndRow(newCol, newRow);
        return this;
    }

    public int getCursorRow() {
        return cursor.getRow();
    }

    public int getCursorCol() {
        return cursor.getCol();
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
            screen.changeCellAtPosition(new Cell(text.charAt(i), attributes), cursor.getCol(), cursor.getRow());
            cursor.symbolWrote();
            if (cursor.getRow() == height) {
                insertEmptyLine();
            }
        }
        return this;
    }

    public TerminalBuffer insertText(String text) {
        int tempLineSize = 0;
        ScreenLine tempLine = new ScreenLine(width);
        for (int i = 0; i < text.length(); i++) {
            Cell returned = screen.insertCellAtPosition(new Cell(text.charAt(i), attributes), cursor.getCol(), cursor.getRow());
            if (returned != Cell.DEFAULT) {
                int currentRow = cursor.getRow();
                do {
                    currentRow++;
                    if (currentRow == height) {
                        returned = tempLine.insertCellAtPosition(returned, 0);
                        tempLineSize++;
                    } else {
                        returned = screen.insertCellAtPosition(returned, 0, currentRow);
                    }
                } while (returned != Cell.DEFAULT);
            }
            cursor.symbolWrote();
            if (cursor.getRow() == height || tempLineSize == width) {
                insertLine(tempLine);
                tempLine = new ScreenLine(width);
                tempLineSize = 0;
            }
        }
        if (tempLineSize > 0) {
            insertLine(tempLine);
        }
        return this;
    }

    public TerminalBuffer fillLineByChar(int row, char symbol) {
        screen.fillLineByCell(row, new Cell(symbol, attributes));
        return this;
    }
}
