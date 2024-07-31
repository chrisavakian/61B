package ngordnet.main;
import java.util.*;
import java.util.HashMap;
import java.util.TreeMap;
public class Graph {

    //HashMap bucket is the parent. LinkedList of bucket is all children
    private HashMap<Integer, Node> hashBrown;

    private HashMap<Integer, ArrayList<Integer>> hashIds;

    //Contains the String and ID relationship
    private TreeMap<String, ArrayList<Integer>> treeBrownString;

    public Graph() {
        //Initializes a new HashMap
        hashBrown = new HashMap<>();
        //Initializes a new HashMap
        hashIds = new HashMap<>();
        //Initializes a new TreeMap
        treeBrownString = new TreeMap<>();
    }

    private class Node {
        //Possibly should make this an Array Of Strings where each value is the Strings in that node
        String[] value;
        int id;
        //Could do an ArrayList of ints and store the ids
        //Use the hashmap to get the node back
        ArrayList<Node> adjList = new ArrayList<>();

        public Node(String[] v, int id) {
            //Initialize String array with the length of input array
            value = new String[v.length];
            this.id = id;

            //Doing ArrayCopy bc of the Golden Rule of Equals
            //Want to seperate input with stored value
            //Is ArrayCopy ok, or should I do a for loop?
            System.arraycopy(v, 0, value, 0, v.length);
        }
    }

    public void createNode(int id, String name) {
        //Adds new id string definition in the TreeMap
        //treeBrown.put(id, name);

        String[] arrName = name.split(" ");

        //Creates new Node with empty adjList
        Node tempNode = new Node(arrName, id);

        for (String i: arrName) {
            if (treeBrownString.get(i) == null) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(id);
                treeBrownString.put(i, temp);
            } else {
                treeBrownString.get(i).add(id);
            }
        }

        //Adds node to HashMap, meaning first node in everyHashMap is the parent Node
        hashBrown.put(id, tempNode);
    }

    //Note String[] children returns a string arr of ints
    public void addEdge(int parent, String[] children) {
        //Parent Node
        Node parentNode = hashBrown.get(parent);

        //Possible inefficiency with creating Nodes
        //Refer to comments in the Node class for more detail

        //Iterates over the children
        for (String i: children) {
            //Adds tempNode as a child of the parentNode
            parentNode.adjList.add(hashBrown.get(Integer.parseInt(i)));

            //Hopefully this should basically be able to recreate the hyponyms
            if (hashIds.containsKey(parent)) {
                hashIds.get(parent).add(Integer.parseInt(i));
            } else {
                ArrayList<Integer> bucket = new ArrayList<>();
                bucket.add(Integer.parseInt(i));
                hashIds.put(parent, bucket);
            }
        }
    }

    public void depthSearch(int id, ArrayList<String> hyponyms) {
        Node parentNode = hashBrown.get(id);

        //Iterate over the String Arr of Names
        //Point of this for loop is to retrieve all the Strings with the same id
        //This means they are in the same node

        for (String s: parentNode.value) {
            hyponyms.add(s);
        }

        ArrayList<Integer> nextIdzzzz = hashIds.get(id);

        if (nextIdzzzz == null) {
            return;
        }

        for (int z: nextIdzzzz) {
            depthSearch(z, hyponyms);
        }
    }

    //Don't forget that a word is a hyponym of itself
    public ArrayList<String> findAllHyponyms(String word) {
        //Create hyponym ArrayList that will contain all hyponyms of the word
        ArrayList<String> hyponyms = new ArrayList<>();

        ArrayList<Integer> id = new ArrayList<>();

        if (treeBrownString.get(word) != null) {
            id = treeBrownString.get(word);
        } else {
            ArrayList<String> temp = new ArrayList<>();
            return temp;
        }

        for (int ids: id) {
            depthSearch(ids, hyponyms);
        }

        //Returns the arrayList with all Hyponyms
        return hyponyms;
    }
}
