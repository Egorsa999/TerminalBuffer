# Terminal Text Buffer

Java-based core for terminal emulators. Handles grid rendering, cursor logic, and scrollback history.

## Quick Start
```bash
mvn clean test
```

## Technical Decisions

* **ArrayDeque for Scrolling:** Used `ArrayDeque` for `Screen` and `Scrollback` to achieve **O(1) scrolling** (adding/removing lines). Linear access for editing is negligible for terminal heights.
* **Immutable Records:** `Cell` and `Attributes` are Java `records`. This ensures memory efficiency and safety when shifting cells, as they are passed by reference without deep copying.

* **Safe Text Insertion:** Implemented a **shift-register approach** using a `tempLine` buffer. This prevents immediate screen scrolls when text overflows a line, preserving cursor context and ensuring zero data loss during heavy insertions.

## Future Improvements
* **Wide Characters:** Support for CJK/Emoji (2-cell width).
* **Resize Logic:** Dynamic re-flow of text on terminal window resize.
* **Ring Buffer:** Custom storage for O(1) random access and scrolling.