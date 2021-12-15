package application.gui;

import application.Globals;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class GuiLayout {

	public static Node createGuiBasicStructure() {
		BorderPane root = new BorderPane();

		// HBox menue = new HBox();

		SplitPane aiVisualisation = new SplitPane();
		// Pane aiTreeVisualisation = new Pane();

		Pane aiTreeVisualisation = new Pane();
		Globals.treeRepresentationGraphicsContext = aiTreeVisualisation;

		Pane aiStateVisualisation = new Pane();
		Globals.stateRepresentationGraphicsContext = aiStateVisualisation;

		HBox.setHgrow(aiTreeVisualisation, Priority.ALWAYS);
		HBox.setHgrow(aiStateVisualisation, Priority.ALWAYS);

		ZoomableScrollPaneOLD left = new ZoomableScrollPaneOLD(aiTreeVisualisation);
		

		ZoomableScrollPaneOLD rigth = new ZoomableScrollPaneOLD(aiStateVisualisation);
		

		aiVisualisation.getItems().addAll(left, rigth);
		root.setCenter(aiVisualisation);

		return root;
	}
}
