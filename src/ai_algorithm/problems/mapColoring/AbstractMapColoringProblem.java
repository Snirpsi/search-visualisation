package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.CspProblem;
import ai_algorithm.problems.State;
import ai_algorithm.problems.mapColoring.australia.MapColoringStateAustralia;
import javafx.scene.shape.Circle;

import java.util.*;

/**
 * The AbstractMapColoringProblem is a abstract problem and implements methods and function from the CspProblem class.
 * The methods are the same for each MapColoring problem from the implementation at the current time and differ only
 * in the specification of the variables, domains and neighbors.
 *
 * The AbstractMapColoring problem is a problem where a map must be colored in such a way
 * that adjacent regions have different colors. The map consists of various regions
 * (variables) that can be assigned specific colors (domains). There are constraints
 * that dictate that neighboring regions cannot share the same color.
 *
 * The agents must assign colors to the regions in a way that all constraints are satisfied.
 * There are various classes that make the problem available.
 *
 * These are: {@link AbstractMapColoringProblem} and {@link AbstractMapColoringState}.
 * In these classes, only the functions relevant to artificial intelligence have been implemented.
 * Each of these classes also has its own visitor functions, which equip them with components
 * for visualization.
 *
 * @author Alexander
 */
public abstract class AbstractMapColoringProblem extends CspProblem {

    protected Map<String, Circle> variableToCircleMap = new HashMap<>(); // Assignment of all variables to their respective circles

    protected Map<String, List<javafx.scene.text.Text>> variableTextMap = new HashMap<>(); // Map of variables to text

    /**
     * Initializes the map coloring problem with the variables, constraints, domain, assignements and arcs.
     * Initializes the fillQueue method.
     * Initializes the start variable.
     */
    public AbstractMapColoringProblem() {
        super();
        variables = new ArrayList<>();
        neighbors = new HashMap<>();
        domain = new HashMap<>();
        contraints = new ArrayList<>();
    }

    /**
     * Fills the queue with the edges and assigns each variable an edge between two nodes.
     */
    protected void fillQueue() {
        // Create constraint list out of neighbors
        for( String var : variables) {
            List<String> constraint = neighbors.get(var);
            for (String other : constraint) {
                contraints.add(new Pair<>(var, other));
            }
        }
    }

    /**
     * Returns the list of all variables.
     *
     * @return list of all variables
     */
    @Override
    public List<String> getVariables() {
        return variables;
    }

    /**
     * Returns the list of all domains.
     *
     * @return list of all domains
     */
    @Override
    public Map<String, List<String>> getDomain() {
        return domain;
    }

    /**
     * Returns the constraints.
     *
     * @return constraints
     */
    @Override
    public List<Pair<String, String>> getContraints() {
        return contraints;
    }

    /**
     * Returns the neighbors of a given variable.
     *
     * @param variable variable
     * @return list of neighbors of the variable
     */
    @Override
    public List<String> getNeighbors(String variable) {
        return neighbors.get(variable);
    }

    /**
     * Sets the variable to circle map.
     * Contains information about the circles assigned to a specific variable
     *
     * @param variableToCircleMap
     */
    public void setVariableToCircleMap(Map<String, Circle> variableToCircleMap) {
        this.variableToCircleMap = variableToCircleMap;
    }

    /**
     * Returns the variable to circle map.
     *
     * @return variable to circle map
     */
    public Map<String, Circle> getVariableToCircleMap() {
        return variableToCircleMap;
    }

    /**
     * Get the variable text map.
     * Contains information about the text assigned to a specific variable
     *
     * @return variableTextMap
     */
    public Map<String, List<javafx.scene.text.Text>> getVariableTextMap() {
        return variableTextMap;
    }

    /**
     * Returns the start variable.
     *
     * @return start variable
     */
    @Override
    public State getInitialState() {
        Map<String, String> initialAssignments = new HashMap<>();
        Map<String, List<String>> initialDomain = new HashMap<>();

        for (String variable : variables) {
            initialDomain.put(variable, new ArrayList<>(domain.get(variable)));
        }
        return new MapColoringStateAustralia(this, initialDomain, initialAssignments);
    }

    /**
     * Returns the goal variable.
     *
     * @return goal variable
     */
    @Override
    public State getGoalState() {
        return null;
    }

    /**
     * Checks whether a given state is a target state.
     *
     * @param state state to be tested
     * @return true if state is goal state else false
     */
    @Override
    public boolean isGoalState(State state) {
        MapColoringStateAustralia stateM = (MapColoringStateAustralia) state;

        for (String variable : variables) {
            if (!stateM.getAssignments().containsKey(variable)) {
                return false;
            }
        }
        for (Pair<String, String> constraint : contraints ) {
            String var1 = constraint.getFirst();
            String var2 = constraint.getSecond();
            String val1 = stateM.getAssignments().get(var1);
            String val2 = stateM.getAssignments().get(var2);
            if (val1.equals(val2)){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the actions of a given state.
     *
     * @param state state
     * @return List l of actions of the state
     */
    @Override
    public List<String> getActions(State state) {

        MapColoringStateAustralia stateM = (MapColoringStateAustralia) state;
        List<String> actions = new ArrayList<>();

        for (String variable : variables) {
            if (stateM.getAssignments().containsKey(variable)) {
                continue;
            }
            List<String> varDomain = stateM.getDomain(variable);
            for (String color : varDomain) {
                actions.add(variable + "=" + color);
            }
        }
        return actions;
    }

    /**
     * Returns the successor of a given state and action.
     *
     * @param state state
     * @param action action
     * @return successor of the state
     */
    @Override
    public State getSuccessor(State state, String action) {
        MapColoringStateAustralia stateM = (MapColoringStateAustralia) state;
        Map<String, String> newAssignments = new HashMap<>(stateM.getAssignments());

        String[] parts = action.split("=");
        String variable = parts[0];
        String value = parts[1];

//        if (!stateM.getDomain(variable).contains(value)) {
        // Check if value is in original domain
        if(!this.getDomain().get(variable).contains(value)) {
            return null;
        }
        newAssignments.put(variable, value);

        Map<String, List<String>> newDomain = new HashMap<>();
        for (String var : variables) {
            if(variable.equals(var)) {
                newDomain.put(var, Arrays.asList(value));
            } else {
                newDomain.put(var, new ArrayList<>(stateM.getDomain(var)));
            }
        }
        return new MapColoringStateAustralia(this, newDomain, newAssignments);
    }

    /**
     * Returns the cost of a given state, action and successor.
     *
     * @param state state
     * @param action action
     * @param succ successor
     * @return cost of the state, action and successor
     */
    @Override
    public double getCost(State state, String action, State succ) {
        return 1;
    }

}
