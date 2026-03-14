package terminal;

import java.util.Collections;
import java.util.Set;

public record Attributes (
        TerminalColor foreground,
        TerminalColor background,
        Set<TextStyle> styles
) {
    public static final Attributes DEFAULT = new Attributes(
            TerminalColor.DEFAULT,
            TerminalColor.DEFAULT,
            Collections.emptySet()
    );
}
