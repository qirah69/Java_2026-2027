package interpreter;

import interpreter.view.Examples;
import interpreter.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        view.addEntry("1", "Ex1: int v; v=2; print(v)  → 2",   Examples.EX1);
        view.addEntry("2", "Ex2: int a,b; a=2+3*5; b=a+1; print(b)  → 18", Examples.EX2);
        view.addEntry("3", "Ex3: bool a; int v; a=true; if a then v=2 else v=3; print(v)  → 2", Examples.EX3);
        view.run();
    }
}