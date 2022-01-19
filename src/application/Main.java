package application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.cityState.CityState;
import ai_algorithm.problems.cityState.GermanyRouteProblem;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import ai_algorithm.search.BreadthFirstSearch;
import ai_algorithm.search.DepthFirstSearchExplored;
import ai_algorithm.search.SearchAlgorithm;
import application.gui.GuiLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Thread.currentThread().setPriority(10);
			
			Scene scene = new Scene((Parent) GuiLayout.createGuiBasicStructure(), 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			UpdateCycle updater = new UpdateCycle(); // <- not a thread
			updater.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
