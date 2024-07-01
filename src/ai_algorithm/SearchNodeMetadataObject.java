package ai_algorithm;

import ecs.GameObjectRegistry;

/**
 * hilfsklasse die viesualisierung erleichtert
 * 
 * @author Severin
 *
 */
public class SearchNodeMetadataObject {

	/**
	 * @deprecated
	 */
	public volatile static SearchNode lastTouched;
	/**
	 * @deprecated
	 */
	public volatile static SearchNode root;

	/**
	 * node that will be expanded next
	 */
	public volatile static SearchNode expanding;
	/**
	 * node that was expanded last
	 */
	public volatile static SearchNode prevExpanding;

	/**
	 * node that is selected
	 */
	public volatile static SearchNode selected;
	/**
	 * node that was selected last
	 */
	public volatile static SearchNode deselected;

	/**
	 * is in frontier
	 */
	public volatile boolean isInFrontier;
	/**
	 * is *probably* in memory
	 */
	public volatile boolean isInMemory;
	/**
	 * is in Explored-Set
	 */
	public volatile boolean isInExploredSet;

	/**
	 * number of search nodes
	 */
	public static volatile int counter = 0;

	/**
	 * search node id
	 */
	public volatile int id = 0;

	/**
	 * construktor aufgerufen von Searchnode
	 */
	public SearchNodeMetadataObject() {
		counter++;
		this.id = counter;
	}

	/**
	 * set as next search node to be expanded
	 * 
	 * @param searchNode
	 */
	public static void setExpandingSearchnode(SearchNode searchNode) {
		prevExpanding = expanding;
		expanding = searchNode;
		if( expanding != null ) {
			GameObjectRegistry.registerForStateChange(expanding);
		}
		if (prevExpanding != null) {
			GameObjectRegistry.registerForStateChange(prevExpanding);
		}
	}

	/**
	 * set as selected
	 * 
	 * @param searchNode
	 */
	public static void select(SearchNode searchNode) {
		deselected = selected;
		selected = searchNode;
	}

	/**
	 * reset datastruchture
	 * 
	 */
	public static void reset() {
		deselected = null;
		selected = null;
		prevExpanding = null;
		expanding = null;
		counter = 0;
		System.out.println("RESET");

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
