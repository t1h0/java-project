package de.tilmanhornung.java;
import javafx.scene.shape.Line;

public class MyLine extends Line{
	private MyPoint point1; // the first point the line was initiated with.
	private MyPoint point2; // the second point the line was initiated with.
	
	/**
	 * Creates an empty line.
	 */
	public MyLine() {
	}

	/**
	 * Creates a new line through two given points and calculates slope and
	 * intercept of this line.
	 * 
	 * @param point1, MyPoint; first data point.
	 * @param point2, MyPoint; second data point.
	 */
	public MyLine(MyPoint point1, MyPoint point2) {
		super(point1.getX(),point1.getY(),point2.getX(),point2.getY());
		this.point1 = point1;
		this.point2 = point2;
	}

	/**
	 * Gets the slope of this line.
	 *
	 * @return double; the slope of this line.
	 */
	public double getSlope() {
		return Utilities.getSlope(this.getStartX(), this.getStartY(), this.getEndX(), this.getEndY());
	}

	/**
	 * Gets the intercept of this line.
	 *
	 * @return double; the intercept of this line.
	 */
	public double getIntercept() {
		return Utilities.getIntercept(this.getStartX(), this.getStartY(), this.getEndX(), this.getEndY());
	}

	/**
	 * Gets the y coordinate of the line to a given x coordinate.
	 *
	 * @param x, double; the x coordinate to compute the corresponding y coordinate to.
	 * @return double; the corresponding y coordinate.
	 */
	public double getY(double x) {
		return this.getSlope() * x + this.getIntercept();
	}

	/**
	 * Gets the first point, the line was initiated with (useful for example for line segments).
	 *
	 * @return MyPoint; the first point, the line was initiated with.
	 */
	public MyPoint getPoint1() {
		return this.point1;
	}
	
	/**
	 * Gets the second point, the line was initiated with (useful for example for line segments).
	 *
	 * @return MyPoint; the second point, the line was initiated with.
	 */
	public MyPoint getPoint2() {
		return this.point2;
	}
	
	/**
	 * Overrides toString() from Object.
	 * Returns a String representation of the line.
	 * 
	 * @return String; "y=mx+b"
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "y = "+this.getSlope()+"x"+" + "+this.getIntercept();
	}
}
