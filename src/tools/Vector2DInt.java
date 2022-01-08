package tools;

import java.util.Objects;

public class Vector2DInt {

	public final static Vector2DInt ZERO = new Vector2DInt(0, 0);
	public final static Vector2DInt ONE = new Vector2DInt(1, 1);
	public final static Vector2DInt UP = new Vector2DInt(0, -1);
	public final static Vector2DInt DOWN = new Vector2DInt(0, 1);
	public final static Vector2DInt LEFT = new Vector2DInt(-1, 0);
	public final static Vector2DInt RIGHT = new Vector2DInt(1, 0);

	public int x, y;

	public Vector2DInt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2DInt() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2DInt(Vector2DInt v) {
		this.x = v.x;
		this.y = v.y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float distanceTo(Vector2DInt v) {
		return (float) Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
	};

	// add
	public Vector2DInt add(Vector2DInt v) {
		return new Vector2DInt(this.x + v.x, this.y + v.y);
	};

	// sub
	public Vector2DInt sub(Vector2DInt v) {
		return new Vector2DInt(this.x - v.x, this.y - v.y);
	};

	// mul
	public Vector2DInt mul(float f) {
		return new Vector2DInt((int) (this.x * f), (int) (this.y * f));
	}

	public Vector2DInt mul(Vector2DInt v) { // changes this.vector
		return new Vector2DInt(this.x * v.x, this.y * v.y);
	};

	// div
	public Vector2DInt div(Vector2DInt v) { // changes this.vector
		return new Vector2DInt(this.x / v.x, this.y / v.y);
	};

	public Vector2DInt div(float f) { // changes this.vector
		return new Vector2DInt((int) (this.x / f), (int) (this.y / f));
	};

	public Vector2DInt normalized() {
		float length = length();
		return new Vector2DInt((int) (this.x / length), (int) (this.y / length));
	}

	public Vector2DInt directionTo(Vector2DInt v) {
		return new Vector2DInt(v.x - this.x, v.y - this.y).normalized();
	}

	public Vector2DInt clamp(Vector2DInt min, Vector2DInt max) {
		Vector2DInt ret = new Vector2DInt(ZERO);

		ret.x = Math.min(Math.max(min.x, this.x), max.x);
		ret.y = Math.min(Math.max(min.y, this.y), max.y);

		return ret;
	}

	public boolean equals(Vector2DInt other) {
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}


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
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
