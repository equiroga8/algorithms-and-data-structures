/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Topological;

import java.util.HashMap;

public class WordNet {

    private HashMap<String, SET<Integer>> wordsToIds = new HashMap<>();
    private HashMap<Integer, SET<String>> idsToWords = new HashMap<>();
    private Digraph digraph = null;
    private int size = 0;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Argument cannot be null");
        parseSynsets(synsets);
        createEdges(hypernyms);
        Topological t = new Topological(digraph);
        if (!t.hasOrder())
            throw new IllegalArgumentException("Input is not rooted DAG");
        int roots = 0;
        for (int i = 0; i < size; i++) {
            if (digraph.outdegree(i) == 0) roots++;
        }
        if (roots != 1) throw new IllegalArgumentException("Graph can only have one root");

    }

    private void parseSynsets(String synsets) {
        In synsetInput = new In(synsets);
        while (synsetInput.hasNextLine()) {
            String line = synsetInput.readLine();
            size++;
            String[] csValues = line.split(",");
            String[] words = csValues[1].split(" ");
            SET<String> wordSet = new SET<>();
            for (String word : words) {
                wordSet.add(word);
                SET<Integer> idSet;
                if (wordsToIds.get(word) == null) {
                    idSet = new SET<>();
                }
                else {
                    idSet = wordsToIds.get(word);
                }
                idSet.add(Integer.parseInt(csValues[0]));
                wordsToIds.put(word, idSet);
            }
            idsToWords.put(Integer.parseInt(csValues[0]), wordSet);
        }
        synsetInput.close();
    }

    private void createEdges(String hypernyms) {
        digraph = new Digraph(size);
        In hypernymInput = new In(hypernyms);
        while (hypernymInput.hasNextLine()) {
            String line = hypernymInput.readLine();
            String[] csValues = line.split(",");
            for (int i = 1; i < csValues.length; i++) {
                digraph.addEdge(Integer.parseInt(csValues[0]), Integer.parseInt(csValues[i]));
            }
        }
        hypernymInput.close();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordsToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("Argument cannot be null");
        return wordsToIds.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (wordsToIds.get(nounA) == null || wordsToIds.get(nounB) == null)
            throw new IllegalArgumentException("Nouns dont exist");
        SAP sap = new SAP(digraph);
        Iterable<Integer> blueIds = wordsToIds.get(nounA);
        Iterable<Integer> redIds = wordsToIds.get(nounB);
        return sap.length(blueIds, redIds);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        SAP sap = new SAP(digraph);
        Iterable<Integer> blueIds = wordsToIds.get(nounA);
        Iterable<Integer> redIds = wordsToIds.get(nounB);
        int ancestorId = sap.ancestor(blueIds, redIds);
        SET<String> nouns = idsToWords.get(ancestorId);
        return nouns.toString().replace("{", "").replace("}", "").replaceAll(", ", " ");
    }

    public static void main(String[] args) {

        WordNet wn = new WordNet("synsets6.txt", "hypernyms6InvalidTwoRoots.txt");
        System.out.println(wn.digraph.toString());
        System.out.println(wn.distance("transition", "increase"));
        System.out.println(wn.sap("transition", "increase"));
    }
}
