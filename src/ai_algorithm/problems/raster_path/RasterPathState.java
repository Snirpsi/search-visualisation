package ai_algorithm.problems.raster_path;

import java.util.Objects;

import ai_algorithm.State;
import tools.Vector2DInt;

public class RasterPathState extends State {

	public RasterPathProblem problem;

	private Vector2DInt position;
	
	public RasterPathState(RasterPathProblem problem ,Vector2DInt position) {
		this.problem = problem;
		this.setPosition(position);
	}

	@Override
	public RasterPathProblem getProblem() {
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

	@Override
	public int hashCode() {
		return Objects.hash(position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RasterPathState other = (RasterPathState) obj;
		return Objects.equals(position, other.position);
	}

	@Override
	public String toString() {

		return position.toString();
	}
	

	
}
