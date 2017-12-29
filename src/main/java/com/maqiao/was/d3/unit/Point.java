package com.maqiao.was.d3.unit;

import java.awt.Color;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;

import static com.maqiao.was.d3.MQConst.ACC_Color_BLACK;

import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.painting.MQPoint;
import com.maqiao.was.d3.MQUtils;

/**
 * 空间定位
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class Point {
	double x = 0f;
	double y = 0f;
	double z = 0f;

	/** 是否需要端点球 */
	boolean isEndpoint = false;
	/** 端点颜色 */
	Color color = null;

	/**
	 * 获得本点Node
	 * @return Node
	 */
	public Node getNode() {
		if (isEndpoint && color != null) return MQPoint.getPoint(this, color);
		return null;
	}

	/**
	 * 获得本点Node 直接设置点的大小
	 * @param pointSize float
	 * @return Node
	 */
	public Node getNode(float pointSize) {
		if (isEndpoint && color != null) return MQPoint.getPoint(this, color, pointSize);
		return null;
	}

	/**
	 * 建立新的黑色端点
	 * @param x float
	 * @param y float
	 * @param z float
	 * @return Point
	 */
	public static Point getDefPoint(float x, float y, float z) {
		Point e = new Point();
		e.init(x, y, z);
		e.setColor(ACC_Color_BLACK);
		return e;
	}

	/**
	 * 建立新的黑色端点
	 * @param x double
	 * @param y double
	 * @param z double
	 * @return Point
	 */
	public static Point getDefPoint(double x, double y, double z) {
		Point e = new Point();
		e.init(x, y, z);
		e.setColor(ACC_Color_BLACK);
		return e;
	}

	/**
	 * 建立新的有颜色端点
	 * @param color Color
	 * @param x float
	 * @param y float
	 * @param z float
	 * @return Point
	 */
	public static Point getDefPoint(Color color, float x, float y, float z) {
		Point e = new Point();
		e.init(x, y, z);
		e.setColor(color);
		return e;
	}

	/**
	 * 建立新的有颜色端点
	 * @param color Color
	 * @param x double
	 * @param y double
	 * @param z double
	 * @return Point
	 */
	public static Point getDefPoint(Color color, double x, double y, double z) {
		Point e = new Point();
		e.init(x, y, z);
		e.setColor(color);
		return e;
	}

	/**
	 * 得到多个点
	 * @param points Point[]
	 * @return Node
	 */
	public static Node getNode(Point... points) {
		TransformGroup group = new TransformGroup();
		for (int i = 0; i < points.length; i++)
			group.addChild(points[i].getNode());
		return group;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * 设置颜色
	 * @param color Color
	 */
	public void setColor(Color color) {
		if (color != null) isEndpoint = true;
		else isEndpoint = false;
		this.color = color;
	}

	/**
	 * 输出float数组
	 * @return float[]
	 */
	public float[] toFloat() {
		float[] e = { (float) x, (float) y, (float) z };
		return e;
	}

	/**
	 * 输出double数组
	 * @return double[]
	 */
	public double[] toDouble() {
		double[] e = { x, y, z };
		return e;
	}

	/**
	 * 输出克隆对象
	 * @return Point
	 */
	public Point toClone() {
		Point e = new Point(x, y, z);
		e.setColor(color);
		return e;
	}

	public Point() {

	}

	public Point(Color color, float x, float y, float z) {
		init(x, y, z);
		setColor(color);
	}

	public Point(double[] vector3d) {
		if (vector3d.length > 0) this.x = vector3d[0];
		if (vector3d.length > 1) this.y = vector3d[1];
		if (vector3d.length > 2) this.z = vector3d[2];
	}

	public Point(Color color, double[] vector3d) {
		this.setColor(color);
		if (vector3d.length > 0) this.x = vector3d[0];
		if (vector3d.length > 1) this.y = vector3d[1];
		if (vector3d.length > 2) this.z = vector3d[2];
	}

	/**
	 * 初始化
	 * @param x float
	 * @param y float
	 * @param z float
	 */
	public void init(float x, float y, float z) {
		init(MQUtils.changeDouble(x), MQUtils.changeDouble(y), MQUtils.changeDouble(z));
	}

	/**
	 * 初始化
	 * @param x double
	 * @param y double
	 * @param z double
	 */
	public void init(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * 初始化
	 * @param e Point
	 */
	public void initInfor(Point e) {
		this.x = e.x;
		this.y = e.y;
		this.z = e.z;
	}

	public Point(float x, float y, float z) {
		init(x, y, z);
	}

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(Color color, double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		setColor(color);
	}

	public double getX() {
		return x;
	}

	public double getX(double offset) {
		return x + offset;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setXoffset(double offset) {
		this.x = this.x + offset;
	}

	public double getY() {
		return y;
	}

	public double getY(double offset) {
		return y + offset;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setYoffset(double offset) {
		this.y = this.y + offset;
	}

	public double getZ() {
		return z;
	}

	public double getZ(double offset) {
		return z + offset;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setZoffset(double offset) {
		this.z = this.z + offset;
	}

	public void setOffset(EnumAxisExt axis, double offset) {
		double toffset = Math.abs(offset);
		switch (axis) {
		case X0:
			setXoffset(toffset);
			break;
		case X1:
			setXoffset(-toffset);
			break;
		case Y0:
			setYoffset(toffset);
			break;
		case Y1:
			setYoffset(-toffset);
			break;
		case Z0:
			setZoffset(toffset);
			break;
		default:
			setZoffset(-toffset);
		}
	}

	public void setOffset(EnumAxis axis, double offset) {
		switch (axis) {
		case X:
			setXoffset(offset);
			break;
		case Y:
			setYoffset(offset);
			break;
		default:
			setZoffset(offset);
		}
	}

	/**
	 * 通过i得到x(0),y(1),z(2)的double值
	 * @param i int
	 * @return double
	 */
	public double getLocal(int i) {
		switch (i % 3) {
		case 0:
			return x;
		case 1:
			return y;
		default:
			return z;
		}
	}

	/**
	 * 通过i得到x(0),y(1),z(2)的float值
	 * @param i int
	 * @return float
	 */
	public float getLocalFloat(int i) {
		return (float) getLocal(i);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (isEndpoint ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Point other = (Point) obj;
		if (color == null) {
			if (other.color != null) return false;
		} else if (!color.equals(other.color)) return false;
		if (isEndpoint != other.isEndpoint) return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) return false;
		return true;
	}

	/**
	 * 点的值与其它点的值是否相等
	 * @param points Point[]
	 * @return boolean
	 */
	public boolean equals(EnumAxis axis, Point... points) {
		double vv;
		switch (axis) {
		case X:
			vv = x;
			break;
		case Y:
			vv = y;
			break;
		default:
			vv = z;
		}
		long v = Double.doubleToLongBits(vv);
		if (points.length == 0) return false;
		double v11;
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) return false;
			switch (axis) {
			case X:
				v11 = points[i].x;
				break;
			case Y:
				v11 = points[i].y;
				break;
			default:
				v11 = points[i].z;
			}
			long v1 = Double.doubleToLongBits(v11);
			if (v != v1) return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + ", isEndpoint=" + isEndpoint + ", color=" + color + "]";
	}

	public boolean isEndpoint() {
		return isEndpoint;
	}

	public void setEndpoint(boolean isEndpoint) {
		this.isEndpoint = isEndpoint;
	}
	
}
