package sorters;

import entities.Balance;
import entities.SortingDirection;

import java.util.Comparator;

public class BalanceSorter implements Comparator<Balance> {
    private SortingDirection sortingDirection;
    private String criteria;

    public BalanceSorter(SortingDirection sortingDirection, String criteria) {
        this.sortingDirection = sortingDirection;
        this.criteria = criteria;
    }

    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    public void setSortingDirection(SortingDirection sortingDirection) {
        this.sortingDirection = sortingDirection;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public int compare(Balance o1, Balance o2) {
        return 0;
    }
}
