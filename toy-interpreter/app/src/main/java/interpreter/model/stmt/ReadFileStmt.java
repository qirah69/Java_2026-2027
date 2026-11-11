package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import interpreter.model.type.IntType;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();

        // 1. Check if variable is declared and is an integer
        if (!symTable.isDefined(varName) || !(symTable.lookup(varName).getType() instanceof IntType)) {
            throw new StatementException("readFile: var " + varName + " not declared or not int");
        }

        // 2. Check if the file name expression evaluates to a string
        IValue val = exp.eval(symTable);
        if (!(val instanceof StringValue fileName)) {
            throw new StatementException("readFile: " + exp + " is not a string");
        }

        // 3. Check if the file is in the FileTable
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName)) {
            throw new StatementException("readFile: " + fileName + " is not open");
        }

        // 4. Read the line
        try {
            var reader = fileTable.lookup(fileName);
            String line = reader.readLine();
            int n = (line == null) ? 0 : Integer.parseInt(line.strip());
            symTable.update(varName, new IntValue(n));
        } catch (IOException | NumberFormatException e) {
            throw new StatementException("readFile: error reading " + fileName + ": " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}