package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.framework.Metrics;

/**
 * Link to the original source code from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/CspListener.java
 *
 * Interface which allows interested clients to register at a CSP solver
 * and follow its progress step by step.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public interface CspListener<VAR extends Variable, VAL> {
    /**
     * Informs about changed assignments and inference steps.
     *
     * @param csp        a CSP, possibly changed by an inference step.
     * @param assignment a new assignment or null if the last processing step was an inference step.
     * @param variable   a variable, whose domain or assignment value has been changed (may be null).
     */
    void stateChanged(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR variable);

    /**
     * A simple CSP listener implementation which counts assignment changes and changes caused by
     * inference steps and provides some metrics.
     * @author Ruediger Lunde
     */
    class StepCounter<VAR extends Variable, VAL> implements CspListener<VAR, VAL> {
        private int assignmentCount = 0;
        private int inferenceCount = 0;

        @Override
        public void stateChanged(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR variable) {
            if (assignment != null)
                ++assignmentCount;
            else
                ++inferenceCount;
        }

        public void reset() {
            assignmentCount = 0;
            inferenceCount = 0;
        }

        /**
         * Returns the number of assignments.
         */
        public Metrics getResults() {
            Metrics result = new Metrics(); // Hashtable<String, String>
            result.set("assignmentCount", assignmentCount); // set(String name, int i)
            if (inferenceCount != 0)
                result.set("inferenceCount", inferenceCount);
            return result;
        }
    }
}