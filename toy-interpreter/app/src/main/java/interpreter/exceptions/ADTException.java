package interpreter.exceptions;

// thrown for invalid ADT operations: pop from empty stack, missing key, etc.
public class ADTException extends MyException {
    public ADTException(String message) {
        super(message);
    }
}
