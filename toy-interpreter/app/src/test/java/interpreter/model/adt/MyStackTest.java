package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    private MyStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    @Test
    void newStackIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    void pushMakesStackNonEmpty() {
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @Test
    void lifoOrder() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void popOnEmptyStackThrows() {
        assertThrows(ADTException.class, () -> stack.pop());
    }

    @Test
    void peekDoesNotRemoveElement() throws ADTException {
        stack.push(42);
        assertEquals(42, stack.peek());
        assertFalse(stack.isEmpty());
    }

    @Test
    void peekOnEmptyStackThrows() {
        assertThrows(ADTException.class, () -> stack.peek());
    }
}
