package tob.service;

import tob.models.Order;
import tob.models.PriceUnit;

import java.util.Comparator;
import java.util.TreeMap;

import static tob.Constants.*;
import static tob.enums.Action.SET;
import static tob.enums.Side.BUY;
import static tob.enums.Side.SELL;

public class TOB {
    private final TreeMap<String, Order> orders = new TreeMap<>();

    private final TreeMap<String, PriceUnit> priceUnits = new TreeMap<>();

    private final TreeMap<String, String> bestPrices = new TreeMap<>();

    public String getBestPrice(String input){
        Order order = new Order(input);
        String response = "-";
        if (SET.equals(order.getAction())) {
            orders.put(order.getKey(), order);
            if (!priceUnits.containsKey(order.getPriceKey())){
                PriceUnit priceUnit = new PriceUnit(order);
                priceUnits.put(priceUnit.getKey(), priceUnit);
                if (!bestPrices.containsKey(order.getBestPriceKey())){
                    bestPrices.put(order.getBestPriceKey(), priceUnit.getKey());
                    response = priceUnit.toString();
                }
                else {
                    Long bestPrice = priceUnits.get(bestPrices.get(order.getBestPriceKey())).getPrice();
                    if (priceUnit.getSide().equals(BUY.getSide()) && priceUnit.getPrice() > bestPrice
                            || priceUnit.getSide().equals(SELL.getSide()) && priceUnit.getPrice() < bestPrice) {
                        bestPrices.put(order.getBestPriceKey(), priceUnit.getKey());
                        response = priceUnit.toString();
                    }
                }
            }
            else {
                PriceUnit priceUnit = priceUnits.get(order.getPriceKey());
                priceUnit.addOrder(order);
                response = priceUnit.toString();
            }
        }
        else {
            Order currentOrder = orders.get(order.getKey());
            currentOrder.setAmountRest(currentOrder.getAmountRest() - order.getAmount());
            currentOrder.setAction(order.getAction());
            if (priceUnits.get(currentOrder.getPriceKey()).getKey().equals(bestPrices.get(currentOrder.getBestPriceKey()))) {
                if (priceUnits.get(currentOrder.getPriceKey()).getAmount() == 0) {
                    PriceUnit newBestPriceUnit;
                    if (BUY.equals(currentOrder.getSide())) {
                        newBestPriceUnit = priceUnits.values().stream().filter(t -> t.getInstrumentId().equals(order.getInstrumentId())).filter(t -> t.getAmount() > 0).max(Comparator.comparing(PriceUnit::getPrice)).orElse(null);
                    } else {
                        newBestPriceUnit = priceUnits.values().stream().filter(t -> t.getInstrumentId().equals(order.getInstrumentId())).filter(t -> t.getAmount() > 0).min(Comparator.comparing(PriceUnit::getPrice)).orElse(null);
                    }
                    if (newBestPriceUnit != null) {
                        bestPrices.put(currentOrder.getBestPriceKey(), newBestPriceUnit.getKey());
                    } else {
                        PriceUnit limitPriceUnit = priceUnits.get(String.join(";", currentOrder.getBestPriceKey(), getLimitPrice(currentOrder).toString()));
                        if (limitPriceUnit == null) {
                            limitPriceUnit = new PriceUnit(currentOrder, getLimitPrice(currentOrder));
                        }
                        priceUnits.put(limitPriceUnit.getKey(), limitPriceUnit);
                        bestPrices.put(currentOrder.getBestPriceKey(), limitPriceUnit.getKey());
                    }
                }
                response = priceUnits.get(bestPrices.get(currentOrder.getBestPriceKey())).toString();
            }
        }
        return response;
    }

    private static Long getLimitPrice(Order order){
        if (BUY.equals(order.getSide())){
            return MIN_PRICE;
        }
        else{
            return MAX_PRICE;
        }
    }
}
