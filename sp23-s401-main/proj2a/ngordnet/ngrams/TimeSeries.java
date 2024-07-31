package ngordnet.ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int i: ts.keySet()) {
            if (i > startYear && i < endYear) {
                put(i, ts.get(i));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> temp = new ArrayList<>();

        for (int i: keySet()) {
            temp.add(i);
        }

        return temp;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> temp = new ArrayList<>();

        for (int i: keySet()) {
            temp.add(get(i));
        }

        return temp;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries temp = new TimeSeries();

        for (int i: this.keySet()) {
            if (ts.containsKey(i)) {
                temp.put(i, this.get(i) + ts.get(i));
            } else {
                temp.put(i, this.get(i));
            }
        }

        for (int j: ts.keySet()) {
            if (this.containsKey(j)) {
                temp.put(j, this.get(j) + ts.get(j));
            } else {
                temp.put(j, ts.get(j));
            }
        }
        return temp;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    //Add if statement tests
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries temp = new TimeSeries();

        for (int i: keySet()) {
            if (!ts.containsKey(i)) {
                throw new IllegalArgumentException("No");
            }
            temp.put(i, this.get(i) / ts.get(i));
        }

        return temp;
    }
}
