package terminal;

public class Cursor {
    private final int width;
    private final int height;
    private int x;
    private int y;

    public Cursor(int width, int height) {
        this.width = width;
        this.height = height;
        x = 0;
        y = 0;
    }

    public Cursor moveDown(int step) {
        x += step;
        x = Math.min(x, height - 1);
        return this;
    }

    public Cursor moveUp(int step) {
        x -= step;
        x = Math.max(x, 0);
        return this;
    }

    public Cursor moveRight(int step) {
        y += step;
        y = Math.min(y, width - 1);
        return this;
    }

    public Cursor moveLeft(int step) {
        y -= step;
        y = Math.max(y, 0);
        return this;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Cursor setXAndY(int newX, int newY) {
        x = newX;
        y = newY;
        x = Math.min(x, height - 1);
        y = Math.min(y, width - 1);
        x = Math.max(x, 0);
        y = Math.max(y, 0);
        return this;
    }

    public Cursor symbolWrote() {
        y++;
        if (y == width) {
            x++;
            y = 0;
        }
        return this;
    }
}
