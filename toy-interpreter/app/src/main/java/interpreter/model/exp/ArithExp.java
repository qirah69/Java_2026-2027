package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record ArithExp(ArithOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue leftVal = left.eval(symTable);
        IValue rightVal = right.eval(symTable);

        if (!(leftVal instanceof IntValue(int n1)))
            throw new ExpressionException("first operand of '" + op + "' is not an integer");
        if (!(rightVal instanceof IntValue(int n2)))
            throw new ExpressionException("second operand of '" + op + "' is not an integer");

        return switch (op) {
            case ADD -> new IntValue(n1 + n2);
            case SUB -> new IntValue(n1 - n2);
            case MUL -> new IntValue(n1 * n2);
            case DIV -> {
                if (n2 == 0) throw new ExpressionException("division by zero");
                yield new IntValue(n1 / n2);
            }
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
