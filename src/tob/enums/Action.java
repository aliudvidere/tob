package tob.enums;

public enum Action {
    SET('0'),
    WITHDRAW('1'),
    COMBINE('2');

    private final Character action;

    Action(Character action){
        this.action = action;
    }

    public boolean equals(Character action){
        return this.action.equals(action);
    }

}
