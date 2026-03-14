package terminal;

import java.util.Arrays;

public class ScreenLine {
    int width;
    Cell[] currentLine;

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
}
