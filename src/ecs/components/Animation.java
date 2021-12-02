package ecs.components;

import ecs.Component;

import tools.Vector2D;

public class Animation extends Component {

	Vector2D startPos = new Vector2D();
	Vector2D endPos = new Vector2D();

	double duration = 1.0;
	double timeUntilEnd = 1.0;

	public Animation() {
	}

	public Animation(double duration) {
		this.setDuration(duration);
	}

	public void setDuration(double duration) {
		this.duration = duration;
		this.timeUntilEnd = duration;
	}

	public void setEndPos(Vector2D endPos) {
		this.endPos = endPos;
		// this.duration = 1.0;
		this.timeUntilEnd = this.duration;
	}

	public void setEndPos(Vector2D endPos, double duration) {
		this.endPos = endPos;
		this.duration = duration;
		this.timeUntilEnd = duration;
	}

	@Override
	public void update(float deltaT) {
		
		
		if (timeUntilEnd <= deltaT) {
			// animation gerade beendet
			startPos = endPos;
			// sicherstellen, dass Entitie genau an der richtigen Position angekommen ist
			
		}
		if (Math.abs( startPos.distanceTo(endPos)) < 0.0001 ) {
//			startPos = endPos;
//			this.entity.getComponent(Position.class).directSetPosition(endPos);
//			return;
		}
		
		if (startPos == endPos) {
			// Animation beendet
			timeUntilEnd = duration; // reset timer
			return;
		}

		
		var vneu = startPos.interpolate(endPos, timeUntilEnd / duration);
		this.entity.getComponent(Position.class).directSetPosition(vneu);
		this.timeUntilEnd -= deltaT;
	}

	@Override
	public void fetchDependencies() {
	}
}
