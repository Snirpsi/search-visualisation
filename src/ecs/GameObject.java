package ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import ecs.components.Animation;
import ecs.visitors.Visitor;
import javafx.print.Collation;
import tools.Vector2DInt;

/**
 * The {@link GameObject} class is used to represent all Objects assigned to the
 * game world. In this case the GameObject is used for all Objects that are used
 * to implement search algorithms. GameObjects are structures to manage
 * {@link Component}s that can be assigned and removed from it. Each GameObject
 * can be Only one {@link Component} of each type can be assigned to the
 * {@link GameObject}. The {@link Component}s are managed by a {@link HashMap}
 * which key is the {@link Component} class. In the Constructor the
 * {@link GameObject} is registered for initialization at the
 * {@link GameObjectRegistry} where it gets its components assigned.
 * The {@link GameObject} is the Entity in the Entity Component System (ECS).
 * 
 * @version 1.0
 */
public class GameObject {

	private String name = "";
	private ConcurrentHashMap<Class<Component>, Component> components; // TODO: Workaround concurrent HashMap
	private List<GameObject> childGameObjects;

	/**
	 * Creates a new GameObject and registers it for initialization to the
	 * {@link GameObjectRegistry},
	 */
	public GameObject() {
		this.components = new ConcurrentHashMap<Class<Component>, Component>();
		this.childGameObjects = new ArrayList<GameObject>();
		GameObjectRegistry.register(this);
	}

	/**
	 * Constructor allows to assign an name to a {@link GameObject}
	 */

	public GameObject(String name) {
		this();
		this.name = name;
	}

	/**
	 * Returns the name of the {@link GameObject}.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Allows to change the {@link GameObject}s name.
	 * 
	 * @param name The name of the Object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This function returns a {@link Component} of the given type assigned to the
	 * {@link GameObject}. If the {@link GameObject} has no {@link Component} of
	 * such type, with default values will be created and assigned to the
	 * {@link GameObject}
	 * 
	 * @param <T>            has To be of the type {@link Component}.
	 * @param componentClass The class of the requested {@link Component}.
	 * @return returns always a valid Component if it doesn't exist in the object it
	 *         will be created.
	 */
	public <T extends Component> T getComponent(Class<T> componentClass) {
		Component c = components.getOrDefault(componentClass, null);
		if (c != null) {
			return componentClass.cast(c);
		}
		// create new component if it doesen't exist in gameObject
		try {
			T newComp = componentClass.getConstructor().newInstance();
			addComponent(newComp);
			System.out.println("WARNING: Component " + newComp.getClass().getName() + " created by getComponent method "
					+ this.getClass().getName() + ".");
			return (T) newComp;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Function to request if {@link Component} exists in {@link GameObject}.
	 * 
	 * @param <T>            Type of the ComponentClass.
	 * @param componentClass The class of the {@link Component} that's existence is
	 *                       Requested.
	 * @return True if component exists in {@link GameObject}, else false.
	 */
	public <T extends Component> boolean hasComponent(Class<T> componentClass) {
		return components.containsKey(componentClass);
	}

	/**
	 * This Function removes a {@link Component} from the {@link GameObject}, if it
	 * exists.
	 * 
	 * @param <T>            Type of component class.
	 * @param componentClass The type of the component to remove.
	 */
	public <T extends Component> void removeComponent(Class<T> componentClass) {
		components.remove(componentClass);
	}

	/**
	 * This function adds a given {@link Component} to the {@link GameObject}.
	 * 
	 * @param component The component to add.
	 */
	@SuppressWarnings("unchecked")
	public void addComponent(Component component) {

		components.put((Class<Component>) component.getClass(), component);// Warum Warning
		component.entity = this; // Reference aus der Komponente auf das zugehörige Object
	}

	/**
	 * This function calls the update function of each {@link Component} of the
	 * {@link GameObject}. The change in time between this and the last update call
	 * can be given, to give time sensitive {@link Component}s like
	 * {@link Animation} or other, the elapsed time.
	 * 
	 * @param deltaT The elapsed time between this and the last call.
	 */
	public void update(float deltaT) {
		for (GameObject child : childGameObjects) {
			child.update(deltaT);
		}

//		Component [] comps = (Component[]) components.values()

		for (Component comp : components.values()) { // TODO: Componenten werden in der laufzeit erstellt führt zu
			// modifikation im iterator
			comp.update(deltaT);
		}

	}

	/**
	 * Starts the {@link GameObject} and its {@link Component}s.
	 * 
	 * @implNote: Currently not used by the framework.
	 */
	public void start() {
		for (GameObject child : childGameObjects) {
			child.start();
		}
		for (Component component : components.values()) {
			component.start();
		}
	}

	/**
	 * Allows to build a hierarchy between {@link GameObject}. A {@link GameObject}
	 * can be child of an other {@link GameObject}.
	 * 
	 * @implNote: Currently not used by the framework.
	 */
	public void addGameObject(GameObject o) {
		this.childGameObjects.add(o);
	}

	/**
	 * Function can return a {@link GameObject}s child by its name.
	 * 
	 * @param name The child's name.
	 * @return The {@link GameObject}s child associated with the name.
	 */
	public GameObject getGameObject(String name) {
		for (GameObject o : childGameObjects) {
			if (o.name.equals(name)) {
				return o;
			}
		}
		return null;
	}

	/**
	 * {@link GameObject}s accept {@link Visitor}s for initialization and changes to
	 * components updates.
	 * 
	 * @param visitor The {@link Visitor} that manipulates/changes the
	 *                {@link GameObject}.
	 */
	public void accept(Visitor visitor) {
		System.out.println("Blub" + this.getClass());
		visitor.visit(this);// this.getClass().cast(this));
	}
}
