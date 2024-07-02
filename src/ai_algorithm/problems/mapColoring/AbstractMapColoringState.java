package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.CspState;

import java.util.*;

public abstract class AbstractMapColoringState extends CspState {

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
    @Override
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

/*
 * Copyright (c) 2024 Alexander Ultsch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */