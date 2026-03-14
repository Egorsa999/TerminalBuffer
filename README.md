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

---

## Post-Submission Improvements

After the initial submission, I conducted a deep self-review and implemented significant architectural and performance optimizations. These changes are located in the dedicated branch: `refactor/architecture-improvements`.

### Key Enhancements:
* **Performance Optimization:** Replaced manual loops with `System.arraycopy` for cell shifting within lines, significantly reducing overhead for line wrapping.
* **Stream Parsing:** Implemented handling for `\n` (Line Feed) and `\r` (Carriage Return) control characters to support realistic shell output.
* **Standardized Coordinates:** Refactored the coordinate system from `X/Y` to `Col/Row` to align with industry-standard UI and terminal conventions.
* **Memory Management:** Transitioned from `ArrayDeque` to a more efficient data structure (RingBuffer logic) for O(1) row access and scrolling.

I chose to maintain these improvements in a separate branch to respect the original submission deadline while demonstrating the ability to iterate and deliver a production-grade terminal core.