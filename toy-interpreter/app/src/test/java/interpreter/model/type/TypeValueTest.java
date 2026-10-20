package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeValueTest {

    @Test
    void intTypeDefaultValueIsZero() {
        assertEquals(new IntValue(0), new IntType().defaultValue());
    }

    @Test
    void boolTypeDefaultValueIsFalse() {
        assertEquals(new BoolValue(false), new BoolType().defaultValue());
    }

    @Test
    void intValueHasIntType() {
        assertEquals(new IntType(), new IntValue(5).getType());
    }

    @Test
    void boolValueHasBoolType() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }

    @Test
    void twoIntTypesAreEqual() {
        assertEquals(new IntType(), new IntType());
    }

    @Test
    void intTypeAndBoolTypeAreNotEqual() {
        assertNotEquals(new IntType(), new BoolType());
    }
}
