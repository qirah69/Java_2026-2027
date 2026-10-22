package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    private static PrgState newState() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    private static IExp value(IValue value) {
        return new ValueExp(value);
    }

    @Test
    void nopLeavesProgramStateUnchanged() throws Exception {
        PrgState state = newState();
        state.getSymTable().put("x", new IntValue(7));

        assertSame(state, new NopStmt().execute(state));
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        assertEquals(1, state.getExeStack().toList().size());
    }

    @Test
    void variableDeclarationStoresDefaultValue() throws Exception {
        PrgState state = newState();

        new VarDeclStmt("x", new IntType()).execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void variableDeclarationRejectsDuplicateName() {
        PrgState state = newState();
        state.getSymTable().put("x", new IntValue(1));

        StatementException exception = assertThrows(
                StatementException.class,
                () -> new VarDeclStmt("x", new IntType()).execute(state));

        assertEquals("variable 'x' is already declared", exception.getMessage());
    }

    @Test
    void assignmentUpdatesDeclaredVariable() throws Exception {
        PrgState state = newState();
        state.getSymTable().put("x", new IntValue(0));

        new AssignStmt("x", value(new IntValue(42))).execute(state);

        assertEquals(new IntValue(42), state.getSymTable().lookup("x"));
    }

    @Test
    void assignmentRejectsUndeclaredVariable() {
        PrgState state = newState();

        assertThrows(StatementException.class,
                () -> new AssignStmt("x", value(new IntValue(42))).execute(state));
    }

    @Test
    void assignmentRejectsMismatchedType() {
        PrgState state = newState();
        state.getSymTable().put("x", new IntValue(0));

        assertThrows(StatementException.class,
                () -> new AssignStmt("x", value(new BoolValue(true))).execute(state));
    }

    @Test
    void compositionPushesStatementsInExecutionOrder() throws Exception {
        PrgState state = newState();
        IStmt first = new NopStmt();
        IStmt second = new VarDeclStmt("x", new IntType());

        new CompStmt(first, second).execute(state);

        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
        assertInstanceOf(NopStmt.class, state.getExeStack().pop());
    }

    @Test
    void ifStatementPushesThenBranchWhenConditionIsTrue() throws Exception {
        PrgState state = newState();
        IStmt thenBranch = new NopStmt();
        IStmt elseBranch = new VarDeclStmt("x", new IntType());

        new IfStmt(value(new BoolValue(true)), thenBranch, elseBranch).execute(state);

        assertSame(thenBranch, state.getExeStack().pop());
    }

    @Test
    void ifStatementPushesElseBranchWhenConditionIsFalse() throws Exception {
        PrgState state = newState();
        IStmt thenBranch = new NopStmt();
        IStmt elseBranch = new VarDeclStmt("x", new IntType());

        new IfStmt(value(new BoolValue(false)), thenBranch, elseBranch).execute(state);

        assertSame(elseBranch, state.getExeStack().pop());
    }

    @Test
    void ifStatementRejectsNonBooleanCondition() {
        PrgState state = newState();
        IfStmt statement = new IfStmt(value(new IntValue(1)), new NopStmt(), new NopStmt());

        assertThrows(StatementException.class, () -> statement.execute(state));
    }

    @Test
    void printStatementAppendsEvaluatedValueToOutput() throws Exception {
        PrgState state = newState();

        new PrintStmt(value(new IntValue(9))).execute(state);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(9), state.getOut().get(0));
    }

    @Test
    void printStatementCanPrintVariableValue() throws Exception {
        PrgState state = newState();
        state.getSymTable().put("flag", new BoolValue(true));

        new PrintStmt(new VarExp("flag")).execute(state);

        assertEquals(new BoolValue(true), state.getOut().get(0));
    }
}
