package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;


public class Controller {
    private final IRepository repo;
    private final boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if (stack.isEmpty())
            throw new StatementException("program state stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();

        repo.logPrgStateExec(); // <-- Log initial state
        int step = 0;
        if (displayFlag) display(prg, step);

        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            step++;

            repo.logPrgStateExec(); // <-- Log after each step
            if (displayFlag) display(prg, step);
        }
    }

    private void display(PrgState state, int step) {
        System.out.println();
        System.out.println("  Step " + step);
        System.out.println("  " + "-".repeat(40));
        System.out.println(state);
    }
}