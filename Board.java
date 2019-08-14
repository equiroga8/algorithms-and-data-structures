/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    private int[][] tiles;
    private int size;

    public Board(int[][] tiles) {
        if (tiles == null || tiles.length != tiles[0].length)
            throw new IllegalArgumentException("Invalid tiles");
        int[][] board = tiles;
        this.tiles = board;
        this.size = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return this.size;
    }

    // number of tiles out of place
    public int hamming() {
        int tilesDisplaced = -1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (j * size + i + 1 != tiles[j][i]) {
                    tilesDisplaced++;
                }
            }
        }
        return tilesDisplaced;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int totalDistanceDisplaced = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[j][i] != 0) {
                    int y = (tiles[j][i] - 1) / size;
                    int x = tiles[j][i] - y * size - 1;
                    totalDistanceDisplaced += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return totalDistanceDisplaced;
    }

    // is this board the goal board?
    public boolean isGoal() {
        boolean isGoal = true;
        outerloop:
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (i != size - 1 && j != size - 1) {
                    if (j * size + i + 1 != tiles[j][i]) {
                        isGoal = false;
                        break outerloop;
                    }
                }

            }
        }
        return isGoal;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.size != that.size) return false;
        for (int i = 0; i < that.tiles.length; i++) {
            for (int j = 0; j < that.tiles[0].length; j++) {
                if (this.tiles[j][i] != that.tiles[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new BoardIterable(this);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        Board boardClone = clone(this);
        if (boardClone.tiles[0][0] != 0 && boardClone.tiles[size - 1][0] != 0) {
            swap(boardClone, 0, 0, size - 1, 0);
        }
        else if (boardClone.tiles[0][0] != 0 && boardClone.tiles[0][size - 1] != 0) {
            swap(boardClone, 0, 0, 0, size - 1);
        }
        else {
            swap(boardClone, size - 1, 0, 0, size - 1);
        }

        return boardClone;
    }

    private void swap(Board board, int i, int j, int x, int y) {
        int swap = board.tiles[i][j];
        board.tiles[i][j] = board.tiles[x][y];
        board.tiles[x][y] = swap;
    }

    private class BoardIterable implements Iterable<Board> {
        private Board board = null;

        public BoardIterable(Board board) {
            this.board = board;
        }

        public Iterator<Board> iterator() {
            return new BoardList(board);
        }
    }

    private class BoardList implements Iterator<Board> {

        private ArrayList<Board> boardList = new ArrayList<>();
        private int current;

        public BoardList(Board board) {
            int x = 0, y = 0;
            for (int i = 0; i < board.tiles.length; i++) {
                for (int j = 0; j < board.tiles[0].length; j++) {
                    if (board.tiles[j][i] == 0) {
                        x = j;
                        y = i;
                    }
                }
            }
            int swap;
            Board boardClone = null;
            for (int i = 0; i < 4; i++) {
                boardClone = Board.this.clone(board);
                try {
                    switch (i) {
                        case 0:
                            swap = boardClone.tiles[x - 1][y];
                            boardClone.tiles[x - 1][y] = 0;
                            boardClone.tiles[x][y] = swap;
                            break;
                        case 1:
                            swap = boardClone.tiles[x][y - 1];
                            boardClone.tiles[x][y - 1] = 0;
                            boardClone.tiles[x][y] = swap;
                            break;
                        case 2:
                            swap = boardClone.tiles[x + 1][y];
                            boardClone.tiles[x + 1][y] = 0;
                            boardClone.tiles[x][y] = swap;
                            break;
                        case 3:
                            swap = boardClone.tiles[x][y + 1];
                            boardClone.tiles[x][y + 1] = 0;
                            boardClone.tiles[x][y] = swap;
                            break;
                        default:
                            break;
                    }
                    boardList.add(boardClone);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }

        }

        public boolean hasNext() {
            return current < boardList.size();
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove method is not supported");
        }

        public Board next() {
            if (current > boardList.size() - 1) throw new NoSuchElementException("No more items");
            Board nextBoard = boardList.get(current++);
            return nextBoard;
        }
    }

    private Board clone(Board board) {
        int[][] tilesCopy = new int[board.size][board.size];
        for (int i = 0; i < board.tiles.length; i++) {
            for (int j = 0; j < board.tiles[0].length; j++) {
                tilesCopy[i][j] = board.tiles[i][j];
            }
        }
        return new Board(tilesCopy);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                { 1, 0, 2 },
                { 7, 5, 4 },
                { 8, 6, 3 }
        };

        Board board = new Board(tiles);

        System.out.println(board.toString());
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());
/*        Iterator<Board> itBoard = board.neighbors().iterator();
        System.out.println(itBoard.next().toString());
        System.out.println(itBoard.next().toString());*/

        System.out.println(board.toString());
        System.out.println("twin:");
        System.out.println(board.twin().toString());
        System.out.println(board.isGoal());


    }
}
