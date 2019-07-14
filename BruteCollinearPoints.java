/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.ArrayList;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> lines = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        // Null argument
        if (points == null) throw new IllegalArgumentException("Argument cannot be null");
        // Null points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Points cannot be null");
        }
        // Duplicates
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("There cannot be duplicate points");
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                for (int m = 0; m < points.length; m++) {
                    for (int n = 0; n < points.length; n++) {
                        if (i != j && i != m && i != n && j != m && j != n && m != n) {
                            double slope1 = points[i].slopeTo(points[j]),
                                    slope2 = points[i].slopeTo(points[m]),
                                    slope3 = points[i].slopeTo(points[n]);

                            int ascendsSize = points[n].compareTo(points[m]) + points[m]
                                    .compareTo(points[j]) + points[j].compareTo(points[i]);

                            if (compareFloats(slope1, slope2) && compareFloats(slope1, slope3)
                                    && ascendsSize == 3) {

                                this.lines.add(new LineSegment(points[i], points[n]));
                            }
                        }

                    }
                }
            }
        }

    }  // finds all line segments containing 4 points

    public int numberOfSegments() {

        return this.lines.size();
    }        // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[this.lines.size()];
        for (int i = 0; i < this.lines.size(); i++) {
            res[i] = this.lines.get(i);
        }
        return res;

    }               // the line segments

    private boolean compareFloats(double a, double b) {
        boolean res = false;
        double difference = a - b;
        if (difference < 0) difference *= -1;
        if (difference <= 1e-9 && difference >= 0) res = true;
        return true;
    }

    public static void main(String[] args) {

        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 0);
        Point p4 = new Point(2, 2);
        Point p5 = new Point(4, 4);
        Point p6 = new Point(3, 2);
        Point p7 = new Point(1, -1);
        Point p8 = new Point(1, 3);

        Point[] points = { p1, p2, p3, p4, p5, p6, p7, p8 };

        BruteCollinearPoints b = new BruteCollinearPoints(points);
        System.out.println(b.numberOfSegments());
    }
}
