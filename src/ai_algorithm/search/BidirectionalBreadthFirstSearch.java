package ai_algorithm.search;

import java.util.List;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.raster_path.RasterPathPath;
import application.debugger.Debugger;

public class BidirectionalBreadthFirstSearch extends SearchAlgorithm {

	@Override
	public SearchNode search() {
		if (problem.getGoalState() == null) {// TODO Auto-generated method stub
			Debugger.consolePrintln("Goal is not known! Abord!");
			return null;
		}

		SearchNode startF = new SearchNode(null, problem.getInitialState(), 0, null);
		SearchNode goalB = new SearchNode(null, problem.getGoalState(), 0, null);

		Frontier frontierStartF = new Frontier();
		frontierStartF.add(startF);

		Frontier frontierGoalB = new Frontier();
		frontierGoalB.add(goalB);

		ExploredSet reachendStartF = new ExploredSet();
		reachendStartF.add(startF);

		ExploredSet reachedGoalB = new ExploredSet();
		reachedGoalB.add(goalB);

		Path solution = null;

		boolean terminated = false;
		boolean toggle = false;
		while (!frontierGoalB.isEmpty() && !frontierStartF.isEmpty() && !terminated) {

			if (toggle = !toggle) {
				var d = proceed("f", frontierStartF, reachendStartF, reachedGoalB) == null ? false
						: (terminated = true);

			} else {
				var d = proceed("b", frontierGoalB, reachedGoalB, reachendStartF) == null ? false : (terminated = true);
			}

		}

		return startF;

	}

	private List<String> proceed(String dir, Frontier frontier, ExploredSet reached, ExploredSet reachedOther) {
		SearchNode searchnode = frontier.removeFirst();
		for (SearchNode child : searchnode.expand()) {
			if (!reached.contains(child.getState())) {
				reached.add(child);
				frontier.add(child);
				if (reachedOther.contains(child.getState())) {
					Debugger.consolePrintln("!!!!!!!!!!!!!!!!!!!!!!!! FOUND !!!!!!!!!!!!!!!!!!!");
					return (List<String>) join(child.getPath(), reachedOther.get(child.getState()).getPath());
				}
			}
		}
		return null;

	}

	private List<String> join(Path a, Path b) {
		List<String> actionA = a.getPathActions();
		List<String> actionB = b.getPathActions();

		actionA.addAll(actionB);
		return actionA;

	}

}
