/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {

    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Argument cannot be null");
        if (!set.contains(p)) set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Argument cannot be null");
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        Iterator<Point2D> it = set.iterator();
        Point2D point;
        while (it.hasNext()) {
            point = it.next();
            StdDraw.point(point.x(), point.y());
        }
        StdDraw.setPenRadius(0.005);
        StdDraw.square(0.5, 0.5, 0.5);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Argument cannot be null");
        ArrayList<Point2D> insidePoints = new ArrayList<>();
        Iterator<Point2D> it = set.iterator();
        Point2D point;
        while (it.hasNext()) {
            point = it.next();
            if (rect.contains(point)) {
                insidePoints.add(point);
            }
        }
        return insidePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Argument cannot be null");
        Iterator<Point2D> it = set.iterator();
        Point2D nearestPoint = null;
        Point2D point;
        double shortestDistance = Double.POSITIVE_INFINITY;
        while (it.hasNext()) {
            point = it.next();
            if (point.distanceTo(p) < shortestDistance) {
                shortestDistance = point.distanceTo(p);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

        PointSET set = new PointSET();
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.2, 0.3);
        Point2D p3 = new Point2D(0.4, 0.3);
        Point2D p4 = new Point2D(0.1, 0.9);
        set.insert(p1);
        set.insert(p2);
        set.insert(p3);
        set.insert(p4);
        set.draw();
        System.out.println(set.nearest(new Point2D(0.5, 0.5)));
        System.out.println(set.range(new RectHV(0.0, 0.0, 0.25, 0.35)));
    }
}
