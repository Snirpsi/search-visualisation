package ai_algorithm.problems.cityState;

import java.util.Objects;
import ai_algorithm.State;
import ai_algorithm.problems.Problem;

public class CityState extends State {

	private final Problem problem;
	private final String city;

	public CityState(Problem problem, String city) {
		this.problem = problem;
		this.city = city;
	}

	@Override
	public Problem getProblem() {
		return problem;
	}

	@Override
	public String toString() {
		return city;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CityState cityState = (CityState) o;
		return problem.equals(cityState.problem) && city.equals(cityState.city);
	}

	@Override
	public int hashCode() {
		return Objects.hash(problem, city);
	}
}