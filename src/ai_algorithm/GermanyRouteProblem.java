package ai_algorithm;

import java.util.*;

import ai_algorithm.problems.Problem;

public class GermanyRouteProblem extends Problem {
	
    protected static final Map<String, Map<String, Integer>> cityDistances = Map.ofEntries(
            Map.entry("Aachen", Map.ofEntries(Map.entry("Essen", 123), Map.entry("Koblenz", 145), Map.entry("Köln", 60))),
            Map.entry("Augsburg", Map.ofEntries(Map.entry("Garmisch-Part.", 117), Map.entry("München", 61), Map.entry("Stuttgart", 149), Map.entry("Ulm", 83))),
            Map.entry("Bayreuth", Map.ofEntries(Map.entry("Nürnberg", 74), Map.entry("Würzburg", 147))),
            Map.entry("Berlin", Map.ofEntries(Map.entry("Cottbus", 125), Map.entry("Frankfurt/Oder", 91), Map.entry("Magdeburg", 131), Map.entry("Neubrandenburg", 130))),
            Map.entry("Bremen", Map.ofEntries(Map.entry("Hamburg", 110), Map.entry("Hannover", 118), Map.entry("Osnabrück", 120), Map.entry("Wilhelmshaven", 110))),
            Map.entry("Chemnitz", Map.ofEntries(Map.entry("Erfurt", 160), Map.entry("Leipzig", 90))),
            Map.entry("Cottbus", Map.ofEntries(Map.entry("Berlin", 125), Map.entry("Dresden", 138), Map.entry("Frankfurt/Oder", 119))),
            Map.entry("Dresden", Map.ofEntries(Map.entry("Cottbus", 138), Map.entry("Leipzig", 140))),
            Map.entry("Erfurt", Map.ofEntries(Map.entry("Kassel", 135), Map.entry("Chemnitz", 160))),
            Map.entry("Essen", Map.ofEntries(Map.entry("Aachen", 123), Map.entry("Köln", 75), Map.entry("Münster", 87), Map.entry("Osnabrück", 135))),
            Map.entry("Frankfurt/Main", Map.ofEntries(Map.entry("Fulda", 95), Map.entry("Karlsruhe", 135), Map.entry("Koblenz", 125), Map.entry("Mannheim", 106), Map.entry("Würzburg", 130))),
            Map.entry("Frankfurt/Oder", Map.ofEntries(Map.entry("Berlin", 91), Map.entry("Cottbus", 119))),
            Map.entry("Freiburg", Map.ofEntries(Map.entry("Karlsruhe", 130))),
            Map.entry("Fulda", Map.ofEntries(Map.entry("Frankfurt/Main", 95), Map.entry("Kassel", 105), Map.entry("Würzburg", 100))),
            Map.entry("Garmisch-Part.", Map.ofEntries(Map.entry("Augsburg", 117), Map.entry("München", 89))),
            Map.entry("Hamburg", Map.ofEntries(Map.entry("Bremen", 110), Map.entry("Kiel", 85), Map.entry("Rostock", 140), Map.entry("Schwerin", 120))),
            Map.entry("Hannover", Map.ofEntries(Map.entry("Bremen", 118), Map.entry("Magdeburg", 136), Map.entry("Osnabrück", 135))),
            Map.entry("Karlsruhe", Map.ofEntries(Map.entry("Frankfurt/Main", 135), Map.entry("Freiburg", 130), Map.entry("Mannheim", 58), Map.entry("Stuttgart", 81))),
            Map.entry("Kassel", Map.ofEntries(Map.entry("Erfurt", 135), Map.entry("Fulda", 105))),
            Map.entry("Kiel", Map.ofEntries(Map.entry("Hamburg", 85), Map.entry("Schwerin", 139))),
            Map.entry("Koblenz", Map.ofEntries(Map.entry("Aachen", 145), Map.entry("Frankfurt/Main", 125), Map.entry("Köln", 110), Map.entry("Mannheim", 145), Map.entry("Trier", 128))),
            Map.entry("Köln", Map.ofEntries(Map.entry("Aachen", 60), Map.entry("Essen", 75), Map.entry("Koblenz", 110), Map.entry("Münster", 144))),
            Map.entry("Leipzig", Map.ofEntries(Map.entry("Dresden", 140), Map.entry("Magdeburg", 108), Map.entry("Chemnitz", 90))),
            Map.entry("Lindau", Map.ofEntries(Map.entry("Ulm", 126))),
            Map.entry("Magdeburg", Map.ofEntries(Map.entry("Berlin", 131), Map.entry("Hannover", 136), Map.entry("Leipzig", 108))),
            Map.entry("Mannheim", Map.ofEntries(Map.entry("Frankfurt/Main", 106), Map.entry("Karlsruhe", 58), Map.entry("Koblenz", 145), Map.entry("Saarbrücken", 117), Map.entry("Stuttgart", 138), Map.entry("Trier", 146))),
            Map.entry("München", Map.ofEntries(Map.entry("Augsburg", 81), Map.entry("Garmisch-Part.", 89), Map.entry("Regensburg", 106), Map.entry("Ulm", 124))),
            Map.entry("Münster", Map.ofEntries(Map.entry("Essen", 87), Map.entry("Köln", 144), Map.entry("Osnabrück", 60))),
            Map.entry("Neubrandenburg", Map.ofEntries(Map.entry("Berlin", 130), Map.entry("Rostock", 103))),
            Map.entry("Nürnberg", Map.ofEntries(Map.entry("Bayreuth", 74), Map.entry("Regensburg", 105), Map.entry("Würzburg", 108))),
            Map.entry("Osnabrück", Map.ofEntries(Map.entry("Bremen", 120), Map.entry("Essen", 135), Map.entry("Hannover", 135), Map.entry("Münster", 60))),
            Map.entry("Passau", Map.ofEntries(Map.entry("Regensburg", 128))),
            Map.entry("Regensburg", Map.ofEntries(Map.entry("München", 106), Map.entry("Nürnberg", 105), Map.entry("Passau", 128))),
            Map.entry("Rostock", Map.ofEntries(Map.entry("Hamburg", 140), Map.entry("Neubrandenburg", 103), Map.entry("Schwerin", 90))),
            Map.entry("Saarbrücken", Map.ofEntries(Map.entry("Mannheim", 117), Map.entry("Trier", 103))),
            Map.entry("Schwerin", Map.ofEntries(Map.entry("Hamburg", 120), Map.entry("Kiel", 139), Map.entry("Rostock", 90))),
            Map.entry("Stuttgart", Map.ofEntries(Map.entry("Augsburg", 149), Map.entry("Karlsruhe", 81), Map.entry("Mannheim", 138), Map.entry("Ulm", 100))),
            Map.entry("Trier", Map.ofEntries(Map.entry("Koblenz", 128), Map.entry("Mannheim", 146), Map.entry("Saarbrücken", 103))),
            Map.entry("Ulm", Map.ofEntries(Map.entry("Augsburg", 83), Map.entry("Lindau", 126), Map.entry("München", 124), Map.entry("Stuttgart", 100))),
            Map.entry("Wilhelmshaven", Map.ofEntries(Map.entry("Bremen", 110))),
            Map.entry("Würzburg", Map.ofEntries(Map.entry("Bayreuth", 147), Map.entry("Frankfurt/Main", 130), Map.entry("Fulda", 100), Map.entry("Nürnberg", 108)))
    );

    protected static final Map<String, Coordinate> cityCoords = Map.ofEntries(
            Map.entry("Aachen", new Coordinate(50.783, 6.083)),
            Map.entry("Augsburg", new Coordinate(48.367, 10.900)),
            Map.entry("Bayreuth", new Coordinate(49.950, 11.583)),
            Map.entry("Berlin", new Coordinate(52.523, 13.413)),
            Map.entry("Bremen", new Coordinate(53.083, 8.817)),
            Map.entry("Cottbus", new Coordinate(51.766, 14.335)),
            Map.entry("Chemnitz", new Coordinate(50.830, 12.917)),
            Map.entry("Dresden", new Coordinate(51.050, 13.739)),
            Map.entry("Erfurt", new Coordinate(50.986, 11.022)),
            Map.entry("Essen", new Coordinate(51.467, 7.017)),
            Map.entry("Frankfurt/Main", new Coordinate(50.117, 8.683)),
            Map.entry("Frankfurt/Oder", new Coordinate(52.342, 14.545)),
            Map.entry("Freiburg", new Coordinate(48.000, 7.850)),
            Map.entry("Fulda", new Coordinate(50.550, 9.683)),
            Map.entry("Garmisch-Part.", new Coordinate(47.500, 11.100)),
            Map.entry("Hamburg", new Coordinate(53.567, 10.033)),
            Map.entry("Hannover", new Coordinate(52.383, 9.733)),
            Map.entry("Karlsruhe", new Coordinate(49.017, 8.400)),
            Map.entry("Kassel", new Coordinate(51.317, 9.500)),
            Map.entry("Kiel", new Coordinate(54.333, 10.133)),
            Map.entry("Koblenz", new Coordinate(50.350, 7.600)),
            Map.entry("Köln", new Coordinate(50.950, 6.950)),
            Map.entry("Leipzig", new Coordinate(51.339, 12.377)),
            Map.entry("Lindau", new Coordinate(47.550, 9.683)),
            Map.entry("Magdeburg", new Coordinate(52.122, 11.619)),
            Map.entry("Mannheim", new Coordinate(49.483, 8.467)),
            Map.entry("München", new Coordinate(48.133, 11.567)),
            Map.entry("Münster", new Coordinate(51.967, 7.633)),
            Map.entry("Neubrandenburg", new Coordinate(53.555, 13.262)),
            Map.entry("Nürnberg", new Coordinate(49.450, 11.083)),
            Map.entry("Osnabrück", new Coordinate(52.283, 8.050)),
            Map.entry("Passau", new Coordinate(48.567, 13.467)),
            Map.entry("Regensburg", new Coordinate(49.017, 12.100)),
            Map.entry("Rostock", new Coordinate(54.089, 12.125)),
            Map.entry("Saarbrücken", new Coordinate(49.233, 7.000)),
            Map.entry("Schwerin", new Coordinate(53.628, 11.412)),
            Map.entry("Stuttgart", new Coordinate(48.783, 9.183)),
            Map.entry("Trier", new Coordinate(49.750, 6.650)),
            Map.entry("Ulm", new Coordinate(48.400, 9.983)),
            Map.entry("Wilhelmshaven", new Coordinate(53.517, 8.117)),
            Map.entry("Würzburg", new Coordinate(49.800, 9.933))
    );


    private final CityState startCity;
    private final CityState goalCity;

    public GermanyRouteProblem() {
        this("Berlin", "Hamburg");
    }

    public GermanyRouteProblem(String startCity, String goalCity) {
        this.startCity = new CityState(this, startCity);
        this.goalCity = new CityState(this, goalCity);
    }

    @Override
    public State getInitialState() {
        return startCity;
    }

    @Override
    public boolean isGoalState(State state) {
        if( !(state instanceof CityState) ) {
            return false;
        }
        CityState cityState = (CityState) state;
        return cityState.equals(goalCity);
    }

    @Override
    public List<String> getActions(State state) {
        CityState city = toCityState(state);
        if( city == null || !cityDistances.containsKey(city.toString()) ) {
            return Collections.emptyList();
        }
        Map<String, Integer> neighbors = cityDistances.get(city.toString());
        return neighbors.keySet().stream().toList();
    }

    @Override
    public State getSuccessor(State state, String action) {
        CityState city = toCityState(state);
        if( city == null ) {
            // TODO: better handling
            System.out.println("State is not a valid City");
            return null;
        }
        if( !cityDistances.containsKey(city.toString()) ) {
            // TODO: better handling
            System.out.println("City '" + city + "' does not exist!");
            return null;
        }
        if( !cityDistances.get(city.toString()).containsKey(action) ) {
            // TODO: better handling
            System.out.println("Action '" + action + "' not possible in city '" + city + "'");
            return null;
        }
        CityState succ = new CityState(this, action);
        return succ;
    }

    @Override
    public double getCost(State s, String action, State succ) {
        CityState city = toCityState(s);
        if( city == null ) {
            // TODO: better handling
            System.out.println("State is not a valid City");
            return Double.POSITIVE_INFINITY;
        }
        if( !cityDistances.containsKey(city.toString()) ) {
            // TODO: better handling
            System.out.println("City '" + city + "' does not exist!");
            return Double.POSITIVE_INFINITY;
        }
        if( !cityDistances.get(city.toString()).containsKey(action) ) {
            // TODO: better handling
            System.out.println("Action '" + action + "' not possible in city '" + city + "'");
            return Double.POSITIVE_INFINITY;
        }
        return cityDistances.get(city.toString()).get(action);
    }

    protected CityState toCityState(State state) {
        if( !(state instanceof CityState) ) {
            return null;
        }
        return (CityState) state;
    }

    record Coordinate(double lon, double lat) {
    }
}
