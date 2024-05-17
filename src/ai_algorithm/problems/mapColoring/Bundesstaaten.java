package ai_algorithm.problems.mapColoring;

import java.util.List;

public class Bundesstaaten {

    String variable;
    List<String> domain;
    List<String> constraints;

    Bundesstaaten (String variable, List<String> domain, List<String> constraints) {
        this.variable = variable;
        this.domain = domain;
        this.constraints = constraints;
    }

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

    public String toString(){
        return this.variable + " " + this.domain + " " + this.constraints;
    }

}
