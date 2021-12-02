package application.gui;

import application.Globals;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class GuiLayout {

	public static Node createGuiBasicStructure() {
		BorderPane root = new BorderPane();

		// HBox menue = new HBox();

		HBox aiVisualisation = new HBox();
		Pane aiTreeVisualisation = new Pane();
		Globals.treeRepresentationGraphicsContext = aiTreeVisualisation;

		Pane aiStateVisualisation = new Pane();
		Globals.stateRepresentationGraphicsContext = aiStateVisualisation;

		HBox.setHgrow(aiTreeVisualisation, Priority.ALWAYS);
		HBox.setHgrow(aiStateVisualisation, Priority.ALWAYS);

		Pane left = new Pane(new ZoomableScrollPaneOLD(aiTreeVisualisation));
		left.setPrefSize(10000, 10000);

		Pane rigth = new Pane(new ZoomableScrollPaneOLD(aiStateVisualisation));
		rigth.setPrefSize(10000, 10000);

		aiVisualisation.getChildren().addAll(left, rigth);
		root.setCenter(aiVisualisation);

		return root;
	}
}
