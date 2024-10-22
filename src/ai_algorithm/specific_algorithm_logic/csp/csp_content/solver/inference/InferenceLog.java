package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/inference/InferenceLog.java
 *
 * Provides information about (1) whether changes have been performed, (2) possibly inferred empty domains , and
 * (3) how to restore the CSP.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public interface InferenceLog<VAR extends Variable, VAL> {
    boolean isEmpty(); // Returns true if no changes have been performed.
    boolean inconsistencyFound(); // Returns true if an empty domain has been inferred.
    void undo(CSP<VAR, VAL> csp); // Restores the CSP to the state before the inference was performed.

    /**-
     * Returns an empty inference log.
     */
    static <VAR extends Variable, VAL> InferenceLog<VAR, VAL> emptyLog() {
        return new InferenceLog<VAR, VAL>() {
            /**
             * Returns true if no changes have been performed.
             */
            @Override
            public boolean isEmpty() { return true; }

            /**
             * Returns false if no empty domain has been inferred.
             */
            @Override
            public boolean inconsistencyFound() { return false; }

            @Override
            public void undo(CSP<VAR, VAL> csp){ } // Does nothing.
        };
    }
}