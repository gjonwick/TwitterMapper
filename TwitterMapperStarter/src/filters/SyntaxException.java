package filters;

/**
 * The exception thrown when parsing a string fails.
 */
public class SyntaxException extends Exception {
    public SyntaxException(String s) {
        super(s);
    }
    public SyntaxException() {
        super();
    }
}
