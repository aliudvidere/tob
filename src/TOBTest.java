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

    @Test
    public void shouldCopeWith1mlnOrders(){
        TOB tob = new TOB();
        for (int i = 0; i < 1_000_000; i++){
            tob.getBestPrice("user;ord%d;0;123;S;101;3;3".formatted(i));
        }
        var result = tob.getBestPrice("user;ord0;1;123;S;101;3;0");
        assertEquals("101", result.split(";")[2]);
    }

    @Test
    public void shouldCopeWith1mlnOrders1(){
        TOB tob = new TOB();
        for (int i = 0; i < 1_000_000; i++){
            tob.getBestPrice("user;ord%d;0;123;B;%d;3;3".formatted(i, 100 + i));
        }
        var result = tob.getBestPrice("user;ord0;1;123;B;100;3;0");
        assertEquals("-", result);
    }

    @Test
    public void shouldCopeWith10mlnOrders(){
        TOB tob = new TOB();
        for (int i = 0; i < 10_000_000; i++){
            tob.getBestPrice("user;ord%d;0;123;S;101;3;3".formatted(i));
        }
        var result = tob.getBestPrice("user;ord0;1;123;S;101;3;0");
        assertEquals("101", result.split(";")[2]);
    }

    @Test
    public void shouldCopeWith10mlnOrders1(){
        TOB tob = new TOB();
        for (int i = 0; i < 10_000_000; i++){
            tob.getBestPrice("user;ord%d;0;123;B;%d;3;3".formatted(i, 100 + i));
        }
        var result = tob.getBestPrice("user;ord0;1;123;B;100;3;0");
        assertEquals("-", result);
    }
}