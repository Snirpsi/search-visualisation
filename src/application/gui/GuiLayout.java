package application.gui;

import ai_algorithm.SearchNodeMetadataObject;
import application.Globals;
import application.SearchThreadRegistryAndFactory;
import application.debugger.DebuggerUI;
import ecs.GameObjectRegistry;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
/**
 * creates the javaFX specific userinterface
 * @author Severin
 *
 */
public class GuiLayout {

	public static ComboBox<String> problemSelect = new ComboBox<>();
	public static ComboBox<String> algoSelect = new ComboBox<>();

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

		ZoomableScrollPane left = new ZoomableScrollPane(aiTreeVisualisation);

		ZoomableScrollPane rigth = new ZoomableScrollPane(aiStateVisualisation);

		aiVisualisation.getItems().addAll(left, rigth);

		root.setCenter(aiVisualisation);

		HBox topMenue = new HBox();
		// Resetbutton
		Button resetButton = new Button("RESET");
		resetButton.setOnAction(e -> {
			SearchThreadRegistryAndFactory.stopAllThreads();
			GameObjectRegistry.removeAllGameObjects();
			SearchNodeMetadataObject.reset();
		});
		topMenue.getChildren().add(resetButton);
		// Strart Button
		Button startButton = new Button("START");
		startButton.setOnAction(e -> {
			SearchThreadRegistryAndFactory.stopAllThreads();
			GameObjectRegistry.removeAllGameObjects();
			SearchNodeMetadataObject.reset();
			SearchThreadRegistryAndFactory.startSearchIfReady();
		});
		topMenue.getChildren().add(startButton);

		problemSelect.getItems().addAll(SearchThreadRegistryAndFactory.getProblemNames());
		topMenue.getChildren().add(problemSelect);

		/**
		 * When a MapColoringProblem is selected the algorithm selection is updated with the CSP algorithms
		 * Otherwise the normal search algorithms are shown
		 */
		problemSelect.setOnAction(e -> {
			if (problemSelect.getValue().equals("ai_algorithm.problems.mapColoring.MapColoringProblem")) {
				algoSelect.getItems().clear();
				algoSelect.getItems().add("ai_algorithm.search.DepthFirstSearch");
				algoSelect.getItems().addAll(SearchThreadRegistryAndFactory.getCspSearchAlgoritmNames());
			} else {
				algoSelect.getItems().clear();
				algoSelect.getItems().addAll(SearchThreadRegistryAndFactory.getSearchAlgoritmNames());
			}
		});

//		algoSelect.getItems().addAll(SearchThreadRegistryAndFactory.getSearchAlgoritmNames());
		topMenue.getChildren().add(algoSelect);

		var debugg = new DebuggerUI();
		topMenue.getChildren().add(debugg.getUiElements());

		root.setTop(topMenue);

		// console layout
		var cons = debugg.getConsole();
		cons.maxHeight(300);
		cons.minHeight(100);
		root.setBottom(cons);
		return root;
	}
}

/*
 * Copyright (c) 2022 Severin Dippold
 * Copyright (c) 2024 Alexander Ultsch
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
