package interpreter.model.type;

import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    void defaultValueIsNullRefOfInnerType() {
        var t = new RefType(new IntType());
        assertEquals(new RefValue(0, new IntType()), t.defaultValue());
    }

    @Test
    void nestedRefTypesCompareStructurally() {
        var a = new RefType(new RefType(new IntType()));
        var b = new RefType(new RefType(new IntType()));
        var c = new RefType(new IntType());

        assertEquals(a, b);
        assertNotEquals(a, c);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toStringShowsNesting() {
        var t = new RefType(new RefType(new IntType()));
        assertEquals("Ref(Ref(int))", t.toString());
    }
}