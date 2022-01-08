package ecs.components.graphics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import ai_algorithm.SearchNode;
import ecs.Component;
import ecs.GameObjectRegistry;
import ecs.components.Position;
import ecs.components.TreeComponent;
import javafx.geometry.Pos;
import tools.Vector2D;
import tools.Vector2DInt;

//Source --> https://stackoverflow.com/questions/33328245/radial-tree-layout-algorithm

public class TreeLayouter extends Component {

	public static final int leaveDistance = 20;

	public static final Vector2D OFFSET = new Vector2D(0, 0);
	public static final double PARENT_DISTANCE = 100;
	public static final double SIBLING_DISTANCE = 40;
	double angle = 0;
	double angleRange = 0;

	public TreeLayouter() {
		this.angleRange = 2 * Math.PI;
	}

	void setPosition(Vector2D position) {
		this.entity.getComponent(Position.class).setPosition(position);
	}

	public void layout2() {
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

	public void layout() { // temporary

		TreeComponent treeOwn = super.entity.getComponent(TreeComponent.class);
		TreeComponent treeRoot = treeOwn.getRoot();

		List<TreeComponent> leafs = treeRoot.getLeafsInOrder();

		int i = 0;
		for (TreeComponent leave : leafs) {
			Position leavePos = leave.entity.getComponent(Position.class);
			leavePos.setPosition(new Vector2D(i * leaveDistance, (float) (PARENT_DISTANCE * (leave.updateDepth()))));
			i++;
			if (leave.getParent() != null) {
				leave.getParent().entity.getComponent(TreeLayouter.class).parentRecursiveLayout();
			}
		}

		for (TreeComponent leaf : leafs) {
			if (!leaf.isRoot()) {
				// number of tree layout iterations more means more beautifull
				for (int j = 0; j < 3; j++) {
					leaf.getParent().entity.getComponent(TreeLayouter.class).placeSiblingsRecursivLayout();
					leaf.getParent().entity.getComponent(TreeLayouter.class).parentRecursiveLayout();
				}
			}
		}

	}

	private void placeSiblingsRecursivLayout() {
		TreeComponent treeOwn = this.entity.getComponent(TreeComponent.class);
		if (treeOwn.isLeave()) {
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
				Vector2D relativSiblingPos = new Vector2D((float) (leaveDistance * (sibling.getSiblingNumber() + 1)),
						0);
				Vector2D newSiblingPos = new Vector2D(ownPos.getFuturePosition()).add(relativSiblingPos);
				siblingPos.setPosition(newSiblingPos);

			}

		}
		Vector2D relativeOwnPos = new Vector2D((float) (leaveDistance * (treeOwn.getSiblingNumber())), 0);
		Vector2D newOwnPos = new Vector2D(ownPos.getFuturePosition()).add(relativeOwnPos);
		ownPos.setPosition(newOwnPos);
	}

	private void parentRecursiveLayout() {
		TreeComponent treeOwn = this.entity.getComponent(TreeComponent.class);

		if (treeOwn.isLeave()) {
			return;
		}

		// position child average

		List<Vector2D> vectors = new LinkedList<Vector2D>();
		for (TreeComponent child : treeOwn.getChildren()) {
			vectors.add(child.entity.getComponent(Position.class).getFuturePosition());
		}

		Vector2D newParentPos = new Vector2D(new Vector2D().average(vectors).x,
				(float) (PARENT_DISTANCE * (treeOwn.updateDepth())));

		// positioniere unter first child
//		TreeComponent ownFirstChild = treeOwn.getChildren().get(0);
//		Vector2D newParentPos = new Vector2D(ownFirstChild.entity.getComponent(Position.class).getFuturePosition().x,
//				leaveDistance * (treeOwn.updateDepth()));
		treeOwn.entity.getComponent(Position.class).setPosition(newParentPos);

		if (!treeOwn.isRoot()) {
			treeOwn.getParent().entity.getComponent(TreeLayouter.class).parentRecursiveLayout();
		}
		// MABY NEEDET AFTER REWORK
		// this.placeSiblingsRecursivLayout();
		return;
	}

	@Override
	public void update(float deltaT) {
	}
}
