/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class QuickUnionUFImp {
    private int[] id;

    public QuickUnionUFImp(int nodes) {
        id = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public void union(int p, int q) {

        int secondGroup = root(p);
        int firstGroup = root(q);

        id[secondGroup] = firstGroup;

    }

    public static void main(String[] args) {

    }
}
