package ecs.components;

import ecs.Component;

import tools.Vector2D;

/**
 * adding animation component to entity enables positional animations
 * 
 * @author Severin
 *
 */
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
			// animation just finished
			startPos = endPos;
			// make sure that Entitie has arrived at exactly the right position

		}
		if (Math.abs(startPos.distanceTo(endPos)) < 0.0001) {
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