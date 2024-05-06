package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.Variables;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.*;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.AbstractBacktrackingSolver;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.FlexibleBacktrackingSolver;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference.AC3Strategy;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference.InferenceLog;
import ai_algorithm.specific_algorithm_logic.csp.csp_examples.NotEqualConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImplementCSPwithAC3Strategy_test {

    private static CSP<Variable, String> csp;
    public static final Variable WA = new Variable("WA");
    public static final Variable NT = new Variable("NT");
    public static final Variable SA = new Variable("SA");
    public static final Variable Q = new Variable("Q");
    public static final Variable NSW = new Variable("NSW");
    public static final Variable V = new Variable("V");
    public static final Variable T = new Variable("T");
    public static final String RED = "RED";
    public static final String GREEN = "GREEN";
    public static final String BLUE = "BLUE";


    public static void main(String[] args) {

        // Gernerierung der verschiedenen Australischen Staaten
        List<Variable> variables = new ArrayList<Variable>();
        variables.add(WA);
        variables.add(NT);
        variables.add(SA);
        variables.add(Q);
        variables.add(NSW);
        variables.add(V);
        variables.add(T);

        // Erstellen der notwendigen Domänen mit den jeweiligen Farben
        List<String> values = Arrays.asList(RED, GREEN, BLUE);
        Domain<String> valueDomain = new Domain<>(values);

        // Erstellen der jeweiligen Constraints für die benachbarten Territorien
        Constraint<Variable, String> wa_nt = new NotEqualConstraint<>(WA, NT);
        Constraint<Variable, String> wa_sa = new NotEqualConstraint<>(WA, SA);
        Constraint<Variable, String> nt_sa = new NotEqualConstraint<>(NT, SA);
        Constraint<Variable, String> nt_q = new NotEqualConstraint<>(NT, Q);
        Constraint<Variable, String> sa_q = new NotEqualConstraint<>(SA, Q);
        Constraint<Variable, String> sa_nsw = new NotEqualConstraint<>(SA, NSW);
        Constraint<Variable, String> q_nsw = new NotEqualConstraint<>(Q, NSW);
        Constraint<Variable, String> sa_v = new NotEqualConstraint<>(SA, V);
        Constraint<Variable, String> nsw_v = new NotEqualConstraint<>(NSW, V);

        // Erstellen der CSP-Instanz und hinzufügen der Variablen, Domänen und Constraints
        csp = new CSP<>(variables);
        // Hinzufügen der Variablen zum CSP
        // Variablen wurden bereits oben erstellt und durch die Einschränkungen hinzugefügt
        // Hinzufügen der Domänen im Ausgangszustand (Alle Domänen haben alle 3 Farben am Start zur Auswahl)
        csp.setDomain(WA, valueDomain);
        csp.setDomain(NT, valueDomain);
        csp.setDomain(SA, valueDomain);
        csp.setDomain(Q, valueDomain);
        csp.setDomain(NSW, valueDomain);
        csp.setDomain(V, valueDomain);
        csp.setDomain(T, valueDomain);
        // Hinzufügen der Variablen
        csp.addConstraint(wa_nt);
        csp.addConstraint(wa_sa);
        csp.addConstraint(nt_sa);
        csp.addConstraint(nt_q);
        csp.addConstraint(sa_q);
        csp.addConstraint(sa_nsw);
        csp.addConstraint(q_nsw);
        csp.addConstraint(sa_v);
        csp.addConstraint(nsw_v);

        // Erstellen einer weiteren AC3Strategy-Instanz
        AC3Strategy ac3Strategy = new AC3Strategy();
        InferenceLog<Variable, String> log = ac3Strategy.apply(csp);

        // Ausgabe der Domänen nach Anwendung der AC3-Strategie
        if(log.isEmpty()) {
            System.out.println("AC3 was successful");
            printDomains(csp);
            printAssignment(csp, ac3Strategy);
            printInferences(log);
        } else {
            System.out.println("AC3 was not successful");
        }

}

    public void solve() {
        // TODO: Implement the solving

    }


    private static void printInferences(InferenceLog<Variable, String> log) {
        System.out.println("\nInferences: ");
        System.out.println(log.toString());
    }

    private static void printAssignment(CSP<Variable, String> csp, AC3Strategy ac3Strategy) {
        System.out.println("\nAktuelle Zuordnungen: ");
        for(Variable var : csp.getVariables()) {
            Domain<String> domain = csp.getDomain(var);
            if(domain.size() == 1) {
                System.out.println(var.getName() + ": " + domain.get(0));
            }else {
                System.out.println(var.getName() + ": " + "Keine eindeutlige Zuordnung gefunden");
            }
        }
    }

    private static void printDomains(CSP<Variable, String> csp) {
        System.out.println("Aktuelle Domänen: ");
        for(Variable var : csp.getVariables()) {
            System.out.println(var.getName() + ": " + csp.getDomain(var));
        }
    }
}
