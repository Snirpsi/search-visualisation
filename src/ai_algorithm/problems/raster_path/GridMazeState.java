package ai_algorithm.problems.raster_path;

import java.util.Objects;

import ai_algorithm.problems.State;
import tools.Vector2DInt;

/**
 * @author Severin
 *
 *Repräsentiert Zustand (Psoition) in der sich ein Spieler befindet.
 *
 */
public class GridMazeState extends State {

	/**
	 * verweis auf das Problem
	 */
	public GridMazeProblem problem;

	/**
	 * verweis auf die Position
	 */
	private Vector2DInt position;
	
	/**
	 * initialisiert zustand mit problem und Position
	 * @param problem
	 * @param position
	 */
	public GridMazeState(GridMazeProblem problem ,Vector2DInt position) {
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
	 * hash funktion
	 * @return hash-wert
	 */
	@Override
	public int hashCode() {
		return Objects.hash(position);
	}

	/**
	 * überprüft auf gleichwertigkeit
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
	 * Position als repräsentant in form eines strings
	 * @return string repräsentation
	 */
	@Override
	public String toString() {

		return position.toString();
	}
	

	
}
