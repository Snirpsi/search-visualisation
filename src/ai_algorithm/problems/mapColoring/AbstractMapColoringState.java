package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.CspState;

import java.util.*;

public abstract class AbstractMapColoringState extends CspState {

    /**
     *  reference to the problem
     */
    final private AbstractMapColoringProblem problem;


    final Map<String, List<String>> domain;

    /**
     *  reference to the assignments
     */
    final Map<String, String> assignments;

    /**
     * initializes state with problem and assignments
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
