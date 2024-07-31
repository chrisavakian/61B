package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern = "";

    //Constructor is probably correct
    public RandomChooser(int wordLength, String dictionaryFile) {

        //Throws exception is wordlength is less than 1
        if(wordLength < 1)
        {
            throw new IllegalArgumentException("Wordlength is less than one");
        }

        FileUtils test = new FileUtils();
        List<String> dictionaryFileList = new ArrayList<>();
        dictionaryFileList = test.readWords(dictionaryFile);

        //Iterate through dictionaryFileList to see which words are of the correct length
        // those words will go into wordsWithCorrectLength
        //Problem with this list, it is empty every time
        List<String> wordsWithCorrectLength = new ArrayList<>();


        System.out.println();
        //Iterate through dictionaryFileList
        for(String i: dictionaryFileList)
        {
            if (i.length() != wordLength)
            {
                //Do nothing, we don't want this inside of wordsWithCorrectLength
            }
            else
            {
                wordsWithCorrectLength.add(i);
            }
        }

        //if wordsWithCorrectLength is empty after running through the for loop,
        // then we need to throw another exception
        if(wordsWithCorrectLength.isEmpty())
        {
            throw new IllegalStateException("No word that has the correct length");
        }

        //need to instantiate pattern with a bunch of empty dashes, since every game starts out with just dashes
        for(int j = 0; j < wordLength; j++)
        {
            pattern = pattern + "-";
        }

        int numWords = wordsWithCorrectLength.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = wordsWithCorrectLength.get(randomlyChosenWordNumber);
    }

    //Untested makeGuess method
    @Override
    public int makeGuess(char letter) {

        int numberOfOccurrences  = 0;

        for(int i = 0; i < chosenWord.length(); i++)
        {
            if (chosenWord.charAt(i) == letter)
            {
                numberOfOccurrences++;
                pattern = pattern.substring(0, i) + letter + pattern.substring(i + 1);
            }
        }
        return numberOfOccurrences;zzzzzzzzzzzz
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
