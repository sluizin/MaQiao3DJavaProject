package com.maqiao.was.d3.painting;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Sphere;

/**
 * 小圆球
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQSphere {
	/**
	 * 得到小圆球
	 * @param endPointSize float
	 * @param color Color
	 * @return Node
	 */
	public static Node getSphere(float endPointSize, Color color) {
		Appearance appearance = new Appearance();
		ColoringAttributes r = new ColoringAttributes();
		r.setColor(new Color3f(color));
		appearance.setColoringAttributes(r);
		Sphere sphere = new Sphere(endPointSize, appearance); // 小球的半径为0.5米
		return sphere;
	}

	/**
	 * 得到小圆球
	 * @param endPointSize float
	 * @param appearance Appearance
	 * @return Node
	 */
	public static Node getSphere(float endPointSize, Appearance appearance) {
		return new Sphere(endPointSize, appearance); // 小球的半径为0.5米
	}
}
