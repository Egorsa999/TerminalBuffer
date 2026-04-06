package terminal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TerminalBufferTest {

    @Test
    void testContentAccess() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenContent());
        assertEquals('\0', buffer.getCharAtPosition(0, 0));
        assertEquals('\0', buffer.getCharAtPosition(4, 1));
    }

    @Test
    void testContentAccessOutOfBounds() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        assertEquals('#', buffer.getCharAtPosition(0, 2));
        assertEquals('#', buffer.getCharAtPosition(0, -1));
        assertEquals('#', buffer.getCharAtPosition(5, 0));
        assertEquals('#', buffer.getCharAtPosition(-1, 0));
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

        assertEquals(0, buffer.getCursorRow());
        assertEquals(0, buffer.getCursorCol());

        buffer.cursorDown(2);

        assertEquals(2, buffer.getCursorRow());
        assertEquals(0, buffer.getCursorCol());

        buffer.cursorDown(100);

        assertEquals(4, buffer.getCursorRow());
        assertEquals(0, buffer.getCursorCol());

        buffer.cursorUp(3);

        assertEquals(1, buffer.getCursorRow());
        assertEquals(0, buffer.getCursorCol());

        buffer.cursorUp(100);

        assertEquals(0, buffer.getCursorRow());
        assertEquals(0, buffer.getCursorCol());

        buffer.cursorRight(2);

        assertEquals(0, buffer.getCursorRow());
        assertEquals(2, buffer.getCursorCol());

        buffer.cursorRight(100);

        assertEquals(0, buffer.getCursorRow());
        assertEquals(4, buffer.getCursorCol());

        buffer.cursorLeft(3);

        assertEquals(0, buffer.getCursorRow());
        assertEquals(1, buffer.getCursorCol());

        buffer.cursorLeft(100);

        assertEquals(0, buffer.getCursorRow());
        assertEquals(0, buffer.getCursorCol());

        buffer.setCursorPosition(20, 20);
        assertEquals(4, buffer.getCursorRow());
        assertEquals(4, buffer.getCursorCol());

        buffer.setCursorPosition(3, 2);
        assertEquals(2, buffer.getCursorRow());
        assertEquals(3, buffer.getCursorCol());
    }

    @Test
    void testWriteTextOverrideContent() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        assertEquals("[\0][\0][\0][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenContent());
        buffer.writeTextOverContent("BOB");
        assertEquals("[B][O][B][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenContent());
        buffer.writeTextOverContent("BOB");
        assertEquals("[B][O][B][B][O]\n[B][\0][\0][\0][\0]\n", buffer.getScreenContent());
        buffer.writeTextOverContent("AAAAAAAAAAAA");
        assertEquals("[A][A][A][A][A]\n[A][A][A][\0][\0]\n", buffer.getScreenContent());
        assertEquals("[B][O][B][B][O]\n[B][A][A][A][A]\n[A][A][A][A][A]\n[A][A][A][\0][\0]\n", buffer.getScreenAndScrollbackContent());

        buffer.setCursorPosition(1, 1);
        buffer.writeTextOverContent("CC");
        assertEquals("[B][O][B][B][O]\n[B][A][A][A][A]\n[A][A][A][A][A]\n[A][C][C][\0][\0]\n", buffer.getScreenAndScrollbackContent());
    }

    @Test
    void testFillLine() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        buffer.fillLineByChar(0, 'A');
        buffer.fillLineByChar(1, 'B');
        assertEquals("[A][A][A][A][A]\n[B][B][B][B][B]\n", buffer.getScreenAndScrollbackContent());
    }

    @Test
    void testInsertText() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 10);
        buffer.setCursorPosition(0, 0).insertText("AAA");
        assertEquals("[A][A][A][\0][\0]\n[\0][\0][\0][\0][\0]\n", buffer.getScreenAndScrollbackContent());

        buffer.setCursorPosition(2, 0).insertText("BBB");
        assertEquals("[A][A][B][B][B]\n[A][\0][\0][\0][\0]\n", buffer.getScreenAndScrollbackContent());

        buffer.setCursorPosition(3, 0).insertText("CCCCCCCCCCCCCC");
        assertEquals("[A][A][B][C][C]\n[C][C][C][C][C]\n[C][C][C][C][C]\n[C][C][B][B][A]\n", buffer.getScreenAndScrollbackContent());
    }

    @Test
    void testOverflow() {
        TerminalBuffer buffer = new TerminalBuffer(5, 2, 2);
        buffer.writeTextOverContent("AAAAABBBBBCCCCC");
        buffer.writeTextOverContent("DDDDDEEEEE");
        assertEquals("""
                [C][C][C][C][C]
                [D][D][D][D][D]
                [E][E][E][E][E]
                [\0][\0][\0][\0][\0]
                """, buffer.getScreenAndScrollbackContent());
        buffer.setCursorPosition(4, 0).insertText("123456");
        assertEquals("""
                [D][D][D][D][D]
                [E][E][E][E][1]
                [2][3][4][5][6]
                [E][\0][\0][\0][\0]
                """, buffer.getScreenAndScrollbackContent());
    }

    @Test
    void testSpecialSymbols() {
        TerminalBuffer buffer = new TerminalBuffer(5, 4, 2);
        buffer.insertText("Hello\n\rWorld");
        assertEquals("""
                [H][e][l][l][o]
                [\0][\0][\0][\0][\0]
                [W][o][r][l][d]
                [\0][\0][\0][\0][\0]
                """, buffer.getScreenAndScrollbackContent());
    }

    @Test
    void testInsertText2() {
        TerminalBuffer buffer = new TerminalBuffer(5, 4, 2);
        buffer.fillLineByChar(0, 'A');
        buffer.fillLineByChar(1, 'A');
        buffer.fillLineByChar(2, 'A');
        buffer.fillLineByChar(3, 'C');
        buffer.setCursorPosition(3, 3).insertText("B".repeat(25));
        assertEquals("""
                [C][C][C][B][B]
                [B][B][B][B][B]
                [B][B][B][B][B]
                [B][B][B][B][B]
                [B][B][B][B][B]
                [B][B][B][C][C]
                """, buffer.getScreenAndScrollbackContent());
    }
}
