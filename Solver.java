/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Iterator;

public class Solver {

    private Node searchNode;
    private MinPQ<Node> searchPq;
    private MinPQ<Node> twinPq;
    private int moves = 0;
    private boolean isSolvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        searchNode = new Node(initial, initial.manhattan(), moves);

        Board twinBoard = initial.twin();
        Node searchTwinNode = new Node(twinBoard, twinBoard.manhattan(), moves);

        searchPq = new MinPQ<>(Node::compareTo);
        searchPq.insert(searchNode);


        twinPq = new MinPQ<>(Node::compareTo);
        twinPq.insert(searchTwinNode);
        boolean isSolved = false;
        if (searchNode.manhattan == 0) {
            isSolved = true;
            isSolvable = true;
        }
        while (!isSolved) {
            isSolved = search(searchPq, false) || search(twinPq, true);
        }
    }

    private boolean search(MinPQ<Node> priorityQueue, boolean isTwin) {
        Node node = priorityQueue.delMin();
        if (node.manhattan == 0) {
            if (!isTwin) {
                searchNode = node;
                isSolvable = true;
            }
            else {
                moves = -1;
            }
            return true;
        }
        Iterator<Board> neighbors = node.board.neighbors().iterator();
        Node neighborNode;
        Board board;
        while (neighbors.hasNext()) {
            board = neighbors.next();
            if (node.previousNode == null || !board.equals(node.previousNode.board)) {
                neighborNode = new Node(board, board.manhattan(), node.moves + 1);
                neighborNode.previousNode = node;
                priorityQueue.insert(neighborNode);
            }
        }
        return false;
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        Node node = searchNode;
        if (moves != -1) {
            ArrayList<Board> g = (ArrayList<Board>) this.solution();
            return g.size() - 1;
        }
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (moves == -1) return null;
        ArrayList<Board> solution = new ArrayList<>();
        Node currentNode = searchNode;
        while (currentNode != null) {
            solution.add(0, currentNode.board);
            currentNode = currentNode.previousNode;
        }
        return solution;
    }

    private class Node implements Comparable<Node> {
        private Node previousNode;
        private Board board;
        private int moves;
        private int priority;
        private int manhattan;

        public Node(Board board, int manhattan, int moves) {
            this.board = board;
            this.manhattan = manhattan;
            this.moves = moves;
            this.previousNode = null;
            this.priority = manhattan + moves;
        }

        public int compareTo(Node that) {
            int res = 0;
            if (this.priority > that.priority) {
                res = 1;
            }
            else if (this.priority < that.priority) {
                res = -1;
            }
            else {
                if (this.manhattan > that.manhattan) {
                    res = 1;
                }
                else if (this.manhattan < that.manhattan) {
                    res = -1;
                }
            }
            return res;
        }
    }


    public static void main(String[] args) {
/*        int[][] tiles = {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 0, 12 },
                { 13, 14, 11, 15 }
        };*/

        int[][] tiles = {
                { 4, 1, 2 },
                { 3, 0, 6 },
                { 5, 7, 8 }

        };

        Board board = new Board(tiles);

        Solver solver = new Solver(board);
        System.out.println(solver.isSolvable);
        System.out.println(solver.moves());
        Iterator<Board> it = solver.solution().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
}
