import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;

public class MyRectangle extends Rectangle {

	// corner coordinates
	private MyPoint bottomLeft;
	private MyPoint topLeft;
	private MyPoint topRight;
	private MyPoint bottomRight;

	/**
	 * Creates an empty rectangle.
	 */
	public MyRectangle() {
		this.setFill(null);
	}

	/**
	 * Creates a new rectangle given two points defining the diagonal.
	 * 
	 * @param point1, MyPoint; the first point.
	 * @param point2, MyPoint; the second point.
	 */
	public MyRectangle(MyPoint point1, MyPoint point2) {
		this();
		this.setPosition(point1, point2);
	}

	/**
	 * Gets the bottom left point of the rectangle.
	 * 
	 * @return MyPoint; the bottom left point of the rectangle.
	 */
	public MyPoint getBottomLeft() {
		return this.bottomLeft;
	}

	/**
	 * Gets the bottom right point of the rectangle.
	 * 
	 * @return MyPoint; the bottom right point of the rectangle.
	 */
	public MyPoint getBottomRight() {
		return this.bottomRight;
	}

	/**
	 * Gets the top left point of the rectangle.
	 * 
	 * @return MyPoint; the top left point of the rectangle.
	 */
	public MyPoint getTopLeft() {
		return this.topLeft;
	}

	/**
	 * Gets the top right point of the rectangle.
	 * 
	 * @return MyPoint; the top right point of the rectangle.
	 */
	public MyPoint getTopRight() {
		return this.topRight;
	}

	/**
	 * Sets the position of the rectangle (defines the corners), given two points
	 * defining the diagonal.
	 * 
	 * @param point1, MyPoint; first point.
	 * @param point2, MyPoint; second point.
	 */
	public void setPosition(MyPoint point1, MyPoint point2) {
		// set corners according to positions of starting points.
		if (point1.getX() > point2.getX()) {
			if (point1.getY() < point2.getY()) {
				this.topRight = point1;
				this.bottomLeft = point2;
				this.topLeft = new MyPoint(this.bottomLeft.getX(), this.topRight.getY());
				this.bottomRight = new MyPoint(this.topRight.getX(), this.bottomLeft.getY());
			} else {
				this.topLeft = point2;
				this.bottomRight = point1;
				this.topRight = new MyPoint(this.bottomRight.getX(), this.topLeft.getY());
				this.bottomLeft = new MyPoint(this.topLeft.getX(), this.bottomRight.getY());
			}
		} else {
			if (point1.getY() < point2.getY()) {
				this.topLeft = point1;
				this.bottomRight = point2;
				this.topRight = new MyPoint(this.bottomRight.getX(), this.topLeft.getY());
				this.bottomLeft = new MyPoint(this.topLeft.getX(), this.bottomRight.getY());
			} else {
				this.topRight = point2;
				this.bottomLeft = point1;
				this.topLeft = new MyPoint(this.bottomLeft.getX(), this.topRight.getY());
				this.bottomRight = new MyPoint(this.topRight.getX(), this.bottomLeft.getY());
			}
		}
		this.setX(this.topLeft.getX());
		this.setY(this.topLeft.getY());
		this.setWidth(Math.abs(this.topLeft.getX() - this.topRight.getX()));
		this.setHeight(Math.abs(this.topLeft.getY() - this.bottomLeft.getY()));
		Tooltip t = new Tooltip("Width: " + this.getWidth() + "\nHeight: " + this.getHeight());
		Tooltip.install(this, t);
	}

	/**
	 * Overrides toString() from Object. Returns a String representation of the
	 * points of the rectangle.
	 * 
	 * @return String; "[x,y] [x,y] [x,y] [x,y]"
	 */
	@Override
	public String toString() {
		String space = "";
		for (int i = 0; i < Math.max(topLeft.toString().length(), bottomLeft.toString().length()); i++) {
			space = space + " ";
		}
		if (topLeft.toString().length() > bottomLeft.toString().length()) {
			return this.topLeft + " __ " + this.topRight + "\n" + space + "|  |\n"
					+ space.substring(0, topLeft.toString().length() - bottomLeft.toString().length()) + this.bottomLeft
					+ " \u203e " + this.bottomRight;
		}
		return space.substring(0, bottomLeft.toString().length() - topLeft.toString().length()) + this.topLeft + " __ "
				+ this.topRight + "\n" + space + "|  |\n" + this.bottomLeft + " \u203e\u203e " + this.bottomRight;

	}

//	DEPRECATED 
//	/**
//	 * Gets the height of the rectangle.
//	 * 
//	 * @return double; the height of the rectangle.
//	 */
//	public double getHeight() {
//		return height;
//	}

//	/**
//	 * Sets the height of the rectangle.
//	 */
//	private void setHeight() {
//		this.width = Math.abs(this.bottomLeft.getY() - this.topLeft.getY());
//	}

//	/**
//	 * Gets the width of the rectangle.
//	 * 
//	 * @return double; the width of the rectangle.
//	 */
//	public double getWidth() {
//		return width;
//	}

//	/**
//	 * Sets the width of the rectangle.
//	 */
//	private void setWidth() {
//		this.width = Math.abs(this.bottomLeft.getX() - this.bottomRight.getX());
//	}

//	/**
//	 * Recreates the rectangle with the given two new points defining the diagonal.
//	 * 
//	 * @param point1, MyPoint; the first point.
//	 * @param point2, MyPoint; the second point.
//	 */
//	public void setDiagonal(MyPoint point1, MyPoint point2) {
//		if ((point1.getX() - point2.getX()) + (point1.getY() - point2.getY()) < 0) {
//			this.bottomLeft = point1;
//			this.topRight = point2;
//		} else {
//			this.bottomLeft = point2;
//			this.topRight = point1;
//		}
//		this.setPosition();
//		this.setWidth();
//		this.setHeight();
//	}
}
