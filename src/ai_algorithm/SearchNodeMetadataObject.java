package ai_algorithm;

public class SearchNodeMetadataObject {
	
	public static SearchNode lastTouched;
	public static SearchNode selected;
	public static SearchNode root;
	
	public static SearchNode expanding;
	public static boolean justExpandend;
	
	public boolean isInFrontier;
	public boolean isInMemory;
	public boolean isInExploredSet;
	
}
