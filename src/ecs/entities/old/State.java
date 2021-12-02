package ecs.entities.old;

public class State {

	private Problem problem;

	public State(Problem problem) {
		this.problem = problem;
	}

	public Problem getProblem() {
		return problem;
	}

	public boolean equals(Object o) {
		return true;
	}

	public void init() {
	}
}
