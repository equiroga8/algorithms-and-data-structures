/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double mean;
    private double stdDeviation;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Trials or Grid size cannot be less than 1");
        this.trials = trials;
        double[] openSitesPerTrial = new double[trials];
        Percolation percolation;
        int row;
        int col;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            openSitesPerTrial[i] = (double) percolation.numberOfOpenSites() / (double) (n * n);
        }
        this.mean = StdStats.mean(openSitesPerTrial);
        this.stdDeviation = StdStats.stddev(openSitesPerTrial);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (1.96 * stdDeviation) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (1.96 * stdDeviation) / Math.sqrt(trials);
    }

    public static void main(String[] args) {

        PercolationStats ps = new PercolationStats(5, 30);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println("[ " + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");

    }
}
