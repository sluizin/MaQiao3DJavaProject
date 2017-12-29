package com.maqiao.was.d3.painting;

import java.awt.Color;
import java.awt.Font;

import javax.media.j3d.Appearance;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.unit.Point;
import com.sun.j3d.utils.geometry.Text2D;

/**
 * 添加文字
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public final class MQFont {
	/**
	 * 添加文字
	 * @param color Color
	 * @param test String
	 * @param fontStyle String
	 * @param fontSize int
	 * @param fontType int
	 * @param point Point
	 * @return Node
	 */
	public static Node getFontText(Color color, String test, String fontStyle, int fontSize, int fontType, Point point) {
		return getFontText(color, test, fontStyle, fontSize, fontType, point.toDouble());
	}

	/**
	 * 添加文字
	 * @param color Color
	 * @param test String
	 * @param fontStyle String
	 * @param fontSize int
	 * @param fontType int
	 * @param coneVector3d float[]
	 * @return Node
	 */
	public static Node getFontText(Color color, String test, String fontStyle, int fontSize, int fontType, float[] coneVector3d) {
		return getFontText(color, test, fontStyle, fontSize, fontType, MQUtils.toDouble(coneVector3d));
	}

	/**
	 * 添加文字
	 * @param color Color
	 * @param test String
	 * @param fontStyle String
	 * @param fontSize int
	 * @param fontType int
	 * @param coneVector3d double[]
	 * @return Node
	 */
	public static Node getFontText(Color color, String test, String fontStyle, int fontSize, int fontType, double[] coneVector3d) {
		TransformGroup coneGroup = new TransformGroup();
		Transform3D coneTransform3D = new Transform3D();
		coneTransform3D.setTranslation(new Vector3d(coneVector3d));
		Text2D text = new Text2D(test, new Color3f(color), fontStyle, fontSize, fontType);
		coneGroup.setTransform(coneTransform3D);
		coneGroup.addChild(text);
		return coneGroup;
	}

	/**
	 * 添加文字
	 * @param color Color
	 * @param test String
	 * @param fontStyle String
	 * @param fontSize int
	 * @param fontType int
	 * @param axis EnumAxis
	 * @param coneVector3d double[]
	 * @return Node
	 */
	public static Node getFontText(Color color, String test, String fontStyle, int fontSize, int fontType, EnumAxis axis, double[] coneVector3d) {
		TransformGroup coneGroup = new TransformGroup();
		Transform3D coneTransform3D = new Transform3D();
		coneTransform3D.setTranslation(new Vector3d(coneVector3d));
		Text2D text = new Text2D(test, new Color3f(color), fontStyle, fontSize, fontType);
		//Text3D a=new Text3D();
		coneGroup.setTransform(coneTransform3D);
		coneGroup.addChild(text);
		return coneGroup;
	}

	public static Node getFontText(Text2D text, EnumAxis axis, double[] coneVector3d) {
		TransformGroup group = new TransformGroup();
		Transform3D transform3D = new Transform3D();
		transform3D.setTranslation(new Vector3d(coneVector3d));
		group.setTransform(transform3D);
		group.addChild(text);
		return group;
	}

	public static Node getFontText3DDefault(Point p, String text) {
		double[] vector3d = { p.getX(), p.getY(), p.getZ() };
		return getFontText3D(Color.BLACK, vector3d, text, "宋体", 1, 0.04, 90, 0, 0);
	}

	public static Node getFontText3DDefault(Point p, String text, double rotX, double rotY, double rotZ) {
		double[] vector3d = { p.getX(), p.getY(), p.getZ() };
		return getFontText3D(Color.BLACK, vector3d, text, "宋体", 1, 0.04f, rotX, rotY, rotZ);
	}

	public static Node getFontText3D(Color color, double[] location, String text, String fontStyle, int fontType, double size, double rotX, double rotY, double rotZ) {
		Font3D f3d = new Font3D(new Font(fontStyle, fontType, 18), new FontExtrusion());
		Text3D txt = new Text3D(f3d, new String(text), new Point3f((float) location[0], (float) location[1], (float) location[2]));
		txt.setString(text);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Appearance a = new Appearance();
		Color3f color3f = new Color3f(color);
		Material m = new Material(color3f, color3f, color3f, white, 80.0f);
		m.setLightingEnable(true);
		a.setMaterial(m);
		Shape3D sh = new Shape3D();
		sh.setGeometry(txt);
		sh.setAppearance(a);
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
		Transform3D rot = new Transform3D();
		rot.set(size);
		rot.setRotation(MQUtils.getEnumAxisMatrix3d(rotX, rotY, rotZ));
		t3d.mul(rot);
		tg.setTransform(t3d);
		tg.addChild(sh);
		return tg;
	}

	public static Node getFontText3DDefault(Point p, String text, EnumAxisExt axisWidth, EnumAxisExt axisHeight) {
		double[] vector3d = { p.getX(), p.getY(), p.getZ() };
		return getFontText3D(Color.BLACK, vector3d, text, "宋体", 1, 0.035f, axisWidth, axisHeight);
	}

	public static Node getFontText3D(Point p, String text, Color color, EnumAxisExt axisWidth, EnumAxisExt axisHeight) {
		double[] vector3d = { p.getX(), p.getY(), p.getZ() };
		return getFontText3D(color, vector3d, text, "黑体", 1, 0.035f, axisWidth, axisHeight);
	}

	public static Node getFontText3D(Point p, String text, Color color, double size, EnumAxisExt axisWidth, EnumAxisExt axisHeight) {
		double[] vector3d = { p.getX(), p.getY(), p.getZ() };
		return getFontText3D(color, vector3d, text, "黑体", 1, size, axisWidth, axisHeight);
	}

	public static Node getFontText3D(Point p, String text, Color color, double size, EnumAxis axisWidth, EnumAxis axisHeight) {
		double[] vector3d = { p.getX(), p.getY(), p.getZ() };
		return getFontText3D(color, vector3d, text, "黑体", 1, size, axisWidth.getForward(), axisHeight.getForward());
	}

	/**
	 * 得到3D文字
	 * @param color Color
	 * @param location double[]
	 * @param text String
	 * @param fontStyle String
	 * @param fontType int
	 * @param size double
	 * @param axisWidth EnumAxisExt
	 * @param axisHeight EnumAxisExt
	 * @return Node
	 */
	public static Node getFontText3D(Color color, double[] location, String text, String fontStyle, int fontType, double size, EnumAxisExt axisWidth, EnumAxisExt axisHeight) {
		Font3D f3d = new Font3D(new Font(fontStyle, fontType, 16), new FontExtrusion());
		Text3D txt = new Text3D(f3d, new String(text));
		Color3f white = new Color3f(2.0f, 2.0f, 2.0f);
		Appearance a = new Appearance();
		Color3f color3f = new Color3f(color);
		Material m = new Material(color3f, color3f, color3f, white, 80.0f);
		m.setLightingEnable(true);
		a.setMaterial(m);
		Shape3D sh = new Shape3D();
		sh.setGeometry(txt);
		sh.setAppearance(a);
		TransformGroup group = new TransformGroup();
		Transform3D rot = new Transform3D();
		rot.setScale(size);
		rot.setTranslation(new Vector3f((float) location[0], (float) location[1], (float) location[2]));
		rot.setRotation(MQUtils.getEnumAxisMatrix3d(axisWidth, axisHeight));
		group.setTransform(rot);
		group.addChild(sh);
		return group;
	}

}
