package tob.service;

import tob.models.Order;
import tob.models.PriceUnit;

import java.util.*;

import static tob.Constants.MAX_PRICE;
import static tob.Constants.MIN_PRICE;
import static tob.enums.Action.SET;
import static tob.enums.Side.BUY;
import static tob.enums.Side.SELL;

public class TOB {
    private final TreeMap<Character, TreeMap<Integer, TreeMap<Long, PriceUnit>>> priceUnits = new TreeMap<>();


    public String getBestPrice(String input){
        Order order = new Order(input);
        String response = "-";
        if (SET.equals(order.getAction())) {
            response = handleSetOrder(order);
        }
        else {
            response = handleNotSetOrder(order);
        }
        return response;
    }

    private String handleSetOrder(Order order){
        Character side = order.getSide();
        Integer instrumentId = order.getInstrumentId();
        Long price = order.getPrice();
        TreeMap<Integer, TreeMap<Long, PriceUnit>> sideMap = priceUnits.get(side);
        TreeMap<Long, PriceUnit> priceMap = sideMap.get(instrumentId);
        if (priceMap == null){
            priceMap = new TreeMap<>();
            PriceUnit priceUnit = new PriceUnit(order);
            priceUnit.increaseAmount(order.getAmount());
            priceMap.put(price, priceUnit);
            sideMap.put(instrumentId, priceMap);
        }
        else{
            PriceUnit priceUnit = priceMap.computeIfAbsent(price, k -> new PriceUnit(order));
            priceUnit.increaseAmount(order.getAmount());
        }
        return getBestPriceBySide(side, instrumentId, priceMap, price);
    }

    private String handleNotSetOrder(Order order){
        Character side = order.getSide();
        Integer instrumentId = order.getInstrumentId();
        Long price = order.getPrice();
        TreeMap<Integer, TreeMap<Long, PriceUnit>> sideMap = priceUnits.get(side);
        TreeMap<Long, PriceUnit> priceMap = sideMap.get(instrumentId);
        PriceUnit currentPriceUnit = priceMap.get(price);
        TreeMap<String, Order> currentOrders = currentPriceUnit.getOrders();
        Order currentOrder = currentOrders.get(order.getKey());
        currentOrder.setAmountRest(currentOrder.getAmountRest() - order.getAmount());
        currentPriceUnit.decreaseAmount(order.getAmount());
        return getBestPriceBySide(side, instrumentId, priceMap, price);
    }

    public TOB(){
        initialisation();
    }

    private void initialisation(){
        priceUnits.put(BUY.getSide(), new TreeMap<>());
        priceUnits.put(SELL.getSide(), new TreeMap<>());
    }
    private static Long getLimitPrice(Character side){
        if (BUY.equals(side)){
            return MIN_PRICE;
        }
        else{
            return MAX_PRICE;
        }
    }

    private String getBestPriceBySide(Character side, Integer instrumentId, TreeMap<Long, PriceUnit> priceMap, Long price){
        Long bestPrice;
        if (BUY.equals(side)) {
            bestPrice = priceMap.lastKey();
            if (price < bestPrice){
                return "-";
            }
        }
        else{
            bestPrice = priceMap.firstKey();
            if (price > bestPrice){
                return "-";
            }
        }
        int amount = priceMap.get(price).getAmount();
        if (amount == 0) {
            priceMap.remove(price);
            if (priceMap.isEmpty()){
                bestPrice = getLimitPrice(side);
            }
            else {
                bestPrice = BUY.equals(side) ? priceMap.lastKey() : priceMap.firstKey();
                amount = priceMap.get(bestPrice).getAmount();
            }
        }
        return String.join(";", instrumentId.toString(), side.toString(), bestPrice.toString(), Integer.toString(amount));
    }
}
