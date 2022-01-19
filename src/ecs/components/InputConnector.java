package ecs.components;

import ecs.GameObject;
import javafx.event.Event;
import javafx.event.EventHandler;

public class InputConnector {

	public static EventHandler<Event> getInputConnector(GameObject o) {
		return e -> {
			o.getComponent(InputHandler.class).handle(e);
		};
	}

}
