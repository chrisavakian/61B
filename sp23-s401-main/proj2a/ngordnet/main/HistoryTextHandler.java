package ngordnet.main;
import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import java.util.List;
import ngordnet.ngrams.NGramMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder temp = new StringBuilder();

        for (String i: words) {
            temp.append(i);
            temp.append(": ");
            temp.append(map.weightHistory(i, startYear, endYear));
            temp.append("\n");
        }

        return temp.toString();
    }
}
