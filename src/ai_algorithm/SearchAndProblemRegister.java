package ai_algorithm;

import ai_algorithm.problems.mapColoring.MapColoringProblem;
import ai_algorithm.problems.raster_path.GridMazeProblem;
import ai_algorithm.problems.slidingTilePuzzle.SlidingTileProblem;
import ai_algorithm.search.BidirectionalBreadthFirstSearch;
import ai_algorithm.search.BreadthFirstSearch;
import ai_algorithm.search.DepthFirstSearch;
import ai_algorithm.search.DepthFirstSearchExplored;
import ai_algorithm.search.ManualSearch;
import ai_algorithm.search.RecursiveDepthSearch;
import ai_algorithm.searchCsp.BacktrackingArcConsistancy3Search;
//import ai_algorithm.search.*;

/**
 * Class to register new problems and Searches
 *
 * @author Severin
 * @author Alexander
 */
public class SearchAndProblemRegister {

	/**
	 * Array in which all problems are to be registered by a string with the class
	 * name
	 */
	public static String[] problems = { //
			GridMazeProblem.class.getName(), //
			SlidingTileProblem.class.getName(), //
			MapColoringProblem.class.getName(), //
			"ai_algorithm.problems.cityState.GermanyRouteProblem",
			// GermanyRouteProblem.class.getName()//
	};

	/**
	 * Array in which all Search-Algorithms are to be registered by a string with
	 * the class name
	 */
	public static String[] searchAlgorithms = { //
			DepthFirstSearch.class.getName(), //
			DepthFirstSearchExplored.class.getName(), //
			RecursiveDepthSearch.class.getName(), //
			BreadthFirstSearch.class.getName(), //
			BidirectionalBreadthFirstSearch.class.getName(), //
			ManualSearch.class.getName(),//
	};

	/**
	 * Array in which all CSP Search-Algorithms are to be registered by a string with
	 * the class name
	 */
	public static String[] cspSearchAlgorithm = {
			BacktrackingArcConsistancy3Search.class.getName()//
	};

}
/*
 * Copyright (c) 2022 Severin Dippold
 * Copyright (c) 2024 Alexander Ultsch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */