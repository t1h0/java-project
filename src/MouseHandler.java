import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;

public class MouseHandler implements EventHandler<MouseEvent> {
	private Pane targetPane; // Pane to act on
	private ToggleGroup shapeOption; // Toggle Group with options for shape drawing
	private CheckBox fill; // Checkbox indicating if objects should be filled
	private ColorPicker color; // Defining the color of any drawn object.
	private Shape shape; // The object to draw
	private double radius = 3; // The radius of points
	private MyPoint startPoint; // Not null if a point was clicked

	/**
	 * Creates a new handler with a given root as reference
	 * 
	 * @param Pane root; the root to dynamically make changes on.
	 */
	public MouseHandler(Pane targetPane, ToggleGroup shapeOption, CheckBox fill, ColorPicker color) {
		this.targetPane = targetPane;
		this.shapeOption = shapeOption;
		this.fill = fill;
		this.color = color;

	}

	/**
	 * Handles mouse clicks and creates a circle at each clicked position.
	 * 
	 * @param e, MouseEvent; the event to handle.
	 */
	@Override
	public void handle(MouseEvent e) {
		if (this.startPoint != null) { // A first point was clicked
			switch (e.getEventType().getName()) {

			case "MOUSE_CLICKED": // second click was made
				int hit = checkHit(e, this.startPoint);
				if (hit != -1) {

					// (second) point was clicked
					MyPoint node = (MyPoint) this.targetPane.getChildren().get(hit);
					this.draw(node.getX(), node.getY());

				} else {

					// no second point was clicked
					this.targetPane.getChildren().remove(this.shape);

				}
				this.targetPane.setOnMouseMoved(null);
				this.startPoint = null;
				break;

			case "MOUSE_MOVED": // mouse was only moved

				this.draw(e.getX(), e.getY());
				break;

			}

		} else { // no first point was clicked
			int hit = this.checkHit(e);
			if (hit != -1) { // (first) point was clicked
				this.startPoint = (MyPoint) this.targetPane.getChildren().get(hit);

				switch (((RadioButton) this.shapeOption.getSelectedToggle()).getText()) { // Which shape to draw?

				case "Line":
					this.shape = new MyLine();
					this.draw(this.startPoint.getX(), this.startPoint.getY());
					break;
				case "Rectangle":
					this.shape = new MyRectangle();
					this.draw(this.startPoint.getX(), this.startPoint.getY());
					break;
				case "Circle":
					this.shape = new MyCircle(this.startPoint, this.startPoint);
					break;
				}

				// Set color and fill according to user
				this.shape.setStroke(this.color.getValue());
				if (this.fill.isSelected()) {
					this.shape.setFill(this.color.getValue());
					;
				}
				this.targetPane.getChildren().add(shape);
				this.targetPane.setOnMouseMoved(this); // draw objects while moving the mouse
			} else { // no point was clicked
				MyPoint point = new MyPoint(e.getX(), e.getY(), this.radius);
				point.setFill(this.color.getValue());
				point.setStroke(this.color.getValue());
				this.targetPane.getChildren().add(point); // adding a point to mouse location
			}

		}
	}

	/**
	 * Checks if a already existing point was clicked.
	 * 
	 * @param e, MouseEvent; the event containing the click coordinates.
	 * @return int; the index of the point in the target panes' childrens' list of
	 *         the hit point or -1 if not point was hit.
	 */
	private int checkHit(MouseEvent e) {
		for (int i = 0; i < this.targetPane.getChildren().size(); i++) {
			Node node = this.targetPane.getChildren().get(i);
			if (node instanceof MyPoint && ((MyPoint) node).getX() - this.radius <= e.getX() // Point was clicked
					&& ((MyPoint) node).getX() + radius >= e.getX() && ((MyPoint) node).getY() - radius <= e.getY()
					&& ((MyPoint) node).getY() + radius >= e.getY()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if a already existing point was clicked but excludes a certain point
	 * (i.e. the point that was previously clicked).
	 * 
	 * @param e,        MouseEvent; the event containing the click coordinates.
	 * @param refPoint, MyPoint; the point to exclude from looking for a hit.
	 * @return int; the index of the point in the target panes' childrens' list of
	 *         the hit point or -1 if not point was hit.
	 */
	private int checkHit(MouseEvent e, MyPoint refPoint) {
		int hit = this.checkHit(e);
		if (hit != -1 && this.targetPane.getChildren().get(hit) != refPoint) {
			return hit;
		}
		return -1;
	}

	/**
	 * Draws the current this.shape at the given location.
	 * 
	 * @param x, double; x-coordinate to use as a second reference to draw the
	 *           object (first reference is this.startPoint).
	 * @param y, double; y-coordinate to use as a second reference to draw the
	 *           object (first reference is this.startPoint).
	 */
	private void draw(double x, double y) {
		Rectangle2D screenProps = Screen.getPrimary().getBounds();
		switch (((RadioButton) this.shapeOption.getSelectedToggle()).getText()) { // Which shape to draw?
		case "Line":
			MyLine line = (MyLine) this.shape;
			double slope = Utilities.getSlope(this.startPoint.getCenterX(), this.startPoint.getCenterY(), x, y);
			double inter = Utilities.getIntercept(this.startPoint.getCenterX(), this.startPoint.getCenterY(), x, y);
			if (Double.isInfinite(slope)) { // vertical line?
				line.setStartX(x);
				line.setStartY(0);
				line.setEndX(x);
				line.setEndY(screenProps.getMaxY());
			} else {
				line.setStartX(0);
				line.setStartY(inter);
				line.setEndX(screenProps.getMaxX());
				line.setEndY(slope * screenProps.getMaxX() + inter);
			}
			break;
		case "Rectangle":
			MyRectangle rect = (MyRectangle) this.shape;
			rect.setPosition(this.startPoint, new MyPoint(x, y));
			break;
		case "Circle":
			MyCircle circle = (MyCircle) this.shape;
			circle.setRadius(Utilities.getDistance(this.startPoint, new MyPoint(x, y)));
			break;
		}
	}

}
