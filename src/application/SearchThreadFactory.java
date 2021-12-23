package application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.cityState.GermanyRouteProblem;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import ai_algorithm.search.BreadthFirstSearch;
import ai_algorithm.search.DepthFirstSearch;
import ai_algorithm.search.SearchAlgorithm;
import application.gui.GuiLayout;

public class SearchThreadFactory {

	public static void startSearchIfReady() {
		if (GuiLayout.problemSelect.getValue() == null) {
			return;
		}
		if (GuiLayout.algoSelect.getValue() == null) {
			return;
		}

		Problem problem = getProblemWithName(GuiLayout.problemSelect.getValue());
		SearchAlgorithm algo = getSearchAlgoritmWithName(GuiLayout.problemSelect.getValue(), problem);

		SearchThread s = new SearchThread(algo);
		s.start();

	}

	public static List<String> getProblemNames() {
		var ret = new LinkedList<String>();
		// !! Hier neue Probleme einfügen !
		ret.add(RasterPathProblem.class.getName());
		ret.add(GermanyRouteProblem.class.getName());
		return ret;
	}

	public static List<String> getSearchAlgoritmNames() {
		var ret = new LinkedList<String>();
		// !! Hier neue Algorithmus einfügen !
		ret.add(DepthFirstSearch.class.getName());
		ret.add(BreadthFirstSearch.class.getName());
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
		SearchAlgorithm algoKS = null;
		if (problem == null) {
			return null;
		}

		try {

			// Siehe:
			// https://stackoverflow.com/questions/7495785/java-how-to-instantiate-a-class-from-string
			Class<SearchAlgorithm> algoClass = (Class<SearchAlgorithm>) Class.forName(searchAlgorithmName);

			System.out.println(Problem.class);
			System.out.println(RasterPathProblem.class);
			System.out.println(algoClass);

			Constructor<SearchAlgorithm> cosntructor = algoClass.getConstructor(Problem.class);
			algoKS = cosntructor.newInstance(problem);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return algoKS;
	}

}
