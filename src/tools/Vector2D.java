package tools;

import java.util.List;

import tools.Vector2D;

public class Vector2D {

	public float x, y;
	public final static Vector2D ZERO = new Vector2D(0, 0);
	public final static Vector2D ONE = new Vector2D(1, 1);
	public final static Vector2D UP = new Vector2D(0, -1);
	public final static Vector2D DOWN = new Vector2D(0, 1);
	public final static Vector2D LEFT = new Vector2D(-1, 0);
	public final static Vector2D RIGHT = new Vector2D(1, 0);

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D() {
		this.x = 0.0f;
		this.y = 0.0f;
	}

	public Vector2D(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float distanceTo(Vector2D v) {
		return (float) Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
	};

	// add
	public Vector2D add(Vector2D v) {
		return new Vector2D(this.x + v.x, this.y + v.y);
	};

	// sub
	public Vector2D sub(Vector2D v) {
		return new Vector2D(this.x - v.x, this.y - v.y);
	};

	// mul
	public Vector2D mul(float f) {
		return new Vector2D(this.x * f, this.y * f);
	}

	public Vector2D mul(Vector2D v) { // changes this.vector
		return new Vector2D(this.x * v.x, this.y * v.y);
	};

	// div
	public Vector2D div(Vector2D v) { // changes this.vector
		return new Vector2D(this.x / v.x, this.y / v.y);
	};

	public Vector2D div(float f) { // changes this.vector
		return new Vector2D(this.x / f, this.y / f);
	};

	public Vector2D normalized() {
		float length = length();
		return new Vector2D(this.x / length, this.y / length);
	}

	public Vector2D directionTo(Vector2D v) {
		return new Vector2D(v.x - this.x, v.y - this.y).normalized();
	}

	public Vector2D clamp(Vector2D min, Vector2D max) {
		Vector2D ret = new Vector2D();
		ret.x = Math.min(Math.max(min.x, this.x), max.x);
		ret.y = Math.min(Math.max(min.y, this.y), max.y);

		return ret;
	}

	public Vector2D rotate(double angle) {
		Vector2D ret = new Vector2D();
		ret.x = (float) (this.x * Math.cos(angle) - this.y * Math.sin(angle));
		ret.y = (float) (this.x * Math.sin(angle) + this.y * Math.cos(angle));
		return ret;
	}

	public Vector2D interpolate(Vector2D other, double factor) {
		var vA = this.mul((float) factor);
		var vB = other.mul((float) (1.0 - factor));
		return vA.add(vB);
	}

	public Vector2D interpolate(List<Vector2D> vectors) {
		float x = 0;
		float y = 0;
		for (Vector2D vector : vectors) {
			x += vector.x / vectors.size();
			y += vector.y / vectors.size();
		}

		Vector2D ret = new Vector2D(x, y);
		return ret;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
