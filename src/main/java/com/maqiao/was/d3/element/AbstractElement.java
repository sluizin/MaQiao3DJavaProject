package com.maqiao.was.d3.element;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.unit.Point;
import com.maqiao.was.d3.unit.Square;
import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.MQUtilsRandColor;
import com.sun.j3d.utils.geometry.Box;

/**
 * 单元抽象类，包含立方体
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public abstract class AbstractElement {
	/** box定位[离原点最近] */
	double[] location = { 0f, 0f, 0f };
	/** 背景色 */
	Color bgColor = null;
	/**
	 * 颜色组。六个方向
	 */
	Map<EnumAxisExt, Color> boxColorMap = new HashMap<EnumAxisExt, Color>();
	/** 颜色组 */
	MQUtilsRandColor randColor = new MQUtilsRandColor();

	/**
	 * 输出 TransformGroup
	 * @return TransformGroup
	 */
	public abstract TransformGroup getFormGroup();

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

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	/** 宽 */
	float width = 1f;
	/** 高 */
	float height = 1f;
	/** 长 */
	float length = 1f;

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setLength(float length) {
		this.length = length;
	}

	/** 方法名称 */
	String frontName = null;

	/**
	 * 得到Box
	 * @return Node
	 */
	Node getNodeBox() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D boxTransform3D = new Transform3D();
		boxTransform3D.setTranslation(new Vector3d(location[0] + width / 2, location[1] + length / 2, location[2] + height / 2));
		group.setTransform(boxTransform3D);
		Appearance appearance = new Appearance();
		if (bgColor != null) {
			ColoringAttributes r = new ColoringAttributes();
			r.setColor(new Color3f(bgColor));
			appearance.setColoringAttributes(r);
		}
		Box box = new Box(width / 2, length / 2, height / 2, Box.GENERATE_TEXTURE_COORDS, new Appearance());
		for (EnumAxisExt key : EnumAxisExt.values()) {
			Color color = boxColorMap.get(key);
			box.getShape(key.getBoxdire()).setAppearance(MQUtils.colorToAppearance(color == null ? bgColor : color));
		}
		group.addChild(box);
		return group;
	}

	/** 文字左侧偏移量 */
	static final double ACC_fontOffset = 0.1f;

	public String getFrontName() {
		return frontName;
	}

	public void setFrontName(String frontName) {
		this.frontName = frontName;
	}

	public Map<EnumAxisExt, Color> getBoxColorMap() {
		return boxColorMap;
	}

	public void setBoxColorMap(Map<EnumAxisExt, Color> boxColorMap) {
		this.boxColorMap = boxColorMap;
	}

	/** 区块数 */
	int blockCount = 1;

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	/**
	 * 得到某个切面
	 * @param i int
	 * @return Square
	 */
	Square getSquare(int i) {
		if (i < 0 || i > blockCount) return null;
		Point point1 = getSectionPoint(i, 0);
		Point point2 = getSectionPoint(i, 1);
		Point point3 = getSectionPoint(i, 2);
		Point point4 = getSectionPoint(i, 3);
		Square t = new Square(point1, point2, point3, point4);
		return t;
	}

	/**
	 * 得到切面的不同的点
	 * @param num int
	 * @param i int
	 * @return Point
	 */
	Point getSectionPoint(int num, int i) {
		if (num < 0 || num > blockCount) return null;
		double y = location[1] + (length / (blockCount)) * num;
		switch (i % 4) {
		case 0:
			return new Point(location[0], y, location[2]);
		case 1:
			return new Point(location[0], y, location[2] + height);
		case 2:
			return new Point(location[0] + width, y, location[2] + height);
		default:
			return new Point(location[0] + width, y, location[2]);
		}
	}

	/** 名称文字大小 */
	static final float ACC_fontNameSize = 0.026f;
	/** 名称文字颜色 */
	static final Color ACC_fontNameColor = Color.PINK;
	/** 参数文字大小 */
	static final float ACC_fontParaSize = 0.015f;
	/** 参数文字颜色 */
	static final Color ACC_fontParaColor = Color.BLACK;
	/** 注解文字大小 */
	static final float ACC_AnnotationSize = 0.018f;
	/** 注解文字颜色 */
	static final Color ACC_AnnotationColor = Color.BLUE;
	/** 注解行间距 */
	static final float ACC_AnnotationRowSpacing = 0.25f;
	/** 方法或属性与注解的间距 */
	static final double annotationspace = 0.04;

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

}
