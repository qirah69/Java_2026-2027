package interpreter;

import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Step2RelationalExpTest {
    @Test
    void testRelationalExpInts() throws Exception {
        var sym = new MyDictionary<String, IValue>();
        var exp = new RelExp(RelOp.GT, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(3)));
        assertEquals(new BoolValue(true), exp.eval(sym));
    }

    @Test
    void testRelationalExpStrings() throws Exception {
        var sym = new MyDictionary<String, IValue>();
        var exp = new RelExp(RelOp.EQ, new ValueExp(new StringValue("a")), new ValueExp(new StringValue("a")));
        assertEquals(new BoolValue(true), exp.eval(sym));
    }
}