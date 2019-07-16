/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    private int[][] board;
    private int size;

    public Board(int[][] tiles) {
        if (tiles == null || tiles.length != tiles[0].length)
            throw new IllegalArgumentException("Invalid tiles");
        int[][] board = tiles;
        this.board = board;
        this.size = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", board[i][j]));
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
        int tilesDisplaced = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (j * size + i + 1 != board[j][i] && 0 != board[size - 1][size - 1]) {
                    tilesDisplaced++;
                }
            }
        }
        return tilesDisplaced;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int totalDistanceDisplaced = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[j][i] == 0) {
                    totalDistanceDisplaced += 2 * (size - 1) - j - i;
                }
                else {
                    int y = (board[j][i] - 1) / size;
                    int x = board[j][i] - y * size - 1;
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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (j * size + i + 1 != board[i][j] || 0 != board[size - 1][size - 1]) {
                    isGoal = false;
                    break outerloop;
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
        for (int i = 0; i < that.board.length; i++) {
            for (int j = 0; j < that.board[0].length; j++) {
                if (this.board[j][i] != that.board[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                { 7, 2, 3, 4 },
                { 1, 6, 5, 8 },
                { 9, 10, 11, 12 },
                { 13, 14, 15, 0 }
        };

        Board board = new Board(tiles);

        System.out.println(board.toString());
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());


    }
}
