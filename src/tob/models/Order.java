package tob.models;

public class Order {
    private final String userId;
    private final String clorderId;
    private Character action;
    private final Integer instrumentId;
    private final Character side;
    private final Long price;
    private final Integer amount;
    private Integer amountRest;

    public Character getAction() {
        return action;
    }

    public void setAction(Character action){
        this.action = action;
    }

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
        return amount;
    }

    public Integer getAmountRest() {
        return amountRest;
    }

    public void setAmountRest(Integer amountRest) {
        this.amountRest = amountRest;
    }

    public Order(String orderString){
        String[] parameters = orderString.split(";");
        this.userId = parameters[0];
        this.clorderId = parameters[1];
        this.action = parameters[2].charAt(0);
        this.instrumentId = Integer.parseInt(parameters[3]);
        this.side = parameters[4].charAt(0);
        this.price = Long.parseLong(parameters[5]);
        this.amount = Integer.parseInt(parameters[6]);
        this.amountRest = Integer.parseInt(parameters[7]);
    }


    public String getKey(){
        return String.join(";", this.userId, this.clorderId);
    }
}
