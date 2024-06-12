package application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.SearchAndProblemRegister;
import ai_algorithm.problems.CspProblem;
import ai_algorithm.problems.Problem;
import ai_algorithm.searchCsp.CspSearchAlgorithm;
import ai_algorithm.search.SearchAlgorithm;
import application.gui.GuiLayout;
import settings.Settings;

/**
 * This class provides the functionality of creating and managing - even possible multiple - threads
 */
public class SearchThreadRegistryAndFactory {

	private static List<SearchThread> searchThreads = Collections.synchronizedList(new ArrayList<SearchThread>());

	/**
	 * creates new thread when both problem and search algorithm are selected 
	 */
	public static void startSearchIfReady() {
		if (GuiLayout.problemSelect.getValue() == null) {
			return;
		}
		if (GuiLayout.algoSelect.getValue() == null) {
			return;
		}

		Problem problem = getProblemByName(GuiLayout.problemSelect.getValue());
//		SearchAlgorithm algo = getSearchAlgoritmByName(GuiLayout.algoSelect.getValue(), problem);

//		SearchThread s = new SearchThread(algo);
//		searchThreads.add(s);
//		s.setPriority(1);
//		s.start();

		if (problem instanceof ai_algorithm.problems.CspProblem) {
			if (GuiLayout.algoSelect.getValue().equals("ai_algorithm.search.DepthFirstSearch")) {
				SearchAlgorithm algo = getSearchAlgoritmByName(GuiLayout.algoSelect.getValue(), problem);
				SearchThread s = new SearchThread(algo);
				searchThreads.add(s);
				s.setPriority(1);
				s.start();
			} else {
				CspSearchAlgorithm algo = getCspSearchAlgoritmByName(GuiLayout.algoSelect.getValue(), (CspProblem) problem);
				SearchThread s = new SearchThread(algo);
				searchThreads.add(s);
				s.setPriority(1);
				s.start();
			}
		} else {
			SearchAlgorithm algo = getSearchAlgoritmByName(GuiLayout.algoSelect.getValue(), problem);
			SearchThread s = new SearchThread(algo);
			searchThreads.add(s);
			s.setPriority(1);
			s.start();
		}
	}
	
	/**
	 * 
	 * @return all registered {@link Problem}
	 */
	public static List<String> getProblemNames() {
		var ret = new LinkedList<String>();

		for (var s : SearchAndProblemRegister.problems) {
			ret.add(s);
		}

		return ret;
	}
	/**
	 *
	 * @return all registered {@link SearchAlgorithm}
	 */
	public static List<String> getSearchAlgoritmNames() {
		var ret = new LinkedList<String>();

		for (var s : SearchAndProblemRegister.searchAlgorithms) {
			ret.add(s);
		}
		return ret;
	}

	/**
	 *
	 * @return all registered {@link CspSearchAlgorithm}
	 */
	public static List<String> getCspSearchAlgoritmNames() {
		var ret = new LinkedList<String>();

		for (var s : SearchAndProblemRegister.cspSearchAlgorithm) {
			ret.add(s);
		}
		return ret;
	}


	public static Problem getProblemByName(String problemName) {
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

	/**
	 * 
	 * 
	 * @param searchAlgorithmName
	 * @param problem
	 * @return
	 */
	public static SearchAlgorithm getSearchAlgoritmByName(String searchAlgorithmName, Problem problem) {
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

	/**
	 *
	 *
	 * @param cspSearchAlgorithmName
	 * @param problem
	 * @return
	 */
	public static CspSearchAlgorithm getCspSearchAlgoritmByName(String cspSearchAlgorithmName, CspProblem problem) {
		if (Settings.DEBUGMODE)
			System.out.println("csp search algoritm: " + cspSearchAlgorithmName);
		CspSearchAlgorithm algo = null;
		if (problem == null) {
			return null;
		}

		try {

			Class<CspSearchAlgorithm> algoClass = (Class<CspSearchAlgorithm>) Class.forName(cspSearchAlgorithmName);

			Constructor<CspSearchAlgorithm> cosntructor = algoClass.getConstructor();
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

		/**
         * method to stop all running threads
         */
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
