package ngordnet.main;

import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;

public class PlotDemo {
    public static void main(String[] args) {
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        
        NGramMap ngm = new NGramMap(wordFile, countFile);
        ArrayList<String> words = new ArrayList<>();
        words.add("cat");
        words.add("dog");

        ArrayList<TimeSeries> lts = new ArrayList<>();
        for (String word : words) {
            final int startYear = 1900;
            final int endYear = 1950;
            lts.add(ngm.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String s = Plotter.encodeChartAsString(chart);
        System.out.println(s);

        // you can also do this to display locally:
        // Plotter.displayChart(chart);

    }
}
