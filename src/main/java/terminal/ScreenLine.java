package terminal;

import java.util.Arrays;

public class ScreenLine {
    private final int width;
    private final Cell[] currentLine;

    public ScreenLine(int width) {
        this.width = width;
        this.currentLine = new Cell[width];
        Arrays.fill(this.currentLine, Cell.DEFAULT);
    }

    public char getCharAtPosition(int y) {
        if (y < 0 || y >= width) {
            return '#';
        }
        return currentLine[y].symbol();
    }

    public Attributes getAttrAtPosition(int y) {
        if (y < 0 || y >= width) {
            return Attributes.DEFAULT;
        }
        return currentLine[y].attributes();
    }

    public String getAsString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < width; i++) {
            result.append('[')
                    .append(currentLine[i].symbol())
                    .append(']');
        }
        return result.toString();
    }

    public ScreenLine clearContent() {
        for (int i = 0; i < width; i++) {
            currentLine[i] = Cell.DEFAULT;
        }
        return this;
    }

    public ScreenLine changeCellAtPosition(Cell cell, int y) {
        currentLine[y] = cell;
        return this;
    }

    public Cell insertCellAtPosition(Cell cell, int y) {
        Cell returned = currentLine[width - 1];
        for (int i = width - 1; i > y; i--) {
            currentLine[i] = currentLine[i - 1];
        }
        currentLine[y] = cell;
        return returned;
    }

    public void fillLineByCell(Cell cell) {
        Arrays.fill(currentLine, cell);
    }
}
