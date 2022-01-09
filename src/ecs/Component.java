package ecs;

/**
 * The {@link Component}(-System) combines the {@link Component} and the System
 * of the Entity Component System (ECS) to one Unit. By adding them to a
 * {@link GameObject} the Object gains the Data (State) and the Behavior
 * (System) of this {@link Component}(-System).
 *
 * For each component a default constructor with no parameters is mandetory.
 * 
 * @author Severin
 *
 */
public abstract class Component {
	/**
	 * Reference of the {@link GameObject} that this {@link Component} is assigned
	 * to.
	 */
	public GameObject entity = null;

	/**
	 * The default constructor of an component.
	 */
	public Component() {
	}

	/**
	 * Method to start the Component.
	 * 
	 * @implNote Not yet used.
	 */
	public void start() {
	}

	@Deprecated(since = "0.4", forRemoval = true)
	/**
	 * This method is deprecated. Components should not save references of other
	 * components.
	 */
	public void fetchDependencies() {
	}

	/**
	 * This function should be called every frame. deltaT is the time elapsed
	 * between frames. It can be used to calculate time sensitive behavior like
	 * physics or animations.
	 * 
	 * @param deltaT
	 */
	public abstract void update(float deltaT);
}
