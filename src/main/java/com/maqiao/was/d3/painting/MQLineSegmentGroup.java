package com.maqiao.was.d3.painting;

import static com.maqiao.was.d3.MQConst.ACC_Color_BLACK;
import static com.maqiao.was.d3.MQConst.ACC_EndPointSize;
import static com.maqiao.was.d3.MQConst.ACC_LineWidth;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;
import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.unit.Point;

/**
 * 多对多线段
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQLineSegmentGroup {

	/**
	 * 输出Node
	 * @param groupX pointGP
	 * @param groupY pointGP
	 * @return Node
	 */
	public static final Node mergePointGroup(PointGroup groupX, PointGroup groupY) {
		//EnumAxis[] axis = { EnumAxis.X, EnumAxis.Y };
		return mergePointGroup(groupX, groupY,null, null);
	}
	/**
	 * 输出Node
	 * @param groupX pointGP
	 * @param groupY pointGP
	 * @param pointArr Point[]
	 * @return Node
	 */
	public static final Node mergePointGroup(PointGroup groupX, PointGroup groupY,Point[] pointArr) {
		return mergePointGroup(groupX, groupY,pointArr, null);
	}

	/**
	 * 输出Node
	 * @param groupX pointGP
	 * @param groupY pointGP
	 * @param pointArr Point[]
	 * @param axisArr EnumAxis[]
	 * @return Node
	 */
	public static final Node mergePointGroup(PointGroup groupX, PointGroup groupY,Point[] pointArr, EnumAxis[] axisArr) {
		TransformGroup group = new TransformGroup();
		Point a = null, b = null;
		if (groupX != null) {
			a = groupX.getGP();
			group.addChild(groupX.getNode());
		}
		if (groupY != null && groupY.getISP() != null) {
			b = groupY.getGP();
			group.addChild(groupY.getNode());
		}
		if (a != null && b != null) group.addChild(MQLineSeg.getLineSeg(a, b,pointArr, axisArr));
		return group;
	}

	/**
	 * 添加直线
	 * @param pointX Point
	 * @param pointY Point
	 * @param colorLine Color
	 * @return Node
	 */
	public static Node getGroupColorLine(Point pointX, Point pointY, Color colorLine) {
		return MQLineSegment.getMaster(pointX, Color.RED, pointY, ACC_Color_BLACK, colorLine, ACC_LineWidth, 0, ACC_EndPointSize);
	}

	/**
	 * 端点组
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.8
	 */
	public static final class PointGroup {
		/** 点数组 */
		List<Point> pointList = new ArrayList<Point>();

		/** 是否自动打开交点距离，将自动修改交点位置 */
		boolean isAutoISP = false;
		/** 点数组的交点 */
		Point pointISP = null;
		/** 自动搜索交点距离 */
		float rangAutoISP = 0.5f;
		/** 设置交点按某轴居中 */
		int axisISP = 0;

		/** 组柄是否输出 */
		boolean isPaintGP = true;
		/** 是否自动打开组柄距离，将自动修改组柄位置 */
		boolean isAutoGP = false;
		/** 点数组的组柄 */
		Point pointGP = null;
		/** 自动搜索组柄点距离 */
		float rangAutoGP = 0.5f;
		/** 设置组柄点按某轴居中 */
		int axisGP = 0;

		/** 内部连接依次按照某轴自动折叠 */
		EnumAxis[] axisInnerArr;
		/** 外部部连接依次按照某轴自动折叠 */
		EnumAxis[] axisOuterArr;

		public PointGroup() {

		}

		public PointGroup(Point[] pointArr, Point pointISP, Point pointGP) {
			init(Arrays.asList(pointArr), pointISP, pointGP);
		}

		public PointGroup(List<Point> pointList, Point pointISP, Point pointGP) {
			init(pointList, pointISP, pointGP);
		}

		/**
		 * 初始化
		 * @param pointList List<Point>
		 * @param pointISP Point
		 * @param pointGP Point
		 */
		void init(List<Point> pointList, Point pointISP, Point pointGP) {
			this.pointList = pointList;
			this.pointISP = pointISP;
			this.pointGP = pointGP;
		}
		/**
		 * 初始化
		 */
		public void Initialization() {
			pointISP=getISP();
			pointGP = getGP();
			//System.out.println("==================================================================");
			//System.out.println("pointISP:"+pointISP);
			//System.out.println("pointGP:"+pointGP);
			//System.out.println("==================================================================");
		}

		public Point getGP() {
			if (!isPaintGP) return pointISP;
			if (isAutoGP) {
				/* 自动寻找连接点 */
				pointGP = MQLineUtils.getAutoGroup(pointList, pointGP, pointISP, rangAutoGP, axisGP);
				isAutoGP = false;
			}
			if (pointGP == null) pointGP = pointISP;
			return pointGP;
		}

		/**
		 * 得到端点组的交点
		 * @return Point
		 */
		public Point getISP() {
			if (isAutoISP) {
				/* 自动寻找连接点 */
				pointISP = MQLineUtils.getAutoIntersect(pointList, pointGP, pointISP, rangAutoISP, axisISP);
				isAutoISP = false;
			}
			if (pointISP != null) return pointISP;
			if (pointList == null || pointList.size() == 0) return null;
			for (int i = 0; i < pointList.size(); i++)
				if (pointList.get(i) != null) return pointList.get(i);
			return null;
		}

		/**
		 * 输出Node
		 * @return Node
		 */
		public Node getNode() {
			TransformGroup group = new TransformGroup();
			Point a = pointISP;
			if (a == null) return group;
			if (a.isEndpoint()) group.addChild(a.getNode());
			for (int i = 0; i < pointList.size(); i++) {
				Point b = pointList.get(i);
				if (b == null) continue;
				if (b.isEndpoint()) group.addChild(b.getNode());
				if (!a.equals(b)) group.addChild(MQLineSeg.getLineSeg(b, a,null, axisInnerArr));
			}
			if (isPaintGP) {
				if (pointGP != null) {
					if (pointGP.isEndpoint()) group.addChild(pointGP.getNode());
					group.addChild(MQLineSeg.getLineSeg(a, pointGP,null, axisOuterArr));
				}
			}
			return group;
		}

		public void setAutoISP(boolean isAutoISP) {
			this.isAutoISP = isAutoISP;
		}

		public void setAutoGP(boolean isAutoGP) {
			this.isAutoGP = isAutoGP;
		}

		public void setPaintGP(boolean isPaintGP) {
			this.isPaintGP = isPaintGP;
		}

		public void setAxisInnerArr(int[] axisInnerArr) {
			this.axisInnerArr = MQUtils.getAxisToEnum(axisInnerArr);
		}

		public void setAxisInnerArr(EnumAxis[] axisInnerArr) {
			this.axisInnerArr = axisInnerArr;
		}

		public void setAxisOuterArr(int[] axisOuterArr) {
			this.axisOuterArr = MQUtils.getAxisToEnum(axisOuterArr);
		}

		public void setAxisOuterArr(EnumAxis[] axisOuterArr) {
			this.axisOuterArr = axisOuterArr;
		}

		public void setAxisISP(EnumAxis... axis) {
			int v = 0;
			for (int i = 0; i < axis.length; i++)
				v = v | axis[i].getValue();
			this.axisISP = v;
		}

		public void setAxisISP(int axisISP) {
			this.axisISP = axisISP;
		}

		public void setAxisGP(EnumAxis... axis) {
			int v = 0;
			for (int i = 0; i < axis.length; i++)
				v = v | axis[i].getValue();
			this.axisGP = v;
		}

		public void setAxisGP(int axisGP) {
			this.axisGP = axisGP;
		}

		public void setRangAutoISP(float rangAutoISP) {
			this.rangAutoISP = rangAutoISP;
		}

		public void setRangAutoGP(float rangAutoGP) {
			this.rangAutoGP = rangAutoGP;
		}

	}

}
