package ai_algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import application.UpdateRegistry;
import ecs.GameObject;

public class Frontier extends GameObject {

	private List<SearchNode> frontier;

	public Frontier() {
		super();
		frontier = new ArrayList<SearchNode>();
	}

	public void add(SearchNode sn) {
		// eventuell notify hinzufügen für fallunterscheidung von verschiedenen aktionen
		this.frontier.add(sn);
		UpdateRegistry.registerForLargeComponentUpdate(sn);
		UpdateRegistry.registerForLargeComponentUpdate(this);
	}

	public void addAll(List<SearchNode> nodes) {
		frontier.addAll(nodes);
		for (SearchNode node : nodes) {
			UpdateRegistry.registerForLargeComponentUpdate(node);
		}
		UpdateRegistry.registerForLargeComponentUpdate(this);
	}

	public void sort(Function<SearchNode, Double> evaluationFunction) {
		frontier.sort(new Comparator<SearchNode>() {
			@Override
			public int compare(SearchNode o1, SearchNode o2) {

				if (evaluationFunction.apply(o1) > evaluationFunction.apply(o2)) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		UpdateRegistry.registerForLargeComponentUpdate(this);
	}

	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	public SearchNode removeFirst() {
		if (frontier.isEmpty()) {
			return null;
		}
		return frontier.remove(0);
	}

	public SearchNode removeLast() {
		if (frontier.isEmpty()) {
			return null;
		}
		return frontier.remove(frontier.size() - 1);
	}

	public boolean contains(SearchNode searchnode) {
		if (frontier.contains(searchnode)) {
			return true;
		}
		return false;
	}

}
