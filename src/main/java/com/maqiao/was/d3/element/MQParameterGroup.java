package com.maqiao.was.d3.element;

import java.util.ArrayList;
import java.util.List;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import com.maqiao.was.d3.MQUtilsJDT;

/**
 * 属性组
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQParameterGroup {
	/** 最底层的位置 */
	double[] local = { -8, 1, 0 };
	/** 属性之间的间隔 */
	double size = 0.2;
	/** 属性之间的错列 */
	double ysize = 0.8;
	/** 长度 */
	float length = 15f;
	/** 属性对象集 */
	IField[] iFieldArr = {};

	/**
	 * 得到属性组Node
	 * @return Node
	 */
	public Node getNode() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		double x = local[0], y = local[1], z = local[2];
		try {
			for (int i = 0; i < iFieldArr.length; i++) {
				IField ifield = iFieldArr[i];
				List<String> frontParaList = new ArrayList<String>();
				frontParaList.add("Name: " + ifield.getElementName());
				frontParaList.add("TYPE: " + ifield.getTypeSignature());
				double[] location = { x, y, z };
				Parameter parameter = new Parameter(null, frontParaList, location);
				parameter.setLength(length);
				if (MQUtilsJDT.isStatic(ifield.getFlags())) parameter.setBgColor(Const.ACC_StaticBgColor);
				IAnnotation[] arr = ifield.getAnnotations();
				parameter.annotationArr = MQUtilsJDT.getAnnotation(arr);
				group.addChild(parameter.getFormGroup());
				y = y + ysize;
				z = parameter.getTopZmax() + size;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return group;
	}
}
