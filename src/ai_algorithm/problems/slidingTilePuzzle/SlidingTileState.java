package ai_algorithm.problems.slidingTilePuzzle;

import java.lang.reflect.Field;
import java.util.Arrays;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import tools.Vector2DInt;

public class SlidingTileState extends State {
	volatile private int[][] field;
	private SlidingTileProblem problem;

	/**
	 * Creates a new State with problem and field.
	 * 
	 * @param problem
	 * @param field
	 */
	public SlidingTileState(SlidingTileProblem problem, int[][] field) {
		this.problem = problem;
		this.field = field;

	}

	/**
	 * returns problemsize
	 * 
	 * @return
	 */
	public Vector2DInt getSize() {
		return this.problem.getSize();
	}

	/**
	 * sets the field
	 * @param field
	 */
	public void setField(int[][] field) {
		if (field == null) {
			return;
		}

		this.field = field;
	}
	/**
	 * Function to acquire a deepCoppy of its own field 
	 * @return field
	 */
	public int[][] getField() {
		return arrayDeepCoppy(field);
	}
	
	/**
	 * Method to create ad deepCoppy of a field 
	 * @param from
	 * @return deepCoppy of field.
	 */
	protected static int[][] arrayDeepCoppy(int[][] from) {
		if (from == null) {
			return null;

		}
		if (from.length == 0) {
			return new int[0][];
		}
		int[][] to = new int[from.length][];

		for (int i = 0; i < from.length; i++) {
			to[i] = new int[from[i].length];
			for (int j = 0; j < from[i].length; j++) {
				to[i][j] = from[i][j];
			}
		}
		return to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(field);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SlidingTileState other = (SlidingTileState) obj;
		return Arrays.deepEquals(field, other.field);
	}

	@Override
	public Problem getProblem() {
		return this.problem;
	}

	@Override
	public String toString() {

		return Arrays.deepToString(getField());
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
