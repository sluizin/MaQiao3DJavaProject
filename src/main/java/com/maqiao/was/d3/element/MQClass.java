package com.maqiao.was.d3.element;

import java.awt.Color;
//import java.util.List;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.PointLight;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
//import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.maqiao.was.d3.Init;
import com.maqiao.was.d3.MQUtilsJDT;

/**
 * 类的分解
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQClass {
	/** 类对象 */
	IType iType = null;
	/** 属性对象集 */
	IField[] iFieldArr = {};
	/** 方法对象集 */
	IMethod[] iMethodArr = {};
	/** 方法列表 
	@SuppressWarnings("unused")
	private List<MethodDeclaration> list = null;*/

	/**
	 * 得到属性组
	 * @return Node
	 */
	private Node getNodeField() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		MQParameterGroup pGroup = new MQParameterGroup();
		pGroup.iFieldArr = this.iFieldArr;
		group.addChild(pGroup.getNode());
		return group;
	}

	private Node getNodeMethod() {
		MQMethodGroupParallel t=new MQMethodGroupParallel(iType);
		return t.getNode();
	}

	public MQClass() {

	}

	public MQClass(IType iType) {
		this.iType = iType;
		Initialization();
	}

	public void Initialization() {
		if (iType == null) return;
		/* 此类的所有属性 */
		try {
			iFieldArr = MQUtilsJDT.getIFieldSort(true, iType.getFields());
			iMethodArr = MQUtilsJDT.getIMethodSort(true, iType.getMethods());
			/* 得到方法列表 List<MethodDeclaration> */
			/*this.list = MQUtilsJDT.getMethodDeclarationList(iType);*/
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	public BranchGroup getBranchGroup() {
		BranchGroup branchGroup = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 1000.0);
		TransformGroup group = new TransformGroup();
		Init.initBranchGroup(branchGroup, group, bounds, Color.GRAY);
		Init.putMouse(group, bounds);
		/* 坐标三条线 */
		Color[] colorArr = { Color.RED, Color.BLUE, Color.ORANGE };
		Init.putCoordinate(group, 22f, colorArr, false, 0.12f);
		group.addChild(getNodeField());
		group.addChild(getNodeMethod());

		branchGroup.addChild(group);
		PointLight p = new PointLight();
		branchGroup.addChild(p);
		return branchGroup;
	}
}
