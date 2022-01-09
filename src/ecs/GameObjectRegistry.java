package ecs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ecs.visitors.GameObjectChangedVisitor;
import ecs.visitors.InitialisationVisitor;
import ecs.visitors.Visitor;

/**
 * This class allows the Framework to manage all GameObjects whether they need
 * to be initialized or changed. This is achieved using {@link Visitor}s that
 * are applied to the {@link GameObject}s.
 * 
 * @author Severin
 *
 */
public class GameObjectRegistry {

	public static List<GameObject> gameObjectRegistry = Collections.synchronizedList(new LinkedList<>());
	public static List<GameObject> largeSingleUpdate = Collections.synchronizedList(new LinkedList<GameObject>());
	public static List<GameObject> needsGameObjectInitialisation = Collections
			.synchronizedList(new ArrayList<GameObject>());

	public static InitialisationVisitor initialisationVisitor = new InitialisationVisitor();
	public static GameObjectChangedVisitor componentUpdateVisitor = new GameObjectChangedVisitor();

	/**
	 * allows new {@link GameObject}to be registered to the framework for
	 * initialization and representation. All {@link GameObject} are managed by this
	 * central structure.
	 * 
	 * @param gameObject The {@link GameObject} to be registered.
	 */
	public static void register(GameObject gameObject) {
		needsGameObjectInitialisation.add(gameObject);
	}

	/**
	 * Initializes all pending objects with the {@link InitialisationVisitor}. It
	 * should only be called by the main GameLoop.
	 */
	public static void initializePendingGameObjects() {
		while (!needsGameObjectInitialisation.isEmpty()) {
			GameObject gameObject = needsGameObjectInitialisation.remove(0);
			gameObject.accept(initialisationVisitor);// initialisieren
			gameObjectRegistry.add(gameObject);
			gameObject.start();
		}
	}

	/**
	 * Registers a {@link GameObject} when the {@link GameObject}'s state has been
	 * changed and its {@link Component}s need to change. should be called in the
	 * AI-Implementation of the {@link GameObject} when the state changes.
	 * 
	 */
	public static void registerForStateChange(GameObject gameObject) {
		largeSingleUpdate.add(gameObject);
	}

	/**
	 * Applies all changes to the pending {@link GameObject} by applying the
	 * {@link GameObjectChangedVisitor} to it.
	 */
	public static void changePendingGameobjects() {
		while (!largeSingleUpdate.isEmpty()) {
			GameObject gameObject = largeSingleUpdate.remove(0);
			gameObject.accept(componentUpdateVisitor);
		}
	}

	/**
	 * This function allows to search the {@link GameObjectRegistry} for all objects
	 * of an given type.
	 * 
	 * @param <T>  The class of the {@link GameObject}.
	 * @param type The Class of the {@link GameObject}.
	 * @return A list of {@link GameObject}s with the associated type.
	 */
	public static <T extends GameObject> List<T> getAllGameObjectsOfType(Class<T> type) {
		List<T> ret = new LinkedList<T>();

		for (GameObject gameObject : gameObjectRegistry) {
			if (gameObject.getClass().isAssignableFrom(type)) {
				ret.add(type.cast(gameObject));
			}
		}
		return ret;
	}

}
