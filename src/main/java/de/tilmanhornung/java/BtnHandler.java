package de.tilmanhornung.java;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BtnHandler implements EventHandler<ActionEvent> {
	private String action; // the action the button should do
	private Stage targetStage; // the stage to act on (for displaying file loading window)
	private Pane targetPane; // the pane to act on
	private ArrayList<MyPoint> intersections; // intersections between shapes are saved here
	private boolean showIntersections = true; // indicates the state of the intersections button
	private ColorPicker color; // the color to draw new points and the intersections in
	private Button targetBtn; // the button to act on (for changing the intersection button's state)

	/**
	 * Creates a Button Handler for the loading button.
	 * 
	 * @param targetStage, Stage; the stage to act on (to show a file loading
	 *                     window).
	 * @param targetPane,  Pane; the pane to act on.
	 * @param color,       ColorPicker; the colorpicker which defines the color the
	 *                     drawn points.
	 */
	public BtnHandler(Stage targetStage, Pane targetPane, ColorPicker color) {
		this.targetStage = targetStage;
		this.targetPane = targetPane;
		this.color = color;
		this.action = "load";
	}
	
	/**
	 * Creates a Button Handler for the saving button.
	 * 
	 * @param targetStage, Stage; the stage to act on (to show a file loading
	 *                     window).
	 * @param targetPane,  Pane; the pane to act on.
	 */
	public BtnHandler(Stage targetStage, Pane targetPane) {
		this.targetStage = targetStage;
		this.targetPane = targetPane;
		this.action = "save";
	}

	/**
	 * Creates a new Button Handler for the Clear Window button.
	 * 
	 * @param targetPane, Pane; the pane to act on.
	 */
	public BtnHandler(Pane targetPane) {
		this.targetPane = targetPane;
		this.action = "clear";
	}

	/**
	 * Creates a new Button Handler for the intersection button.
	 * 
	 * @param targetPane, Pane; the pane to act on.
	 * @param color,      ColorPicker; the colorpicker which defines the color the
	 *                    drawn points.
	 * @param targetBtn,  Button; the intersections button (for changing its state
	 *                    from show to hide and vice versa).
	 */
	public BtnHandler(Pane targetPane, ColorPicker color, Button targetBtn) {
		this.targetPane = targetPane;
		this.action = "intersection";
		this.intersections = new ArrayList<MyPoint>();
		this.color = color;
		this.targetBtn = targetBtn;
	}

	/**
	 * Handles the ActionEvent of the button.
	 * 
	 * @param e, ActionEvent; the event to handle.
	 */
	@Override
	public void handle(ActionEvent e) {
		switch (this.action) {
		case "load":
			this.load();
			break;
		case "save":
			this.save();
			break;
		case "clear":
			this.clear();
			break;
		case "intersection":
			this.intersect();
			break;
		}
	}

	/**
	 * Loads new data points from a file.
	 */
	private void load() {
		// Show file window.
		FileChooser fileChooser = new FileChooser();
	    fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv - Comma-separated values", "*.csv"));
		fileChooser.setTitle("Open File with Points");
		File file = fileChooser.showOpenDialog(this.targetStage);
		// Load the file
		ArrayList<MyPoint> newPoints = Utilities.loadMyPoints(file);
		// Create the points.
		Iterator<MyPoint> iter = newPoints.iterator();
		while (iter.hasNext()) {
			MyPoint point = iter.next();
			point.setFill(this.color.getValue());
			point.setStroke(this.color.getValue());
			targetPane.getChildren().add(point);
		}
	}
	
	/**
	 * Save data points to a file.
	 */
	private void save() {
		// Show file window.
		FileChooser fileChooser = new FileChooser();
	    fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv - Comma-separated values", "*.csv"));
		fileChooser.setTitle("Save points to file");
		File file = fileChooser.showSaveDialog(this.targetStage);
		// Save the file
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(file));
			for(Node i:this.targetPane.getChildren()) {
				if(i instanceof MyPoint) {
					MyPoint point = (MyPoint) i;
					out.write((int) point.getX() + "," + (int) point.getY() + "\n");
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clears all drawn objects from the workspace.
	 */
	private void clear() {
		this.targetPane.getChildren().clear();
		// Reset the grid.
		Rectangle2D screenProps = Screen.getPrimary().getBounds();
		for (int i = 0; i < Math.max(screenProps.getMaxX(), screenProps.getMaxY()); i = i + 10) {
			Line hLine = new Line(0, i, screenProps.getMaxX(), i);
			Line vLine = new Line(i, 0, i, screenProps.getMaxY());
			hLine.getStyleClass().add("grid");
			vLine.getStyleClass().add("grid");
			this.targetPane.getChildren().add(hLine);
			this.targetPane.getChildren().add(vLine);
		}
	}

	/**
	 * Shows or hides all intersections of objects on the screen.
	 */
	private void intersect() {
		if (this.showIntersections) { // Show intersections
			for (int i = 0; i < this.targetPane.getChildren().size(); i++) {
				Node child1 = this.targetPane.getChildren().get(i);
				if (this.isMyObject(child1)) {
					for (int j = i + 1; j < this.targetPane.getChildren().size(); j++) {
						Node child2 = this.targetPane.getChildren().get(j);
						if (this.isMyObject(child2)) {
							ArrayList<MyPoint> inter = Utilities.getPointOfIntersection(child1, child2);
							for (MyPoint point : inter) {
								point.setFill(this.color.getValue());
								point.setStroke(this.color.getValue());
								this.intersections.add(point);

							}
						}
					}
				}
			}
			this.targetPane.getChildren().addAll(this.intersections);
			this.showIntersections = false;
			this.targetBtn.setText("Hide Intersection");
		} else { // hide intersections
			this.targetPane.getChildren().removeAll(this.intersections);
			this.intersections.clear();
			this.showIntersections = true;
			this.targetBtn.setText("Show Intersection");
		}
	}

	/**
	 * Checks whether a node is an instance of MyCircle, MyLine or MyRectangle.
	 * 
	 * @param n, Node; the node to check.
	 * @return boolean; true if node is an instance of MyCircle, MyLine or
	 *         MyRectangle, false otherwise.
	 */
	private boolean isMyObject(Node n) {
		return (n instanceof MyLine || n instanceof MyRectangle || n instanceof MyCircle);
	}

}
