import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    
    private double[] ratios;
    private int trialCount;
    
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        ratios = new double[trials];
        for (int trial=0; trial < trials; trial++){
            Percolation perc = new Percolation(n);
            int openedNodesST = 0;
            while (!perc.percolates()){
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (!perc.isOpen(row, col)){
                    perc.open(row,col);
                    openedNodesST++;
                }
            }
            double ratio = (double) openedNodesST / (n*n);
            ratios[trial] = ratio;
            trialCount++;
        }
    }
    
    public double mean(){
        return StdStats.mean(ratios);
    }
    public double stddev(){
        return StdStats.stddev(ratios);
    }
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trialCount));
    }
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trialCount));
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        String confidence = stats.confidenceLo() + " -- " + stats.confidenceHi();
        System.out.println("Mean = " + stats.mean());
        System.out.println("Standard Deviation = " + stats.stddev());
        System.out.println("95% Conf = " + confidence);
    }
}