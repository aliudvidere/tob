package tob.models;

import java.util.ArrayList;
import java.util.List;

public class PriceUnit {
    private final Integer instrumentId;
    private final Character side;
    private final Long price;
    private final List<Order> orders = new ArrayList<>();

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public Character getSide() {
        return side;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getAmount() {
        return this.orders.stream().mapToInt(Order::getAmountRest).sum();
    }

    public PriceUnit(Order order){
        this.instrumentId = order.getInstrumentId();
        this.side = order.getSide();
        this.price = order.getPrice();
        this.orders.add(order);
    }

    public PriceUnit (Order order, Long price){
        this.instrumentId = order.getInstrumentId();
        this.side = order.getSide();
        this.price = price;
    }

    public String getKey(){
        return String.join(";", this.getInstrumentId().toString(), this.getSide().toString(), this.getPrice().toString());
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

    @Override
    public String toString(){
        return String.join(";", this.getInstrumentId().toString(), this.getSide().toString(), this.getPrice().toString(), this.getAmount().toString());
    }
}
