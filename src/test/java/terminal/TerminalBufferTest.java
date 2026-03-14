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

    @Test
    void testNotDependFunctions() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        buffer.insertEmptyLine();
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenContent());
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenAndScrollbackContent());
        buffer.clearScreen();
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenContent());
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenAndScrollbackContent());
        buffer.clearScreenAndScrollback();
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenContent());
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenAndScrollbackContent());
    }

    @Test
    void testCursorMoves() {
        TerminalBuffer buffer = new TerminalBuffer(5, 5, 10);

        assertEquals(0, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());

        buffer.cursorDown(2);

        assertEquals(2, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());

        buffer.cursorDown(100);

        assertEquals(4, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());

        buffer.cursorUp(3);

        assertEquals(1, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());

        buffer.cursorUp(100);

        assertEquals(0, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());

        buffer.cursorRight(2);

        assertEquals(0, buffer.getCursorX());
        assertEquals(2, buffer.getCursorY());

        buffer.cursorRight(100);

        assertEquals(0, buffer.getCursorX());
        assertEquals(4, buffer.getCursorY());

        buffer.cursorLeft(3);

        assertEquals(0, buffer.getCursorX());
        assertEquals(1, buffer.getCursorY());

        buffer.cursorLeft(100);

        assertEquals(0, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());

        buffer.setCursorPosition(20, 20);
        assertEquals(4, buffer.getCursorX());
        assertEquals(4, buffer.getCursorY());

        buffer.setCursorPosition(2, 3);
        assertEquals(2, buffer.getCursorX());
        assertEquals(3, buffer.getCursorY());
    }
}
