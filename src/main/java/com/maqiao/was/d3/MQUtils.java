package com.maqiao.was.d3;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import static com.maqiao.was.d3.MQConst.EnumAxis;

import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.unit.Point;

/**
 * 公共方法
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public final class MQUtils {

	/**
	 * 多个node插入
	 * @param t TransformGroup
	 * @param nodeArray Node[]
	 */
	public static void addNode(TransformGroup t, Node... nodeArray) {
		for (int i = 0; i < nodeArray.length; i++)
			t.addChild(nodeArray[i]);
	}

	/**
	 * 多个node插入
	 * @param t TransformGroup
	 * @param nodeList List<Node>
	 */
	public static void addNode(TransformGroup t, List<Node> nodeList) {
		for (int i = 0, len = nodeList.size(); i < len; i++)
			t.addChild(nodeList.get(i));
	}

	/**
	 * 分解数组
	 * @param i int
	 * @param arr float[]
	 * @return Point
	 */
	public static Point positionSplit(int i, float... arr) {
		final int len = arr.length;
		Point e = new Point();
		if (i < 0 || len <= 0) return e;
		int p = i * 3;
		if (len < p) e.setX(arr[p]);
		if (len < p + 1) e.setY(arr[p + 1]);
		if (len < p + 2) e.setZ(arr[p + 2]);
		return e;
	}

	/**
	 * 合并
	 * @param rest Point
	 * @return float[]
	 */
	public static float[] concatAll(Point... rest) {
		int totalLength = rest.length * 3;
		float[] result = new float[totalLength];
		int offset = 0;
		for (Point e : rest) {
			System.arraycopy(e.toFloat(), 0, result, offset, 3);
			offset += 3;
		}
		return result;
	}

	/**
	 * 转换float[] 至 double[]
	 * @param arr float[]
	 * @return double[]
	 */
	public static double[] toDouble(float... arr) {
		double[] newarr = new double[arr.length];
		for (int i = 0, len = arr.length; i < len; i++)
			newarr[i] = changeDouble(arr[i]);
		return newarr;
	}

	/**
	 * float转成double
	 * @param f float
	 * @return double
	 */
	public static double changeDouble(float f) {
		return (new BigDecimal(String.valueOf(f))).doubleValue();
	}

	/**
	 * 判断v是否有k中
	 * @param k int
	 * @param v int
	 * @return boolean
	 */
	public static final boolean contain(int k, int v) {
		return (k & v) > 0;
	}

	/**
	 * 把int数组转成枚举数组
	 * @param axisArr int[]
	 * @return EnumAxis[]
	 */
	public static final EnumAxis[] getAxisToEnum(int... axisArr) {
		List<EnumAxis> list = new ArrayList<EnumAxis>(3);
		for (int i = 0; i < axisArr.length; i++) {
			EnumAxis e = EnumAxis.getEnumAxis(axisArr[i]);
			if (e != null) list.add(e);
		}
		EnumAxis[] arr = {};
		return list.toArray(arr);
	}

	/**
	 * 得到double数组中的中间值
	 * @param arr double[]
	 * @return double
	 */
	public static final double getCenter(double... arr) {
		if (arr == null || arr.length == 0) return 0;
		if (arr.length == 1) return arr[0];
		double min = arr[0], max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < min) min = arr[i];
			if (arr[i] > max) max = arr[i];
		}
		return min + (max - min) / 2;
	}

	@Deprecated
	public static final Matrix3d getEnumAxisMatrixText(EnumAxisExt axisE) {
		Matrix3d m1 = new Matrix3d();//x 方向
		Matrix3d m2 = new Matrix3d();//y 方向
		Matrix3d m3 = new Matrix3d();//z 方向

		double angleX = 0, angleY = 0, angleZ = 0;
		switch (axisE) {
		case X0:
			angleY = 90;
			break;
		case X1:
			angleY = -90;
			break;
		default:
		}
		m1.rotX(Math.toRadians(angleX));// 绕X轴旋转45度。
		m2.rotY(Math.toRadians(angleY));// 绕y轴旋转45度。
		m3.rotZ(Math.toRadians(angleZ));// 绕z轴旋转45度。
		m2.mul(m3);
		m1.mul(m2);
		return m1;
	}

	public static final Matrix3d getEnumAxisMatrix3d(EnumAxisExt axisWidth, EnumAxisExt axisHeight) {
		double[] arr = MQUtilsMatrix3d.getEnumAxis3dDouble(axisWidth, axisHeight);
		return getEnumAxisMatrix3d(arr[0], arr[1], arr[2]);
	}

	/**
	 * 依照 X轴 Y轴 Z轴进行旋转 double为角度，如：45/90/180 度
	 * @param angleX double
	 * @param angleY double
	 * @param angleZ double
	 * @return Matrix3d
	 */
	public static final Matrix3d getEnumAxisMatrix3d(double angleX, double angleY, double angleZ) {
		Matrix3d m1 = new Matrix3d();//x 方向
		Matrix3d m2 = new Matrix3d();//y 方向
		Matrix3d m3 = new Matrix3d();//z 方向
		m1.rotX(Math.toRadians(angleX));// 绕X轴旋转45度。
		m2.rotY(Math.toRadians(angleY));// 绕y轴旋转45度。
		m3.rotZ(Math.toRadians(angleZ));// 绕z轴旋转45度。
		m2.mul(m1);
		m3.mul(m2);
		return m3;
	}

	/**
	 * Color转成Appearance
	 * @param color Color
	 * @return Appearance
	 */
	public static final Appearance colorToAppearance(Color color) {
		if (color == null) return null;
		Appearance app = new Appearance();
		ColoringAttributes r = new ColoringAttributes();
		r.setColor(new Color3f(color));
		app.setColoringAttributes(r);
		return app;
	}

	/**
	 * 设置定位
	 * @param tf3d Transform3D
	 * @param x double
	 * @param y double
	 * @param z double
	 * @return Transform3D
	 */
	public static final Transform3D getTransform3DPoint(Transform3D tf3d, double x, double y, double z) {
		if (tf3d == null) tf3d = new Transform3D();
		tf3d.setTranslation(new Vector3d(x, y, z));
		return tf3d;
	}
}
