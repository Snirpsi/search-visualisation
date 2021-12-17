package application;

import ai_algorithm.problems.cityState.GermanyRouteProblem;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import ai_algorithm.search.BreadthFirstSearch;
import ai_algorithm.search.DepthFirstSearch;
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
			GermanyRouteProblem grp = new GermanyRouteProblem("Berlin", "Hamburg");

			DepthFirstSearch searchiDepth = new DepthFirstSearch(problem);
			BreadthFirstSearch searchiBreadth = new BreadthFirstSearch(problem);

			SearchThread s = new SearchThread(searchiBreadth);

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
