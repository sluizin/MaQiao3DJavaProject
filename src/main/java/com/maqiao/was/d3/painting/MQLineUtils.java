package com.maqiao.was.d3.painting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.unit.Point;

/**
 * 直线与线段工具
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQLineUtils {

	/**
	 * 跟随轴
	 * @param pointX Point
	 * @param pointY Point
	 * @param axisArr int[]
	 * @return Point[]
	 */
	static Point[] getFollowAxisPoint(Point pointX, Point pointY, int... axisArr) {
		return getFollowAxisPoint(pointX, pointY, MQUtils.getAxisToEnum(axisArr));
	}

	/**
	 * 跟随轴
	 * @param pointX Point
	 * @param pointY Point
	 * @param axisArr EnumAxis[]
	 * @return Point[]
	 */
	static Point[] getFollowAxisPoint(Point pointX, Point pointY, EnumAxis... axisArr) {
		Point a = pointX.toClone();
		boolean t1 = true, t2 = true, t3 = true;
		List<Point> list = new ArrayList<Point>(3);
		if (pointX == null || pointY == null) {
			Point[] ab = {};
			return ab;
		}
		for (int i = 0, len = axisArr.length; i < len; i++) {
			EnumAxis axis = axisArr[i];
			if (t1 && axis == EnumAxis.X) {
				a.setX(pointY.getX());
				list.add(a.toClone());
				t1 = false;
				continue;
			}
			if (t2 && axis == EnumAxis.Y) {
				a.setY(pointY.getY());
				list.add(a.toClone());
				t2 = false;
				continue;
			}
			if (t3 && axis == EnumAxis.Z) {
				a.setZ(pointY.getZ());
				list.add(a.toClone());
				t3 = false;
			}
		}
		Point[] ab = {};
		return list.toArray(ab);
	}

	/**
	 * 获取多个端点X,Y,Z值的最小值或最大值
	 * @param pointList List<Point>
	 * @param i int
	 * @param isMax boolean
	 * @return double
	 */
	public static double getPointExtremum(List<Point> pointList, int i, boolean isMax) {
		double e = 0;
		for (int ii = 0; ii < pointList.size(); ii++) {
			Point p = pointList.get(ii);
			double v = p.getLocal(i);
			if (ii == 0) e = v;
			if (isMax) {
				if (v > e) e = v;
			} else {
				if (v < e) e = v;
			}
		}
		return e;
	}

	/**
	 * 得到极值数组，为3*2数组。分别为<br>
	 * xmax,xmin<br>
	 * ymax,ymin<br>
	 * zmax,zmin<br>
	 * @param pointList List<Point>
	 * @return double[][]
	 */
	public static double[][] getExtremum(List<Point> pointList) {
		double[][] extremum = new double[3][2];
		extremum[0][0] = getPointExtremum(pointList, 0, true);
		extremum[0][1] = getPointExtremum(pointList, 0, false);
		extremum[1][0] = getPointExtremum(pointList, 1, true);
		extremum[1][1] = getPointExtremum(pointList, 1, false);
		extremum[2][0] = getPointExtremum(pointList, 2, true);
		extremum[2][1] = getPointExtremum(pointList, 2, false);
		return extremum;
	}

	/**
	 * 得到极值数组，为3*2数组。分别为<br>
	 * xmax,xmin<br>
	 * ymax,ymin<br>
	 * zmax,zmin<br>
	 * @param points Point[]
	 * @return double[][]
	 */
	public static double[][] getExtremum(Point... points) {
		return getExtremum(Arrays.asList(points));
	}

	/**
	 * 自动寻找连接点
	 * @param pointList List<Point>
	 * @param pointGP Point
	 * @param pointISP Point
	 * @param rangAutoISP float
	 * @param axis int
	 * @return Point
	 */
	static Point getAutoIntersect(List<Point> pointList, Point pointGP, Point pointISP, float rangAutoISP, int axis) {
		if (pointList == null || pointList.size() == 0) return new Point();
		double x, y, z;
		double[][] extremum = getExtremum(pointList);
		double x1 = extremum[0][0];
		double x2 = extremum[0][1];
		double y1 = extremum[1][0];
		double y2 = extremum[1][1];
		double z1 = extremum[2][0];
		double z2 = extremum[2][1];
		if (EnumAxis.X.isEnumAxis(axis)) {
			x = MQUtils.getCenter(x1, x2);
		} else {
			if (pointGP != null && pointGP.getX() > x1) x = x1 + rangAutoISP;
			else x = x2 - rangAutoISP;
		}
		if (EnumAxis.Y.isEnumAxis(axis)) {
			y = MQUtils.getCenter(y1, y2);
		} else {
			if (pointGP != null && pointGP.getY() > y1) y = y1 + rangAutoISP;
			else y = y2 - rangAutoISP;
		}
		if (EnumAxis.Z.isEnumAxis(axis)) {
			z = MQUtils.getCenter(z1, z2);
		} else {
			if (pointGP != null && pointGP.getZ() > z1) z = z1 + rangAutoISP;
			else z = z2 - rangAutoISP;
		}
		if (pointISP == null) pointISP = Point.getDefPoint(x, y, z);
		else pointISP.init(x, y, z);
		return pointISP;
	}

	static Point getAutoGroup(List<Point> pointList, Point pointGP, Point pointISP, float rangAutoGP, int axis) {
		if (pointISP == null) return new Point();
		double x = 0, y = 0, z = 0;
		if (pointGP == null) pointGP = Point.getDefPoint(x, y, z);
		double[][] extremum = getExtremum(pointList);
		double x1 = extremum[0][0];
		double y1 = extremum[1][0];
		double z1 = extremum[2][0];
		pointGP.initInfor(pointISP);
		if (EnumAxis.X.isEnumAxis(axis)) pointGP.setX((pointISP.getX() > x1) ? pointISP.getX() + rangAutoGP : pointISP.getX() - rangAutoGP);
		if (EnumAxis.Y.isEnumAxis(axis)) pointGP.setY((pointISP.getY() > y1) ? pointISP.getY() + rangAutoGP : pointISP.getY() - rangAutoGP);
		if (EnumAxis.Z.isEnumAxis(axis)) pointGP.setZ((pointISP.getZ() > z1) ? pointISP.getZ() + rangAutoGP : pointISP.getZ() - rangAutoGP);
		return pointGP;
	}
}
