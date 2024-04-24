package ai_algorithm.specific_algorithm_logic.csp.csp_content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/CSP.java
 *
 * Artificial Intelligence A Modern Approach (3rd Ed.): Section 6.1, Page 202.<br>
 * <br>
 * A constraint satisfaction problem or CSP consists of three components, X, D,
 * and C:
 * <ul>
 * <li>X is a set of variables, {X1, ... ,Xn}.</li>
 * <li>D is a set of domains, {D1, ... ,Dn}, one for each variable.</li>
 * <li>C is a set of constraints that specify allowable combinations of values.</li>
 * </ul>
 *
 * @param <VAR> Type which is used to represent variables
 * @param <VAL> Type which is used to represent the values in the domains
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class CSP<VAR extends Variable, VAL> implements Cloneable {

    private final List<VAR> variables; // List of variables
    private List<Domain<VAL>> domains; // List of domains for each variable
    private final List<Constraint<VAR, VAL>> constraints; // List of constraints

    /**
     * Lookup, which maps a variable to its index in the list of variables.
     */
    private final Hashtable<Variable, Integer> varIndexHash;
    /**
     * Constraint network. Maps variables to those constraints in which they
     * participate.
     */
    private final Hashtable<Variable, List<Constraint<VAR, VAL>>> cnet;

    /**
     * Constructor - Creates a new CSP object.
     * The variables, domains, and constraints have to be added later.
     */
    public CSP() {
        variables = new ArrayList<>(); // List of variables
        domains = new ArrayList<>(); // List of domains for each variable
        constraints = new ArrayList<>(); // List of constraints

        varIndexHash = new Hashtable<>(); // Lookup, which maps a variable to its index in the list of variables
        cnet = new Hashtable<>(); // Constraint network. Maps variables to those constraints in which they participate
    }

    /**
     * Constructor - Creates a new CSP object.
     *
     * @param vars List of variables
     *             The variables, domains, and constraints have to be added later.
     */
    public CSP(List<VAR> vars) {
        this(); // Calls the default constructor
        vars.forEach(this::addVariable); // Adds the variables
    }

    /**
     * Adds a new variable only if its name is new.
     *
     * @param var The variable to be added to the CSP object
     */
    protected void addVariable(VAR var) {
        if (!varIndexHash.containsKey(var)) { // Checks if the variable is already present
            Domain<VAL> emptyDomain = new Domain<>(Collections.emptyList()); // Creates an empty domain
            variables.add(var); // Adds the variable
            domains.add(emptyDomain); // Adds the domain
            varIndexHash.put(var, variables.size() - 1); // Adds the variable to the lookup
            cnet.put(var, new ArrayList<>()); // Adds the variable to the constraint network
        } else { // If the variable is already present
            throw new IllegalArgumentException("Variable with same name already exists."); // Throws an IllegalArgumentException
        }
    }

    /**
     * @return An unmodifiable list of variables
     */
    public List<VAR> getVariables() {
        return Collections.unmodifiableList(variables);
    }

    /**
     * Returns the index of the specified variable.
     *
     * @param var The variable whose index is to be found
     * @return The index of the variable
     */
    public int indexOf(Variable var) {
        return varIndexHash.get(var);
    }

    /**
     * Set the domain of the specified variable.
     *
     * @param var The variable whose domain is to be set
     * @param domain The domain to be set
     */
    public void setDomain(VAR var, Domain<VAL> domain) {
        domains.set(indexOf(var), domain);
    }

    /**
     * Returns the domain of the specified variable.
     *
     * @param var The variable whose domain is to be found
     * @return The domain of the variable
     */
    public Domain<VAL> getDomain(Variable var) {
        return domains.get(varIndexHash.get(var));
    }

    /**
     * Replaces the domain of the specified variable by new domain, which
     * contains all values of the old domain except the specified value.
     *
     * @param var The variable whose domain is to be modified
     * @param value The value to be removed from the domain
     * @return true if the domain was modified, false otherwise
     */
    public boolean removeValueFromDomain(VAR var, VAL value) {
        Domain<VAL> currDomain = getDomain(var); // Gets the current domain of the variable
        List<VAL> values = new ArrayList<>(currDomain.size()); // Creates a new list of values
        for (VAL v : currDomain)
            if (!v.equals(value))
                values.add(v);
        if (values.size() < currDomain.size()) { // Checks if the domain was modified
            setDomain(var, new Domain<>(values)); // Sets the new domain
            return true;
        }
        return false;
    }

    /**
     * Adds a new constraint to the CSP object.
     *
     * @param constraint The constraint to be added
     */
    public void addConstraint(Constraint<VAR, VAL> constraint) {
        constraints.add(constraint);
        constraint.getScope().forEach(var -> cnet.get(var).add(constraint));
    }

    /**
     * Removes a constraint from the CSP object.
     *
     * @param constraint The constraint to be removed
     * @return true if the constraint was removed, false otherwise
     */
    public boolean removeConstraint(Constraint<VAR, VAL> constraint) {
        if (constraints.remove(constraint)) {
            constraint.getScope().forEach(var -> cnet.get(var).remove(constraint));
            return true;
        }
        return false;
    }

    /**
     * @return An unmodifiable list of constraints
     */
    public List<Constraint<VAR, VAL>> getConstraints() {
        return constraints;
    }

    /**
     * Returns all constraints in which the specified variable participates.
     */
    public List<Constraint<VAR, VAL>> getConstraints(Variable var) {
        return cnet.get(var);
    }

    /**
     * Returns for binary constraints the other variable from the scope.
     *
     * @return a variable or null for non-binary constraints.
     */
    public VAR getNeighbor(VAR var, Constraint<VAR, VAL> constraint) {
        List<VAR> scope = constraint.getScope(); // Gets the scope of the constraint
        if (scope.size() == 2) {
            if (var.equals(scope.get(0))) // Checks if the variable is the first element of the scope
                return scope.get(1);
            else if (var.equals(scope.get(1))) // Checks if the variable is the second element of the scope
                return scope.get(0);
        }
        return null;
    }

    /**
     * Returns a copy which contains a copy of the domains list and is in all
     * other aspects a flat copy of this.
     *
     * @return a copy of this object.
     */
    @SuppressWarnings("unchecked")
    public CSP<VAR, VAL> copyDomains() {
        CSP<VAR, VAL> result;
        try {
            result = (CSP<VAR, VAL>) clone();
            result.domains = new ArrayList<>(domains);
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException("Could not copy domains.");
        }
        return result;
    }
}