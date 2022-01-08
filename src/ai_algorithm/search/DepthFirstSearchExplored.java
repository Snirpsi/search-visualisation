package ai_algorithm.search;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.State;
import ai_algorithm.problems.Problem;
import application.debugger.Debugger;

public class DepthFirstSearchExplored extends SearchAlgorithm {
//default construchtor required
	public DepthFirstSearchExplored() {
		super();
	}
	
	public DepthFirstSearchExplored(Problem problem) {
		super(problem);
	}

	@Override
	public SearchNode search() {

		Frontier frontier = new Frontier();

		ExploredSet explored = new ExploredSet();

		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
		explored.add(start);

		Debugger.pause();

		if (this.problem.isGoalState(start.getState())) {
			return start;
		}

		frontier.add(start);
		Debugger.pause();

		while (!frontier.isEmpty()) {

			SearchNode node = frontier.removeLast();
			Debugger.pause();
			System.out.println(node);
			for (SearchNode child : node.expand()) {
				State state = child.getState();
				if (problem.isGoalState(state)) {
					Debugger.pause("Finished");
					return child;
				}
				if (!explored.contains(state)) {
					Debugger.pause();
					explored.add(child);
					frontier.add(child);
				}
			}

		}
		return null;
	}

}
