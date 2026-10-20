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

    @Test
    void updateChangesValue() throws ADTException {
        dict.put("v", 10);
        dict.update("v", 20);
        assertEquals(20, dict.lookup("v"));
    }

    @Test
    void updateMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.update("ghost", 99));
    }

    @Test
    void removeDeletesKey() throws ADTException {
        dict.put("k", 7);
        dict.remove("k");
        assertFalse(dict.isDefined("k"));
    }

    @Test
    void removeMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.remove("absent"));
    }
}
