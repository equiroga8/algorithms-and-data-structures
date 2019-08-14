/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF sites;
    private int size;
    private int numberOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.size = n + 1;
        this.sites = new WeightedQuickUnionUF(n * n + 2);
        this.grid = new boolean[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                this.grid[i][j] = false;
            }
        }
        this.grid[0][n] = true;
        this.grid[n][0] = true;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row >= size || col <= 0 || col >= size)
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        if (!grid[row][col]) {
            grid[row][col] = true;
            numberOfOpenSites++;
            connectAdjacentSites(row, col);
            if (row == 1) sites.union(0, index(row, col));
            else if (row == size - 1) sites.union((size - 1) * (size - 1) + 1, index(row, col));
        }
    }

    private void connectAdjacentSites(int row, int col) {
        if (row <= 0 || row >= size || col <= 0 || col >= size)
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        int connectingSite = index(row, col);
        if (row - 1 != 0 && grid[row - 1][col]) sites.union(connectingSite, index(row - 1, col));
        if (row + 1 != size && grid[row + 1][col])
            sites.union(connectingSite, index(row + 1, col));
        if (col - 1 != 0 && grid[row][col - 1]) sites.union(connectingSite, index(row, col - 1));
        if (col + 1 != size && grid[row][col + 1])
            sites.union(connectingSite, index(row, col + 1));
    }

    private int index(int row, int col) {
        return (row - 1) * (size - 1) + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row >= size || col <= 0 || col >= size)
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row >= size || col <= 0 || col >= size)
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        return sites.connected(0, index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.connected(0, (size - 1) * (size - 1) + 1);
    }

    private String print() {
        StringBuilder s = new StringBuilder();
        s.append("n = " + size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j]) {
                    s.append(String.format("%2s ", '0'));
                }
                else {
                    s.append(String.format("%2s ", 'x'));
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        Percolation p = new Percolation(4);
        System.out.println(p.sites.count());
        p.open(1, 1);
        p.open(2, 2);

        System.out.println(p.isFull(2, 2));
        p.open(2, 1);
        p.open(3, 2);
        p.open(4, 2);
        System.out.println(p.print());
        System.out.println(p.numberOfOpenSites());
    }
}
