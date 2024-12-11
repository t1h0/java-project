package de.tilmanhornung.java;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Circle;

public class MyPoint extends Circle{

	/**
	 * Creates a new data point with the given x and y value and radius 3.
	 * 
	 * @param x, double; the x coordinate.
	 * @param y, double; the y coordinate.
	 */
	public MyPoint(double x, double y) {
		this(x,y,3);
	}
	
	/**
	 * Creates a new data point with the given x and y value and given radius.
	 * 
	 * @param x, double; the x coordinate.
	 * @param y, double; the y coordinate.
	 * @param radius, double; the radius
	 */
	public MyPoint(double x, double y, double radius) {
		super(x, y, radius);
		Tooltip t = new Tooltip("[" + this.getX() + "," + this.getY() + "]");
		Tooltip.install(this, t);
	}

	/**
	 * Gets the x coordinate of this point.
	 * 
	 * @return double; the x coordinate of this point.
	 */
	public double getX() {
		return this.getCenterX();
	}

	/**
	 * Gets the y coordinate of this point.
	 * 
	 * @return double; the y coordinate of this point.
	 */
	public double getY() {
		return this.getCenterY();
	}

	/**
	 * Sets the x coordinate of this point.
	 * 
	 * @param x, double; the new x coordinate of this point.
	 */
	public void setX(double x) {
		this.setCenterX(x);
	}

	/**
	 * Sets the y coordinate of this point.
	 * 
	 * @param y, double; the new y coordinate of this point.
	 */
	public void setY(double y) {
		this.setCenterY(y);
	}

	/**
	 * Gets the coordinates of this point.
	 * 
	 * @return double[]; the coordinates of this point.
	 */
	public double[] getCoords() {
		return new double[] { this.getX(), this.getY() };
	}

	/**
	 * Overrides toString() from Object.
	 * Returns a String representation of the point.
	 * 
	 * @return String; "[x,y]"
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+this.getX()+","+this.getY()+"]";
	}

}
