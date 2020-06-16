package sorters;

import entities.Balance;

import java.util.Comparator;

public class BalanceSorter implements Comparator<Balance> {
    private int sortingDirection;
    private String criteria;

    public BalanceSorter(int sortingDirection, String criteria) {
        this.sortingDirection = sortingDirection;
        this.criteria = criteria;
    }

    public int getSortingDirection() {
        return sortingDirection;
    }

    public void setSortingDirection(int sortingDirection) {
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
        int retval = 0;
        if(criteria.equalsIgnoreCase("amount"))
            retval = o1.getAmount() - o2.getAmount();
        else if(criteria.equalsIgnoreCase("date"))
            retval = o1.getDateTime().compareTo(o2.getDateTime());
        else if(criteria.equalsIgnoreCase("currency"))
            retval = o1.getCurrency().compareTo(o2.getCurrency());

        return retval * this.sortingDirection;
    }
}
