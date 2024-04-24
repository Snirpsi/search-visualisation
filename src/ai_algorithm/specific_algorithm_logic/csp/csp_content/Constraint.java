package ai_algorithm.specific_algorithm_logic.csp.csp_content;

import java.util.List;

/**
 * A constraint specifp
 * @author Ruediger Lunde
 */
public interface Constraint<VAR extends Variable, VAL> {
    /** Returns a tuple of variables that participate in the constraint. */
    List<VAR> getScope();

    /** Constrains the values that the variables can take on. */
    boolean isSatisfiedWith(Assignment<VAR, VAL> assignment);
}
