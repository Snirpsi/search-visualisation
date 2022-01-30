package ai_algorithm;

import ecs.GameObjectRegistry;

/**
 * hilfsklasse die viesualisierung erleichtert
 * 
 * @author Severin
 *
 */
public class SearchNodeMetadataObject {

	/**
	 * @deprecated
	 */
	public volatile static SearchNode lastTouched;
	/**
	 * @deprecated
	 */
	public volatile static SearchNode root;

	/**
	 * knoten der als nächstes expandiert wird
	 */
	public volatile static SearchNode expanding;
	/**
	 * knoten der zuletzt expandiert wurde
	 */
	public volatile static SearchNode prevExpanding;

	/**
	 * knoten der selektiert ist
	 */
	public volatile static SearchNode selected;
	/**
	 * knoten der zuletzt selektiert war
	 */
	public volatile static SearchNode deselected;

	/**
	 * ist in frontier
	 */
	public volatile boolean isInFrontier;
	/**
	 * ist *vermutlich* in Speicher
	 */
	public volatile boolean isInMemory;
	/**
	 * ist in Explored-Set
	 */
	public volatile boolean isInExploredSet;

	/**
	 * anzahl der suchknoten
	 */
	public static volatile int counter = 0;

	/**
	 * suchknotennummer
	 */
	public volatile int number = 0;

	/**
	 * construktor aufgerufen von Searchnode
	 */
	public SearchNodeMetadataObject() {
		counter++;
		this.number = counter;
	}

	/**
	 * 
	 * @param searchNode
	 */
	public static void setExpandingSearchnode(SearchNode searchNode) {
		prevExpanding = expanding;
		expanding = searchNode;
		GameObjectRegistry.registerForStateChange(expanding);
		if (prevExpanding != null) {
			GameObjectRegistry.registerForStateChange(prevExpanding);
		}
	}

	public static void select(SearchNode searchNode) {
		deselected = selected;
		selected = searchNode;
	}

	public static void reset() {
		deselected = null;
		selected = null;
		prevExpanding = null;
		expanding = null;
		counter = 0;
		System.out.println("RESET");

	}

}
