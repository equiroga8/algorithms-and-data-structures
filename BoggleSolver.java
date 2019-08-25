/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;

import java.util.HashMap;
import java.util.Iterator;

public class BoggleSolver {

    private Trie trie = new Trie();
    private SET<String> validWords = new SET<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException("Dictionary cannot be null");
        for (String word : dictionary) {
            trie.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        validWords = new SET<>();
        if (board == null) throw new IllegalArgumentException("Board cannot be null");
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                HashMap<Integer, Coor> visitedDice = new HashMap<>();
                Coor p = new Coor(i, j, board);
                searchWords(board, "", visitedDice, p);
            }
        }
        return validWords;
    }

    private void searchWords(BoggleBoard board, String prefix, HashMap<Integer, Coor> visitedDice,
                             Coor currentDie) {
        visitedDice.put(currentDie.key, currentDie);
        String letter = Character.toString(board.getLetter(currentDie.row, currentDie.col));
        if (letter.equals("Q")) {
            prefix += "QU";
        }
        else {
            prefix += letter;
        }
        if (prefix.length() > 2 && !trie.keysWithPrefix(prefix).iterator().hasNext()) return;
        if (prefix.length() > 2 && trie.contains(prefix)) validWords.add(prefix);
        HashMap<Integer, Coor> adj = adjacentDice(currentDie, visitedDice, board);
        for (Coor die : adj.values()) {
            HashMap<Integer, Coor> copy = new HashMap<>(visitedDice);
            searchWords(board, prefix, visitedDice, die);
            visitedDice = copy;
        }
    }

    private HashMap<Integer, Coor> adjacentDice(Coor currentDie, HashMap<Integer, Coor> visitedDice,
                                                BoggleBoard board) {
        HashMap<Integer, Coor> adj = new HashMap<>();
        int row = currentDie.row;
        int col = currentDie.col;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Coor adjDie = new Coor(row + i, col + j, board);
                if (insideBounds(row + i, col + j, board.rows(), board.cols()) && visitedDice
                        .get(adjDie.key) == null) {
                    adj.put(adjDie.key, adjDie);
                }
            }
        }
        return adj;
    }

    private boolean insideBounds(int row, int col, int maxRow, int maxCol) {
        boolean isInsideBounds = true;
        if (row < 0 || row > maxRow - 1 || col < 0 || col > maxCol - 1) isInsideBounds = false;
        return isInsideBounds;
    }

    private static class Coor {

        private int key;
        private int row;
        private int col;

        public Coor(int row, int col, BoggleBoard board) {
            this.row = row;
            this.col = col;
            this.key = row * board.cols() + col + 1;
        }
    }


    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException("Word cannot be null");
        int points = 0;
        int length = word.length();
        if (length == 3 || length == 4) {
            points = 1;
        }
        else if (length == 5 || length == 6) {
            points = length - 3;
        }
        else if (length == 7) {
            points = 5;
        }
        else if (length > 7) {
            points = 11;
        }
        return points;
    }

    public static void main(String[] args) {

        In in = new In("dictionary.txt");

        BoggleSolver bg = new BoggleSolver(in.readAllLines());
        in.close();
        BoggleBoard bb = new BoggleBoard("boggleq.txt");
        System.out.println(bb.toString());
        Iterator<String> it = bg.getAllValidWords(bb).iterator();
        int score = 0;
        while (it.hasNext()) {
            String word = it.next();
            System.out.println(word);
            score += bg.scoreOf(word);
        }
        System.out.println(score);
        Coor c1 = new Coor(2, 3, bb);
        Coor c2 = new Coor(3, 2, bb);
        Coor c3 = new Coor(2, 3, bb);
        HashMap<Integer, Coor> c = new HashMap<>();
        c.put(c1.key, c1);
        c.put(c2.key, c2);

        System.out.println(c.get(c3.key));

    }
}
