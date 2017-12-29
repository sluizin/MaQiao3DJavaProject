package com.maqiao.was.d3;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.painting.MQCone;
import com.maqiao.was.d3.painting.MQFont;
import com.maqiao.was.d3.painting.MQLine;
import com.maqiao.was.d3.unit.Point;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

public class Init {
	/**
	 * 设置BranchGroup与背景色
	 * @param bounds
	 * @param color
	 * @return BranchGroup
	 */
	public static void initBranchGroup(BranchGroup branchGroup, TransformGroup transformGroup, BoundingSphere bounds, Color color) {
		if (bounds == null) bounds = new BoundingSphere(new Point3d(0, 2.0, 7.0), 1000.0);
		//set coordinates
		Transform3D transform3D = new Transform3D();
		transform3D.setTranslation(new Vector3d(0, 0, -7));
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformGroup.setTransform(transform3D);
		//set back color
		Color3f bgColor3f = new Color3f(color);
		Background background = new Background(bgColor3f);
		background.setApplicationBounds(bounds);
		branchGroup.addChild(background);
	}

	/**
	 * 添加鼠标功能
	 * @param t
	 * @param bounds
	 */
	public static void putMouse(TransformGroup t, BoundingSphere bounds) {
		if (bounds == null) bounds = new BoundingSphere(new Point3d(0, 2.0, 7.0), 1000.0);
		//set mouse's behavior
		MouseRotate mouseRotate = new MouseRotate();
		mouseRotate.setTransformGroup(t);
		mouseRotate.setSchedulingBounds(bounds);
		t.addChild(mouseRotate);

		MouseWheelZoom mouseZoom = new MouseWheelZoom();
		mouseZoom.setTransformGroup(t);
		mouseZoom.setSchedulingBounds(bounds);
		t.addChild(mouseZoom);

		MouseTranslate mouseTranslate = new MouseTranslate();
		mouseTranslate.setTransformGroup(t);
		mouseTranslate.setSchedulingBounds(bounds);
		t.addChild(mouseTranslate);
	}

	/**
	 * 坐标三条线
	 * @param t TransformGroup
	 * @param width float
	 * @param colorArr Color[]
	 * @param isPunctuation boolean
	 * @param keyPercent float
	 */
	public static void putCoordinate(TransformGroup t, float width, Color[] colorArr,boolean isPunctuation, float keyPercent) {
		float[] linevertX = { -width, 0f, 0f, width, 0f, 0f };
		float[] linevertY = { 0, -width, 0f, 0, width, 0f };
		float[] linevertZ = { 0, 0, -width, 0, 0, width };
		float keyWidth = width * (1 - keyPercent);

		final float lineWidth = 7.0f;
		final LineAttributes lineAttributes = new LineAttributes();
		lineAttributes.setLineWidth(lineWidth);
		Appearance lineappearance = new Appearance();
		PointAttributes e = new PointAttributes(0.7f, true);
		lineappearance.setPointAttributes(e);
		final float[] ConeArr = { 0.1f, 0.3f };
		final double size=0.06f;
		/*
		 * X
		 */
		{
			
		Color xColor = colorArr[0];
		t.addChild(MQLine.getLine(linevertX, xColor, lineWidth));
		double[] coneVector3dX = { width, 0, 0 };
		t.addChild(MQCone.getCone(xColor, ConeArr, coneVector3dX, 0, 0, -90));
		//t.addChild(MQFont.getFontText(xColor, "X", "黑体", 120, 2, coneVector3dPX));

		double[] vector3d = { keyWidth, 0, 0 };
		EnumAxisExt axisWidth=EnumAxisExt.X0;
		EnumAxisExt axisHeight=EnumAxisExt.Y0;
		t.addChild(MQFont.getFontText3D(new Point(vector3d), "X",xColor,size,axisWidth,axisHeight));
		}
		/*
		 * Y
		 */
		{
		Color yColor = colorArr[1];
		t.addChild(MQLine.getLine(linevertY, yColor, lineWidth));

		double[] coneVector3dY = { 0, width, 0 };
		t.addChild(MQCone.getCone(yColor, ConeArr, coneVector3dY, 0, 0, 0));

		//double[] coneVector3dPY = { 0, keyWidth, 0 };
		//t.addChild(MQFont.getFontText(yColor, "Y", "黑体", 80, 2, coneVector3dPY));

		double[] vector3d = {0, keyWidth,  0 };
		EnumAxisExt axisWidth=EnumAxisExt.X1;
		EnumAxisExt axisHeight=EnumAxisExt.Y0;
		t.addChild(MQFont.getFontText3D(new Point(vector3d), "Y",yColor,size,axisWidth,axisHeight));
		}
		/*
		 * Z
		 */
		{
		Color zColor = colorArr[2];
		t.addChild(MQLine.getLine(linevertZ, zColor, lineWidth));

		double[] coneVector3dZ = { 0, 0, width };
		t.addChild(MQCone.getCone(zColor, ConeArr, coneVector3dZ, 90, 0, 0));

		//double[] coneVector3dPZ = { 0, 0, keyWidth };
		//t.addChild(MQFont.getFontText(zColor, "Z", "黑体", 80, 2, coneVector3dPZ));

		double[] vector3d = {0, 0,keyWidth };
		EnumAxisExt axisWidth=EnumAxisExt.X1;
		EnumAxisExt axisHeight=EnumAxisExt.Y0;
		t.addChild(MQFont.getFontText3D(new Point(vector3d), "Z",zColor,size,axisWidth,axisHeight));
		}
		if(isPunctuation)
		for(float i=1;i<width;i++) {
			Point point1=Point.getDefPoint(Color.BLACK, i, 0, 0);
			t.addChild(point1.getNode());
			Point point2=Point.getDefPoint(Color.BLACK, 0, i, 0);
			t.addChild(point2.getNode());
			Point point3=Point.getDefPoint(Color.BLACK, 0, 0, i);
			t.addChild(point3.getNode());
		}
	}
}
