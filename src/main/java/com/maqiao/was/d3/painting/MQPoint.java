package com.maqiao.was.d3.painting;

import java.awt.Color;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.maqiao.was.d3.unit.Point;

import static com.maqiao.was.d3.MQConst.ACC_EndPointSize;
import static com.maqiao.was.d3.MQConst.ACC_Color_BLACK;

/**
 * 线段中的某个点
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public final class MQPoint {

	/**
	 * 线段中的某个点
	 * @param location Point
	 * @return Node
	 */
	public static final Node getPoint(Point location) {
		return getPoint(location, ACC_Color_BLACK);
	}
	
	/**
	 * 线段中的某个点
	 * @param location Point
	 * @param color Color
	 * @return Node
	 */
	public static final Node getPoint(Point location, Color color) {
		return getPoint(location, color, ACC_EndPointSize);
	}

	/**
	 * 线段中的某个点
	 * @param location Point
	 * @param color Color
	 * @param endPointSize float
	 * @return Node
	 */
	public static final Node getPoint(Point location, Color color, float endPointSize) {
		Appearance appearance = new Appearance();
		ColoringAttributes r = new ColoringAttributes();
		r.setColor(new Color3f(color));
		appearance.setColoringAttributes(r);
		TransformGroup group = new TransformGroup();
		Transform3D transform3D = new Transform3D();
		transform3D.setTranslation(new Vector3d(location.toDouble()));
		group.setTransform(transform3D);
		Node sphere = MQSphere.getSphere(endPointSize, appearance);
		group.addChild(sphere);
		return group;
	}
}
