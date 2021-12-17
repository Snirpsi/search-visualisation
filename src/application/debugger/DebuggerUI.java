package application.debugger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class DebuggerUI {
	private HBox controlElements;
	private TextArea console;


	public DebuggerUI() {
		controlElements = new HBox();
		console = new TextArea();
		
		
		
		Button step = new Button("STEP");
		step.setOnAction(e -> {
			Debugger.resume();
			console.setText(Debugger.getConsoleText());
		});

		ToggleButton autostep = new ToggleButton("Auto");

		autostep.setOnAction(e -> {
			if (autostep.isSelected()) {
				Debugger.autostepEnable();
				Debugger.resume();
			} else {
				Debugger.autostepDisable();
			}

		});

		Slider pauseTime = new Slider(0.1, 10, 1);
		pauseTime.setMaxWidth(300);
		pauseTime.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Debugger.setPauseTime(newValue.doubleValue());
			}
		});

		controlElements.getChildren().addAll(step, autostep, pauseTime);
	}

	public Node getUiElements() {
		return controlElements;
	}

	public Node getConsole() {
		return console;

	}

}
