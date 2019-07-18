/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private int moves;
    private Board searchNode;
    private Board previousSearchNode;
    private MinPQ<Board> pq = new MinPQ<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.searchNode = initial;

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return null;
    }

    public static void main(String[] args) {

    }
}
