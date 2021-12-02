package application;

import ecs.GameObject;
import javafx.animation.AnimationTimer;

public class UpdateCycle {
	// grafischer gameloop (javaFX)
	AnimationTimer animationTimer;

	// Suchalgorithmus gameloop
	// SearchTread searchAlgorithm;

	public UpdateCycle() {
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

				for (GameObject rootGameObjects : UpdateRegistry.gameObjectRegistry) {
					rootGameObjects.update(deltaT);
				}
				UpdateRegistry.initializePendingGameObjects();

				UpdateRegistry.largeUpdatePendingComponents();
			}
		};
	}

	public void start() {
		this.animationTimer.start();
	}
}
