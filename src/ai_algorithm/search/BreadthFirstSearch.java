package ai_algorithm.search;

import java.util.HashSet;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.State;
import ai_algorithm.problems.Problem;
import application.debugger.Debugger;

public class BreadthFirstSearch extends SearchAlgorithm {

	public BreadthFirstSearch(Problem problem) {
		super(problem);
	}

	@Override
	public SearchNode search() {

		Frontier frontier = new Frontier();

		ExploredSet explored = new ExploredSet();

		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
		explored.add(start);

		Debugger.pause("INIT");

		if (this.problem.isGoalState(start.getState())) {
			return start;
		}

		frontier.add(start);
		Debugger.pause("add first to frontier");

		while (!frontier.isEmpty()) {

			SearchNode node = frontier.removeFirst();
			System.out.println(node);
			for (SearchNode child : node.expand()) {
				State state = child.getState();
				if (problem.isGoalState(state)) {
					System.out.println("Finished");
					return child;
				}
				if (!explored.contains(state)) {
					Debugger.pause("Add to frontier");
					explored.add(child);
					frontier.add(child);
				}
			}

		}
		return null;
	}

}
