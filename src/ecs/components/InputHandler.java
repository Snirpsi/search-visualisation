package ecs.components;

import java.util.function.Function;

import ecs.Component;
import javafx.event.Event;
import settings.Settings;

public class InputHandler extends Component {

	Function<Event, ?> action = null;

	public InputHandler(Function<Event, ?> action) {
		this.action = action;
	}

	@Override
	public void update(float deltaT) {

	}

	public void handle(Event event) {
		if (event == null) {
			this.action.apply(event);
		}
		if (Settings.DEBUGMODE)
			System.out.println("HANDLE EVENT" + event);
		this.action.apply(event);
	}
}
