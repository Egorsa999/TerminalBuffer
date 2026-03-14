package terminal;

import java.util.ArrayDeque;
import java.util.Deque;

public class Screen {
    private final int width;
    private final int height;
    private final Deque<ScreenLine> screen;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        screen = new ArrayDeque<>();

        for (int i = 0; i < height; i++) {
            this.screen.addLast(new ScreenLine(width));
        }
    }

    public char getCharAtPosition(int col, int row) {
        if (row >= height || col >= width || col < 0) {
            return '#';
        } else {
            return DequeUtils.get(screen, row).getCharAtPosition(col);
        }
    }

    public Attributes getAttrAtPosition(int col, int row) {
        if (row >= height || col >= width || col < 0) {
            return Attributes.DEFAULT;
        } else {
            return DequeUtils.get(screen, row).getAttrAtPosition(col);
        }
    }

    public String getLineAsString(int row) {
        if (row >= height) {
            return "ERROR";
        } else {
            return DequeUtils.get(screen, row).getAsString();
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

    public ScreenLine eraseLineAtFront() {
        return screen.removeFirst();
    }

    public Screen insertLineAtBack(ScreenLine line) {
        screen.addLast(line);
        return this;
    }

    public Screen insertEmptyLineAtBack() {
        this.insertLineAtBack(new ScreenLine(width));
        return this;
    }

    public Screen clearContent() {
        for (ScreenLine obj : screen) {
            obj.clearContent();
        }
        return this;
    }

    public Screen changeCellAtPosition(Cell cell, int col, int row) {
        DequeUtils.get(screen, row).changeCellAtPosition(cell, col);
        return this;
    }

    public Cell insertCellAtPosition(Cell cell, int col, int row) {
        return DequeUtils.get(screen, row).insertCellAtPosition(cell, col);
    }

    public Screen fillLineByCell(int row, Cell cell) {
        if (row < 0 || row >= height) return this;
        DequeUtils.get(screen, row).fillLineByCell(cell);
        return this;
    }
}
