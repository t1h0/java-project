package de.tilmanhornung.java;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

	/**
	 * Launches the application.
	 * 
	 * @param args, String[]
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Starts the application (shows the primary stage)
	 * 
	 * @param primaryStage, Stage; the primary stage to use for the application.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create the root BorderPane
		BorderPane root = new BorderPane();

		// Create the Center Pane
		Pane center = new Pane();
		Rectangle2D screenProps = Screen.getPrimary().getBounds();
		for (int i = 0; i < Math.max(screenProps.getMaxX(), screenProps.getMaxY()); i = i + 10) {
			Line hLine = new Line(0, i, screenProps.getMaxX(), i);
			Line vLine = new Line(i, 0, i, screenProps.getMaxY());
			hLine.getStyleClass().add("grid");
			vLine.getStyleClass().add("grid");
			center.getChildren().add(hLine);
			center.getChildren().add(vLine);
		}

		// Create the Top Pane
		FlowPane top = new FlowPane();
		top.getStyleClass().add("menu");
		RadioButton lineBtn = new RadioButton("Line");
		RadioButton rectBtn = new RadioButton("Rectangle");
		RadioButton circBtn = new RadioButton("Circle");
		ToggleGroup shapeGroup = new ToggleGroup();
		CheckBox fillBox = new CheckBox("Fill");
		Button intersectBtn = new Button("Show Intersection");
		ColorPicker colPick = new ColorPicker(Color.BLACK);
		lineBtn.setToggleGroup(shapeGroup);
		rectBtn.setToggleGroup(shapeGroup);
		circBtn.setToggleGroup(shapeGroup);
		shapeGroup.selectToggle(lineBtn);
		top.getChildren().addAll(lineBtn, rectBtn, circBtn, fillBox, intersectBtn, colPick);

		// Create the Bottom Pane
		FlowPane bottom = new FlowPane();
		bottom.getStyleClass().add("menu");
		Button loadBtn = new Button("Load Data");
		Button saveBtn = new Button("Save Data");
		Button clearBtn = new Button("Clear Screen");
		bottom.getChildren().addAll(loadBtn, saveBtn, clearBtn);

		// Add Event-Handlers
		center.setOnMouseClicked(new MouseHandler(center, shapeGroup, fillBox, colPick));
		loadBtn.setOnAction(new BtnHandler(primaryStage, center, colPick));
		saveBtn.setOnAction(new BtnHandler(primaryStage, center));
		clearBtn.setOnAction(new BtnHandler(center));
		intersectBtn.setOnAction(new BtnHandler(center, colPick, intersectBtn));

		// Add Top, Center, Bottom to BorderPane
		root.setCenter(center);
		root.setTop(top);
		root.setBottom(bottom);

		// Setup scene
		Scene scene = new Scene(root, 500, 500);
		scene.getStylesheets().add("style.css");
		primaryStage.setTitle("PK1 - Project");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
