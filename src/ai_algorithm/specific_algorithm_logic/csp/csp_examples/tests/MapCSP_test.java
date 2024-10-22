package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.csp_examples.MapCSP;

public class MapCSP_test {

    /**
     * Main method to test the MapCSP class
     * This class gives an example of a CSP with variables, domains and constraints
     * The output is a table with the variables, their domains and the constraints for this CSP-example
     *
     * @param args
     *
     * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
     * @author Alexander (Comments adjusted)
     */
    public static void main(String[] args){

        MapCSP mapCSP = new MapCSP(); // create a new MapCSP object
        System.out.println("Constraints = " + mapCSP.getConstraints()); // print the constraints
        String line = new String(new char[101]).replace('\0', '-'); // create a char array with 101 elements and replace '\0'with '-'
        System.out.println(line); // print the line of the table with the '-'
//        System.out.format("| %-9S | %18S |%66S|\n", "variables"), StringUtils.center("domains", 18), StringUtils.center("corresponding constraints", 66));
        System.out.format("| %-9S | %18S |%66S|\n", "variables", "domains     ", "corresponding constraints                    "); // print the header of the table (Variables, Domains, Correspoding Constraints)
        System.out.println(line); // print the line of the table with the '-' again
        mapCSP.getVariables().forEach( // for each variable in the list of variables
                var -> System.out.format("| %-9s | %-18s | %-64s |%n", var,
                        mapCSP.getDomain(var), mapCSP.getConstraints(var)) // print the variable, the domain and the constraints for this variable in the table
        );
        System.out.println(line); // print the line of the table with the '-' again

    }

}

