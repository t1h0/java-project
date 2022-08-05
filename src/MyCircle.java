import javafx.scene.control.Tooltip;
import javafx.scene.shape.Circle;

public class MyCircle extends Circle{
	private MyPoint center;
	private double radius;

	/**
	 * Creates a new circle given a center and a point on the circle (therefore
	 * defining the radius).
	 * 
	 * @param center,        MyPoint; the center of the circle.
	 * @param pointOnCircle, MyPoint; a point on the circle, defining the radius.
	 */
	public MyCircle(MyPoint center, MyPoint pointOnCircle) {
		super(center.getX(),center.getY(),Utilities.getDistance(center, pointOnCircle),null);
		this.center = center;
		this.radius = Utilities.getDistance(this.center, pointOnCircle);
		Tooltip t = new Tooltip("Center: [" + this.getCenterX() + "," + this.getCenterY() + "]\nRadius: " + this.getRadius());
		Tooltip.install(this, t);
	}

	/**
	 * Gets the center of the circle.
	 * 
	 * @return MyPoint; the center of the circle.
	 */
	public MyPoint getCenter() {
		return this.center;
	}
	
	/**
	 * Overrides toString() from Object.
	 * Returns a String representation of the circle.
	 * 
	 * @return String; "Center: [x,y]
	 * 					Radius: .."
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Center: " + this.center + "\nRadius: "+this.radius;
	}
	
//	DEPRECATED
//	/**
//	 * Sets the radius of the circle.
//	 * 
//	 * @param radius, double; the new radius of the circle.
//	 */
//	public void setRadius(double radius) {
//		this.radius = radius;
//	}
//
//	/**
//	 * Gets the radius of the circle.
//	 * 
//	 * @return double; the radius of the circle.
//	 */
//	public double getRadius() {
//		return radius;
//	}

}
