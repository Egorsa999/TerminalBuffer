package terminal;

public class Cursor {
    private final int width;
    private final int height;
    private int row;
    private int col;

    public Cursor(int width, int height) {
        this.width = width;
        this.height = height;
        row = 0;
        col = 0;
    }

    public Cursor moveDown(int step) {
        row += step;
        row = Math.min(row, height - 1);
        return this;
    }

    public Cursor moveUp(int step) {
        row -= step;
        row = Math.max(row, 0);
        return this;
    }

    public Cursor moveRight(int step) {
        col += step;
        col = Math.min(col, width - 1);
        return this;
    }

    public Cursor moveLeft(int step) {
        col -= step;
        col = Math.max(col, 0);
        return this;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Cursor setColAndRow(int col, int row) {
        this.row = row;
        this.col = col;
        this.row = Math.min(this.row, height - 1);
        this.col = Math.min(this.col, width - 1);
        this.row = Math.max(this.row, 0);
        this.col = Math.max(this.col, 0);
        return this;
    }

    public Cursor symbolWrote() {
        col++;
        if (col == width) {
            row++;
            col = 0;
        }
        return this;
    }
}
