package terminal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TerminalBufferTest {

    @Test
    void testContentAccess() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenContent());
        assertEquals('\0', buffer.getCharAtPosition(0, 0));
        assertEquals('\0', buffer.getCharAtPosition(1, 4));
    }

    @Test
    void testContentAccessOutOfBounds() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        assertEquals('#', buffer.getCharAtPosition(2, 0));
        assertEquals('#', buffer.getCharAtPosition(-1, 0));
        assertEquals('#', buffer.getCharAtPosition(0, 5));
        assertEquals('#', buffer.getCharAtPosition(0, -1));
        assertEquals("ERROR", buffer.getLineAsString(-1));
        assertEquals("ERROR", buffer.getLineAsString(2));
    }
}
