package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state); // Ref int v
        new VarDeclStmt("x", new IntType()).execute(state);              // int x
    }

    // --- NewStmt (new) Tests ---

    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newWithUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new NewStmt("y", new ValueExp(new IntValue(20))).execute(state));
    }

    @Test
    void newWithNonReferenceVariableThrows() {
        assertThrows(StatementException.class,
                () -> new NewStmt("x", new ValueExp(new IntValue(20))).execute(state));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        assertThrows(StatementException.class,
                () -> new NewStmt("v", new ValueExp(new BoolValue(true))).execute(state));
    }

    // --- ReadHeapExp (rH) Tests ---

    @Test
    void rHReadsTheValue() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new IntValue(20), new ReadHeapExp(new VarExp("v")).eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void rHWithNonReferenceExpressionThrows() {
        assertThrows(ExpressionException.class,
                () -> new ReadHeapExp(new ValueExp(new IntValue(20))).eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void rHWithMissingAddressThrows() {
        // RefValue pointing to address 99, which is not in the heap
        var exp = new ReadHeapExp(new ValueExp(new RefValue(99, new IntType())));
        assertThrows(ADTException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap()));
    }

    // --- WriteHeapStmt (wH) Tests ---

    @Test
    void wHChangesTheHeapLocation() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    @Test
    void wHWithNonReferenceVariableThrows() {
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("x", new ValueExp(new IntValue(30))).execute(state));
    }

    @Test
    void wHWithAddressNotInHeapThrows() {
        // Address 0 is the null address; it is not in the heap
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state));
    }

    @Test
    void wHWithWrongTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(true))).execute(state));
    }
}