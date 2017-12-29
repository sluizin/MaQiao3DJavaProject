package com.maqiao.was.d3;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
/**
 * 方法转换3D
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 *
 */
public class MQUnitMethod {
	/** box定位 */
	double[] location = { 0f, 0f, 0f };
	/** 宽 */
	float width =.2f;
	/** 高 */
	float height = 1.9f;
	/** 长 */
	float length = 1f;
	/** 背景色 */
	Color bgColor = Color.BLUE;
	/** 名称 */
	String name = "";
	/** 显示标题 */
	String text = "com.maqiao.was.d3.MQUnitMethod";
	/**
	 * 输出 TransformGroup
	 * @return TransformGroup
	 */
	public TransformGroup getFormGroup() {
		TransformGroup boxGroup = new TransformGroup();
		boxGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D boxTransform3D = new Transform3D();
		boxTransform3D.setTranslation(new Vector3d(location[0], location[1], location[2]));
		boxGroup.setTransform(boxTransform3D);
		Appearance appearance = new Appearance();
		if (bgColor != null) {
			ColoringAttributes r = new ColoringAttributes();
			r.setColor(new Color3f(bgColor));
			appearance.setColoringAttributes(r);
		}
		Box box = new Box(width, height, length, Box.GENERATE_TEXTURE_COORDS, new Appearance());
		box.getShape(Box.FRONT).setAppearance(appearance);
		box.getShape(Box.BACK).setAppearance(appearance);
		box.getShape(Box.LEFT).setAppearance(appearance);
		
		boxGroup.addChild(box);
		return boxGroup;
	}
}
