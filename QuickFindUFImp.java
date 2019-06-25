/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class QuickFindUFImp {

    private int[] id;

    public QuickFindUFImp(int nodes) {
        id = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {

        int secondGroup = id[p];
        int firstGroup = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == secondGroup) {
                id[i] = firstGroup;
            }
        }

    }

    public static void main(String[] args) {

    }
}
