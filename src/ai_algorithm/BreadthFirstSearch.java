package ai_algorithm;

import java.util.HashSet;

public class BreadthFirstSearch extends SearchAlgorithm {

	public BreadthFirstSearch(Problem problem) {
		super(problem);
	}

	@Override
	public SearchNode search() {

		Frontier frontier = new Frontier();
		// muss noch eigene Klasse werden;
		HashSet<State> explored = new HashSet<>();

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

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			SearchNode node = frontier.removeFirst();
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
