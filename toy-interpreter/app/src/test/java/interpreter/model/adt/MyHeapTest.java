package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    private MyHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsIncrementingAddressesStartingAtOne() {
        assertEquals(1, heap.allocate(new IntValue(20)));
        assertEquals(2, heap.allocate(new IntValue(30)));
        assertEquals(3, heap.allocate(new IntValue(40)));
    }

    @Test
    void getReturnsTheStoredValue() throws ADTException {
        int address = heap.allocate(new IntValue(20));
        assertEquals(new IntValue(20), heap.get(address));
    }

    @Test
    void getMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    void updateChangesTheStoredValue() throws ADTException {
        int address = heap.allocate(new IntValue(20));
        heap.update(address, new IntValue(30));
        assertEquals(new IntValue(30), heap.get(address));
    }

    @Test
    void updateMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(30)));
    }

    @Test
    void isDefinedReflectsAllocation() {
        assertFalse(heap.isDefined(1));
        heap.allocate(new IntValue(20));
        assertTrue(heap.isDefined(1));
    }

    @Test
    void getContentIsReadOnlyCopy() {
        heap.allocate(new IntValue(20));
        Map<Integer, interpreter.model.value.IValue> content = heap.getContent();
        assertEquals(1, content.size());
        assertThrows(UnsupportedOperationException.class,
                () -> content.put(2, new IntValue(30)));
    }

    @Test
    void setContentReplacesHeap() {
        heap.allocate(new IntValue(20));
        heap.setContent(Map.of(5, new IntValue(99)));
        assertFalse(heap.isDefined(1));
        assertTrue(heap.isDefined(5));
    }
}
