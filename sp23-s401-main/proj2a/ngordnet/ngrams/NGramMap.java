package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;
import java.util.Collection;
import java.util.TreeMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    private TreeMap<String, TimeSeries> nameAndYearsFreq;
    private TimeSeries yearsFreq2;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    //Works...Probably
    public NGramMap(String wordsFilename, String countsFilename) {
        //FOR WORDSFILENAME

        //Create a TreeMap, this will hold the year and a TimeSeries
        nameAndYearsFreq = new TreeMap<>();

        //Makes our two arguements into readable, iterable files
        In in = new In(wordsFilename);
        In in2 = new In(countsFilename);

        //This will run for everything in the Words File
        while (in.hasNextLine() && !in.isEmpty()) {
            //Create a new temp TimeSeries which will contain the year and freq
            TimeSeries yearsFreq = new TimeSeries();

            //Store the string, year, and freq in values
            String name = in.readString();
            int year = in.readInt();
            int freq = in.readInt();

            //This is a check if the name is already in the TreeMap
            if (nameAndYearsFreq.containsKey(name)) {
                //If it is, then get the TimeSeries
                TimeSeries tempTimeSeries = nameAndYearsFreq.get(name);

                //Set yearsFreq value to the currentFreq and the freq from the previous definition
                //So now this TimeSeries has two keys
                tempTimeSeries.put(year, (double) freq);

                //Set the name of it to the yearsFreq
                nameAndYearsFreq.put(name, tempTimeSeries);

                //Skip last value
                in.readInt();
            } else {
                //Set the TimeSeries values to the year and freq
                yearsFreq.put(year, (double) freq);

                //Place this in the TreeMap
                nameAndYearsFreq.put(name, yearsFreq);

                //Skip last value
                in.readInt();
            }
        }

        //FOR COUNTS FILE

        //This is everything that is for the Counts File
        //Creating a new TimeSeries that will contain the year and the number of words each year contains
        yearsFreq2 = new TimeSeries();

        //Runs for as long as there is another line
        while (in2.hasNextLine() && !in2.isEmpty()) {
            //Takes the first line out of in2
            String[] x = in2.readString().split(",");

            //Gets the year and freq
            //Note they come in as Strings, so we do parseInt to get them as int
            int year = Integer.parseInt(x[0]);
            Double freq = Double.parseDouble(x[1]);

            //Checks if the year is already in the TimeSeries
            if (yearsFreq2.containsKey(year)) {
                //Then put the year with the previous value plus the new value
                yearsFreq2.put(year, yearsFreq2.get(year) + (double) freq);
            } else {
                //If this runs then this is the first time the year exists
                yearsFreq2.put(year, (double) freq);
            }
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        //Creates a new TimeSeries
        TimeSeries temp = new TimeSeries();

        //If times out, then replace for loop with if containskey(word)
        if (nameAndYearsFreq.containsKey(word)) {
            //go through every key in the TimeSeries
            for (int j: nameAndYearsFreq.get(word).keySet()) {
                //Check if the year in the TimeSeries is between the given start and end years
                if (j >= startYear && j <= endYear) {
                    //If true then add the year and the frequency of the word
                    temp.put(j, nameAndYearsFreq.get(word).get(j));
                }
                //If it does not fit in the given years, then do nothing
                //Don't want it to add to the temp TimeSeries
            }
        }

        //Return the temp TimeSeries
        return temp;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        //Create temp TimeSeries
        TimeSeries temp = new TimeSeries();

        //Check if TreeMap contains the given word
        if (nameAndYearsFreq.containsKey(word)) {

            //This created TimeSeries is just to make code more readable
            //This gets the TimeSeries of the word
            TimeSeries bruh = nameAndYearsFreq.get(word);

            //We need to iterate through the TimeSeries for every word
            for (int i: bruh.keySet()) {
                temp.put(i, bruh.get(i));
            }
        }
        return temp;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        //Creates a temp TimeSeries
        TimeSeries temp = new TimeSeries();

        //Iterate though every year in the countsFile
        for (int i: yearsFreq2.keySet()) {

            //Put the year and the value into the temp TimeSeries
            temp.put(i, yearsFreq2.get(i));
        }
        return temp;

        //Essentially we just copied yearsFreq2 and returned that
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        //Creates a new TimeSeries
        TimeSeries temp = new TimeSeries();
        if (nameAndYearsFreq.containsKey(word)) {
            //go through every key in the TimeSeries
            for (int j: nameAndYearsFreq.get(word).keySet()) {
                //Check if the year in the TimeSeries is between the given start and end years
                if (j >= startYear && j <= endYear) {
                    //If true then add the year and the frequency of the word
                    temp.put(j, nameAndYearsFreq.get(word).get(j) / yearsFreq2.get(j));
                }
                //If it does not fit in the given years, then do nothing
                //Don't want it to add to the temp TimeSeries
            }
        }
        //Return the temp TimeSeries
        return temp;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        //Creates a new TimeSeries
        TimeSeries temp = new TimeSeries();
        for (String word: words) {
            temp = temp.plus(weightHistory(word, startYear, endYear));
        }
        return temp;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries temp = new TimeSeries();
        for (String word: words) {
            temp = temp.plus(weightHistory(word, MIN_YEAR, MAX_YEAR));
        }
        return temp;
    }
}
