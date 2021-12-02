package ecs;

public abstract class Component {

	// optimize with dirty bit

	public GameObject entity = null;

	public void start() {
	}

	public void fetchDependencies() {
	}

	public abstract void update(float deltaT);
}
