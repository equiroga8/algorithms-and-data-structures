/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {


    private Node root;
    private int size;
    private Point2D nearestPoint;

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return this.root == null;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Argument cannot be null");
        if (!this.contains(p)) {
            root = put(root, p, true, null);
            size++;
        }
    }

    private Node put(Node node, Point2D p, boolean vertical, Node parent) {
        if (node == null) return new Node(p, vertical, parent);
        int cmp;
        if (vertical) {
            if (p.x() >= node.p.x()) {
                node.rt = put(node.rt, p, false, node);
            }
            else {
                node.lb = put(node.lb, p, false, node);
            }
        }
        else {
            if (p.y() >= node.p.y()) {
                node.rt = put(node.rt, p, true, node);
            }
            else {
                node.lb = put(node.lb, p, true, node);
            }
        }
        return node;
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Argument cannot be null");
        return get(p) != null;
    }

    private Node get(Point2D p) {
        return get(root, p);
    }

    private Node get(Node node, Point2D p) {
        if (node == null) return null;
        if (node.vertical) {
            if (p.x() > node.p.x()) {
                return get(node.rt, p);
            }
            else if (p.x() < node.p.x()) {
                return get(node.lb, p);
            }
            else {
                if (p.y() == node.p.y()) {
                    return node;
                }
                else {
                    return get(node.rt, p);
                }
            }
        }
        else {
            if (p.y() > node.p.y()) {
                return get(node.rt, p);
            }
            else if (p.y() < node.p.y()) {
                return get(node.lb, p);
            }
            else {
                if (p.x() == node.p.x()) {
                    return node;
                }
                else {
                    return get(node.rt, p);
                }
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        StdDraw.square(0.5, 0.5, 0.5);
    }

    private void draw(Node node) {
        if (node == null) return;

        StdDraw.setPenRadius(0.005);
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.p.x(), node.p.y());
        draw(node.lb);
        draw(node.rt);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Argument cannot be null");
        ArrayList<Point2D> points = new ArrayList<>();
        if (!this.isEmpty()) range(points, rect, root);
        return points;
    }

    private void range(ArrayList<Point2D> points, RectHV rect, Node node) {
        if (node == null) return;
        if (rect.intersects(node.rect)) {
            if (rect.contains(node.p)) points.add(node.p);
            range(points, rect, node.rt);
            range(points, rect, node.lb);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Argument cannot be null");
        double shortestDistance = 100000.0;
        nearest(p, root, shortestDistance);
        return nearestPoint;
    }

    private void nearest(Point2D p, Node node, double shortestDistance) {
        if (node == null) return;
        double distance = p.distanceTo(node.p);
        if (shortestDistance > distance) {
            shortestDistance = distance;
            nearestPoint = node.p;
            if (node.vertical) {
                if (p.x() >= node.p.x()) {
                    nearest(p, node.rt, shortestDistance);
                }
                else {
                    nearest(p, node.lb, shortestDistance);
                }
            }
            else {
                if (p.y() >= node.p.y()) {
                    nearest(p, node.rt, shortestDistance);
                }
                else {
                    nearest(p, node.lb, shortestDistance);
                }
            }
        }
    }


    private class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean vertical;

        public Node(Point2D p, boolean vertical, Node parent) {
            this.p = p;
            this.vertical = vertical;
            this.rect = formRectangle(p, parent);
        }

        private RectHV formRectangle(Point2D point, Node parent) {
            if (parent == null) return new RectHV(0, 0, 1, 1);
            RectHV rectangle;
            boolean parentVertical = parent.vertical;
            if (parentVertical) {
                if (point.x() >= parent.p.x()) {
                    rectangle = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(),
                                           parent.rect.ymax());
                }
                else {
                    rectangle = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(),
                                           parent.rect.ymax());
                }
            }
            else {
                if (point.y() >= parent.p.y()) {
                    rectangle = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(),
                                           parent.rect.ymax());
                }
                else {
                    rectangle = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                           parent.rect.xmax(), parent.p.y());
                }
            }
            return rectangle;
        }
    }

    public static void main(String[] args) {

        KdTree kdt = new KdTree();
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);
        kdt.insert(p1);
        kdt.insert(p2);
        kdt.insert(p3);
        kdt.insert(p4);
        kdt.insert(p5);
        kdt.draw();
        // RectHV rect = new RectHV(0.1, 0.1, 0.45, 0.9);
        Point2D p = new Point2D(0.5, 0.5);
        System.out.println(kdt.nearest(p));
        System.out.println(kdt.size());

    }
}
