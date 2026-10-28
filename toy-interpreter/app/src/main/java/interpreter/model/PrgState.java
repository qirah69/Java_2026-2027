package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

import java.util.Map;
import java.util.Objects;
public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack,
                    IDictionary<String, IValue> symTable,
                    IList<IValue> out,
                    IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        exeStack.push(program);
    }

    // --- Getters ---

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOut(IList<IValue> out) {
        this.out = out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // --- Execution Stack (top first) ---
        sb.append("  ExeStack:\n");
        var stmts = exeStack.toList();
        if (stmts.isEmpty()) {
            sb.append("    (empty)\n");
        } else {
            for (int i = 0; i < stmts.size(); i++) {
                sb.append(i == 0 ? "    --> " : "        ");
                sb.append(stmts.get(i));
                sb.append("\n");
            }
        }

        // --- Symbol Table ---
        sb.append("  SymTable:\n");
        var vars = symTable.entries();
        if (vars.isEmpty()) {
            sb.append("    (empty)\n");
        } else {
            vars.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> sb.append("    ")
                            .append(e.getKey())
                            .append(" = ")
                            .append(e.getValue())
                            .append("\n"));
        }

        // --- Output ---
        sb.append("  Output:\n");
        if (out.size() == 0) {
            sb.append("    (empty)\n");
        } else {
            for (int i = 0; i < out.size(); i++) {
                try {
                    sb.append("    [").append(i).append("] ").append(out.get(i)).append("\n");
                } catch (Exception ignored) {}
            }
        }

        return sb.toString();
    }
}