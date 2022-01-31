package ai_algorithm.problems;

import java.util.List;

import ecs.GameObject;

/**
 * Class template for problems
 * 
 * @author Severin
 *
 *
 */
public abstract class Problem extends GameObject {

	/**
	 * Returns initial state
	 * 
	 * @return initial state
	 */
	public abstract State getInitialState();

	/**
	 * Returns goal state if known otherwise it returns null
	 * 
	 * @return goal state or null
	 */
	public State getGoalState() {
		return null;
	}

	/**
	 * Check if state is goal state
	 * 
	 * @param state
	 * @return true if state is goal state, otherwise false
	 */
	public abstract boolean isGoalState(State state);

	/**
	 * Indicates all actions that are possible in a state
	 * 
	 * @param state
	 * @return list of possible actions
	 */
	public abstract List<String> getActions(State state);

	/**
	 * Calculates the successor state for a state and its action
	 * 
	 * @param state
	 * @param action
	 * @return successor state
	 */
	public abstract State getSuccessor(State state, String action);

	/**
	 * specifies for a state and an action and the successor state how high the
	 * costs are
	 * 
	 * @param state
	 * @param action
	 * @param succ successor state
	 * @return cost of the action from state
	 */
	public abstract double getCost(State state, String action, State succ);

}

/*
 * Copyright (c) 2022 Severin Dippold
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
