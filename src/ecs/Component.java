package ecs;

/**
 * The {@link Component}(-System) combines the {@link Component} and the System
 * of the Entity Component System (ECS) to one Unit. By adding them to a
 * {@link GameObject} the Object gains the Data (State) and the Behavior
 * (System) of this {@link Component}(-System).
 *
 * For each component a default constructor with no parameters is mandetory.
 * 
 * @author Severin
 *
 */
public abstract class Component {
	/**
	 * Reference of the {@link GameObject} that this {@link Component} is assigned
	 * to.
	 */
	public GameObject entity = null;

	/**
	 * The default constructor of an component.
	 */
	public Component() {
	}

	/**
	 * Method to start the Component.
	 * 
	 * @implNote Not yet used.
	 */
	public void start() {
	}

	@Deprecated(since = "0.4", forRemoval = true)
	/**
	 * This method is deprecated. Components should not save references of other
	 * components.
	 */
	public void fetchDependencies() {
	}

	/**
	 * This function should be called every frame. deltaT is the time elapsed
	 * between frames. It can be used to calculate time sensitive behavior like
	 * physics or animations.
	 * 
	 * @param deltaT
	 */
	public abstract void update(float deltaT);
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