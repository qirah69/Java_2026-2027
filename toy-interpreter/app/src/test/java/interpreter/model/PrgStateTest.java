package interpreter.model;

import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {
    @Test
    void constructorStoresComponentsAndPushesInitialProgram() throws Exception {
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();
        IStmt program = new NopStmt();

        PrgState state = new PrgState(exeStack, symTable, out, new MyDictionary<>(), program);

        assertSame(exeStack, state.getExeStack());
        assertSame(symTable, state.getSymTable());
        assertSame(out, state.getOut());
        assertSame(program, state.getOriginalProgram());
        assertSame(program, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void settersReplaceStateComponents() {
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();
        IStmt program = new NopStmt();
        PrgState state = new PrgState(exeStack, symTable, out, new MyDictionary<>(), program);

        state.setExeStack(exeStack);
        state.setSymTable(symTable);
        state.setOut(out);

        assertSame(exeStack, state.getExeStack());
        assertSame(symTable, state.getSymTable());
        assertSame(out, state.getOut());
    }

    @Test
    void toStringIncludesStackSymbolTableAndOutput() {
        PrgState state = PrgState.of(new NopStmt());
        state.getSymTable().put("x", new IntValue(5));
        state.getOut().add(new IntValue(5));

        String text = state.toString();
        assertTrue(text.contains("ExeStack:"));
        assertTrue(text.contains("SymTable:"));
        assertTrue(text.contains("x --> 5"));
        assertTrue(text.contains("Out:"));
        assertTrue(text.contains("5"));
        }
}
