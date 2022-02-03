package application.debugger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import settings.Settings;
/**
 * javafx specific ui connector 
 * @author Severin
 *
 */
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

		Slider pauseTime = new Slider(Settings.DEBUGGER_MINIMUM_TIME_DELAY, Settings.DEBUGGER_MAXIMUM_TIME_DELAY, 1);
		pauseTime.setMaxWidth(300);
		pauseTime.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Debugger.setPauseTime(newValue.doubleValue());
			}
		});

		controlElements.getChildren().addAll(step, autostep, pauseTime);
		Debugger.connectToUi(this); // << wegen der zeile geht alle kaputt warum ??? weil javafx nicht thread save
									// ist

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


/*
 * Copyright (c) 2022 Severin Dippold
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