package ngordnet.main;
import edu.princeton.cs.algs4.In;
import java.util.*;

public class WordNet {

    //private Graph object
    private Graph graph;

    public WordNet(String hyponym, String synset) {
        //Initalizes new graph object
        graph = new Graph();

        //In object for synset, which we will read
        In in = new In(synset);
        //Same as line 15, but for hyponym
        In in2 = new In(hyponym);

        //While sysnset.txt has a nextLine and is not empty
        while (in.hasNextLine() && !in.isEmpty()) {
            //Creates a string of Arrays which will read the nextLine and splits between commas
            String[] arr = in.readLine().split(",");

            //From the structure of synset, the 0 index of the array is the id
            //And the 1 index contains the string the id is associated with
            String id = arr[0];
            String name = arr[1];

            //We create a new node of the graph that contains the id and the name
            //Noting id is a String and needs to be parsed as an Int
            graph.createNode(Integer.parseInt(id), name);
        }

        //While hyponym.txt has a nextLine and is not Empty
        while (in2.hasNextLine() && !in2.isEmpty()) {
            //Create a String array that reads the nextline and splits based on commas
            String[] arr = in2.readLine().split(",");

            //The 0 index of the array will always be the parent
            String parent = arr[0];

            //Every index after that is the children of the array
            String[] children = Arrays.copyOfRange(arr, 1, arr.length);

            //We add an edge to the graph that is the parent and the children
            //Noting the parent is a string and needs to be parsed as an int
            graph.addEdge(Integer.parseInt(parent), children);
        }

    }

    //This function will return an ArrayList of Strings
    //ArrayList contains the Strings of every child of the word
    //Inclusive of the passed in argument
    public ArrayList<String> allHyponyms(String word) {
        return graph.findAllHyponyms(word);
    }
}
