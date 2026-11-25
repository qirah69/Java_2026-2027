package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record ReadHeapExp(IExp exp) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        IValue val = exp.eval(symTable, heap);

        if (!(val instanceof RefValue ref))
            throw new ExpressionException("rH: " + exp + " is not a reference");

        // Per the PDF, a missing address inside MyHeap throws ADTException automatically
        return heap.get(ref.address());
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}