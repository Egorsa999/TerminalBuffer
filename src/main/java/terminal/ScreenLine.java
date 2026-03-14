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

    public char getCharAtPosition(int col) {
        if (col < 0 || col >= width) {
            return '#';
        }
        return currentLine[col].symbol();
    }

    public Attributes getAttrAtPosition(int col) {
        if (col < 0 || col >= width) {
            return Attributes.DEFAULT;
        }
        return currentLine[col].attributes();
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

    public ScreenLine changeCellAtPosition(Cell cell, int col) {
        currentLine[col] = cell;
        return this;
    }

    public Cell insertCellAtPosition(Cell cell, int col) {
        Cell returned = currentLine[width - 1];
        for (int i = width - 1; i > col; i--) {
            currentLine[i] = currentLine[i - 1];
        }
        currentLine[col] = cell;
        return returned;
    }

    public void fillLineByCell(Cell cell) {
        Arrays.fill(currentLine, cell);
    }
}
