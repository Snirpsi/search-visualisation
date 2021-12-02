package ai_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Uebung02 {

	public static void main(String[] args) {

		GermanyRouteProblem prob = new GermanyRouteProblem("Aachen", "Leipzig");

		SearchNode solution = breadthFirstSearch(prob);

		printSolution(solution);
	}

	public static SearchNode breadthFirstSearch(Problem prob) {
		HashSet<State> explored = new HashSet<>();
		List<SearchNode> frontier = new ArrayList<>();

		SearchNode start = new SearchNode(null, prob.getInitialState(), 0, null);

		if (prob.isGoalState(start.getState())) {
			return start;
		}

		frontier.add(start);

		while (!frontier.isEmpty()) {
			SearchNode node = frontier.remove(0);

//			Thread.currentThread().suspend();

			for (SearchNode child : expand(node)) {
				State state = child.getState();
				if (prob.isGoalState(state)) {
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

	public static void printSolution(SearchNode solution) {
		List<SearchNode> nodes = new ArrayList<>();
		SearchNode curr = solution;
		while (curr != null) {
			nodes.add(curr);
			curr = curr.getParent();
		}

		Collections.reverse(nodes);

		for (SearchNode n : nodes) {
			// System.out.println(n.getAction() + " -> " + n.getState());
			System.out.println(n.getPathCost() + " -> " + n.getState());
		}
	}

	public static List<SearchNode> expand(SearchNode node) {
		List<SearchNode> list = new ArrayList<>();

		State s = node.getState();
		Problem prob = s.getProblem();

		for (String a : prob.getActions(s)) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			State succState = prob.getSuccessor(s, a);
			SearchNode succ = new SearchNode(node, succState, node.getPathCost() + prob.getCost(s, a, succState), a);
			list.add(succ);
		}

		return list;
	}
}
