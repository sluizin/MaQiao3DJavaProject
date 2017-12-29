package com.maqiao.was.d3.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.painting.MQRoundWire;
import com.maqiao.was.d3.unit.Point;
import com.maqiao.was.d3.unit.Square;
import com.sun.j3d.utils.geometry.Box;

/**
 * box
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public abstract class AbstractBox extends Abstract {

	/**
	 * 输出 TransformGroup
	 * @return TransformGroup
	 */
	public abstract TransformGroup getFormGroup();

	/** 文字左侧偏移量 */
	protected static final double ACC_fontOffset = 0.1f;

	/**
	 * 得到Box
	 * @return Node
	 */
	protected Node getNodeBox() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D boxTransform3D = new Transform3D();
		boxTransform3D.setTranslation(new Vector3d(location[0] + width / 2, location[1] + length / 2, location[2] + height / 2));
		group.setTransform(boxTransform3D);
		Appearance appearance = new Appearance();
		Color bgcolor = bgColor;
		if (bgcolor == null) bgcolor = ACC_RandColor.rndColor();
		ColoringAttributes r = new ColoringAttributes();
		r.setColor(new Color3f(bgcolor));
		appearance.setColoringAttributes(r);
		Box box = new Box(width / 2, length / 2, height / 2, Box.GENERATE_TEXTURE_COORDS, appearance);
		box = setColorBox(box);
		group.addChild(box);
		return group;
	}

	/**
	 * 给box各个面设置颜色值
	 * @param box Box
	 * @return Box
	 */
	private Box setColorBox(Box box) {
		if (CubeColorMap.isEmpty()) return box;
		for (EnumAxisExt key : EnumAxisExt.values()) {
			Color color = CubeColorMap.get(key);
			if (color != null) box.getShape(key.getBoxdire()).setAppearance(MQUtils.colorToAppearance(color));
		}
		return box;
	}

	/**
	 * 得到线圈
	 * @param isPoint boolean
	 * @return Node
	 */
	protected Node getNodeRoundWires(boolean isPoint) {
		int blockCount = getBlockCount();
		if (blockCount <= 1) return null;
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		for (int i = 0; i <= blockCount; i++) {
			Point point1 = getSectionPoint(i, 0);
			if (isPoint) point1.setColor(Color.BLACK);
			Point point2 = getSectionPoint(i, 1);
			if (isPoint) point2.setColor(Color.BLACK);
			Point point3 = getSectionPoint(i, 2);
			if (isPoint) point3.setColor(Color.BLACK);
			Point point4 = getSectionPoint(i, 3);
			if (isPoint) point4.setColor(Color.BLACK);
			group.addChild(MQRoundWire.getRoundLine(roundColor, true, point1, point2, point3, point4));
		}
		return group;
	}

	/**
	 * 区块数
	 * @return
	 */
	public abstract int getBlockCount();

	/** 线圈颜色 */
	Color roundColor = Color.BLACK;
	/** 前面 */
	Square squareFront = new Square();
	/** 后面 */
	Square squareBack = new Square();
	/**
	 * Square列表<br>
	 * 0:左<br>
	 * 1:上<br>
	 * 2:右<br>
	 * 3:底<br>
	 */
	List<List<Square>> squareList = new ArrayList<List<Square>>(4);
	/** 连接点颜色 */
	Color pointColor = Color.BLACK;

	/**
	 * 初始化线圈
	 */
	protected void initialSquare() {
		int blockCount = getBlockCount();
		if (blockCount <= 1) return;
		List<Point> Point1List = new ArrayList<Point>(blockCount);
		List<Point> Point2List = new ArrayList<Point>(blockCount);
		List<Point> Point3List = new ArrayList<Point>(blockCount);
		List<Point> Point4List = new ArrayList<Point>(blockCount);
		for (int i = 0; i <= blockCount; i++) {
			Point1List.add(getSectionPoint(i, 0));
			Point2List.add(getSectionPoint(i, 1));
			Point3List.add(getSectionPoint(i, 2));
			Point4List.add(getSectionPoint(i, 3));
		}
		int len = Point1List.size();
		if (len == 0) return;
		squareFront.set(Point1List.get(0), Point2List.get(0), Point3List.get(0), Point4List.get(0));
		int backNum = len - 1;
		squareBack.set(Point1List.get(backNum), Point2List.get(backNum), Point3List.get(backNum), Point4List.get(backNum));
		List<Square> listLeft = new ArrayList<Square>();
		List<Square> listTop = new ArrayList<Square>();
		List<Square> listRight = new ArrayList<Square>();
		List<Square> listBottom = new ArrayList<Square>();
		for (int i = 1; i < len; i++) {
			listLeft.add(new Square(EnumAxis.Z, pointColor, Point1List.get(i - 1), Point2List.get(i - 1), Point1List.get(i), Point2List.get(i)));
			listTop.add(new Square(EnumAxis.Y, pointColor, Point3List.get(i - 1), Point2List.get(i - 1), Point3List.get(i), Point2List.get(i)));
			listRight.add(new Square(EnumAxis.Z, pointColor, Point3List.get(i - 1), Point4List.get(i - 1), Point3List.get(i), Point4List.get(i)));
			listBottom.add(new Square(EnumAxis.Y, pointColor, Point4List.get(i - 1), Point1List.get(i - 1), Point4List.get(i), Point1List.get(i)));
		}
		squareList.add(listLeft);
		squareList.add(listTop);
		squareList.add(listRight);
		squareList.add(listBottom);
	}

	/**
	 * 得到某个切面
	 * @param i int
	 * @return Square
	 */
	Square getSquare(int i) {
		if (i < 0 || i > getBlockCount()) return null;
		Point point1 = getSectionPoint(i, 0);
		Point point2 = getSectionPoint(i, 1);
		Point point3 = getSectionPoint(i, 2);
		Point point4 = getSectionPoint(i, 3);
		return new Square(point1, point2, point3, point4);
	}

	public List<Square> getSquareListLeft() {
		return squareList.get(0);
	}

	public List<Square> getSquareListTop() {
		return squareList.get(1);
	}

	public List<Square> getSquareListRight() {
		return squareList.get(2);
	}

	public List<Square> getSquareListBottom() {
		return squareList.get(3);
	}

	public Square getSquareFront() {
		return squareFront;
	}

	public Square getSquareBack() {
		return squareBack;
	}

	/**
	 * 得到切面的不同的点
	 * @param num int
	 * @param i int
	 * @return Point
	 */
	Point getSectionPoint(int num, int i) {
		if (num < 0 || num > getBlockCount()) return null;
		double y = location[1] + (length / (getBlockCount())) * num;
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
}
