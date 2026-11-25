package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void whileWithFalseConditionRunsZeroTimes() throws MyException {
        var loop = new WhileStmt(new ValueExp(new BoolValue(false)), new PrintStmt(new ValueExp(new IntValue(1))));
        loop.execute(state);

        // Since condition is false, nothing is pushed to the stack.
        // The initial "NopStmt" is still the only thing on it.
        assertEquals(1, state.getExeStack().toList().size());
        assertEquals(new NopStmt(), state.getExeStack().peek());
    }

    @Test
    void whileWithTrueConditionPushesItselfAndTheBody() throws MyException {
        var loop = new WhileStmt(new ValueExp(new BoolValue(true)), new PrintStmt(new ValueExp(new IntValue(1))));
        loop.execute(state);

        // Stack should have: NopStmt (bottom), WhileStmt, PrintStmt (top)
        assertEquals(3, state.getExeStack().toList().size());
        assertEquals(new PrintStmt(new ValueExp(new IntValue(1))), state.getExeStack().peek());
    }

    @Test
    void whileWithNonBooleanConditionThrows() {
        var loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        assertThrows(StatementException.class, () -> loop.execute(state));
    }
}