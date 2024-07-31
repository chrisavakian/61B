import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    //This will hold where the user has opened spots
    boolean[][] openArr;

    //For convinence
    int numOfOpen = 0;
    int N;

    //create a virtual top site and a virtual bottom site
    //Create a weighted quick union
    WeightedQuickUnionUF weightUnion;
    WeightedQuickUnionUF forBackWash;

    //Dummy variables, like sentinel nodes, the top and bottom of the world will connect to these
    int virtualTop;
    int virtualBottom;

    private int xyTo1D(int row, int col) {
        //Converts row and col into a number
        if (row >= 0 && col >= 0 && row < openArr[0].length && col < openArr[0].length) {
            return openArr[0].length * row + col;
        }
        return -1;
    }

    public Percolation(int N) {

        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Size not valid number");
        }

        this.N = N;

        virtualTop = N * N;
        virtualBottom = N * N + 1;

        //This creates a 2x2 array and by default Java implements everything with false
        openArr = new boolean[N][N];

        //Initialize weightUnion with N (Correct?) amount of thingies.
        weightUnion = new WeightedQuickUnionUF(N * N + 2);
        forBackWash = new WeightedQuickUnionUF(N * N + 2);

        //Assign the top and bottom of the array with the virtual top and bottom site
        for (int i = 0; i < openArr[0].length; i++) {
            //This will add the top row to the virtualTop
            weightUnion.union(virtualTop, xyTo1D(0, i));

            forBackWash.union(virtualTop, xyTo1D(0, i));

            //This will add the bottom row to the virtualBottom
            weightUnion.union(virtualBottom, xyTo1D(openArr[0].length - 1, i));
        }

        /*
        By this point, boolean array has been initialized with all false.
        weightUnion initalized with N (Correct?) things
        unioned everything from top row to virtualTop
        unioned everything from bottom row to virtualBottom
         */

    }

    public void open(int row, int col) {

        if (row >= 0 && col >= 0 && row < openArr[0].length && col < openArr[0].length) {
            if (!this.isOpen(row, col)) {
                numOfOpen++;
                openArr[row][col] = true;

                //Check if sides are open as well
                if (isOpen(row + 1, col)) {
                    weightUnion.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                    forBackWash.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                }
                if (isOpen(row - 1, col)) {
                    weightUnion.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                    forBackWash.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                }
                if (isOpen(row, col + 1)) {
                    weightUnion.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                    forBackWash.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                }
                if (isOpen(row, col - 1)) {
                    weightUnion.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                    forBackWash.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                }
            }
        } else {
            throw new java.lang.IndexOutOfBoundsException("Index Out of Bounds");
        }
    }

    public boolean isOpen(int row, int col) {
        if (row >= 0 && col >= 0 && row < openArr[0].length && col < openArr[0].length) {
            return openArr[row][col];
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        //This method will most likely have the check for backwash
        if (row >= 0 && col >= 0 && row < openArr[0].length && col < openArr[0].length && isOpen(row, col)) {
            return forBackWash.connected(virtualTop, xyTo1D(row, col));
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numOfOpen;
    }

    public boolean percolates() {
        //for loop each bottom thing and check if it's open and connected to virtualbottom/top
        if (N == 1) {
            return isOpen(0, 0);
        }
        return weightUnion.connected(virtualTop, virtualBottom);
    }
}
