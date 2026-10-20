package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    private MyDictionary<String, IValue> symTable;

    private static IExp num(int n) { return new ValueExp(new IntValue(n)); }

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void addition() throws MyException {
        assertEquals(new IntValue(7), new ArithExp(ArithOp.ADD, num(3), num(4)).eval(symTable));
    }

    @Test
    void subtraction() throws MyException {
        assertEquals(new IntValue(7), new ArithExp(ArithOp.SUB, num(10), num(3)).eval(symTable));
    }

    @Test
    void multiplication() throws MyException {
        assertEquals(new IntValue(20), new ArithExp(ArithOp.MUL, num(4), num(5)).eval(symTable));
    }

    @Test
    void division() throws MyException {
        assertEquals(new IntValue(5), new ArithExp(ArithOp.DIV, num(10), num(2)).eval(symTable));
    }

    @Test
    void operatorPrecedenceEncodedInTree() throws MyException {
        // 2 + 3 * 5 = 17
        IExp e = new ArithExp(ArithOp.ADD, num(2),
                    new ArithExp(ArithOp.MUL, num(3), num(5)));
        assertEquals(new IntValue(17), e.eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IExp e = new ArithExp(ArithOp.DIV, num(1), num(0));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("division by zero", ex.getMessage());
    }

    @Test
    void nonIntegerLeftOperandThrows() {
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    void nonIntegerRightOperandThrows() {
        IExp e = new ArithExp(ArithOp.MUL, num(2), new ValueExp(new BoolValue(false)));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    void undefinedVariableThrows() {
        IExp e = new ArithExp(ArithOp.ADD, new VarExp("x"), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    void lookupDefinedVariable() throws MyException {
        symTable.put("x", new IntValue(10));
        assertEquals(new IntValue(10), new VarExp("x").eval(symTable));
    }
}
