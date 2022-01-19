package settings;

import application.debugger.Debugger;
import javafx.scene.paint.Color;

public class Settings {
	
	
	public final static boolean DEBUGMODE = false;
	
	

	/**
	 * INITIALISATION_PER_FRAME_MAX_COUNT detemens the maximum of how many
	 * gameObjects will be initialized in one frame.
	 * 
	 * a value of 0 disables the visualisation but the console and the
	 * searchalgoritm will be executed.
	 */
	public final static int INITIALISATION_PER_FRAME_MAX_COUNT = 1;

	/**
	 * the minimum delay the debugger is causing has to be greater then 0
	 */
	public final static double DEBUGGER_MINIMUM_TIME_DELAY = 0.1;

	/**
	 * The maximum delay the debugger can have. Has to be greater then
	 * {@link DEBUGGER_MINIMUM_TIME_DELAY}
	 */
	public static final double DEBUGGER_MAXIMUM_TIME_DELAY = 2.0;

	// Colortheme TREEVis
	// QUAD

	static public class DEFAULTCOLORS {

		// General ColorTheme
		// #A882CE
		static Color LIGHT_PURPLE = new Color(0.654, 0.505, 0.807, 1.0);

		// #D58B8B
		static Color LIGHT_RED = new Color(0.835, 0.541, 0.545, 1.0);

		// #B8DB94
		static Color LIGHT_GREEN_YELLOW = new Color(0.345, 0.721, 0.043, 1.0);

		// #9EE1E1
		static Color LIGHT_CYAN = new Color(0.619, 0.882, 0.882, 1.0);

		public static Color EXPANDED = LIGHT_PURPLE;
		public static Color GOAL = LIGHT_RED;
		public static Color EXPANDING = LIGHT_GREEN_YELLOW;
		public static Color IN_FRONTIER = LIGHT_CYAN;
		public static Color IN_MEMORY = Color.GRAY;
		public static Color NOT_IN_MEMORY = Color.LIGHTGRAY;

	}

}
