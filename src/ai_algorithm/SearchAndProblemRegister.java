package ai_algorithm;

import ai_algorithm.problems.cityState.GermanyRouteProblem;
import ai_algorithm.problems.raster_path.GridMazeProblem;
import ai_algorithm.problems.slidingTilePuzzle.SlidingTileProblem;
import ai_algorithm.search.BidirectionalBreadthFirstSearch;
import ai_algorithm.search.BreadthFirstSearch;
import ai_algorithm.search.DepthFirstSearch;
import ai_algorithm.search.DepthFirstSearchExplored;
import ai_algorithm.search.ManualSearch;
import ai_algorithm.search.RecursiveDepthSearch;

public class SearchAndProblemRegister {

	public static String[] problems = { //
			GridMazeProblem.class.getName(), //
			SlidingTileProblem.class.getName(), //
			GermanyRouteProblem.class.getName()//
	};

	public static String[] searchAlgorithms = { //
			DepthFirstSearch.class.getName(), //
			DepthFirstSearchExplored.class.getName(), //
			RecursiveDepthSearch.class.getName(), //
			BreadthFirstSearch.class.getName(), //
			BidirectionalBreadthFirstSearch.class.getName(), //
			ManualSearch.class.getName()//
	};

}
