package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;

public record RelExp(RelOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue l = left.eval(symTable);
        IValue r = right.eval(symTable);

        if (l instanceof IntValue(int a) && r instanceof IntValue(int b)) {
            return new BoolValue(switch (op) {
                case LT -> a < b;
                case LE -> a <= b;
                case EQ -> a == b;
                case NE -> a != b;
                case GT -> a > b;
                case GE -> a >= b;
            });
        }

        if (l instanceof StringValue(String a) && r instanceof StringValue(String b)) {
            if (op == RelOp.EQ) return new BoolValue(a.equals(b));
            if (op == RelOp.NE) return new BoolValue(!a.equals(b));
            throw new ExpressionException("Only == and != are supported for strings");
        }

        throw new ExpressionException("Operands must be both integers or both strings");
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}