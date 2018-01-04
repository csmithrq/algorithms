import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int[] status;
    private int topSite;
    private int bottomSite;
    private int n;
    private int sites;
    private WeightedQuickUnionUF uf;
    
    // Create all the nodes and assign them initial state of 0, which is closed.
    public Percolation(int n){
        if (n <= 0){
            throw new IllegalArgumentException();
        }
        status = new int[n*n + 1];
        uf = new WeightedQuickUnionUF(n*n + 2);
        for (int i=0; i < ( (n*n) + 1); i++){
            status[i] = 0;
        }
        this.n = n;
        // Create virtual topsite and bottom site outside of the grid.
        topSite = (n*n);
        bottomSite = (n*n) + 1;
    }
    
    // Open checks the top, bottom, left and right of the node to see if the neighbor is open. Edges are considered to maintain the grid.
    // If the neighbor is open, it unions the node to the neighbor.
    // If the neighbor is not open, it doesn't.
    //// Special cases:
    // a virtual top site and bottom site are introduced to simplify the connection calls.
    // In the case where node < n (top row), a union is made between the node and the virtual top site.
    // In the case where node > n(n-1) (bottom row), a union is made between the node and the virtual bottom site.
    
    // Keeps track of the number of open sites.
    public void open(int row, int col){
        if (row <= 0 || col <= 0){
            throw new IllegalArgumentException();
        }
        int node = ((row-1) * this.n) + col;
        if (status[node] != 1){
            status[node] = 1;
            sites++;
        }
        
        //top check.
        if (node > this.n){
            if (isOpen( (row-1), col)){
                uf.union(node, node-n);
            }
        } else {
            uf.union(node, topSite);
        }
        //bottom check.
        if (node < (this.n * (this.n - 1))){
            if (isOpen((row+1), col)){
                uf.union(node, node+n);
            } else {
                uf.union(node, bottomSite);
            }
        }
        //right check.
        if (node % this.n != 0){
            if (isOpen(row, (col + 1) )){
                uf.union(node, node+1);
            }
        }
        //left check
        if (node % (this.n-1) != 0){
            if (isOpen(row, (col - 1) )){
                uf.union(node, node-1);
            }
        }
    }
    
    // Check the status array for open or full nodes. Full node check invokes open node check.
    public boolean isOpen(int row, int col){
        if (row <= 0 || col <= 0){
            throw new IllegalArgumentException();
        }
        int node = ((row-1) * n) + col;
        if (status[node] == 1){
            return true;
        } else {
            return false;
        }
    }
    
    // A full node is one that connects to the top virtual site.
    public boolean isFull(int row, int col){
        if (row <= 0 || col <= 0){
            throw new IllegalArgumentException();
        }
        int node = (this.n * (row-1)) + col;
        if (uf.connected(node, topSite)){
            return true;
        } else {
            return false;
        }
    }
    
    public int numberOfOpenSites(){
        return sites;
    }
    
    public boolean percolates(){
        if (uf.connected(topSite, bottomSite)){
            return true;
        } else {
            return false;
        }
    }
  
}