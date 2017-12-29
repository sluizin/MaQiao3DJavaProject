package com.maqiao.was.d3.element;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.maqiao.was.d3.MQUtilsRandColor;
import com.maqiao.was.d3.MQConst.EnumAxisExt;

/**
 * 基础类，含定位与长宽高
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class Abstract {
	/** box定位[离原点最近] */
	protected double[] location = { 0f, 0f, 0f };
	/** 宽 */
	protected float width = 1f;
	/** 高 */
	protected float height = 1f;
	/** 长 */
	protected float length = 1f;
	/** 背景色 */
	protected Color bgColor = null;
	/**
	 * 颜色组。六个方向
	 */
	protected Map<EnumAxisExt, Color> CubeColorMap = new HashMap<EnumAxisExt, Color>();

	/** 颜色组 */
	protected static final MQUtilsRandColor ACC_RandColor = new MQUtilsRandColor();

	public double getX() {
		return location[0];
	}

	public double getY() {
		return location[1];
	}

	public double getZ() {
		return location[2];
	}

	public void setLocation(double[] location) {
		this.location = location;
	}

	public double[] getLocation() {
		return location;
	}

	/**
	 * 极值
	 * @return double
	 */
	public double getTopXmin() {
		return location[0];
	}

	/**
	 * 极值
	 * @return double
	 */
	public double getTopXmax() {
		return location[0] + width;
	};

	/**
	 * 极值
	 * @return double
	 */
	public double getTopYmin() {
		return location[1];
	}

	/**
	 * 极值
	 * @return double
	 */
	public double getTopYmax() {
		return location[1] + length;
	}

	/**
	 * 极值
	 * @return double
	 */
	public double getTopZmin() {
		return location[2];
	}

	/**
	 * 极值
	 * @return double
	 */
	public double getTopZmax() {
		return location[2] + height;
	}

	public void setX(double x) {
		this.location[0] = x;
	}

	public void setY(double y) {
		this.location[1] = y;
	}

	public void setZ(double z) {
		this.location[2] = z;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public Map<EnumAxisExt, Color> getCubeColorMap() {
		return CubeColorMap;
	}

	public void setCubeColorMap(Map<EnumAxisExt, Color> cubeColorMap) {
		CubeColorMap = cubeColorMap;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

}
