package interpreter;

import interpreter.model.adt.MyDictionary;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

class Step3FileTableTest {
    @Test
    void testDictionaryWorksAsFileTable() throws Exception {
        var ft = new MyDictionary<StringValue, BufferedReader>();
        var f = new StringValue("test.txt");

        // Pass a dummy reader instead of null
        var dummyReader = new BufferedReader(new StringReader(""));
        ft.put(f, dummyReader);

        assertTrue(ft.isDefined(f));
        assertEquals(1, ft.toMap().size());

        ft.remove(f);
        assertFalse(ft.isDefined(f));
    }
}