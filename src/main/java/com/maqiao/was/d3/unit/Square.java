package com.maqiao.was.d3.unit;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.media.j3d.TransformGroup;

import static com.maqiao.was.d3.MQConst.EnumAxis;

import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.element.Const;
import com.maqiao.was.d3.painting.MQFont;
import com.maqiao.was.d3.painting.MQLineUtils;

/**
 * 正方形 只针对某个轴的方形，斜面非法
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public final class Square {
	Point a = new Point();
	Point b = new Point();
	Point c = new Point();
	Point d = new Point();
	/** 判断平面与某轴相垂直 */
	EnumAxis axis = null;
	/** 平面中心多个点以某轴平行 */
	EnumAxis colAxis = EnumAxis.Z;
	List<Point> cPointList = new ArrayList<Point>();
	/** 点颜色 */
	Color cPointColor = null;
	/** 文字内容 */
	List<String> textList = new ArrayList<String>();
	/** 文字方向 */
	EnumAxisExt textAxisWidth = EnumAxisExt.X0;
	/** 文字方向 */
	EnumAxisExt textAxisHeight = EnumAxisExt.Y0;
	List<Point> lPointList = new ArrayList<Point>();

	public Square() {

	}

	public Square(Point a, Point b, Point c, Point d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Square(EnumAxis colAxis, Color pointColor, Point a, Point b, Point c, Point d) {
		this.colAxis = colAxis;
		this.cPointColor = pointColor;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public TransformGroup getFormGroup() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		for (int i = 0; i < cPointList.size(); i++)
			group.addChild(cPointList.get(i).getNode());
		return group;
	}
	/**
	 * 在平面里写上文件，并输出
	 * @return TransformGroup
	 */
	public TransformGroup writeTextList() {
		return writeTextList(Const.ACC_fontSize);
	}
	/**
	 * 在平面里写上文件，并输出
	 * @param fontSize float
	 * @return TransformGroup
	 */
	public TransformGroup writeTextList(float fontSize) {
		if (textList == null || textList.size() == 0) return null;
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		int count = textList.size();
		Square newSquare = this.toClone();
		newSquare.putPoint(count);
		List<Point> lPointList = newSquare.lPointList;
		for (int i = 0; i < count; i++) {
			Point p = lPointList.get(i);
			p.setXoffset(Const.ACC_fontOffset);
			group.addChild(MQFont.getFontText3D(p, textList.get(i), Const.ACC_fontColor,fontSize, textAxisWidth, textAxisHeight));
		}
		return group;		
	}

	/**
	 * 设置多个点
	 * @param count int
	 */
	public void putPoint(int count) {
		if (count <= 0 || (!isSafe())) return;
		installPointList(count);
	}

	/**
	 * 设置新的点
	 */
	public void putNewPoint() {
		if (!isSafe()) return;
		int count = cPointList.size() + 1;
		installPointList(count);
	}

	/**
	 * 设置多个中心点
	 * @param count int
	 */
	void installPointList(int count) {
		cPointList.clear();
		if (count <= 0 || axis == null || colAxis == null || axis == colAxis) return;
		/* 极值 */
		double[][] extremArr = MQLineUtils.getExtremum(a, b, c, d);
		double x = 0, y = 0, z = 0;
		double x0 = 0, y0 = 0, z0 = 0;
		double size;
		switch (colAxis) {
		case X:
			size = (extremArr[0][0] - extremArr[0][1]) / (count + 1);/* 步长 */
			break;
		case Y:
			size = (extremArr[1][0] - extremArr[1][1]) / (count + 1);/* 步长 */
			break;
		default:
			size = (extremArr[2][0] - extremArr[2][1]) / (count + 1);/* 步长 */
		}
		switch (axis) {
		case X:
			x = x0 = a.getX();/* 固定 */
			break;
		case Y:
			y = y0 = a.getY();/* 固定 */
			break;
		default:
			z = z0 = a.getZ();/* 固定 */
		}
		/* 剩余轴 */
		EnumAxis remain = EnumAxis.getEnumAxis(7 ^ axis.getValue() ^ colAxis.getValue());
		if (remain == null) return;
		switch (remain) {
		case X:
			x = (x0 = extremArr[0][1]) + (extremArr[0][0] - extremArr[0][1]) / 2;/* 固定 */
			break;
		case Y:
			y = (y0 = extremArr[1][1]) + (extremArr[1][0] - extremArr[1][1]) / 2;/* 固定 */
			break;
		default:
			z = (z0 = extremArr[2][1]) + (extremArr[2][0] - extremArr[2][1]) / 2;/* 固定 */
		}
		for (int i = 1; i <= count; i++) {
			switch (colAxis) {
			case X:
				x = x0 = extremArr[0][1] + i * size;
				break;
			case Y:
				y = y0 = extremArr[1][1] + i * size;
				break;
			default:
				z = z0 = extremArr[2][1] + i * size;
			}
			Point e = new Point(cPointColor, x, y, z);
			cPointList.add(e);
			lPointList.add(new Point(x0, y0, z0));
		}
	}

	public boolean isSafe() {
		check();
		if (axis == null) return false;
		if (!isVerticalBox()) return false;
		return true;
	}

	/**
	 * 判断是否为垂直方形
	 * @return boolean
	 */
	boolean isVerticalBox() {
		if (axis == EnumAxis.X) {
			if (isVerti(EnumAxis.Y) && isVerti(EnumAxis.Z)) return true;
		}
		if (axis == EnumAxis.Y) {
			if (isVerti(EnumAxis.X) && isVerti(EnumAxis.Z)) return true;
		}
		if (axis == EnumAxis.Z) {
			if (isVerti(EnumAxis.X) && isVerti(EnumAxis.Y)) return true;
		}
		return false;
	}

	private boolean isVerti(EnumAxis axis) {
		return (a.equals(axis, b) && c.equals(axis, d)) || (a.equals(axis, c) && b.equals(axis, d)) || (a.equals(axis, d) && b.equals(axis, c));
	}

	/**
	 * 判断平面与某轴垂直
	 */
	void check() {
		if (axis != null) return;
		if (a.equals(EnumAxis.X, b, c, d)) {
			axis = EnumAxis.X;
			return;
		}
		if (a.equals(EnumAxis.Y, b, c, d)) {
			axis = EnumAxis.Y;
			return;
		}
		if (a.equals(EnumAxis.Z, b, c, d)) {
			axis = EnumAxis.Z;
			return;
		}
	}

	public List<Point> getPointList() {
		return cPointList;
	}

	public Point getA() {
		return a;
	}

	public Point getB() {
		return b;
	}

	public Point getC() {
		return c;
	}

	public Point getD() {
		return d;
	}

	public EnumAxis getAxis() {
		return axis;
	}

	public EnumAxis getColAxis() {
		return colAxis;
	}

	public void setA(Point a) {
		this.a = a;
	}

	public void setB(Point b) {
		this.b = b;
	}

	public void setC(Point c) {
		this.c = c;
	}

	public void setD(Point d) {
		this.d = d;
	}

	public void setAxis(EnumAxis axis) {
		this.axis = axis;
	}

	public void setColAxis(EnumAxis colAxis) {
		this.colAxis = colAxis;
	}

	public Color getPointColor() {
		return cPointColor;
	}

	public void setPointColor(Color pointColor) {
		this.cPointColor = pointColor;
	}

	public List<Point> getcPointList() {
		return cPointList;
	}

	public void setcPointList(List<Point> cPointList) {
		this.cPointList = cPointList;
	}

	public Color getcPointColor() {
		return cPointColor;
	}

	public void setcPointColor(Color cPointColor) {
		this.cPointColor = cPointColor;
	}

	public List<Point> getlPointList() {
		return lPointList;
	}

	public void setlPointList(List<Point> lPointList) {
		this.lPointList = lPointList;
	}

	/**
	 * 赋值
	 * @param a Point
	 * @param b Point
	 * @param c Point
	 * @param d Point
	 */
	public void set(Point a, Point b, Point c, Point d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	/**
	 * 克隆对象
	 * @return Square
	 */
	public Square toClone() {
		Square s = new Square();
		s.a = this.a;
		s.b = this.b;
		s.c = this.c;
		s.d = this.d;
		s.axis = this.axis;
		s.colAxis = this.colAxis;
		s.cPointColor = this.cPointColor;
		s.cPointList = this.cPointList;
		s.lPointList = this.lPointList;
		s.textList = this.textList;
		s.textAxisWidth = this.textAxisWidth;
		s.textAxisHeight = this.textAxisHeight;
		return s;
	}

	@Override
	public String toString() {
		return "Square [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", axis=" + axis + ", colAxis=" + colAxis + ", pointList=" + cPointList + ", pointColor=" + cPointColor + "]";
	}

	public List<String> getTextList() {
		return textList;
	}
	/**
	 * 给平面设置文字，允许多行
	 * @param arr String[]
	 */
	public void setTextList(String... arr) {
		if (arr == null) return;
		this.textList = Arrays.asList(arr);
	}

	public void setTextList(List<String> textList) {
		this.textList = textList;
	}

	public EnumAxisExt getTextAxisWidth() {
		return textAxisWidth;
	}

	public void setTextAxisWidth(EnumAxisExt textAxisWidth) {
		this.textAxisWidth = textAxisWidth;
	}

	public EnumAxisExt getTextAxisHeight() {
		return textAxisHeight;
	}

	public void setTextAxisHeight(EnumAxisExt textAxisHeight) {
		this.textAxisHeight = textAxisHeight;
	}

}
