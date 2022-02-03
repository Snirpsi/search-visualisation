package tools;

import java.util.List;

/**
 * The {@link Vector2D} class is used to describe a 2D vector with floating
 * point precision. The class offers many functions for 2D Vector arithmetics.
 *
 * @version 1.0
 */
public class Vector2D {

	public float x, y;
	
	public final static Vector2D ZERO = new Vector2D(0, 0);
	public final static Vector2D ONE = new Vector2D(1, 1);
	public final static Vector2D UP = new Vector2D(0, -1);
	public final static Vector2D DOWN = new Vector2D(0, 1);
	public final static Vector2D LEFT = new Vector2D(-1, 0);
	public final static Vector2D RIGHT = new Vector2D(1, 0);

	/**
	 * Constructor initializes the vector with the given values.
	 * 
	 * @param x The x (first) value of the vector.
	 * @param y The y (second) value of the vector.
	 */
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Default Constructor initializes the vector with 0.0 values.
	 */
	public Vector2D() {
		this.x = 0.0f;
		this.y = 0.0f;
	}

	/**
	 * Constructor initializes the vector with the given values.
	 * 
	 * @param v is the other vector, which is used to initialize this vector.
	 */
	public Vector2D(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}

	/**
	 * Constructor initializes the vector with the given values.
	 * 
	 * @param x The x (first) value of the vector.
	 * @param y The y (second) value of the vector.
	 */
	public Vector2D(double x, double y) {
		this((float) x, (float) y);
	}

	/**
	 * Calculates the length of this vector.
	 * @return The length of this vector.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the distance between the two points the vectors are pointing to.
	 * 
	 * @param v The other vector.
	 * @return The distance between the two vectors.
	 */
	public float distanceTo(Vector2D v) {
		return (float) Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
	};

	/**
	 * This function calculates the arithmetic sum of two vectors and returns it in
	 * an new vector.
	 * 
	 * @param v The other vector.
	 * @return A new Vector which is the sum of this and the other vector v.
	 */
	public Vector2D add(Vector2D v) {
		return new Vector2D(this.x + v.x, this.y + v.y);
	};

	/**
	 * This function calculates the arithmetic subtraction of two vectors and
	 * returns it in an new vector.
	 * 
	 * @param v The vector which is subtracted from this vector.
	 * @return A new Vector which is subtracted of this and the other vector v.
	 */
	public Vector2D sub(Vector2D v) {
		return new Vector2D(this.x - v.x, this.y - v.y);
	};

	/**
	 * This function calculates the arithmetic multiplication of two vectors and
	 * returns it in an new vector.
	 * 
	 * @param f The number which is multiplicated to this vector.
	 * @return A new Vector which is the multiplication of this and the value f.
	 */
	public Vector2D mul(float f) {
		return new Vector2D(this.x * f, this.y * f);
	}

	/**
	 * This function calculates the arithmetic multiplication of two vectors and
	 * returns it in an new vector.
	 * 
	 * @param v The vector which is multiplicated to this vector.
	 * @return A new Vector which is the multiplication of this and the other vector
	 *         v.
	 */
	public Vector2D mul(Vector2D v) { // changes this.vector
		return new Vector2D(this.x * v.x, this.y * v.y);
	};

	/**
	 * This function calculates the arithmetic division of two vectors and returns
	 * it in an new vector.
	 * 
	 * @param v The vector which is divided on this vector.
	 * @return A new Vector which is the division of this and the other vector v.
	 */
	public Vector2D div(Vector2D v) { 
		return new Vector2D(this.x / v.x, this.y / v.y);
	};

	/**
	 * This function calculates the arithmetic division of two vectors and returns
	 * it in an new vector.
	 * 
	 * @param f The number by which this vector is divided.
	 * @return A new Vector which is the division of this and the value f.
	 */
	public Vector2D div(float f) {
		return new Vector2D(this.x / f, this.y / f);
	};

	/**
	 * Normalizes this vector and returns a new one. After the normalization the new
	 * vector has a Length of 1.0
	 * 
	 * @return The normalized version of this vector.
	 */
	public Vector2D normalized() {
		float length = length();
		return new Vector2D(this.x / length, this.y / length);
	}

	/**
	 * This function calculates the direction from the point this vector is pointing
	 * to to the point the other vector is pointing to and returns it as a new
	 * normalized vector.
	 * 
	 * @param v The other vector.
	 * @return A new Vector with the pointing direction.
	 */
	public Vector2D directionTo(Vector2D v) {
		return new Vector2D(v.x - this.x, v.y - this.y).normalized();
	}

	/**
	 * This function constrains all values of the vector between a given minimum and
	 * a given maximum.
	 * 
	 * @param min The minimum value a value of the vector can have.
	 * @param max The maximum value a value of the vector can have.
	 * @return A new clamped vector.
	 */
	public Vector2D clamp(Vector2D min, Vector2D max) {
		Vector2D ret = new Vector2D();
		ret.x = Math.min(Math.max(min.x, this.x), max.x);
		ret.y = Math.min(Math.max(min.y, this.y), max.y);

		return ret;
	}

	/**
	 * This function rotates the vector by an angle, in radians and returns it as a
	 * new vector.
	 * 
	 * @param angle The angle the vector is rotated, in radians.
	 * @return The new rotated vector.
	 */
	public Vector2D rotate(double angle) {
		Vector2D ret = new Vector2D();
		ret.x = (float) (this.x * Math.cos(angle) - this.y * Math.sin(angle));
		ret.y = (float) (this.x * Math.sin(angle) + this.y * Math.cos(angle));
		return ret;
	}

	/**
	 * This function linear interpolates between two vectors. A value must be given
	 * to represent the influence of the interpolated vector. A new vector is
	 * returned.
	 * 
	 * @param v      The other vector.
	 * @param factor represents the influence of this vector 1.0 means the
	 *               interpolated vector is equal to this vector, 0.0 means the new
	 *               vector is equal to the other vector v.
	 * @return The new interpolated vector.
	 */
	public Vector2D interpolate(Vector2D v, double factor) {
		var vA = this.mul((float) factor);
		var vB = v.mul((float) (1.0 - factor));
		return vA.add(vB);
	}

	/**
	 * Calculates the average between a list of vectors.
	 * 
	 * @param vectors A list of vectors to average
	 * @return The new average vector.
	 */
	public Vector2D average(List<Vector2D> vectors) {
		float x = 0;
		float y = 0;
		for (Vector2D vector : vectors) {
			x += vector.x / vectors.size();
			y += vector.y / vectors.size();
		}
		Vector2D ret = new Vector2D(x, y);
		return ret;
	}

	/**
	 * Creates a string representation of the transposed vector.
	 * 
	 * @return The string representation.
	 */
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
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
