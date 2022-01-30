package application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.SearchAndProblemRegister;
import ai_algorithm.problems.Problem;
import ai_algorithm.problems.cityState.GermanyRouteProblem;
import ai_algorithm.problems.raster_path.GridMazeProblem;
import ai_algorithm.problems.slidingTilePuzzle.SlidingTileProblem;
import ai_algorithm.search.*;
import application.gui.GuiLayout;
import settings.Settings;

/**
 * This class provides the functionality
 */
public class SearchThreadRegistryAndFactory {

	private static List<SearchThread> searchThreads = Collections.synchronizedList(new ArrayList<SearchThread>());

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
		searchThreads.add(s);
		s.setPriority(1);
		s.start();

	}

	public static List<String> getProblemNames() {
		var ret = new LinkedList<String>();

		for (var s : SearchAndProblemRegister.problems) {
			ret.add(s);
		}

		return ret;
	}

	public static List<String> getSearchAlgoritmNames() {
		var ret = new LinkedList<String>();

		for (var s : SearchAndProblemRegister.searchAlgorithms) {
			ret.add(s);
		}
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
		if (Settings.DEBUGMODE)
			System.out.println("search algoritm: " + searchAlgorithmName);
		SearchAlgorithm algo = null;
		if (problem == null) {
			return null;
		}

		try {

			Class<SearchAlgorithm> algoClass = (Class<SearchAlgorithm>) Class.forName(searchAlgorithmName);

			Constructor<SearchAlgorithm> cosntructor = algoClass.getConstructor();
			algo = cosntructor.newInstance();
			if (Settings.DEBUGMODE)
				System.out.println(algo.getClass());

			algo.setProblem(problem);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return algo;
	}

	public static void stopAllThreads() {
		for (SearchThread thread : searchThreads) {
			thread.interrupt();
			if (Settings.DEBUGMODE)
				System.out.println("theads Stoped!");
		}
		searchThreads.clear();

		// time for threads to finish
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
