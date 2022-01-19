package ai_algorithm.search;

import java.util.HashSet;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.State;
import ai_algorithm.problems.Problem;
import application.debugger.Debugger;

public class BreadthFirstSearch extends SearchAlgorithm {

	public BreadthFirstSearch() {
		super();
	}

	public BreadthFirstSearch(Problem problem) {
		super(problem);
	}

	@Override
	public SearchNode search() {

		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
		Debugger.pause("INIT First Search Node");

		Frontier frontier = new Frontier();
		Debugger.pause("INIT Frontier");

		ExploredSet explored = new ExploredSet();
		explored.add(start);
		Debugger.pause("INIT ExploredSet");


		Debugger.pause("add initial state to ExploredSet");

		if (this.problem.isGoalState(start.getState())) {
			Debugger.consolePrintln("goal is first");
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
					explored.add(child);
					frontier.add(child);
					Debugger.pause("Add to frontier");
				}
			}

		}
		return null;
	}

}
