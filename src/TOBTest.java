import org.junit.Test;
import tob.service.TOB;

import static org.junit.Assert.*;

public class TOBTest {

    @Test
    public void getBestPriceBuy() {
        TOB tob = new TOB();
        String result = tob.getBestPrice("A;1;0;55;B;100;2;2");
        assertEquals("55;B;100;2", result);
        result = tob.getBestPrice("A;2;0;55;B;101;3;3");
        assertEquals("55;B;101;3", result);
        result = tob.getBestPrice("B;1;0;55;B;99;10;10");
        assertEquals("-", result);
        result = tob.getBestPrice("A;2;1;55;B;101;3;0");
        assertEquals("55;B;100;2", result);
        result = tob.getBestPrice("B;2;0;55;B;100;3;3");
        assertEquals("55;B;100;5", result);
        result = tob.getBestPrice("A;1;2;55;B;100;1;1");
        assertEquals("55;B;100;4", result);
    }

    @Test
    public void getBestPriceSeveralInstruments() {
        TOB tob = new TOB();
        String result = tob.getBestPrice("A;1;0;55;B;100;2;2");
        assertEquals("55;B;100;2", result);
        result = tob.getBestPrice("C;1;0;56;B;105;3;3");
        assertEquals("56;B;105;3", result);
        result = tob.getBestPrice("A;2;0;55;B;101;3;3");
        assertEquals("55;B;101;3", result);
        result = tob.getBestPrice("D;1;0;56;B;108;5;5");
        assertEquals("56;B;108;5", result);
    }

    @Test
    public void getBestPriceSell() {
        TOB tob = new TOB();
        String result = tob.getBestPrice("A;1;0;55;S;100;2;2");
        assertEquals("55;S;100;2", result);
        result = tob.getBestPrice("B;1;0;55;S;99;10;10");
        assertEquals("55;S;99;10", result);
        result = tob.getBestPrice("A;2;0;55;S;101;3;3");
        assertEquals("-", result);
    }

    @Test
    public void getBestPriceLimitBuy() {
        TOB tob = new TOB();
        String result = tob.getBestPrice("A;2;0;55;B;101;3;3");
        assertEquals("55;B;101;3", result);
        result = tob.getBestPrice("A;2;1;55;B;101;3;0");
        assertEquals("55;B;0;0", result);
        result = tob.getBestPrice("A;3;0;55;B;101;4;4");
        assertEquals("55;B;101;4", result);
    }

    @Test
    public void getBestPriceLimitSell() {
        TOB tob = new TOB();
        String result = tob.getBestPrice("A;2;0;55;S;101;3;3");
        assertEquals("55;S;101;3", result);
        result = tob.getBestPrice("A;2;1;55;S;101;3;0");
        assertEquals("55;S;999999999999999999;0", result);
        result = tob.getBestPrice("A;3;0;55;S;101;4;4");
        assertEquals("55;S;101;4", result);
    }
}