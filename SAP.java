/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.HashSet;

public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Argument cannot be null");
        this.digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int size = digraph.V() - 1;
        if (v > size || v < 0 || w > size || w < 0)
            throw new IllegalArgumentException("Argument cannot be out of range");
        if (shortestPath(v, w).ancestor != -1) {
            return shortestPath(v, w).distance;
        }
        else {
            return -1;
        }

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int size = digraph.V() - 1;
        if (v > size || v < 0 || w > size || w < 0)
            throw new IllegalArgumentException("Argument cannot be out of range");
        return shortestPath(v, w).ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("Argument cannot be null");

        ArrayList<ShortestPath> potentialPaths = potentialPaths(v, w);

        int minLength = Integer.MAX_VALUE;

        for (ShortestPath sp : potentialPaths) {
            if (sp.distance < minLength) {
                minLength = sp.distance;
            }
        }

        if (minLength == Integer.MAX_VALUE) {
            return -1;
        }

        return minLength;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("Argument cannot be null");
        ArrayList<ShortestPath> potentialPaths = potentialPaths(v, w);

        int minLength = Integer.MAX_VALUE;
        int ancestor = -1;

        for (ShortestPath sp : potentialPaths) {
            if (sp.distance < minLength) {
                minLength = sp.distance;
                ancestor = sp.ancestor;
            }
        }
        return ancestor;
    }

    private ShortestPath shortestPath(int blue, int red) {
        return new ShortestPath(digraph, blue, red);
    }

    private ArrayList<Integer> toArrayList(Iterable<Integer> v) {
        ArrayList<Integer> list = new ArrayList<>();
        v.forEach(list::add);
        return list;
    }

    private ArrayList<ShortestPath> potentialPaths(Iterable<Integer> v, Iterable<Integer> w) {

        ArrayList<Integer> blues = toArrayList(v);
        ArrayList<Integer> reds = toArrayList(w);

        ArrayList<ShortestPath> potentialPaths = new ArrayList<>();

        for (int blue : blues) {
            for (int red : reds) {
                potentialPaths.add(new ShortestPath(digraph, blue, red));
            }
        }

        return potentialPaths;
    }

    private class ShortestPath {

        private int ancestor = -1;
        private int distance = Integer.MAX_VALUE;

        public ShortestPath(Digraph digraph, int blue, int red) {
            BreadthFirstDirectedPaths bfsBlue = new BreadthFirstDirectedPaths(digraph, blue);
            BreadthFirstDirectedPaths bfsRed = new BreadthFirstDirectedPaths(digraph, red);
            HashSet<Integer> posibleAncestors = new HashSet<>();
            for (int i = 0; i < digraph.V(); i++) {
                if (bfsBlue.hasPathTo(i) && bfsRed.hasPathTo(i)
                        && bfsBlue.distTo(i) + bfsRed.distTo(i) < distance) {
                    distance = bfsBlue.distTo(i) + bfsRed.distTo(i);
                    ancestor = i;
                }
            }
        }
    }

    public static void main(String[] args) {

        Digraph G1 = new Digraph(13);
        G1.addEdge(7, 3);
        G1.addEdge(8, 3);
        G1.addEdge(3, 1);
        G1.addEdge(4, 1);
        G1.addEdge(5, 1);
        G1.addEdge(9, 5);
        G1.addEdge(9, 4);
        G1.addEdge(10, 5);
        G1.addEdge(11, 10);
        G1.addEdge(12, 10);
        G1.addEdge(1, 0);
        G1.addEdge(2, 0);

        Digraph G2 = new Digraph(9);
        G2.addEdge(7, 6);
        G2.addEdge(6, 3);
        G2.addEdge(3, 0);
        G2.addEdge(0, 6);
        G2.addEdge(3, 2);
        G2.addEdge(3, 4);
        G2.addEdge(4, 1);
        G2.addEdge(1, 3);
        G2.addEdge(5, 4);
        G2.addEdge(5, 8);


        System.out.println(G1.toString());
        SAP sap = new SAP(G1);

        System.out.println("Ancestor: " + sap.ancestor(8, 4));
        System.out.println("Length: " + sap.length(8, 4));

    }
}
