package com.maqiao.was.d3.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;
import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.painting.MQFont;
import com.maqiao.was.d3.painting.MQLineSegment;
import com.maqiao.was.d3.unit.Point;
import com.maqiao.was.d3.unit.Square;
import com.sun.j3d.utils.geometry.Cylinder;

/**
 * 属性对象
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class Parameter extends AbstractElement {
	/** 前面 */
	Square squareFront = null;
	/** 后面 */
	Square squareBack = null;
	/** 连接点开始 与Y轴平行 */
	Point linkPointA = null;
	/** 连接点结束 与Y轴平行 */
	Point linkPointB = null;
	/** 注解字符串数组 */
	String[] annotationArr = {};

	public Parameter() {
		Initialization();
	}

	public Parameter(String frontName) {
		this.frontName = frontName;
		Initialization();
	}

	public Parameter(String frontName, List<String> frontParaList) {
		this.frontName = frontName;
		this.frontParaList = frontParaList;
		Initialization();
	}

	public Parameter(String frontName, List<String> frontParaList, double[] location) {
		this.frontName = frontName;
		this.frontParaList = frontParaList;
		setLocation(location);
		Initialization();
	}

	public Parameter(String frontName, double[] location, String... arrs) {
		this.frontName = frontName;
		setLocation(location);
		this.frontParaList = java.util.Arrays.asList(arrs);
		Initialization();
	}

	/**
	 * 默认初始化
	 */
	public void Initialization() {
		setWidth(5.5f);
		setLength(15f);
		setHeight(0.8f);
		setColorBack(Color.WHITE);
		setColorFront(Color.WHITE);
		Map<EnumAxisExt, Color> boxColorMap = new HashMap<EnumAxisExt, Color>();
		boxColorMap.put(EnumAxisExt.Y0, Color.WHITE);
		boxColorMap.put(EnumAxisExt.Y1, Color.WHITE);
		boxColorMap.put(EnumAxisExt.X1, Color.WHITE);
		setBoxColorMap(boxColorMap);
		Color color = randColor.rndColor();
		setBgColor(color);
	}

	@Override
	public TransformGroup getFormGroup() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		group.addChild(getCylinder());
		group.addChild(getNodeBox());
		group.addChild(getAnnotation());
		initialSquare();
		if (linkPointA != null) group.addChild(linkPointA.getNode());
		if (linkPointB != null) group.addChild(linkPointB.getNode());

		group.addChild(MQLineSegment.getDef(linkPointA, linkPointB));
		group.addChild(getFrontNode());
		return group;
	}

	/**
	 * 初始化
	 */
	void initialSquare() {
		squareFront = getSquare(0);
		squareBack = getSquare(1);

		linkPointA = squareFront.getC().toClone();
		linkPointA.setXoffset(height / 2);
		linkPointA.setZoffset(-height / 2);
		linkPointA.setColor(Color.BLACK);
		linkPointB = linkPointA.toClone();
		linkPointB.setYoffset(length);
	}

	/**
	 * 得到圆柱体
	 * @return Node
	 */
	private Node getCylinder() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Cylinder box = new Cylinder(height / 2, length);
		box.getShape(0).setAppearance(MQUtils.colorToAppearance(this.bgColor));
		box.getShape(1).setAppearance(MQUtils.colorToAppearance(this.colorBack));
		box.getShape(2).setAppearance(MQUtils.colorToAppearance(this.colorFront));
		/** 中心点 */
		Point point = new Point(location[0] + width, location[1] + length / 2, location[2] + height / 2);
		group.setTransform(MQUtils.getTransform3DPoint(null, point.getX(), point.getY(), point.getZ()));
		group.addChild(box);
		return group;
	}

	/**
	 * 画出注解
	 * @return Node
	 */
	private Node getAnnotation() {
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

	double getAnnotationHeight() {
		int len = annotationArr.length;
		if (len == 0) return 0;
		return len * 0.2 + (len - 1) * ACC_AnnotationRowSpacing;
	}

	Color colorBack = null;
	Color colorFront = null;

	public void setColorBack(Color colorBack) {
		this.colorBack = colorBack;
	}

	public void setColorFront(Color colorFront) {
		this.colorFront = colorFront;
	}

	/** 参数名称 */
	List<String> frontParaList = new ArrayList<String>();

	/**
	 * 前端有多少行。包括名称与参数列表行
	 * @return int
	 */
	public int getFrontRows() {
		return frontParaList.size() + (frontName == null ? 0 : 1);
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

	@Override
	public double getTopXmax() {
		return location[0] + width + height / 2;
	}


	@Override
	public double getTopZmax() {
		return location[2] + height + annotationspace + getAnnotationHeight();
	}

}
