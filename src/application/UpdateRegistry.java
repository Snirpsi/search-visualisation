package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ecs.GameObject;
import ecs.visitors.ComponentUpdateVisitor;
import ecs.visitors.InitialisationVisitor;

public class UpdateRegistry {

	public static List<GameObject> gameObjectRegistry = Collections.synchronizedList(new LinkedList<>());
	public static List<GameObject> largeSingleUpdate = Collections.synchronizedList(new LinkedList<GameObject>());
	public static List<GameObject> needsGameObjectInitialisation = Collections
			.synchronizedList(new ArrayList<GameObject>());

	public static InitialisationVisitor initialisationVisitor = new InitialisationVisitor();
	public static ComponentUpdateVisitor componentUpdateVisitor = new ComponentUpdateVisitor();

	public static void register(GameObject gameObject) {
		// synchronized (gameObjectRegistry) {
		needsGameObjectInitialisation.add(gameObject);
		// }

	}

	public static void initializePendingGameObjects() {
		while (!needsGameObjectInitialisation.isEmpty()) {
			GameObject gameObject = needsGameObjectInitialisation.remove(0);
			gameObject.accept(initialisationVisitor);// initialisieren
			gameObjectRegistry.add(gameObject);
		}
	}

	public static void registerForLargeComponentUpdate(GameObject gameObject) {
		largeSingleUpdate.add(gameObject);
	}

	public static void largeUpdatePendingComponents() {
		while (!largeSingleUpdate.isEmpty()) {
			GameObject gameObject = largeSingleUpdate.remove(0);
			gameObject.accept(componentUpdateVisitor);
		}
	}

	public static <T extends GameObject> List<T> getAllGameObjectsOfType(Class<T> type) {
		List<T> ret = new LinkedList<T>();

		for (GameObject gameObject : gameObjectRegistry) {
			if (gameObject.getClass().isAssignableFrom(type)) {
				ret.add( type.cast(gameObject));
			}
		}

		return ret;
	}

}
