package terminal;

import java.util.Set;

public record Attributes (
        TerminalColor foreground,
        TerminalColor background,
        Set<TextStyle> styles
) {}
