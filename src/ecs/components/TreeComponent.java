package ecs.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ecs.Component;

// Diese Klasse ist etwas redundant, sie ermöglicht es aber den Komponenten 
// auf einer Baumstruktur unabhängig von der "echten Baumstruktur" zu navigieren

/**
 * This class allows to represent a tree data structure between GameObjects
 * independent of the data structure the search algorithm is creating.
 * 
 * @author Severin
 *
 */
public class TreeComponent extends Component {

	TreeComponent parent = null;
	List<TreeComponent> children = null;
	int depth = 0;
	int sibling = 0;

	/**
	 * Default constructor
	 */
	public TreeComponent() {
		this.children = new ArrayList<TreeComponent>();
		this.parent = null;
		this.depth = 0;
	}

	/**
	 * Initializes the tree component the parameter as its parent.
	 * 
	 * @param parent
	 */
	public TreeComponent(TreeComponent parent) {
		this();
		parent.addChild(this); // das ruft eigentlich die nötigen Sachen auf ... oder ???
		// System.out.println("depth " + depth + " " + parent );
	}

	/**
	 * adds a child to the {@link TreeComponent}.
	 * 
	 * @param node
	 */
	public void addChild(TreeComponent node) {
		this.children.add(node);
		node.parent = this;
		node.depth = this.depth + 1;
		node.sibling = getChildren().size();
	}

	/**
	 * Adds multiple children to the {@link TreeComponent}.
	 * 
	 * @param nodes
	 */

	public void addChildren(List<TreeComponent> nodes) {
		this.getChildren().addAll(nodes);
		for (TreeComponent node : nodes) {
			node.parent = this;
			node.depth = this.depth + 1;
			node.sibling = getChildren().size();
		}
	}

	/**
	 * @returns the depth of the {@link TreeComponent}
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * Recalculates the depth of the tree.
	 * 
	 * @return returns the fresh calculated depth
	 */
	public int updateDepth() {
		if (this.parent == null) {
			return 0;
		}
		this.depth = this.parent.updateDepth() + 1;
		return depth;
	}

	@Override
	public void update(float deltaT) {
		/**
		 * no update per frame
		 */
	}

	/**
	 * 
	 * @return the nodes parent
	 */
	public TreeComponent getParent() {
		return parent;
	}

	/**
	 * @returns the children
	 */
	public List<TreeComponent> getChildren() {
		return children;
	}

	/**
	 * @return the number of siblings
	 */
	public int getSiblingCount() {
		return sibling;
	}

	/**
	 * the ordinal of an sibling the siblings are ordered in the order of insertion
	 * 
	 * @return index of the own node under its siblings
	 */
	public int getSiblingNumber() {
		if (this.getParent() == null) {
			return 0;
		}
		return this.getParent().getChildren().indexOf(this);
	}

	/**
	 * 
	 * @return returns alls siblings
	 */
	public List<TreeComponent> getSiblings() {
		if (this.getParent() == null) {
			var l = new LinkedList<TreeComponent>();
			l.add(this);
			return l;
		}
		return this.getParent().getChildren();
	}

	/**
	 * 
	 * @return true if its a leaf
	 */

	public boolean isLeaf() {
		if (this.getChildren() == null) {
			return true;
		}
		if (this.getChildren().size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return true if root
	 */
	public boolean isRoot() {
		if (this.getParent() == null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @returns the root of the tree the node is in
	 */
	public TreeComponent getRoot() {
		TreeComponent rootComp = this;
		while (rootComp.getParent() != null) {
			rootComp = rootComp.getParent();
		}
		return rootComp;
	}

	/**
	 * 
	 * @return returns all leafs of the own subtree in order
	 */
	public List<TreeComponent> getLeafsInOrder() {

		List<TreeComponent> leaves = new LinkedList<TreeComponent>();
		List<TreeComponent> frontier = new LinkedList<TreeComponent>();

		frontier.add(this);

		while (!frontier.isEmpty()) {

			TreeComponent now = frontier.remove(frontier.size() - 1);
			if (now.isLeaf()) {
				leaves.add(now);
			} else {
				for (int i = now.getChildren().size() - 1; i >= 0; i--) {
					frontier.add(now.getChildren().get(i));
				}
			}
		}
		return leaves;
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
