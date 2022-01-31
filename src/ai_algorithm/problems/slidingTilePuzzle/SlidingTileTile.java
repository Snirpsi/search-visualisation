package ai_algorithm.problems.slidingTilePuzzle;

import ecs.GameObject;
import tools.Vector2DInt;

/**
 * Tile to represen one tile
 * 
 * @author Severin
 *
 */

//TODO: DEBUGG

public class SlidingTileTile extends GameObject {
	protected int num;
	Vector2DInt pos;

	public SlidingTileTile() {
		pos = Vector2DInt.ZERO;
		num = 0;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setPos(Vector2DInt pos) {
		this.pos = pos;
	}

	public SlidingTileTile(int x, int y, int num) {
		pos = new Vector2DInt(x, y);
		this.num = num;
	}

	public Vector2DInt getPos() {
		return pos;
	}

	public void setPos(int x, int y) {
		pos = new Vector2DInt(x, y);
	}

	@Override
	public String toString() {
		return "" + num;

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