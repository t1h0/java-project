import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Node;

public class Utilities {

	/**
	 * Compares two doubles for equality.
	 * 
	 * @param a Double; The first double.
	 * @param b Double; The second double.
	 * @return boolean; true if a==b (a-b<delta), false otherwise.
	 */
	public static boolean doubleComparison(double a, double b) {
		double delta = 0.000001; // Difference to be tolerated.
		if (Math.abs(a - b) <= delta) {
			return true; // a==b
		} else {
			return false; // a!=b
		}
	}

	/**
	 * Compares two doubles (equal, greater or lesser).
	 * 
	 * @param a Double; The first double.
	 * @param b Double; The second double.
	 * @return int; 1 if d1>d2, 0 if d1==d2, -1 if d1<d2.
	 */
	public static int doubleComparison2(double d1, double d2) {
		double delta = 0.000001; // Difference to be tolerated.
		if (Math.abs(d1 - d2) <= delta) {
			return 0; // d1=d2
		} else if (d1 - d2 > 0) {
			return 1; // d1>d2
		} else {
			return -1; // d1<d2
		}
	}

	/**
	 * Cuts all numbers after the second decimal place of a given double.
	 * 
	 * @param value, double; The number to round.
	 * @return double; the rounded number.
	 */
	public static double round2(double value) {
		int dec = (int) value;
		return dec + ((int) ((value - dec) * 100)) * 0.01;
	}

	/**
	 * Calculates the slope of the line connecting two given data points.
	 * 
	 * @param x1, double; x-coordinate of the first data point.
	 * @param y1, double; y-coordinate of the first data point.
	 * @param x2, double; x-coordinate of the second data point.
	 * @param y2, double; y-coordinate of the second data point.
	 * @return double; the slope of the line connecting the two given data points.
	 */
	public static double getSlope(double x1, double y1, double x2, double y2) {
		return (y2 - y1) / (x2 - x1);
	}

	/**
	 * Calculates the slope of the line connecting two given data points.
	 * 
	 * @param point1, MyPoint; first data point.
	 * @param point2, MyPoint; second data point.
	 * @return double; the slope of the line connecting the two given data points.
	 */
	public static double getSlope(MyPoint point1, MyPoint point2) {
		return (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
	}

	/**
	 * Calculates the intercept of the line connecting two given data points.
	 * 
	 * @param x1, double; x-coordinate of the first data point.
	 * @param y1, double; y-coordinate of the first data point.
	 * @param x2, double; x-coordinate of the second data point.
	 * @param y2, double; y-coordinate of the second data point.
	 * @return double; the intercept of the line connecting the two given data
	 *         points.
	 */
	public static double getIntercept(double x1, double y1, double x2, double y2) {
		return y1 - getSlope(x1, y1, x2, y2) * x1;
	}

	/**
	 * Calculates the intercept of the line connecting two given data points.
	 * 
	 * @param point1, MyPoint; first data point.
	 * @param point2, MyPoint; second data point.
	 * @return double; the intercept of the line connecting the two given data
	 *         points.
	 */
	public static double getIntercept(MyPoint point1, MyPoint point2) {
		return point1.getY() - getSlope(point1, point2) * point1.getX();
	}

	/**
	 * Calculates the intersection point(s) of two Nodes with each being either a
	 * MyLine, a MyRectangle or a MyCircle
	 * 
	 * @param node1, Node; first node.
	 * @param node2, Node; second node.
	 * @return ArrayList<MyPoint>; a list containing the intersection point(s).
	 *         Empty if no intersection.
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(Node node1, Node node2) {

		int run = 2;

		while (run > 0) {
			if (node1 instanceof MyLine && node2 instanceof MyCircle) { // Line and circle
				return getPointOfIntersection((MyLine) node1, (MyCircle) node2);
			} else if (node1 instanceof MyLine && node2 instanceof MyRectangle) { // Line and rectangle
				return getPointOfIntersection((MyRectangle) node2, (MyLine) node1);
			} else if (node1 instanceof MyLine && node2 instanceof MyLine) { // Two lines
				return getPointOfIntersection((MyLine) node1, (MyLine) node2);
			} else if (node1 instanceof MyCircle && node2 instanceof MyCircle) { // Two circles
				return getPointOfIntersection((MyCircle) node1, (MyCircle) node2);
			} else if (node1 instanceof MyCircle && node2 instanceof MyRectangle) { // Circle and rectangle
				return getPointOfIntersection((MyRectangle) node2, (MyCircle) node1);
			} else if (node1 instanceof MyRectangle && node2 instanceof MyRectangle) { // Two rectangles
				return getPointOfIntersection((MyRectangle) node1, (MyRectangle) node2);
			}
			// switch nodes if none of the above conditions applied.
			Node temp = node1;
			node1 = node2;
			node2 = temp;
			run--;
		}
		return new ArrayList<MyPoint>();
	}

	/**
	 * Calculates the intersection point of two given lines.
	 * 
	 * @param line1, MyLine; first line.
	 * @param line2, MyLine; second line.
	 * @return ArrayList<MyPoint>; list with the intersection point of the two lines
	 *         (empty if no intersection).
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyLine line1, MyLine line2) {
		double slope1 = line1.getSlope();
		double slope2 = line2.getSlope();
		if (slope1 == slope2) { // parallel or identical lines?
			return new ArrayList<MyPoint>();
		}
		double inter1 = line1.getIntercept();
		double inter2 = line2.getIntercept();
		if (Double.isInfinite(slope1)) { // line 1 vertical?
			double x = line1.getStartX();
			return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(x, line2.getY(x))));
		} else if (Double.isInfinite(slope2)) { // line 2 vertical?
			double x = line2.getStartX();
			return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(x, line1.getY(x))));
		}
		double intersectionX = (inter2 - inter1) / (slope1 - slope2);
		return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(intersectionX, line1.getY(intersectionX))));
	}

	/**
	 * Calculates the intersection point(s) of a given line with a given circle. See
	 * https://de.wikipedia.org/wiki/Schnittpunkt#Schnittpunkte_einer_Geraden_mit_einem_Kreis
	 * 
	 * @param line,   MyLine; the line.
	 * @param circle, MyCircle; the circle.
	 * @return ArrayList<MyPoint>; the intersection point(s) of the line with the
	 *         circle. Empty if no intersection.
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyLine line, MyCircle circle) {
		// y=mx+b <=> mx-y=-b ~> ax+by = c
		double a = line.getSlope();
		double b;
		double c;
		if(Double.isInfinite(a)) { // vertical line?
			a = 1;
			b = 0;
			c = line.getStartX();
		} else {
			b = -1;
			c = -line.getIntercept();
		}
		double a2 = Math.pow(a, 2); // a squared
		double b2 = Math.pow(b, 2); // b squared
		double d = c - a * circle.getCenter().getX() - b * circle.getCenter().getY();
		double d2 = Math.pow(d, 2); // d squared
		double r2 = Math.pow(circle.getRadius(), 2);
		if (doubleComparison2(r2 * (a2 + b2), d2) == -1) {
			return new ArrayList<MyPoint>(); // no intersection
		}
		double x1 = circle.getCenter().getX() + (a * d + b * Math.sqrt(r2 * (a2 + b2) - d2)) / (a2 + b2);
		double y1 = circle.getCenter().getY() + (b * d - a * Math.sqrt(r2 * (a2 + b2) - d2)) / (a2 + b2);
		if (doubleComparison2(r2 * (a2 + b2), d2) == 1) {
			double x2 = circle.getCenter().getX() + (a * d - b * Math.sqrt(r2 * (a2 + b2) - d2)) / (a2 + b2);
			double y2 = circle.getCenter().getY() + (b * d + a * Math.sqrt(r2 * (a2 + b2) - d2)) / (a2 + b2);
			return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(x1, y1), new MyPoint(x2, y2))); // two intersections
		}
		return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(x1, y1))); // one intersection

	}

	/**
	 * Calculates the intersection point(s) of two given circles. See
	 * https://de.wikipedia.org/wiki/Schnittpunkt#Schnittpunkte_zweier_Kreise
	 * 
	 * @param circle1, MyCircle; the first circle.
	 * @param circle2, MyCircle; the second circle.
	 * @return ArrayList<MyPoint>; the intersection point(s) of the two circles.
	 *         Empty if no intersection.
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyCircle circle1, MyCircle circle2) {
		double d12 = Utilities.getDistance(circle1.getCenter(), circle2.getCenter()); // distance between the centers
		double r12 = Math.pow(circle1.getRadius(), 2); // first radius squared
		double r22 = Math.pow(circle2.getRadius(), 2);// second radius squared
		double d0 = (r12 - r22 + Math.pow(d12, 2)) / (2 * d12); // distance of power line to first center
		double d02 = Math.pow(d0, 2); // distance of power line to first center squared
		if (doubleComparison2(r12, d02) == -1) { // no intersection
			return new ArrayList<MyPoint>();
		}
		double e0 = Math.sqrt(r12 - d02); // distance of intersections of the line through the two centers
		double x01 = circle1.getCenter().getX();
		double y01 = circle1.getCenter().getY();
		double x02 = circle2.getCenter().getX();
		double y02 = circle2.getCenter().getY();
		double x1 = x01 + d0 * ((x02 - x01) / d12) + e0 * (-(y02 - y01) / d12);
		double y1 = y01 + d0 * ((y02 - y01) / d12) + e0 * ((x02 - x01) / d12);
		if (doubleComparison2(r12, d02) == 1) {
			double x2 = x01 + d0 * ((x02 - x01) / d12) - e0 * (-(y02 - y01) / d12);
			double y2 = y01 + d0 * ((y02 - y01) / d12) - e0 * ((x02 - x01) / d12);
			return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(x1, y1), new MyPoint(x2, y2))); // two intersections
		}
		return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(x1, y1))); // one intersection

	}

	/**
	 * Calculates the intersection point(s) of a rectangle and a circle.
	 * 
	 * @param rect,   MyRectangle; the rectangle.
	 * @param circle, MyCircle; the circle.
	 * @return ArrayList<MyPoint>; the intersection point(s). Empty if no
	 *         intersection.
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyRectangle rect, MyCircle circle) {
		// convert the edges of the rectangle to lines
		MyLine[] edges = { new MyLine(rect.getTopLeft(), rect.getTopRight()),
				new MyLine(rect.getBottomRight(), rect.getTopRight()),
				new MyLine(rect.getBottomLeft(), rect.getBottomRight()),
				new MyLine(rect.getBottomLeft(), rect.getTopLeft()) };
		ArrayList<MyPoint> result = new ArrayList<MyPoint>();
		for (MyLine i : edges) {
			ArrayList<MyPoint> inter = getPointOfIntersection(i, circle);
			for (MyPoint j : inter) {
				// make sure, the possible intersection lies in between the edge
				if (j.getX() >= i.getPoint1().getCenterX() && j.getX() <= i.getPoint2().getCenterX()
						&& j.getY() <= i.getPoint1().getCenterY() && j.getY() >= i.getPoint2().getCenterY()) {
					result.add(j);
				}
			}
		}
		return result;
	}

	/**
	 * Calculates the intersection point(s) of a rectangle and a line.
	 * 
	 * @param rect, MyRectangle; the rectangle.
	 * @param line, MyLine; the line.
	 * @return ArrayList<MyPoint>; the intersection point(s). Empty if no
	 *         intersection.
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyRectangle rect, MyLine line) {
		// convert the edges of the rectangle to lines
		MyLine[] edges = { new MyLine(rect.getTopLeft(), rect.getTopRight()),
				new MyLine(rect.getBottomRight(), rect.getTopRight()),
				new MyLine(rect.getBottomLeft(), rect.getBottomRight()),
				new MyLine(rect.getBottomLeft(), rect.getTopLeft()) };
		ArrayList<MyPoint> result = new ArrayList<MyPoint>();
		for (MyLine i : edges) {
			for (MyPoint inter : getPointOfIntersection(i, line)) {
				// make sure, the possible intersection lies in between the edge
				if (inter.getX() >= i.getPoint1().getX() && inter.getX() <= i.getPoint2().getX()
						&& inter.getY() <= i.getPoint1().getY() && inter.getY() >= i.getPoint2().getY()) {
					result.add(inter);
				}
			}
		}
		return result;
	}

	/**
	 * Calculates the intersection point(s) of two rectangles.
	 * 
	 * @param rect1, MyRectangle; the first rectangle.
	 * @param rect2, MyRectangle; the second rectangle.
	 * @return ArrayList<MyPoint>; the intersection point(s). Empty if no
	 *         intersection.
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyRectangle rect1, MyRectangle rect2) {
		// convert the edges of the rectangles to lines
		MyLine[] edges1 = { new MyLine(rect1.getTopLeft(), rect1.getTopRight()),
				new MyLine(rect1.getBottomRight(), rect1.getTopRight()),
				new MyLine(rect1.getBottomLeft(), rect1.getBottomRight()),
				new MyLine(rect1.getBottomLeft(), rect1.getTopLeft()) };
		MyLine[] edges2 = { new MyLine(rect2.getTopLeft(), rect2.getTopRight()),
				new MyLine(rect2.getBottomRight(), rect2.getTopRight()),
				new MyLine(rect2.getBottomLeft(), rect2.getBottomRight()),
				new MyLine(rect2.getBottomLeft(), rect2.getTopLeft()) };
		ArrayList<MyPoint> result = new ArrayList<MyPoint>();
		for (MyLine i : edges1) {
			for (MyLine j : edges2) {
				for (MyPoint inter : getPointOfIntersection(i, j)) {
					// make sure, the possible intersection lies in between the two edges
					if (inter.getX() >= i.getPoint1().getCenterX() && inter.getX() <= i.getPoint2().getCenterX()
							&& inter.getY() <= i.getPoint1().getCenterY() && inter.getY() >= i.getPoint2().getCenterY()
							&& inter.getX() >= j.getPoint1().getCenterX() && inter.getX() <= j.getPoint2().getCenterX()
							&& inter.getY() <= j.getPoint1().getCenterY()
							&& inter.getY() >= j.getPoint2().getCenterY()) {
						result.add(inter);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Calculates the euclidean distance between two given data points.
	 * 
	 * @param point1, MyPoint; first data point.
	 * @param point2, MyPoint; second data point.
	 * @return double; the euclidean distance between the two given data points.
	 */
	public static double getDistance(MyPoint point1, MyPoint point2) {
		return Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2));
	}

	/**
	 * Loads points from a (csv) file (one row per point, first column x-, second
	 * y-value).
	 * 
	 * @param file, File; the file to load the points from.
	 * @return ArrayList<MyPoint>; the loaded points.
	 */
	public static ArrayList<MyPoint> loadMyPoints(File file) {
		ArrayList<MyPoint> points = new ArrayList<MyPoint>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String line;
			while ((line = in.readLine()) != null) {
				String[] coords = line.split("\\s*,\\s*");
				points.add(new MyPoint(Integer.valueOf(coords[0]), Integer.valueOf(coords[1])));
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return points;
	}

	/**
	 * Loads points from a (csv) file (one row per point, first column x-, second
	 * y-value).
	 * 
	 * @param path, String; path to the file to load the points from.
	 * @return ArrayList<MyPoint>; the loaded points.
	 */
	public static ArrayList<MyPoint> loadMyPoints(String path) {
		return loadMyPoints(new File(path));
	}

	/**
	 * Checks whether two given slopes are equal and thus the corresponding lines
	 * are parallel.
	 * 
	 * @param slope1, double; the slope of the first line.
	 * @param slope2, double; the slope of the second line.
	 * @return boolean; true if both slopes are equal and thus the lines are
	 *         parallel, false otherwise.
	 */
	public static boolean isParallel(double slope1, double slope2) {
		return doubleComparison(slope1, slope2);
	}

	/**
	 * Checks whether two given lines are parallel.
	 * 
	 * @param line1, MyLine; first line.
	 * @param line2, MyLine; second line.
	 * @return boolean; true if the lines are parallel, false otherwise.
	 */
	public static boolean isParallel(MyLine line1, MyLine line2) {
		return isParallel(line1.getSlope(), line2.getSlope());
	}

	/**
	 * Checks whether the product of two given slopes are equal to -1 and thus the
	 * corresponding lines are orthogonal.
	 * 
	 * @param slope1, double; the slope of the first line.
	 * @param slope2, double; the slope of the second line.
	 * @return boolean; true if the product of two given slopes is equal to -1 and
	 *         thus the corresponding lines are orthogonal, false otherwise.
	 */
	public static boolean isOrthogonal(double slope1, double slope2) {
		return slope1 * slope2 == -1;
	}

	/**
	 * Checks whether two given lines are orthogonal.
	 * 
	 * @param line1, MyLine; first line.
	 * @param line2, MyLine; second line.
	 * @return boolean; true if the two given lines are orthogonal, false otherwise.
	 */
	public static boolean isOrthogonal(MyLine line1, MyLine line2) {
		return isOrthogonal(line1.getSlope(),line2.getSlope());
	}

//	DEPRECATED
//
//	/**
//	 * Calculates the intersection point of the line connecting the first two given
//	 * data points with the line connecting the last two given data points.
//	 * 
//	 * @param x1, double; x-coordinate of the first data point.
//	 * @param y1, double; y-coordinate of the first data point.
//	 * @param x2, double; x-coordinate of the second data point.
//	 * @param y2, double; y-coordinate of the second data point.
//	 * @return ArrayList<MyPoint>; a list containing the intersection point.
//	 */
//	public static ArrayList<MyPoint> getPointOfIntersection(double[] point1, double[] point2, double[] point3,
//			double[] point4) {
//		double slope1 = getSlope(point1[0], point1[1], point2[0], point2[1]);
//		double slope2 = getSlope(point3[0], point3[1], point4[0], point4[1]);
//		double inter1 = getIntercept(point1[0], point1[1], point2[0], point2[1]);
//		double inter2 = getIntercept(point3[0], point3[1], point4[0], point4[1]);
//		double intersectionX = (inter2 - inter1) / (slope1 - slope2);
//		return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(intersectionX, slope1 * intersectionX + inter1)));
//	}
//
//	/**
//	 * Calculates the intersection point of the line connecting the first two given
//	 * data points with the line connecting the last two given data points.
//	 * 
//	 * @param point1, MyPoint; first data point.
//	 * @param point2, MyPoint; second data point.
//	 * @param point3, MyPoint; third data point.
//	 * @param point4, MyPoint; fourth data point.
//	 * @return double; the intersection point of the line connecting the first two
//	 *         given data points with the line connecting the last two given data
//	 *         points.
//	 */
//	public static ArrayList<MyPoint> getPointOfIntersection(MyPoint point1, MyPoint point2, MyPoint point3,
//			MyPoint point4) {
//		double slope1 = getSlope(point1, point2);
//		double slope2 = getSlope(point3, point4);
//		double inter1 = getIntercept(point1, point2);
//		double inter2 = getIntercept(point3, point4);
//		double intersectionX = (inter2 - inter1) / (slope1 - slope2);
//		return new ArrayList<MyPoint>(Arrays.asList(new MyPoint(intersectionX, slope1 * intersectionX + inter1)));
//	}

}
