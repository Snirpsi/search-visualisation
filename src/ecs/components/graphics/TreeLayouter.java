package ecs.components.graphics;

import java.util.LinkedList;
import java.util.List;

import ecs.Component;
import ecs.components.Position;
import ecs.components.TreeComponent;
import settings.Settings;
import tools.Vector2D;

//Source --> https://stackoverflow.com/questions/33328245/radial-tree-layout-algorithm

public class TreeLayouter extends Component {



	public static final Vector2D OFFSET = new Vector2D(0, 0);
	public static final int LEAF_DISTANCE = Settings.TREE_LAYOUT.LEAF_DISTANCE;
	public static final double PARENT_DISTANCE = Settings.TREE_LAYOUT.PARENT_DISTANCE;
	public static final double SIBLING_DISTANCE = Settings.TREE_LAYOUT.SIBLING_DISTANCE;
	
	boolean growUp = false;
	double angle = 0;
	double angleRange = 0;
    
	public TreeLayouter() {
		this.angleRange = 2 * Math.PI;
	}

	void setPosition(Vector2D position) {
		this.entity.getComponent(Position.class).setPosition(position);
	}

	/**
	 *  um baum nach oben wachsen zu lassen wurzelknoten auf true setzen
	 */
	public void setGrowUp(boolean growUp) {
		this.growUp = growUp;
	}
	
	public void layout2() {
		// Source -->
		// https://stackoverflow.com/questions/33328245/radial-tree-layout-algorithm
		// Benötigte Komponenten holen
		// System.out.println("BAUM UPDATE LAYOUT");
		TreeComponent nodeComp = super.entity.getComponent(TreeComponent.class);
		Position pos = super.entity.getComponent(Position.class);
		nodeComp.updateDepth();

		if (nodeComp.isRoot()) {
			pos.setPosition(OFFSET);
		}
		int n = nodeComp.getChildren().size();
		for (int i = 0; i < n; i++) {
			TreeComponent child = nodeComp.getChildren().get(i);
			TreeLayouter childTreeLayouter = child.entity.getComponent(TreeLayouter.class);
			if (childTreeLayouter == null) {
				return;
			}

			childTreeLayouter.angle = this.angle + this.angleRange / n * i;
			childTreeLayouter.angleRange = this.angleRange / n;

			// System.out.println(child.getDepth());
			var neuPos = new Vector2D(
					(float) /* (DISTANCE * child.getDepth()) */ (PARENT_DISTANCE * child.getDepth() * child.getDepth()
							+ child.getDepth() * child.getSiblingCount() * SIBLING_DISTANCE
							+ Math.sin(PARENT_DISTANCE * angle * PARENT_DISTANCE) * SIBLING_DISTANCE),
					0);
			Vector2D neuRotPos = neuPos.rotate(childTreeLayouter.angle);
			childTreeLayouter.setPosition(neuRotPos.add(OFFSET));
			// BENÖTIGT WENN Baum zu einem späteren zeitpunkt Struktur grundlegend ändert
			childTreeLayouter.layout();

		}
	}

	public void layout() {

		TreeComponent treeOwn = super.entity.getComponent(TreeComponent.class);
		TreeComponent treeRoot = treeOwn.getRoot();
		
		int growdir= 1;
		
		if (treeRoot.entity.hasComponent(TreeLayouter.class) ) {
			var layouter = treeRoot.entity.getComponent(TreeLayouter.class);
				if (layouter.growUp) {
					growdir = -1;
				}
		}

		List<TreeComponent> leafs = treeRoot.getLeafsInOrder();

		int i = 0;
		for (TreeComponent leave : leafs) {
			Position leavePos = leave.entity.getComponent(Position.class);
			leavePos.setPosition(new Vector2D(i * LEAF_DISTANCE, (float) (PARENT_DISTANCE * (leave.updateDepth()) * growdir )));
			i++;
			if (!leave.isRoot()) {
				leave.getParent().entity.getComponent(TreeLayouter.class).parentRecursiveLayout(growdir);
			}
		}

		for (TreeComponent leaf : leafs) {
			if (!leaf.isRoot()) {
				// number of tree layout iterations more means more beautifull
				for (int j = 0; j < 2; j++) {
					leaf.getParent().entity.getComponent(TreeLayouter.class).placeSiblingsRecursivLayout(growdir);
					leaf.getParent().entity.getComponent(TreeLayouter.class).parentRecursiveLayout(growdir);
				}
			}
		}

	}

	private void placeSiblingsRecursivLayout(int growdir) {
		TreeComponent treeOwn = this.entity.getComponent(TreeComponent.class);
		if (treeOwn.isLeaf()) {
			return;
		}
		if (treeOwn.isRoot()) {
			return;
		}
		Position ownPos = treeOwn.entity.getComponent(Position.class);
		for (TreeComponent sibling : treeOwn.getSiblings()) {

			if ((sibling.getChildren() == null || sibling.getChildren().size() == 0) && sibling != treeOwn) {

				Position siblingPos = sibling.entity.getComponent(Position.class);
				// set sibling neighboring own node
				Vector2D relativSiblingPos = new Vector2D((float) (LEAF_DISTANCE * (sibling.getSiblingNumber() + 1)),
						0);
				Vector2D newSiblingPos = new Vector2D(ownPos.getFuturePosition()).add(relativSiblingPos);
				siblingPos.setPosition(newSiblingPos);

			}

		}
		Vector2D relativeOwnPos = new Vector2D((float) (LEAF_DISTANCE * (treeOwn.getSiblingNumber())), 0);
		Vector2D newOwnPos = new Vector2D(ownPos.getFuturePosition()).add(relativeOwnPos);
		ownPos.setPosition(newOwnPos);
	}

	private void parentRecursiveLayout( int growdir) {
		TreeComponent treeOwn = this.entity.getComponent(TreeComponent.class);

		if (treeOwn.isLeaf()) {
			return;
		}

		// position child average

		List<Vector2D> vectors = new LinkedList<Vector2D>();
		for (TreeComponent child : treeOwn.getChildren()) {
			vectors.add(child.entity.getComponent(Position.class).getFuturePosition());
		}

		Vector2D newParentPos = new Vector2D(new Vector2D().average(vectors).x,
				(float) (PARENT_DISTANCE * (treeOwn.updateDepth() * growdir)));

		// positioniere unter first child
//		TreeComponent ownFirstChild = treeOwn.getChildren().get(0);
//		Vector2D newParentPos = new Vector2D(ownFirstChild.entity.getComponent(Position.class).getFuturePosition().x,
//				leaveDistance * (treeOwn.updateDepth()));
		treeOwn.entity.getComponent(Position.class).setPosition(newParentPos);

		if (!treeOwn.isRoot()) {
			treeOwn.getParent().entity.getComponent(TreeLayouter.class).parentRecursiveLayout(growdir);
		}
		// MABY NEEDET AFTER REWORK
		// this.placeSiblingsRecursivLayout();
		return;
	}
	
	

	@Override
	public void update(float deltaT) {
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