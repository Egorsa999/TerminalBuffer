package terminal;

public class RingBuffer {
    private final ScreenLine[] buffer;
    private final int capacity;
    private int head;
    private int size;

    public RingBuffer(int capacity) {
        this.buffer = new ScreenLine[capacity];
        this.capacity = capacity;
        this.head = 0;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void addLast(ScreenLine screenLine) {
        buffer[(head + size) % capacity] = screenLine;
        size++;
    }

    public ScreenLine getAtPosition(int row) {
        return buffer[(head + row) % capacity];
    }

    public ScreenLine removeFirst() {
        ScreenLine returned = buffer[head];
        head++;
        head %= capacity;
        size--;
        return returned;
    }

    public void clearContent() {
        for (int i = 0; i < size; i++) {
            buffer[(head + i) % capacity].clearContent();
        }
    }

    public String getContent() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(buffer[(head + i) % capacity].getAsString());
            result.append('\n');
        }
        return result.toString();
    }

    public void clear() {
        size = 0;
    }
}
