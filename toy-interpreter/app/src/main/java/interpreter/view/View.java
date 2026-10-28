package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class View {

    private record MenuEntry(String description, IStmt program) {}

    private final Map<String, MenuEntry> entries = new LinkedHashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void addEntry(String key, String description, IStmt program) {
        entries.put(key, new MenuEntry(description, program));
    }

    private boolean handleOne() {
        System.out.println();
        System.out.println("  Toy Language Interpreter");
        System.out.println("  " + "=".repeat(40));
        entries.forEach((key, entry) ->
                System.out.println("  " + key + ")  " + entry.description()));
        System.out.println("  0)  Exit");
        System.out.println("  " + "-".repeat(40));
        System.out.print("  > ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("0")) return false;

        if (!entries.containsKey(choice)) {
            System.out.println("  Unknown option '" + choice + "'. Try again.");
            return true;
        }

        MenuEntry entry = entries.get(choice);
        System.out.println();
        System.out.println("  Running: " + entry.description());
        System.out.println("  " + "=".repeat(40));

        try {
            PrgState state = new PrgState(
                    new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                    entry.program());
            Controller ctrl = new Controller(new Repository(state), true);
            ctrl.allStep();
            System.out.println();
            System.out.println("  " + "=".repeat(40));
            System.out.println("  Final output: " + state.getOut());
            System.out.println("  " + "=".repeat(40));
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }

        return true;
    }

    public void run() {
        //noinspection StatementWithEmptyBody
        while (handleOne()) { /* keep looping */ }
        System.out.println("Goodbye.");
    }
}
