package terminal;

public record Cell (
        char symbol,
        Attributes attributes
) {
    public static final Cell DEFAULT = new Cell(
            '\0',
            Attributes.DEFAULT
    );
}
