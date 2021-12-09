package ecs.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ecs.Component;

// Diese Klasse ist etwas redundant, sie ermöglicht es aber den Komponenten 
// auf einer Baumstruktur unabhängig von der "echten Baumstruktur" zu navigieren

public class TreeComponent extends Component {

	TreeComponent parent = null;
	List<TreeComponent> children = null;
	int depth = 0;
	int sibling = 0;

	public TreeComponent() {
		this.children = new ArrayList<TreeComponent>();
		this.parent = null;
		this.depth = 0;
	}

	public TreeComponent(TreeComponent parent) {
		this();
		parent.addChild(this); // das ruft eigentlich die nötigen Sachen auf ... oder ???
		// System.out.println("depth " + depth + " " + parent );
	}

	public void addChild(TreeComponent node) {
		this.children.add(node);
		node.parent = this;
		node.depth = this.depth + 1;
		node.sibling = getChildren().size();
	}

	public void addChildren(List<TreeComponent> nodes) {
		this.getChildren().addAll(nodes);
		for (TreeComponent node : nodes) {
			node.parent = this;
			node.depth = this.depth + 1;
			node.sibling = getChildren().size();
		}
	}

	public int getDepth() {
		return this.depth;
	}

	public int updateDepth() {
		if (this.parent == null) {
			return 0;
		}
		this.depth = this.parent.updateDepth() + 1;
		return depth;
	}

	@Override
	public void update(float deltaT) {
	}

	public TreeComponent getParent() {
		return parent;
	}

	/**
	 * @return the children
	 */
	public List<TreeComponent> getChildren() {
		return children;
	}

	/**
	 * @return the sibling
	 */
	public int getSiblingCount() {
		return sibling;
	}

	public int getSiblingNumber() {
		if (this.getParent() == null) {
			return 0;
		}
		return this.getParent().getChildren().indexOf(this);
	}

	public List<TreeComponent> getSiblings() {
		if (this.getParent() == null) {
			var l = new LinkedList<TreeComponent>();
			l.add(this);
			return l;
		}
		return this.getParent().getChildren();
	}

	public boolean isLeave() {
		this.updateDepth();
		if (this.getChildren() == null) {
			return true;
		}
		if (this.getChildren().size() == 0) {
			return true;
		}

		return false;
	}

	public boolean isRoot() {
		if (this.getParent() == null) {
			return true;
		}
		return false;
	}

	public TreeComponent getRoot() {
		TreeComponent rootComp = this;
		while (rootComp.getParent() != null) {
			rootComp = rootComp.getParent();
		}
		return rootComp;
	}

	public List<TreeComponent> getLeafsInOrder() {

		List<TreeComponent> leaves = new LinkedList<TreeComponent>();
		List<TreeComponent> frontier = new LinkedList<TreeComponent>();

		frontier.add(this);

		while (!frontier.isEmpty()) {

			TreeComponent now = frontier.remove(frontier.size() - 1);
			if (now.isLeave()) {
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
