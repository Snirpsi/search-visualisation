package application;

import application.debugger.Debugger;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import javafx.animation.AnimationTimer;

public class MainLoop {
	// grafischer gameloop (javaFX)
	AnimationTimer animationTimer;

	// Suchalgorithmus gameloop
	// SearchTread searchAlgorithm;

	public MainLoop() {
		animationTimer = new AnimationTimer() {

			@SuppressWarnings("unused")
			long framecounter = 0;
			long frameTimeLast = System.currentTimeMillis();
			long frameTimeThis = System.currentTimeMillis();
			float deltaT = 0;

			@Override
			public void handle(long now) {
				// Globals.fps = 0;
				framecounter++;
				frameTimeLast = frameTimeThis;
				frameTimeThis = System.currentTimeMillis();

				// Time between frames (may be used by physics systems)
				deltaT = (frameTimeThis - frameTimeLast) / 1000.0f;

				Globals.fps = (int) (1 / deltaT);

				// Initialize
				GameObjectRegistry.initializePendingGameObjects();
				// Change
				GameObjectRegistry.changePendingGameobjects();
// update
				for (GameObject rootGameObjects : GameObjectRegistry.getGameObjectRegistry()) {
					rootGameObjects.update(deltaT);
				}

				if (Debugger.getDebuggerUI() != null) {
					Debugger.getDebuggerUI().updateText();
				}
			}
		};
	}

	public void start() {
		this.animationTimer.start();
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
