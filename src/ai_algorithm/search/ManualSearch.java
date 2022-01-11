package ai_algorithm.search;

import java.util.List;

import ai_algorithm.SearchNode;
import ai_algorithm.SearchNodeMetadataObject;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import application.debugger.Debugger;

public class ManualSearch extends SearchAlgorithm {

	@Override
	public SearchNode search() {
		SearchNode node = new SearchNode(null, this.problem.getInitialState(), 0, null);

		if (problem.isGoalState(node.getState())) {
			return node;
		}

		SearchNodeMetadataObject.select(node);

		while (SearchNodeMetadataObject.selected != null) {

			var var = "var";
			var = var + var;

			System.out.println(var);

			Debugger.pause("Select a node to expand:");
			List<SearchNode> children = SearchNodeMetadataObject.selected.expand();
			for (SearchNode c : children) {
				if (problem.isGoalState(c.getState())) {
					return (c);
				}
			}
		}

		return null;

	}

}
