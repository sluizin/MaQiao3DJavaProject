package com.maqiao.was.d3.element;

import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * 方法对象组，并列关系
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQMethodGroupParallel {
	/** 类 */
	IType iType = null;
	/** 最底层的位置 */
	double[] local = { 1, 1, 0 };
	/** 属性之间的间隔 */
	double size = 0.2;

	@SuppressWarnings("unused")
	private MQMethodGroupParallel() {

	}

	public MQMethodGroupParallel(IType iType) {
		this.iType = iType;
	}

	public Node getNode() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		try {
			IMethod[] iMethodArr = iType.getMethods();
			double x = 1f;
			for (int i = 0; i < iMethodArr.length; i++) {
				double[] location = { x, 1f, 1f };
				IMethod iMethod = iMethodArr[i];
				MQMethod mqMethod = new MQMethod(location,iMethod);
				group.addChild(mqMethod.getFormGroup());
				x = mqMethod.getTopXmax() + Const.ACC_MethodXInterval;
			}
		} catch (JavaModelException ee) {
			ee.printStackTrace();
		}
		return group;
	}
}
