package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private MyDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void putAndLookup() throws ADTException {
        dict.put("x", 5);
        assertEquals(5, dict.lookup("x"));
    }

    @Test
    void isDefinedReturnsFalseForMissingKey() {
        assertFalse(dict.isDefined("missing"));
    }

    @Test
    void isDefinedReturnsTrueAfterPut() {
        dict.put("a", 1);
        assertTrue(dict.isDefined("a"));
    }

    @Test
    void lookupMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("noSuchKey"));
    }

    // TODO: test update once implemented
    // TODO: test remove once implemented
}
