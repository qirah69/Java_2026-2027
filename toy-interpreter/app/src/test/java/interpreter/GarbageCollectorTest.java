package interpreter;

import interpreter.controller.GarbageCollector;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GarbageCollectorTest {

    // The state of Ex9 after new(v, 30): v -> (3, int), a -> (2, Ref(int))
    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()),
            new RefValue(2, new RefType(new IntType()))
    );

    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20),
            2, new RefValue(1, new IntType()),
            3, new IntValue(30),
            4, new IntValue(7)
    );

    @Test
    void getAddrFromSymTableExtractsAddresses() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        assertEquals(Set.of(2, 3), roots);
    }

    @Test
    void unsafeCollectorKeepsOnlyDirectSymTableReferences() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.unsafeGarbageCollector(roots, heap);

        // Address 1 is dropped because it's only reachable through address 2,
        // not directly from the SymTable. Address 4 is garbage.
        assertEquals(Set.of(2, 3), kept.keySet());
    }

    @Test
    void safeCollectorKeepsLocationsReachableThroughTheHeap() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);

        // Address 1 is kept because 2 refers to it. Address 4 is garbage.
        assertEquals(Set.of(1, 2, 3), kept.keySet());
    }

    @Test
    void reachableAddressesFollowsRefValuesUntilFixedPoint() {
        Set<Integer> reachable = GarbageCollector.reachableAddresses(Set.of(3), heap);
        assertEquals(Set.of(2, 3), reachable);
    }
}