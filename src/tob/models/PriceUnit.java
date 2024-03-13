package tob.models;

import java.util.Collections;
import java.util.TreeMap;

public class PriceUnit {
    private TreeMap<String, Order> orders;
    private Integer amount;

    public TreeMap<String, Order> getOrders() {
        return orders;
    }

    public Integer getAmount() {
        return amount;
    }

    public PriceUnit(Order order){
        this.orders = new TreeMap<>(Collections.singletonMap(order.getKey(), order));
        this.amount = 0;
    }

    public void increaseAmount(Integer amount){
        this.amount += amount;
    }

    public void decreaseAmount(Integer amount){
        this.amount -= amount;
    }
}
