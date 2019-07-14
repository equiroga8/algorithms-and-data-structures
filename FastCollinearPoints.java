import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {


    private ArrayList<LineSegment> lines = new ArrayList<>();


    public FastCollinearPoints(
            Point[] points) { // finds all line segments containing 4 or more points
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
        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            System.arraycopy(points, 0, aux, 0, points.length);
            aux = swap(aux, i);
            Arrays.sort(aux, 1, aux.length, aux[0].slopeOrder());
            formLineSegments(aux);
        }
    }

    public int numberOfSegments() { // the number of line segments
        return this.lines.size();
    }

    public LineSegment[] segments() {   // the line segments
        LineSegment[] res = new LineSegment[this.lines.size()];
        for (int i = 0; i < this.lines.size(); i++) {
            res[i] = this.lines.get(i);
        }
        return res;
    }

    private Point[] swap(Point[] points, int index) {
        Point swap = points[index];
        points[index] = points[0];
        points[0] = swap;
        return points;
    }

    private void formLineSegments(Point[] points) {
        int nAdjacent = 1;
        double slope = points[0].slopeTo(points[1]);
        for (int i = 2; i <= points.length; i++) {
            if (i != points.length && compareFloats(slope, points[0].slopeTo(points[i]))) {
                nAdjacent++;
                slope = points[0].slopeTo(points[i]);
            }
            else if (nAdjacent >= 3) {
                Point maxPoint = points[0], minPoint = points[0];
                for (int j = 0; j < nAdjacent; j++) {
                    if (maxPoint.compareTo(points[i - j - 1]) <= 0) {
                        maxPoint = points[i - j - 1];
                    }
                    if (minPoint.compareTo(points[i - j - 1]) > 0) {
                        minPoint = points[i - j - 1];
                    }

                }
                boolean repeated = false;
                LineSegment newLine = new LineSegment(minPoint, maxPoint);
                for (int j = 0; j < this.lines.size(); j++) {
                    if (lines.get(j).toString().equals(newLine.toString())) repeated = true;
                }
                if (!repeated)
                    this.lines.add(newLine);
                nAdjacent = 1;
            }
            else {
                nAdjacent = 1;
                if (i != points.length) slope = points[0].slopeTo(points[i]);
            }
        }
    }

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
        Point p4 = new Point(4, 4);
        Point p5 = new Point(2, 2);
        Point p6 = new Point(3, 2);
        Point p7 = new Point(1, -1);
        Point p8 = new Point(3, 3);
        Point p9 = new Point(1, 3);
        Point p10 = new Point(-1, -1);

        Point[] points = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10 };

        FastCollinearPoints b = new FastCollinearPoints(points);
        System.out.println(b.numberOfSegments());
        for (int i = 0; i < b.numberOfSegments(); i++) {
            System.out.println(b.lines.get(i));
        }
    }
}
