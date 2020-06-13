package sorters;

import entities.Activity;
import entities.SortingDirection;

import java.util.Comparator;

public class ActivitySorter implements Comparator<Activity> {
    private SortingDirection sortingDirection;
    private String criteria;

    public ActivitySorter(SortingDirection sortingDirection, String criteria) {
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
    public int compare(Activity o1, Activity o2) {
        return 0;
    }
}
