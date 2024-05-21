package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;

import java.util.*;

/**
 * The MapColoringState class represents a state in the map coloring problem.
 * The state is represented by a map of variables to colors.
 * The class provides methods to access the problem and the assignments.
 * The class also provides methods to compare states and to generate hash codes.
 * The class is used in the MapColoringProblem class.
 *
 * @author Alexander
 */

public class MapColoringState extends State {

    /**
     *  reference to the problem
     */
    private MapColoringProblem problem;

    /**
     *  reference to the assignments
     */
    private String position;

    /**
     * initializes state with problem and assignments
     *
     * @param problem
     * @param position
     */
    public MapColoringState(MapColoringProblem problem, String position) {
        this.problem = problem;
        this.setPosition(position);
    }

    public void setPosition(String position) {
        this.position = position;
    }

    /**
     *  reference to the problem
     *
     * @return problem
     */
    @Override
    public MapColoringProblem getProblem() {
        return this.problem;
    }

    /**
     * Get the reference to the position
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Assignments as representation in the form of a string
     *
     * @return string representation of the state
     */
//    public Map<String, String> getAssignments() {
//        return assignments;
//    }

    /**
     * Set the assignments -> variable -> color // Vermutlich nicht notwendig
     *
     * @param assignments
     */
//    private void setAssignments(Map<String, String> assignments) {
//        this.assignments = assignments;
//    }

    /**
     * hash function
     *
     * @return hash value
     */
    @Override
    public int hashCode() {
//        return Objects.hash(assignments);
        return Objects.hash(position);
    }

    /**
     * checked for equivalence
     *
     * @param obj
     * @return true if equal false if not equal or null
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapColoringState other = (MapColoringState) obj;
        return Objects.equals(position, other.position);
    }

    /**
     * Assignements as representation in the form of a string
     *
     * @return string representation of the state
     */
    @Override
    public String toString() {
        return position.toString();
    }

}
