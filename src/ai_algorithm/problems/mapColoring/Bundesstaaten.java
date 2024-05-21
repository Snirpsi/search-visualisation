package ai_algorithm.problems.mapColoring;

import java.util.List;

/**
 * The Bundesstaaten class represents a variable in the map coloring problem.
 * The Node is represented by a variable, a domain and a list of constraints.
 * The class provides methods to access the variable, the domain and the constraints.
 * The class is used in the MapColoringProblem class.
 *
 * @author Alexander
 */
public class Bundesstaaten {

    String variable;
    List<String> domain;
    List<String> constraints;

    /**
     * Constructor for initialising the node with variable, domain and constraints
     *
     * @param variable
     * @param domain
     * @param constraints
     */
    Bundesstaaten (String variable, List<String> domain, List<String> constraints) {
        this.variable = variable;
        this.domain = domain;
        this.constraints = constraints;
    }

    /** Getter and Setter methods \/ */
    public String getVariable() {
        return variable;
    }

    public List<String> getDomain() {
        return domain;
    }

    public List<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<String> constraints) {
        this.constraints = constraints;
    }

    public void setDomain(List<String> domain) {
        this.domain = domain;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    /**
     * Method to print the node
     * Method works if the state list was previously iterated through
     *
     * @return String
     */
    public String toString(){
        return this.variable + " " + this.domain + " " + this.constraints;
    }

}
