package com.maqiao.was.d3.painting;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import com.maqiao.was.d3.unit.Point;
import com.sun.j3d.utils.geometry.Cone;

/**
 * 圆锥形
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQCone {
	/**
	 * 圆锥形
	 * @param color Color
	 * @param coneArr float[]
	 * @param point Point
	 * @param rotateX double
	 * @param rotateY double
	 * @param rotateZ double
	 * @return Node
	 */
	public static final Node getCone(Color color, float[] coneArr, Point point, double rotateX, double rotateY, double rotateZ) {
		return getCone(color, coneArr, point.toDouble(), rotateX, rotateY, rotateZ);
	}

	/**
	 * 圆锥形
	 * @param color Color
	 * @param coneArr float[]
	 * @param coneVector3d double[]
	 * @param rotateX double
	 * @param rotateY double
	 * @param rotateZ double
	 * @return Node
	 */
	public static final Node getCone(Color color, float[] coneArr, double[] coneVector3d, double rotateX, double rotateY, double rotateZ) {
		Appearance appearance = new Appearance();
		ColoringAttributes r = new ColoringAttributes();
		r.setColor(new Color3f(color));
		appearance.setColoringAttributes(r);
		TransformGroup coneGroup = new TransformGroup();
		Transform3D coneTransform3D = new Transform3D();
		Matrix3d m1 = new Matrix3d();//x 方向
		Matrix3d m2 = new Matrix3d();//y 方向
		Matrix3d m3 = new Matrix3d();//z 方向
		m1.rotX(Math.toRadians(rotateX));// 绕X轴旋转45度。
		m2.rotY(Math.toRadians(rotateY));// 绕y轴旋转45度。
		m3.rotZ(Math.toRadians(rotateZ));// 绕z轴旋转45度。
		m2.mul(m3);
		m1.mul(m2);
		coneTransform3D.setRotation(m1);
		coneTransform3D.setTranslation(new Vector3d(coneVector3d));
		coneGroup.setTransform(coneTransform3D);
		Cone cone = new Cone(coneArr[0], coneArr[1], appearance);
		coneGroup.addChild(cone);
		return coneGroup;
	}
}
