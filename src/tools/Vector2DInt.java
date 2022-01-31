package tools;

import java.util.Objects;

/**
 * The {@link Vector2DInt} class is used to describe a 2D integer vector. The
 * class offers many functions for 2D Vector arithmetics.
 *
 * @version 1.0
 */
public class Vector2DInt {

	public int x, y;

	public final static Vector2DInt ZERO = new Vector2DInt(0, 0);
	public final static Vector2DInt ONE = new Vector2DInt(1, 1);
	public final static Vector2DInt UP = new Vector2DInt(0, -1);
	public final static Vector2DInt DOWN = new Vector2DInt(0, 1);
	public final static Vector2DInt LEFT = new Vector2DInt(-1, 0);
	public final static Vector2DInt RIGHT = new Vector2DInt(1, 0);

	/**
	 * Constructor initializes the vector with the given values.
	 * 
	 * @param x The x (first) value of the vector.
	 * @param y The y (second) value of the vector.
	 */
	public Vector2DInt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Default Constructor initializes the vector with 0 values.
	 */
	public Vector2DInt() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructor initializes the vector with the given values.
	 * 
	 * @param v is the other vector, which is used to initialize this vector.
	 */
	public Vector2DInt(Vector2DInt v) {
		this.x = v.x;
		this.y = v.y;
	}

	/**
	 * Calculates the length of this vector.
	 * 
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
	public float distanceTo(Vector2DInt v) {
		return (float) Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
	};

	/**
	 * This function calculates the arithmetic sum of two vectors and returns it in
	 * an new vector.
	 * 
	 * @param v The other vector.
	 * @return A new Vector which is the sum of this and the other vector v.
	 */
	public Vector2DInt add(Vector2DInt v) {
		return new Vector2DInt(this.x + v.x, this.y + v.y);
	};

	/**
	 * This function calculates the arithmetic subtraction of two vectors and
	 * returns it in an new vector.
	 * 
	 * @param v The vector which is subtracted from this vector.
	 * @return A new Vector which is subtracted of this and the other vector v.
	 */
	public Vector2DInt sub(Vector2DInt v) {
		return new Vector2DInt(this.x - v.x, this.y - v.y);
	};

	/**
	 * This function calculates the arithmetic multiplication of two vectors and
	 * returns it in an new vector.
	 * 
	 * @param f The number which is multiplicated to this vector.
	 * @return A new Vector which is the multiplication of this and the value f.
	 */
	public Vector2DInt mul(float f) {
		return new Vector2DInt((int) (this.x * f), (int) (this.y * f));
	}

	/**
	 * This function calculates the arithmetic multiplication of two vectors and
	 * returns it in an new vector.
	 * 
	 * @param v The vector which is multiplicated to this vector.
	 * @return A new Vector which is the multiplication of this and the other vector
	 *         v.
	 */
	public Vector2DInt mul(Vector2DInt v) {
		return new Vector2DInt(this.x * v.x, this.y * v.y);
	};

	/**
	 * This function calculates the arithmetic division of two vectors and returns
	 * it in an new vector.
	 * 
	 * @param v The vector which is divided on this vector.
	 * @return A new Vector which is the division of this and the other vector v.
	 */
	public Vector2DInt div(Vector2DInt v) {
		return new Vector2DInt(this.x / v.x, this.y / v.y);
	};

	/**
	 * This function calculates the arithmetic division of two vectors and returns
	 * it in an new vector.
	 * 
	 * @param f The number by which this vector is divided.
	 * @return A new Vector which is the division of this and the value f.
	 */
	public Vector2DInt div(float f) {
		return new Vector2DInt((int) (this.x / f), (int) (this.y / f));
	};

	/**
	 * Normalizes this vector and returns a new one. After the normalization the new
	 * vector has a Length of 1.0
	 * 
	 * @return The normalized version of this vector.
	 */
	public Vector2DInt normalized() {
		float length = length();
		return new Vector2DInt((int) (this.x / length), (int) (this.y / length));
	}

	/**
	 * This function calculates the direction from the point this vector is pointing
	 * to to the point the other vector is pointing to and returns it as a new
	 * normalized vector.
	 * 
	 * @param v The other vector.
	 * @return A new Vector with the pointing direction.
	 */
	public Vector2DInt directionTo(Vector2DInt v) {
		return new Vector2DInt(v.x - this.x, v.y - this.y).normalized();
	}

	/**
	 * This function constrains all values of the vector between a given minimum and
	 * a given maximum.
	 * 
	 * @param min The minimum value a value of the vector can have.
	 * @param max The maximum value a value of the vector can have.
	 * @return A new clamped vector.
	 */
	public Vector2DInt clamp(Vector2DInt min, Vector2DInt max) {
		Vector2DInt ret = new Vector2DInt(ZERO);

		ret.x = Math.min(Math.max(min.x, this.x), max.x);
		ret.y = Math.min(Math.max(min.y, this.y), max.y);

		return ret;
	}

	/**
	 * This function test if two {@link Vector2DInt} have the same value.
	 * 
	 * @param v The other vector this one is compared with.
	 * @return If both are equal true is returned else false.
	 */
	public boolean equals(Vector2DInt v) {
		if (this.x != v.x) {
			return false;
		}
		if (this.y != v.y) {
			return false;
		}
		return true;
	}

	/**
	 * This function calculates the hash value.
	 * 
	 * @return The hash value.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * This function test if two {@link Vector2DInt} have the same value.
	 * 
	 * @param obj The other vector this one is compared with.
	 * @return If both are equal true is returned else false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2DInt other = (Vector2DInt) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Creates a string representation of the transposed vector.
	 * 
	 * @return The string representation.
	 */
	@Override
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