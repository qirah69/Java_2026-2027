package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private MyList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void newListHasSizeZero() {
        assertEquals(0, list.size());
    }

    @Test
    void addIncreasesSize() {
        list.add("a");
        assertEquals(1, list.size());
    }

    @Test
    void getReturnsCorrectElement() throws ADTException {
        list.add("first");
        list.add("second");
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void getOutOfBoundsThrows() {
        list.add("only");
        assertThrows(ADTException.class, () -> list.get(5));
    }

    @Test
    void getNegativeIndexThrows() {
        assertThrows(ADTException.class, () -> list.get(-1));
    }
}
