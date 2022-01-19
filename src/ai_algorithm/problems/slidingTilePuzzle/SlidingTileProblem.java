package ai_algorithm.problems.slidingTilePuzzle;

import java.util.Arrays;
import java.util.List;

import ai_algorithm.State;
import ai_algorithm.problems.Problem;
import ai_algorithm.search.DepthFirstSearch;
import tools.Vector2DInt;

public class SlidingTileProblem extends Problem {

	public static void main(String[] args) {
		var f = new SlidingTileProblem();
		System.out.println(Arrays.deepToString(f.goalState.getField()));
	}

	Vector2DInt size;
	Vector2DInt emptyTile;

	SlidingTileState startState;
	SlidingTileState goalState;

	public SlidingTileProblem() {
		this(4, 5);
	}

	private SlidingTileProblem(int with, int height) {
		goalState = new SlidingTileState();
		startState = new SlidingTileState();

		goalState.setField(new int[height][with]);

		System.out.println(Arrays.deepToString(goalState.getField()));
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < with; j++) {

				goalState.getField()[i][j] = (i * (with)) + j;
			}
		}

		System.out.println(Arrays.deepToString(SlidingTileState.arrayDeepCoppy(goalState.getField())));

	}

	@Override
	public State getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGoalState(State state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getActions(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getSuccessor(State state, String action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getCost(State s, String action, State succ) {
		// TODO Auto-generated method stub
		return 0;
	}

}
