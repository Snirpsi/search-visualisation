package ai_algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import application.debugger.Debugger;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import javafx.scene.Node;

/**
 * Suchknoten sind die zentrale datenstruktur der KI-Algorithmen
 * 
 * @author Severin
 */
public class SearchNode extends GameObject {


	/**
	 * elternknoten
	 */
	private final SearchNode parent;
	/**
	 * kindknoten
	 */
	private LinkedList<SearchNode> children = null;
	/**
	 * zustand
	 */
	private final State state;
	/**
	 * pfadkosten
	 */
	private final double pathCost;
	/**
	 * aktion
	 */
	private final String action;
	/**
	 * verweis auf den zurückgelegten pfad
	 */
	private Path path;

	/**
	 * objekt hauptsächlich für visualisierung
	 */
	public SearchNodeMetadataObject metadata;

	/**
	 * Konstruktor
	 * 
	 * @param parent   null wenn wurzelknoten
	 * @param state    wenn wurzel startzustand
	 * @param pathCost pfadkosten
	 * @param action   aktion die in zustand geführt hat
	 */
	public SearchNode(SearchNode parent, State state, double pathCost, String action) {
		super();
		this.metadata = new SearchNodeMetadataObject();
		

		this.parent = parent;
		this.state = state;
		this.pathCost = pathCost;
		this.action = action;
		if (this.parent != null) {
			this.metadata.root = this;
			if (this.parent.children == null) {
				this.parent.children = new LinkedList<SearchNode>();
			} else {
				this.parent.children.add(this);
			}
		}
		this.path = new Path(this);

	}

	/**
	 * gibt elternknoten zurück
	 * 
	 * @return elternknoten
	 */
	public SearchNode getParent() {
		return parent;
	}

	/**
	 * testet ob knoten wurzel ist
	 * 
	 * @return true, false
	 */
	public boolean isRoot() {
		if (this.getParent() == null) {
			return true;
		}
		return false;
	}

	/**
	 * gibt zugehörigen zustand
	 * 
	 * @return
	 */
	public State getState() {
		return state;
	}

	/**
	 * gibt die Pfadkosten an
	 * 
	 * @return pfadkosten
	 */
	public double getPathCost() {
		return pathCost;
	}

	/**
	 * aktion die in zustand geführt hat
	 * 
	 * @return aktion
	 */
	public String getAction() {
		return action;
	}

	/**
	 * gibt alle kindknoten als liste an
	 * 
	 * @return liste aller kindknoten
	 */
	public LinkedList<SearchNode> getChildren() {
		return children;
	}

	/**
	 * setzt die kindknoten
	 * 
	 * @param children
	 */
	public void setChildren(LinkedList<SearchNode> children) {
		this.children = children;
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * @return gibt pfad-objekt zurück
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * expandiert automatisch wendet alle in zustand des knotens möglichen aktionen
	 * an und fügt diese in neue knoten ein. alle knoten werden anschließend dem
	 * expandierten knoten als kinder hinzugefügt
	 * 
	 * es wird empfolen diese Methode zu verwenden und mindestens einmahl pro
	 * iteration aufzurufen
	 * 
	 * @return
	 */
	public List<SearchNode> expand() {
		//visualisierung
		SearchNodeMetadataObject.setExpandingSearchnode(this);
		Debugger.pause("Expanding: " + this);
		//ende vis
		
		List<SearchNode> futureCildren = new ArrayList<>();

		if (this.getChildren() != null && this.getChildren().size() != 0) {
			return this.getChildren(); // <-- only expand children if they don't alredy exist
		}

		State state = this.getState();
		Problem prob = state.getProblem();
		for (String action : prob.getActions(state)) {
			State succState = prob.getSuccessor(state, action);
			//neue knoten erzeugen
			SearchNode succ = new SearchNode(this, succState,
					this.getPathCost() + prob.getCost(state, action, succState), action);
			futureCildren.add(succ);
			Debugger.pause("EXPANSION: " + action);
		}
		this.children.addAll(futureCildren);
		//visualsiieren
		GameObjectRegistry.registerForStateChange(this);
		//ende vis
		return this.children;
	}
	/**
	 * Stringrepräsentation
	 * @return Searchnode
	 */
	@Override
	public String toString() {
		return "SearchNode [state=" + state + ", pathCost=" + pathCost + ", action=" + action + "]";
	}

}
