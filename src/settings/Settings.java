package settings;

//import ecs.components.graphics.drawables.MapCSP2D;
import ecs.components.graphics.drawables.TileMap2D;
import javafx.scene.paint.Color;

/**
 * Settings class contains all the settings for the framework.
 * The settings are divided into different categories.
 * The {@link TREE_LAYOUT} contains settings for the layout of the tree.
 * The {@link TILEMAP} contains settings for the {@link TileMap2D}.
 * The {@link DEFAULTCOLORS} contains different colors for the visualization.
 * The {@link DEBUGMODE} enables or disables the debug mode.
 * The {@link INITIALISATION_PER_FRAME_MAX_COUNT} determents the maximum of how
 * many gameObjects will be initialized in one frame.
 * The {@link DEBUGGER_MINIMUM_TIME_DELAY} and
 * {@link DEBUGGER_MAXIMUM_TIME_DELAY} determents the minimum and maximum delay
 * the debugger is causing
 *
 * @author Severin
 * @author Alexander (Comments adjusted)
 */

public class Settings {

	/**
	 * Enables or disables the debug mode.
	 */
	public final static boolean DEBUGMODE = true;

	static public class TREE_LAYOUT {
		/**
		 * minimum distance between leavenodes
		 */
		public static final int LEAF_DISTANCE = 20;
		public static final double PARENT_DISTANCE = 40;
		public static final double SIBLING_DISTANCE = 1;

	}

	/**
	 * settings regarding the {@link TileMap2D}
	 * 
	 * @author Severin
	 *
	 */
	static public class TILEMAP {
		public static final int TILE_SIZE = 20;
		public static final int BORDER_WITH = 1;
	}

//	/**
//	 * settings regarding the {@link MapCSP2D}
//	 *
//	 * @author Alexander
//	 */
//	static public class MAPCSP {
//		public static final int CIRCLE_NUMBER = 7;
//		// TODO: Brauche ich das in meinem Fall überhaupt ???
//	}


	/**
	 * INITIALISATION_PER_FRAME_MAX_COUNT determents the maximum of how many
	 * gameObjects will be initialized in one frame.
	 * 
	 * a value of 0 disables the visualization but the console and the search
	 * algorithm will be executed.
	 */
	public final static int INITIALISATION_PER_FRAME_MAX_COUNT = 1;

	/**
	 * the minimum delay the debugger is causing has to be greater then 0
	 */
	public final static double DEBUGGER_MINIMUM_TIME_DELAY = 0.1;

	/**
	 * The maximum delay the debugger can have. Has to be greater then
	 * DEBUGGER_NINIMUM_TIME_DELAY
	 */
	public static final double DEBUGGER_MAXIMUM_TIME_DELAY = 2.0;

	/**
	 * Structure contains different colors for use in the framework
	 */
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
/*
 * Copyright (c) 2022 Severin Dippold
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
