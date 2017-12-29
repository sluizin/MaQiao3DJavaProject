package com.maqiao.was.d3.painting;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color4f;

import static com.maqiao.was.d3.MQConst.ACC_LineWidth;
import static com.maqiao.was.d3.MQConst.ACC_Color_BLACK;
import com.maqiao.was.d3.MQUtils;
import com.maqiao.was.d3.unit.Point;

/**
 * 得到直线
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQLine {

	/**
	 * 得到直线
	 * @param pointX Point
	 * @param pointY Point
	 * @param lineWidth float
	 * @return Node
	 */
	public static Node getLine(Point pointX, Point pointY) {
		return getLine(pointX, pointY, ACC_Color_BLACK, ACC_LineWidth);
	}
	/**
	 * 得到直线
	 * @param pointX Point
	 * @param pointY Point
	 * @param lineWidth float
	 * @return Node
	 */
	public static Node getLine(Point pointX, Point pointY, float lineWidth) {
		return getLine(pointX, pointY,ACC_Color_BLACK, lineWidth);
	}

	/**
	 * 得到直线
	 * @param pointX Point
	 * @param pointY Point
	 * @param color Color
	 * @param lineWidth float
	 * @return Node
	 */
	public static Node getLine(Point pointX, Point pointY, Color color) {
		if(pointX==null || pointY==null)return null;
		return getLine(MQUtils.concatAll(pointX, pointY), color, ACC_LineWidth);
	}

	/**
	 * 得到直线
	 * @param pointX Point
	 * @param pointY Point
	 * @param color Color
	 * @param lineWidth float
	 * @return Node
	 */
	public static Node getLine(Point pointX, Point pointY, Color color, float lineWidth) {
		return getLine(MQUtils.concatAll(pointX, pointY), color, lineWidth);
	}

	/**
	 * 得到直线
	 * @param pointX
	 * @param color
	 * @param lineWidth
	 * @return Node
	 */
	public static Node getLine(float[] linevertX, Color color, float lineWidth) {
		LineArray lineX = new LineArray(2, LineArray.COORDINATES | LineArray.COLOR_4);
		lineX.setCoordinates(0, linevertX);
		Color4f[] colors = { new Color4f(color) };
		lineX.setColors(0, colors);

		LineAttributes lineAttributes = new LineAttributes();
		lineAttributes.setLineWidth(lineWidth);

		Appearance lineappearance = new Appearance();
		PointAttributes e = new PointAttributes(0.5f, true);
		lineappearance.setPointAttributes(e);

		TransformGroup lineGroupX = new TransformGroup();
		lineGroupX.setTransform(new Transform3D());
		Shape3D lineShape3DX = new Shape3D();
		lineShape3DX.setGeometry(lineX);
		lineShape3DX.setAppearance(lineappearance);
		lineGroupX.addChild(lineShape3DX);
		return lineGroupX;
	}
}
