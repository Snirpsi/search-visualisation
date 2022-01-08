package ecs.components;

import java.util.function.Function;

import ecs.Component;
import javafx.event.Event;

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

		System.out.println("HANDLE EVENT" + event);
		/*
		 * System.out.println("Handle event");
		 * System.out.println(this.componentAssignableObject);
		 * 
		 * this.componentAssignableObject.getComponent(Graphics.class).clicked();
		 * 
		 * this.componentAssignableObject.getComponent(StateRepresentationGraphics.class
		 * ).showStateRepresentation();
		 */
		this.action.apply(event);
	}
}
