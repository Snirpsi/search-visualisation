package ai_algorithm;

import ecs.GameObjectRegistry;

public class SearchNodeMetadataObject {

	public volatile static SearchNode lastTouched;

	public volatile static SearchNode root;

	public volatile static SearchNode prevExpanding;
	public volatile static SearchNode expanding;
	public volatile boolean justExpanded;

	public volatile static SearchNode selected;
	public volatile static SearchNode deselected;

	public volatile boolean isInFrontier;
	public volatile boolean isInMemory;
	public volatile boolean isInExploredSet;

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
	}

}
