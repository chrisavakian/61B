package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wn;
    private NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        //Collect the relevant values from given q
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        int k = q.k();

        //Create arrayList. Stores the TreeSets of each input word
        ArrayList<TreeSet<String>> individualOutput = new ArrayList<>();

        //Iterates over the user-inputted words
        for (String i: words) {
            //Create treeSet which will cover alphabetizing and duplicates
            TreeSet<String> sortedWords = new TreeSet<>();

            //Create ArrayList and store all hyponyms in it
            ArrayList<String> arr = wn.allHyponyms(i);

            //Iterate over all the Hyponyms
            for (String j: arr) {
                //Add every string to the TreeSet
                //Will sort and handle duplicates
                sortedWords.add(j);
            }

            //Add this TreeSet to the ArrayList
            individualOutput.add(sortedWords);
        }

        //This will keep all hyponyms all TreeSets have in Common
        TreeSet<String> intersection = intersection(individualOutput);

        if (k == 0) {
            return intersection.toString();
        } else {
            return kGreaterThanZero(k, startYear, endYear, intersection).toString();
        }
    }

    private TreeSet<String> intersection(ArrayList<TreeSet<String>> input) {
        TreeSet<String> intersection = input.get(0);
        for (TreeSet<String> i: input) {
            intersection.retainAll(i);
        }
        return intersection;
    }

    private TreeSet<String> kGreaterThanZero(int k, int startYear, int endYear, TreeSet<String> intersection) {
        TreeMap<Double, String> returnValue = new TreeMap<>();

        double totalCountForString = 0.0;
        for (String str: intersection) {
            TimeSeries countValues = ngm.countHistory(str, startYear, endYear);
            for (int year: countValues.keySet()) {
                totalCountForString = totalCountForString + countValues.get(year);
            }

            if (totalCountForString != 0.0) {
                returnValue.put(totalCountForString, str);
            }
            totalCountForString = 0.0;
        }

        return kGreaterThanZeroSorter(returnValue, k);
    }

    private TreeSet<String> kGreaterThanZeroSorter(TreeMap<Double, String> tMap, int k) {
        TreeSet<Double> tSet = new TreeSet<>(Collections.reverseOrder());
        TreeSet<String> returnValue = new TreeSet<>();

        for (double i: tMap.keySet()) {
            tSet.add((i));
        }

        if (k > tMap.size()) {
            k = tMap.size();
        }

        for (int j = 0; j < k; j++) {
            returnValue.add(tMap.get(tSet.pollFirst()));
        }

        return returnValue;
    }

}
