package ai_algorithm.problems.raster_path;

import java.util.Objects;

import ai_algorithm.problems.State;
import tools.Vector2DInt;

/**
 * @author Severin
 *
 *         Represents state (psoition) in which an agent is located.
 *
 */
public class GridMazeState extends State {

	/**
	 * reference to the problem
	 */
	public GridMazeProblem problem;

	/**
	 * reference to the position
	 */
	private Vector2DInt position;

	/**
	 * initializes state with problem and position
	 * 
	 * @param problem
	 * @param position
	 */
	public GridMazeState(GridMazeProblem problem, Vector2DInt position) {
		this.problem = problem;
		this.setPosition(position);
	}

	@Override
	public GridMazeProblem getProblem() {
		return this.problem;
	}

	/**
	 * @return the position
	 */
	public Vector2DInt getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2DInt position) {
		this.position = position;
	}

	/**
	 *  hash function
	 * 
	 * @return hash value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(position);
	}

	/**
	 * checked for equivalence
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridMazeState other = (GridMazeState) obj;
		return Objects.equals(position, other.position);
	}

	/**
	 * Position as representative in the form of a string
	 * 
	 * @return string representation
	 */
	@Override
	public String toString() {

		return position.toString();
	}

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
