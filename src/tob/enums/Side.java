package tob.enums;

public enum Side {
    BUY('B'),
    SELL('S');
    private final Character side;

    Side(Character side){
        this.side = side;
    }

    public Character getSide(){
        return this.side;
    }

    public boolean equals(Character side){
        return this.getSide().equals(side);
    }

}
