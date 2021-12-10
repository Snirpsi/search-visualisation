package application;

import ai_algorithm.BreadthFirstSearch;
import ai_algorithm.RasterPathProblem;

import application.gui.GuiLayout;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene((Parent) GuiLayout.createGuiBasicStructure(), 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			
			RasterPathProblem problem = new RasterPathProblem();

			BreadthFirstSearch searchi = new BreadthFirstSearch(problem);

			SearchThread s = new SearchThread(searchi);
			s.start();

			UpdateCycle updater = new UpdateCycle();
			updater.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
