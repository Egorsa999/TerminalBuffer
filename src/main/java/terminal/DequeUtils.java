package terminal;

import java.util.Deque;

public class DequeUtils {
    public static <T> T get(Deque<T> deque, int index) {
        if (index < 0 || index >= deque.size()) {
            throw new IndexOutOfBoundsException("Bad index: " + index);
        }
        int currentIndex = 0;
        for (T obj : deque) {
            if (currentIndex == index) {
                return obj;
            }
            currentIndex++;
        }
        return null;
    }
}
