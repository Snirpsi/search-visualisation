package ai_algorithm.problems.slidingTilePuzzle;

public class SlidingTileTest {
	public static void main(String[] args) {
		var prob = new SlidingTileProblem();

		System.out.println(prob.getInitialState());
		System.out.println(prob.getSuccessor(prob.getInitialState(), "UP"));
		System.out.println(prob.getSuccessor(prob.getInitialState(), "DOWN"));
		System.out.println(prob.getSuccessor(prob.getInitialState(), "LEFT"));
		System.out.println(prob.getSuccessor(prob.getInitialState(), "RIGHT"));

	}
}
