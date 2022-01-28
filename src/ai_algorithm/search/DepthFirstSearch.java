package ai_algorithm.search;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import application.debugger.Debugger;
/**
 * Tiefensuche nach Mittag
 * 
 * @author Severin
 *
 */
public class DepthFirstSearch extends SearchAlgorithm {
//default construchtor required

	public DepthFirstSearch() {
		super();
	}

	public DepthFirstSearch(Problem problem) {
		super(problem);
	}

	@Override
	public SearchNode search() {

		Frontier frontier = new Frontier();

		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);

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
			if (problem.isGoalState(node.getState())) {
				return node;
			}

			for (SearchNode child : node.expand()) {
				State state = child.getState();
				if (problem.isGoalState(state)) {
					Debugger.pause("Finished");
					return child;
				}
				if (!contains2(node, state)) {
					frontier.add(child);
				}

			}

		}
		Debugger.pause("No Sulution found");
		return null;
	}

	public boolean contains(SearchNode sn, State state) {
		if (sn.getState().equals(state)) {
			return true;
		} else if (sn.getParent() != null) {
			return contains(sn.getParent(), state);
		}
		return false;
	}

	public boolean contains2(SearchNode sn, State state) {
		return sn.getPath().getVisitedStates().contains(state);
	}

}
