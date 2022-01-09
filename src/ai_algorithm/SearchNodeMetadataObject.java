package ai_algorithm;

public class SearchNodeMetadataObject {

	public volatile static SearchNode lastTouched;
	public volatile static SearchNode selected;
	public volatile static SearchNode root;

	public volatile static SearchNode expanding;
	public volatile static boolean justExpandend;

	public volatile boolean isInFrontier;
	public volatile boolean isInMemory;
	public volatile boolean isInExploredSet;

}
