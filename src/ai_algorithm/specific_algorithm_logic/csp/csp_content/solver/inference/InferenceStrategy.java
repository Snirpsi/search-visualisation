package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/inference/InferenceStrategy.java
 *
 * Defines a common interface for backtracking inference strategies.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public interface InferenceStrategy<VAR extends Variable, VAL> {

    /**
     * Inference method which is called before backtracking is started.
     *
     * @param csp The CSP which should be checked for consistency.
     */
    InferenceLog<VAR, VAL> apply(CSP<VAR, VAL> csp);

    /**
     * Inference method which is called after the assignment has (recursively) been extended by a value assignment
     * for <code>var</code>.
     *
     * @param csp The CSP which should be checked for consistency.
     * @param assignment The assignment which has been extended by a value assignment for <code>var</code>.
     * @param var The variable which has been assigned a value.
     * @return An object which indicates success/failure and contains data to undo the operation.
     */
    InferenceLog<VAR, VAL> apply(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var);
}