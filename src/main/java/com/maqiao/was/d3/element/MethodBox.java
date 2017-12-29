package com.maqiao.was.d3.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;
import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.painting.MQFont;
import com.maqiao.was.d3.painting.MQRoundWire;
import com.maqiao.was.d3.unit.Point;
import com.maqiao.was.d3.unit.Square;

/**
 * 方法Box在第一相限[x,y,z皆是正数]
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MethodBox extends AbstractElement {
	/** 线圈颜色 */
	Color roundColor = Color.BLACK;
	/** 前面 */
	Square squareFront = new Square();
	/** 后面 */
	Square squareBack = new Square();
	/** 参数名称 */
	List<String> frontParaList = new ArrayList<String>();
	/** 注解字符串数组 */
	String[] annotationArr = {};
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

	public MethodBox() {
		Initialization();
	}

	/**
	 * @param frontName String
	 * @param frontParaList List< String >
	 * @param location double[]
	 */
	public MethodBox(String frontName, List<String> frontParaList, double[] location) {
		this.frontName = frontName;
		this.frontParaList = frontParaList;
		this.location = location;
		Initialization();
	}

	/**
	 * 默认初始化
	 */
	public void Initialization() {
		Color color = randColor.rndColor();
		setBgColor(color);
		setWidth(6.4f);
		setLength(15f);
		setRoundColor(Color.RED);

		Map<EnumAxisExt, Color> boxColorMap = new HashMap<EnumAxisExt, Color>();
		boxColorMap.put(EnumAxisExt.Y0, Color.WHITE);
		boxColorMap.put(EnumAxisExt.Y1, Color.WHITE);
		setBoxColorMap(boxColorMap);

		setPointColor(Color.YELLOW);
		squareFront.setColAxis(EnumAxis.Z);
		squareBack.setColAxis(EnumAxis.X);
	}

	/**
	 * 输出 TransformGroup
	 * @return TransformGroup
	 */
	public TransformGroup getFormGroup() {
		TransformGroup group = new TransformGroup();
		if (blockCount <= 0) return group;
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		setAllWidth();
		setAllHeight();
		group.addChild(getNodeBox());
		group.addChild(getNodeRoundWires(false));
		group.addChild(getFrontNode());
		return group;
	}

	void setAllHeight() {
		float height = 0;
		if (frontName != null && frontName.length() > 0) height += 0.8;
		height += frontParaList.size() * 0.35;
		setHeight(height);
	}

	void setAllWidth() {
		float width = 0;
		String str = "";
		if (frontName != null && frontName.length() > 0) str = frontName;
		for (int i = 0, len = frontParaList.size(); i < len; i++)
			if (frontParaList.get(i).length() > str.length()) str = frontParaList.get(i);
		width = 0.1f + str.length() * 0.14f;
		if (width < 4f) width = 4f;
		setWidth(width);
	}

	/**
	 * 画出注解
	 * @return Node
	 */
	public Node getAnnotation() {
		int len = annotationArr.length;
		if (len == 0) return null;
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		double z = location[2] + height + annotationspace;
		for (int i = 0; i < len; i++) {
			Point p = new Point(location[0], location[1], z);
			p.setXoffset(ACC_fontOffset);
			group.addChild(MQFont.getFontText3D(p, annotationArr[i], ACC_AnnotationColor, ACC_AnnotationSize, EnumAxisExt.X0, EnumAxisExt.Z0));
			z += ACC_AnnotationRowSpacing;
		}
		return group;
	}

	/**
	 * 输出属性点
	 * @return Node
	 */
	public Node getSquareNode() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		if (squareFront != null) group.addChild(squareFront.getFormGroup());
		for (int i = 0, len = squareList.size(); i < len; i++) {
			List<Square> list = squareList.get(i);
			for (int ii = 0, len2 = list.size(); ii < len2; ii++)
				group.addChild(list.get(ii).getFormGroup());
		}
		if (squareBack != null) group.addChild(squareBack.getFormGroup());
		return group;
	}

	/**
	 * 得到线圈
	 * @param isPoint boolean
	 * @return Node
	 */
	private Node getNodeRoundWires(boolean isPoint) {
		if (blockCount <= 1) return null;
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		List<Point> Point1List = new ArrayList<Point>(blockCount);
		List<Point> Point2List = new ArrayList<Point>(blockCount);
		List<Point> Point3List = new ArrayList<Point>(blockCount);
		List<Point> Point4List = new ArrayList<Point>(blockCount);
		for (int i = 0; i <= blockCount; i++) {
			Point point1 = getSectionPoint(i, 0);
			if (isPoint) point1.setColor(Color.BLACK);
			Point point2 = getSectionPoint(i, 1);
			if (isPoint) point2.setColor(Color.BLACK);
			Point point3 = getSectionPoint(i, 2);
			if (isPoint) point3.setColor(Color.BLACK);
			Point point4 = getSectionPoint(i, 3);
			if (isPoint) point4.setColor(Color.BLACK);
			Point1List.add(point1);
			Point2List.add(point2);
			Point3List.add(point3);
			Point4List.add(point4);
			group.addChild(MQRoundWire.getRoundLine(roundColor, true, point1, point2, point3, point4));
		}
		initialSquare(Point1List, Point2List, Point3List, Point4List);
		return group;
	}

	void initialSquare(List<Point> Point1List, List<Point> Point2List, List<Point> Point3List, List<Point> Point4List) {
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
	 * 得到前面的文字列表
	 * @return Node
	 */
	public Node getFrontNode() {
		int num = getFrontRows();
		squareFront.putPoint(num);
		List<Point> list = squareFront.getlPointList();
		Collections.reverse(list);
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		int i = 0;
		EnumAxisExt axisWidth = EnumAxisExt.X0;
		EnumAxisExt axisHeight = EnumAxisExt.Z0;
		if (frontName != null) {
			Point p = list.get(i++);
			p.setXoffset(ACC_fontOffset);
			group.addChild(MQFont.getFontText3D(p, frontName, ACC_fontNameColor, ACC_fontNameSize, axisWidth, axisHeight));
		}
		for (int ii = 0; i < list.size(); i++) {
			Point p = list.get(i);
			p.setXoffset(ACC_fontOffset);
			group.addChild(MQFont.getFontText3D(p, frontParaList.get(ii++), ACC_fontParaColor, ACC_fontParaSize, axisWidth, axisHeight));
		}

		return group;
	}

	/**
	 * 前端有多少行。包括名称与参数列表行
	 * @return int
	 */
	public int getFrontRows() {
		return frontParaList.size() + (frontName == null ? 0 : 1);
	}

	/**
	 * 给指定块赋值
	 * @param num int
	 * @param block int
	 * @param count int
	 */
	public void putPoint(int num, int block, int count) {
		List<Square> list = squareList.get(num % 4);
		int len = list.size();
		if (block >= len || block < 0) return;
		list.get(block).putPoint(count);
	}

	/**
	 * 给指定块赋值
	 * @param block
	 * @param count
	 */
	public void putPointLeft(int block, int count) {
		putPoint(0, block, count);
	}

	/**
	 * @param arrCount int[]
	 */
	public void putPointLeftArr(int... arrCount) {
		for (int i = 0; i < arrCount.length; i++)
			putPoint(0, i, arrCount[i]);
	}

	/**
	 * 给指定块赋值
	 * @param block
	 * @param count
	 */
	public void putPointTop(int block, int count) {
		putPoint(1, block, count);
	}

	/**
	 * @param arrCount int[]
	 */
	public void putPointTopArr(int... arrCount) {
		for (int i = 0; i < arrCount.length; i++)
			putPoint(1, i, arrCount[i]);
	}

	/**
	 * 给指定块赋值
	 * @param block
	 * @param count
	 */
	public void putPointRight(int block, int count) {
		putPoint(2, block, count);
	}

	/**
	 * @param arrCount int[]
	 */
	public void putPointRightArr(int... arrCount) {
		for (int i = 0; i < arrCount.length; i++)
			putPoint(2, i, arrCount[i]);
	}

	/**
	 * 给指定块赋值
	 * @param block
	 * @param count
	 */
	public void putPointBottom(int block, int count) {
		putPoint(3, block, count);
	}

	/**
	 * @param arrCount int[]
	 */
	public void putPointBottomArr(int... arrCount) {
		for (int i = 0; i < arrCount.length; i++)
			putPoint(3, i, arrCount[i]);
	}

	public Color getRoundColor() {
		return roundColor;
	}

	public void setRoundColor(Color roundColor) {
		this.roundColor = roundColor;
	}

	public Color getPointColor() {
		return pointColor;
	}

	public void setPointColor(Color pointColor) {
		this.pointColor = pointColor;
	}

	public List<List<Square>> getSquareList() {
		return squareList;
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

}
