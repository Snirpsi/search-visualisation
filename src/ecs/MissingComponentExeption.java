package ecs;

public class MissingComponentExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code MissingComponentExeption} with no detail message.
	 */
	public MissingComponentExeption() {
		super();
	}

	/**
	 * Constructs a {@code MissingComponentExeption} with the specified detail
	 * message.
	 *
	 * @param s the detail message.
	 */
	public MissingComponentExeption(String s) {
		super(s);
	}
}
