package com.maqiao.was.d3.painting;

import java.awt.Color;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;
import static com.maqiao.was.d3.MQConst.ACC_Endpoint_A;
import static com.maqiao.was.d3.MQConst.ACC_Endpoint_AB;
import static com.maqiao.was.d3.MQConst.ACC_Endpoint_B;
import static com.maqiao.was.d3.MQConst.ACC_Color_BLACK;
import static com.maqiao.was.d3.MQConst.ACC_EndPointSize;
import static com.maqiao.was.d3.MQConst.ACC_LineWidth;
import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.unit.Point;

/**
 * 添加线段
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public final class MQLineSegment {
	/**
	 * 添加线段 默认
	 * @param location float[]
	 * @return Node
	 */
	public static Node getDef(float... location) {
		Point pointX = MQUtils.positionSplit(0, location);
		Point pointY = MQUtils.positionSplit(1, location);
		return getDef(pointX, pointY);
	}

	/**
	 * 添加线段
	 * @param pointX Point
	 * @param pointY Point
	 * @return Node
	 */
	public static Node getDef(Point pointX, Point pointY) {
		return getDef(pointX, pointY, 1 | 2);
	}

	/**
	 * 添加线段
	 * @param pointX Point
	 * @param pointY Point
	 * @param endPoint int
	 * @return Node
	 */
	public static Node getDef(Point pointX, Point pointY, int endPoint) {
		return getMaster(pointX, Color.RED, pointY, ACC_Color_BLACK, ACC_Color_BLACK, ACC_LineWidth, endPoint, ACC_EndPointSize);

	}

	/**
	 * 得到折叠线段 直角
	 * @param endPoint int
	 * @param pointX Point
	 * @param pointY Point
	 * @param axisArr int[]
	 * @return Node
	 */
	public static Node getDef(int endPoint, Point pointX, Point pointY, int... axisArr) {
		Point[] arr = {};
		if (axisArr == null || axisArr.length == 0) return getDef(endPoint, pointX, pointY, arr);
		arr = MQLineUtils.getFollowAxisPoint(pointX, pointY, axisArr);
		return getDef(endPoint, pointX, pointY, arr);
	}

	/**
	 * 一条线段，多个中间点<br>
	 * 是否需要顶点<br>
	 * 0:不含顶点
	 * 1:开始顶点
	 * 2:结尾顶点
	 * 4:中间顶点
	 * @param pointX Point
	 * @param pointY Point
	 * @param colorLine Color
	 * @param locationArr Point[]
	 * @return Node
	 */
	public static Node getMasterLine(int endPoint, Point pointX, Point pointY, Color colorLine, Point... locationArr) {
		TransformGroup group = new TransformGroup();
		if (locationArr == null || locationArr.length == 0) {
			group.addChild(getMasterLine(pointX, pointY, colorLine));
			return group;
		}
		final int len = locationArr.length;
		Point a = pointX.toClone();
		Point b = locationArr[0];
		group.addChild(getMasterLine(a, b, colorLine, (MQUtils.contain(endPoint, ACC_Endpoint_A) ? 1 : 0) | (MQUtils.contain(endPoint, ACC_Endpoint_AB) ? 2 : 0)));
		a = locationArr[0];
		for (int i = 1; i < len; i++) {
			b = locationArr[i];
			group.addChild(getMasterLine(a, b, colorLine, MQUtils.contain(endPoint, ACC_Endpoint_AB) ? 2 : 0));
			a = locationArr[i];
		}
		group.addChild(getMasterLine(a, pointY, colorLine, MQUtils.contain(endPoint, ACC_Endpoint_B) ? 2 : 0));
		return group;
	}

	/**
	 * 添加直线
	 * @param pointX Point
	 * @param pointY Point
	 * @param endPoint int
	 * @return Node
	 */
	public static Node getMasterLine(Point pointX, Point pointY, Color colorLine) {
		return getMasterLine(pointX, pointY, colorLine, 0);

	}

	/**
	 * 添加线段
	 * @param pointX Point
	 * @param pointY Point
	 * @param colorLine Color
	 * @param endPoint int
	 * @return Node
	 */
	public static Node getMasterLine(Point pointX, Point pointY, Color colorLine, int endPoint) {
		TransformGroup group = new TransformGroup();
		group.addChild(MQLine.getLine(pointX, pointY, colorLine, ACC_LineWidth));
		/*
		 * 添加两端端点
		 */
		if (pointX.isEndpoint() && MQUtils.contain(endPoint, ACC_Endpoint_A)) group.addChild(MQPoint.getPoint(pointX, pointX.getColor(), ACC_EndPointSize));
		if (pointX.isEndpoint() && MQUtils.contain(endPoint, ACC_Endpoint_B)) group.addChild(MQPoint.getPoint(pointY, pointY.getColor(), ACC_EndPointSize));
		return group;
	}

	/**
	 * 添加线段
	 * @param colorX Color
	 * @param colorY Color
	 * @param location float[]
	 * @return Node
	 */
	public static Node get(Color colorX, Color colorY, float... location) {
		Point pointX = MQUtils.positionSplit(0, location);
		Point pointY = MQUtils.positionSplit(1, location);
		return get(pointX, colorX, pointY, colorY);
	}

	/**
	 * 添加线段
	 * @param pointX Point
	 * @param colorX Color
	 * @param pointY Point
	 * @param colorY Color
	 * @return Node
	 */
	public static Node get(Point pointX, Color colorX, Point pointY, Color colorY) {
		return get(pointX, colorX, pointY, colorY, ACC_Color_BLACK);

	}

	/**
	 * 添加线段
	 * @param pointX Point
	 * @param colorX Color
	 * @param pointY Point
	 * @param colorY Color
	 * @param colorLine Color
	 * @return Node
	 */
	public static Node get(Point pointX, Color colorX, Point pointY, Color colorY, Color colorLine) {
		return getMaster(pointX, colorX, pointY, colorY, colorLine, 2f, 1 | 2, 0.04f);

	}

	/**
	 * 添加线段
	 * @param pointX Point
	 * @param colorX Color
	 * @param pointY Point
	 * @param colorY Color
	 * @param colorLine Color
	 * @param lineWidth float
	 * @param endPoint int
	 * @param endPointSize float
	 * @return Node
	 */
	public static Node getMaster(Point pointX, Color colorX, Point pointY, Color colorY, Color colorLine, float lineWidth, int endPoint, float endPointSize) {
		if (pointX == null || pointY == null) return null;
		TransformGroup group = new TransformGroup();
		group.addChild(MQLine.getLine(pointX, pointY, colorLine, lineWidth));
		/*
		 * 添加两端端点
		 */
		if (MQUtils.contain(endPoint, ACC_Endpoint_A)) group.addChild(MQPoint.getPoint(pointX, colorX, endPointSize));
		if (MQUtils.contain(endPoint, ACC_Endpoint_B)) group.addChild(MQPoint.getPoint(pointY, colorY, endPointSize));
		return group;
	}

	/**
	 * 一条线段，多个中间点<br>
	 * 是否需要顶点<br>
	 * 0:不含顶点
	 * 1:开始顶点
	 * 2:结尾顶点
	 * 4:中间顶点
	 * @param pointX Point
	 * @param pointY Point
	 * @param locationArr Point[]
	 * @return Node
	 */
	public static Node getDef(int endPoint, Point pointX, Point pointY, Point... locationArr) {
		TransformGroup group = new TransformGroup();
		if (locationArr == null || locationArr.length == 0) {
			group.addChild(getDef(pointX, pointY, endPoint));
			return group;
		}
		final int len = locationArr.length;
		Point a = pointX.toClone();
		Point b = locationArr[0];
		group.addChild(getDef(a, b, (MQUtils.contain(endPoint, ACC_Endpoint_A) ? 1 : 0) | (MQUtils.contain(endPoint, ACC_Endpoint_AB) ? 2 : 0)));
		a = locationArr[0];
		for (int i = 1; i < len; i++) {
			b = locationArr[i];
			group.addChild(getDef(a, b, MQUtils.contain(endPoint, ACC_Endpoint_AB) ? 2 : 0));
			a = locationArr[i];
		}
		group.addChild(getDef(a, pointY, MQUtils.contain(endPoint, ACC_Endpoint_B) ? 2 : 0));
		return group;
	}

}
