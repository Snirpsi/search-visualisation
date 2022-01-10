package application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.cityState.GermanyRouteProblem;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import ai_algorithm.search.*;
import application.gui.GuiLayout;

/**
 * This class provides the functionality
 */
public class SearchThreadFactory {

	public static void startSearchIfReady() {
		if (GuiLayout.problemSelect.getValue() == null) {
			return;
		}
		if (GuiLayout.algoSelect.getValue() == null) {
			return;
		}

		Problem problem = getProblemWithName(GuiLayout.problemSelect.getValue());
		SearchAlgorithm algo = getSearchAlgoritmWithName(GuiLayout.algoSelect.getValue(), problem);

		SearchThread s = new SearchThread(algo);
		s.start();

	}

	public static List<String> getProblemNames() {
		var ret = new LinkedList<String>();
		// !! Hier neue Probleme einf�gen !
		ret.add(RasterPathProblem.class.getName());
		ret.add(GermanyRouteProblem.class.getName());
		return ret;
	}

	public static List<String> getSearchAlgoritmNames() {
		var ret = new LinkedList<String>();
		// !! Hier neue Algorithmus einf�gen !
		ret.add(DepthFirstSearchExplored.class.getName());
		ret.add(BreadthFirstSearch.class.getName());
		ret.add(DepthFirstSearch.class.getName());
		ret.add(RecursiveDepthSearch.class.getName());
		return ret;
	}

	public static Problem getProblemWithName(String problemName) {
		Problem problem = null;

		try {
			Class<Problem> problemClass = (Class<Problem>) Class.forName(problemName);
			problem = problemClass.getConstructor().newInstance();

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return problem;
	}

	public static SearchAlgorithm getSearchAlgoritmWithName(String searchAlgorithmName, Problem problem) {

		System.out.println("search algoritm " + searchAlgorithmName);
		SearchAlgorithm algoKS = null;
		if (problem == null) {
			return null;
		}

		try {

			Class<SearchAlgorithm> algoClass = (Class<SearchAlgorithm>) Class.forName(searchAlgorithmName);

			Constructor<SearchAlgorithm> cosntructor = algoClass.getConstructor();
			System.out.println(" CLASSEEEEEE" + cosntructor.newInstance().getClass());
			algoKS = cosntructor.newInstance();
			System.out.println(algoKS.getClass());

			algoKS.setProblem(problem);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return algoKS;
	}

}
