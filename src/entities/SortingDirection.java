package entities;

public enum SortingDirection {
    ASCENDING(1),
    DESCENDING(-1);

    public final int value;
    private SortingDirection(int value){
        this.value = value;
    }
}
