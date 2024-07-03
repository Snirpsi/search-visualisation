package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.CspState;

import java.util.*;

/**
 * The AbstractMapColoringState is a abstract state and implements methods and function from the CspState class.
 * The methods are the same for each MapColoringProblem from the implementation at the current time and differ only
 * in the specification of the variables, domains and neighbors.
 *
 * The AbstractMapColoringState class represents a abstract state in the MapColoringProblem.
 * The class provides methods to access the problem, the domains and the assignments.
 * The class also provides methods to compare states and to generate hash codes.
 * The class is used in the AbstractMapColoringProblem class.
 *
 * @author Alexander
 */
public abstract class AbstractMapColoringState extends CspState {

    /**
     *  reference to the problem
     */
    final private AbstractMapColoringProblem problem;

    /**
     * initializes state with problem, domain and assignments
     *
     * @param problem
     * @param domain
     * @param assignments
     */
    public AbstractMapColoringState(AbstractMapColoringProblem problem,
                            Map<String, List<String>> domain,
                            Map<String, String> assignments) {
        this.problem = problem;
        this.domain = new HashMap<>(domain);
        this.assignments = new HashMap<>(assignments);
    }

    /**
     * assignments as representation in the form of a string
     *
     * @return string representation of the state
     */
    @Override
    public Map<String, String> getAssignments() {
        return assignments;
    }

    /**
     * get the domain of a variable
     *
     * @param variable
     * @return domain of the variable
     */
    @Override
    public List<String> getDomain(String variable) {
        return new ArrayList<>(domain.get(variable));
    }

    /**
     * return Map of all variables and their domains
     *
     * @return domain
     */
    public Map<String, List<String>> getDomains() {
        return this.domain;
    }

    /**
     *  reference to the problem
     *
     * @return problem
     */
    @Override
    public AbstractMapColoringProblem getProblem() {
        return this.problem;
    }

    /**
     * hash function
     *
     * @return hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(problem, assignments, domain);
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
        AbstractMapColoringState other = (AbstractMapColoringState) obj;
        return Objects.equals(assignments, other.assignments) &&
                Objects.equals(problem, other.problem) &&
                Objects.equals(domain, other.domain);
    }

    /**
     * Assignements as representation in the form of a string
     *
     * @return string representation of the state
     */
    @Override
    public String toString() {
        return assignments.toString();
    }

}
