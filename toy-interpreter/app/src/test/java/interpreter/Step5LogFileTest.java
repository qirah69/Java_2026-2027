package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Step5LogFileTest {
    @TempDir Path dir;

    @Test
    void testLogFileCreationAndFormat() throws Exception {
        PrgState state = PrgState.of(new NopStmt());
        Path logPath = dir.resolve("test-log.txt");

        Repository repo = new Repository(state, logPath.toString());
        Controller ctrl = new Controller(repo, false);

        ctrl.allStep();

        assertTrue(Files.exists(logPath));
        String logContent = Files.readString(logPath);

        // Check the required order of sections
        int a = logContent.indexOf("ExeStack:");
        int b = logContent.indexOf("SymTable:");
        int c = logContent.indexOf("Out:");
        int d = logContent.indexOf("FileTable:");

        assertTrue(a >= 0 && a < b && b < c && c < d);
    }
}