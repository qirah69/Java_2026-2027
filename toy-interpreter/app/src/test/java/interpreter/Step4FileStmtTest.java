package interpreter;

import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Step4FileStmtTest {
    @TempDir Path dir;

    @Test
    void testFileStatements() throws Exception {
        // 1. Create a temporary file with a number in it
        Path file = dir.resolve("test.txt");
        Files.writeString(file, "42\n");

        // 2. Set up the state and declare the 'v' variable
        PrgState state = PrgState.of(new NopStmt());
        state.getSymTable().put("v", new IntValue(0));

        var fileExp = new ValueExp(new StringValue(file.toString()));

        // 3. Test Open
        new OpenRFileStmt(fileExp).execute(state);
        assertTrue(state.getFileTable().isDefined(new StringValue(file.toString())));

        // 4. Test Read (should read 42)
        new ReadFileStmt(fileExp, "v").execute(state);
        assertEquals(new IntValue(42), state.getSymTable().lookup("v"));

        // 5. Test Read at EOF (should give 0)
        new ReadFileStmt(fileExp, "v").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));

        // 6. Test Close
        new CloseRFileStmt(fileExp).execute(state);
        assertFalse(state.getFileTable().isDefined(new StringValue(file.toString())));
    }
}