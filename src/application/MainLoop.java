package application;

import application.debugger.Debugger;
import application.debugger.DebuggerUI;
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

				GameObjectRegistry.initializePendingGameObjects();

				GameObjectRegistry.changePendingGameobjects();

				for (GameObject rootGameObjects : GameObjectRegistry.gameObjectRegistry) {
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
