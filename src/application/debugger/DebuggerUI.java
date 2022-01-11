package application.debugger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class DebuggerUI {
	private HBox controlElements;
	private TextArea console;
	private boolean textChanged;

	public DebuggerUI() {
		controlElements = new HBox();
		console = new TextArea();

		Button step = new Button("STEP");
		step.setOnAction(e -> {
			Debugger.resume();
		});

		ToggleButton autostep = new ToggleButton("AUTO");

		autostep.setOnAction(e -> {
			if (autostep.isSelected()) {
				Debugger.autostepEnable();
			} else {
				Debugger.autostepDisable();
			}

		});

		Slider pauseTime = new Slider(0.1, 2, 1);
		pauseTime.setMaxWidth(300);
		pauseTime.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Debugger.setPauseTime(newValue.doubleValue());
			}
		});

		controlElements.getChildren().addAll(step, autostep, pauseTime);
		Debugger.connectToUi(this); // << wegen der zeile geht alle kaputt warum ??? weil javafx nicht thread save ist

	}

	public Node getUiElements() {
		return controlElements;
	}

	public Node getConsole() {
		return console;
	}

	public void notifyTextChange() {
		this.textChanged = true;
	}

	// true if text has changed since last call
	private boolean hasTextChanged() {
		boolean ret = textChanged;
		textChanged = false;
		return ret;

	}

	public void updateText() {
		if (hasTextChanged()) {
			console.setText(Debugger.getConsoleText());
			console.setScrollTop(Double.MAX_VALUE);
		}
	}

}
