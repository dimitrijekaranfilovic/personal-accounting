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
        int retval = 0;

        switch (this.criteria)
        {
            case "Description":
                retval = o1.getDescription().compareTo(o2.getDescription());
                break;
            case "Amount":
                retval = o1.getAmount() - o2.getAmount();
                break;
            case "Currency":
                retval = o1.getCurrency().compareTo(o2.getCurrency());
                break;
            case "Date":
                retval = o1.getTime().compareTo(o2.getTime());
                break;
            case "Version":
                retval = o1.getActivityVersion().compareTo(o2.getActivityVersion());
                break;
            default:
                break;
        }

        if(this.sortingDirection == SortingDirection.ASCENDING)
            return retval;
        return retval * -1;

    }
}
