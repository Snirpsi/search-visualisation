package ecs.visitors;

import ecs.GameObject;

/**
 * The visitor class is used to visit {@link GameObject} to add or remove or
 * change components of the {@link GameObject}. Each {@link Visitor} that
 * implements this abstract {@link Visitor} should provide a implementation for
 * every possible {@link GameObject} that could be visited.
 * 
 * @author Severin
 *
 */
public abstract class Visitor {

	/**
	 * The behavior of the visitor is defined in the visit method.
	 * 
	 * @param gameObject
	 */
	public void visit(GameObject gameObject) {
		System.out.println("The object " + gameObject + " was visited by " + this);
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