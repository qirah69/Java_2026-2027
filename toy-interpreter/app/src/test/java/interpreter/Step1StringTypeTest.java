package interpreter;

import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Step1StringTypeTest {
    @Test
    void testStringTypeAndValue() {
        StringType type = new StringType();
        StringValue val = new StringValue("hello");
        assertEquals(type, val.getType());
        assertEquals(new StringValue(""), type.defaultValue());
        assertEquals("\"hello\"", val.toString());
    }
}