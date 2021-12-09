package ai_algorithm.search;

import java.util.HashSet;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.State;
import ai_algorithm.Uebung02;
import ai_algorithm.problems.Problem;
import application.Debugger;

public class DepthFirstSearch extends SearchAlgorithm {

	public DepthFirstSearch(Problem problem) {
		super(problem);
	}

	@Override
	public SearchNode search() {

		Frontier frontier = new Frontier();
		// muss noch eigene Klasse werden;
		ExploredSet explored = new ExploredSet();

		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
		explored.add(problem.getInitialState());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (this.problem.isGoalState(start.getState())) {
			return start;
		}

		frontier.add(start);

		while (!frontier.isEmpty()) {

			Debugger.pause();
			
			SearchNode node = frontier.removeLast();
			System.out.println(node);
			for (SearchNode child : Uebung02.expand(node)) {
				State state = child.getState();
				if (problem.isGoalState(state)) {
					System.out.println("Finished");
					//Uebung02.expand(child);
					return child;
				}
				if (!explored.contains(state)) {
					explored.add(state);
					frontier.add(child);
				}
			}

		}
		return null;
	}

}
