package com.maqiao.was.d3.painting;

import static com.maqiao.was.d3.MQConst.ACC_Endpoint_A;
import static com.maqiao.was.d3.MQConst.ACC_Endpoint_AB;
import static com.maqiao.was.d3.MQConst.ACC_Endpoint_B;
import static com.maqiao.was.d3.MQConst.ACC_Color_BLACK;
import java.awt.Color;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;
import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.unit.Point;

/**
 * 添加线段
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQLineSeg {

	/**
	 * 得到折叠直线 直角
	 * @param pointX Point
	 * @param pointY Point
	 * @param pointArr Point[]
	 * @param axisArr int[]
	 * @return Node
	 */
	public static Node getLineSeg(Point pointX, Point pointY,Point[] pointArr, int[] axisArr) {
		return getLineSeg(pointX, pointY,pointArr, MQUtils.getAxisToEnum(axisArr));
	}

	/**
	 * 得到折叠直线 直角
	 * @param pointX Point
	 * @param pointY Point
	 * @param pointArr Point[]
	 * @param axisArr EnumAxis[]
	 * @return Node
	 */
	public static Node getLineSeg(Point pointX, Point pointY,Point[] pointArr, EnumAxis[] axisArr) {
		return getLineSeg(pointX, pointY, ACC_Color_BLACK,pointArr, axisArr);
	}

	/**
	 * 得到折叠直线 直角
	 * @param pointX Point
	 * @param pointY Point
	 * @param colorLine Color
	 * @param pointArr Point[]
	 * @param axisArr int[]
	 * @return Node
	 */
	public static Node getLineSeg(Point pointX, Point pointY, Color colorLine,Point[] pointArr, int[] axisArr) {
		return getLineSeg(pointX, pointY, colorLine,pointArr, MQUtils.getAxisToEnum(axisArr));
	}

	/**
	 * 得到折叠直线 直角
	 * @param pointX Point
	 * @param pointY Point
	 * @param colorLine Color
	 * @param pointArr Point[]
	 * @param axisArr EnumAxis[]
	 * @return Node
	 */
	public static Node getLineSeg(Point pointX, Point pointY, Color colorLine, Point[] pointArr, EnumAxis[] axisArr) {
		if (pointArr != null && pointArr.length > 0) return getLineSeg(0, pointX, pointY, colorLine, pointArr);
		Point[] arr = {};
		if (axisArr == null || axisArr.length == 0) return getLineSeg(0, pointX, pointY, colorLine, arr);
		arr = MQLineUtils.getFollowAxisPoint(pointX, pointY, axisArr);
		return getLineSeg(0, pointX, pointY, colorLine, arr);
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
	public static Node getLineSeg(int endPoint, Point pointX, Point pointY, Color colorLine, Point... locationArr) {
		TransformGroup group = new TransformGroup();
		if (locationArr == null || locationArr.length == 0) {
			group.addChild(getLine(pointX, pointY, colorLine));
			return group;
		}
		final int len = locationArr.length;
		Point a = pointX.toClone();
		Point b = locationArr[0];
		group.addChild(getLineSeg(a, b, colorLine, (MQUtils.contain(endPoint, ACC_Endpoint_A) ? 1 : 0) | (MQUtils.contain(endPoint, ACC_Endpoint_AB) ? 2 : 0)));
		a = locationArr[0];
		for (int i = 1; i < len; i++) {
			b = locationArr[i];
			group.addChild(getLineSeg(a, b, colorLine, MQUtils.contain(endPoint, ACC_Endpoint_AB) ? 2 : 0));
			a = locationArr[i];
		}
		group.addChild(getLineSeg(a, pointY, colorLine, MQUtils.contain(endPoint, ACC_Endpoint_B) ? 2 : 0));
		return group;
	}

	/**
	 * 添加直线
	 * @param pointX Point
	 * @param pointY Point
	 * @param colorLine Color
	 * @return Node
	 */
	public static Node getLine(Point pointX, Point pointY, Color colorLine) {
		return getLineSeg(pointX, pointY, colorLine, 0);

	}

	/**
	 * 添加线段
	 * @param pointX Point
	 * @param pointY Point
	 * @param colorLine Color
	 * @param endPoint int
	 * @return Node
	 */
	public static Node getLineSeg(Point pointX, Point pointY, Color colorLine, int endPoint) {
		TransformGroup group = new TransformGroup();
		group.addChild(MQLine.getLine(pointX, pointY, colorLine));
		/*
		 * 添加两端端点
		 */
		if (pointX.isEndpoint() && MQUtils.contain(endPoint, ACC_Endpoint_A)) group.addChild(pointX.getNode());
		if (pointX.isEndpoint() && MQUtils.contain(endPoint, ACC_Endpoint_B)) group.addChild(pointY.getNode());
		return group;
	}
}
