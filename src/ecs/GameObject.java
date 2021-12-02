package ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.UpdateRegistry;
import ecs.visitors.Visitor;

public class GameObject { // aka GameObject

	private String name = "";
	private HashMap<Class<Component>, Component> components;
	private List<GameObject> childGameObjects;

	public GameObject() {
		this.components = new HashMap<>();
		this.childGameObjects = new ArrayList<>();
		UpdateRegistry.register(this);
	}

	public GameObject(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public <T extends Component> T getComponent(Class<T> componentClass) {
		Component c = components.getOrDefault(componentClass, null);
		if (c != null) {
			return componentClass.cast(c);
		}
		// create new component if it doesen't exist in gameObject
		try {
			T newComp = componentClass.getConstructor().newInstance();
			addComponent(newComp);
			System.out.println("WARNING: Component created by getComponent method.");
			return (T) newComp;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public <T extends Component> boolean hasComponent(Class<T> componentClass) {
		return components.containsKey(componentClass);
	}

	public <T extends Component> void removeComponent(Class<T> componentClass) {
		components.remove(componentClass);
	}

	@SuppressWarnings("unchecked")
	public void addComponent(Component component) {
		components.put((Class<Component>) component.getClass(), component);// Warum Warning
		component.entity = this; // Reference aus der Komponente auf das zugehörige Object
	}

	public void update(float deltaT) throws MissingComponentExeption {
		for (GameObject child : childGameObjects) {
			child.update(deltaT);
		}
		for (Class<Component> key : components.keySet()) {
			components.get(key).update(deltaT);
		}
	}

	public void start() {
		for (GameObject child : childGameObjects) {
			child.start();
		}
		for (Class<Component> key : components.keySet()) {
			components.get(key).start();
		}
	}

	public void addGameObject(GameObject o) {
		this.childGameObjects.add(o);
	}

	public GameObject getGameObject(String name) {
		for (GameObject o : childGameObjects) {
			if (o.name.equals(name)) {
				return o;
			}
		}
		return null;
	}

	public void accept(Visitor visitor) {
		System.out.println("Blub" + this.getClass());
		visitor.visit(this);// this.getClass().cast(this));
	}
}
