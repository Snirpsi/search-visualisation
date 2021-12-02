package ecs.entities.old;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import ecs.GameObject;

public class Frontier extends GameObject {

	private List<SearchNode> frontier;
	private SearchAlgorithm searchAlgorithm;
	private Function<State, Double> evaluationFunction;

	public Frontier(SearchAlgorithm searchAlgorithm) {
		super();
		frontier = new ArrayList<SearchNode>();
		this.searchAlgorithm = searchAlgorithm;

	}

	public void add(SearchNode sn) {
		this.frontier.add(sn);
		// state node Farbe setzen!!
		if (evaluationFunction == null) {
			return;
		}
		frontier.sort(new Comparator<SearchNode>() {
			@Override
			public int compare(SearchNode o1, SearchNode o2) {
				if (evaluationFunction.apply(o1.getState()) > evaluationFunction.apply(o2.getState())) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
}
