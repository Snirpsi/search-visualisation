package ai_algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import ai_algorithm.problems.State;
import ecs.GameObject;
import ecs.GameObjectRegistry;

/**
 * Struktur zur verwaltung der Knoten die noch zu expandieren sind
 * 
 * @author Severin
 * 
 */
public class Frontier extends GameObject { // maby it is althou collection ??? {
	/**
	 * verwaltungsstruktur
	 */
	private List<SearchNode> frontier;

	/**
	 * erzeuge neue Frontier
	 */
	public Frontier() {
		super();
		frontier = new ArrayList<SearchNode>();
	}

	/**
	 * füge suchknoten hinzu
	 * 
	 * @param node
	 */
	public void add(SearchNode node) {
		// eventuell notify hinzufügen für fallunterscheidung von verschiedenen aktionen
		this.frontier.add(node);
		node.metadata.isInFrontier = true;
		node.metadata.isInMemory = true;
//visualisieren
		GameObjectRegistry.registerForStateChange(node);
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * füge suchknoten hinzu
	 * 
	 * @param node
	 */
	public void addAll(List<SearchNode> nodes) {
		frontier.addAll(nodes);
		for (SearchNode node : nodes) {
			node.metadata.isInFrontier = true;
			node.metadata.isInMemory = true;
			GameObjectRegistry.registerForStateChange(node);
		}
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * sortiert die suchknoten nach einem übergebenen kriterium
	 * 
	 * @param evaluationFunction
	 */
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
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * prüfe ob sie lehr ist
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	/**
	 * gibt füllstand an
	 * 
	 * @return size
	 */
	public int size() {
		return frontier.size();
	}

	/**
	 * gibt ersten Suchknoten aus der Frontier
	 * 
	 * @return
	 */

	public SearchNode removeFirst() {
		if (frontier.isEmpty()) {
			return null;
		}
		SearchNode first = frontier.remove(0);
		first.metadata.isInFrontier = false;
		first.metadata.isInMemory = false;
		return first;
	}

	/**
	 * gbt den letzten Suchknoten der frontier
	 * 
	 * @return
	 */

	public SearchNode removeLast() {
		if (frontier.isEmpty()) {
			return null;
		}
		SearchNode last = frontier.remove(frontier.size() - 1);
		last.metadata.isInFrontier = false;
		last.metadata.isInMemory = false;
		return last;
	}

	/**
	 * prüft ob ein suchknoten enthalten ist oder nicht
	 * 
	 * @param searchnode
	 * @return true, false
	 */

	public boolean contains(SearchNode searchnode) {
		if (frontier.contains(searchnode)) {
			return true;
		}
		return false;
	}

	/**
	 * prüft ob ein suchknoten mit entsprechendem zustand vorhanden ist
	 * 
	 * @param state
	 * @return true, false
	 */
	public boolean containsNodeWithState(State state) {
		for (SearchNode searchNode : frontier) {
			if (searchNode.getState().equals(state)) {
				return true;
			}
		}
		return false;
	}

}
