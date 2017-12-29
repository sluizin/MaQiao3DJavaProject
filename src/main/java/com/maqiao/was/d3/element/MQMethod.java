package com.maqiao.was.d3.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Statement;

import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.MQUtilsJDT;
import com.maqiao.was.d3.painting.MQFont;
import com.maqiao.was.d3.unit.Point;
import com.maqiao.was.d3.unit.Square;

/**
 * 方法
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQMethod extends AbstractBox {
	/** 方法注释 */
	Javadoc doc = null;
	/** box名称 */
	String frontName = null;
	/** 方法 */
	IMethod iMethod = null;
	/** 参数名称 */
	List<String> paraNameList = new ArrayList<String>();
	/** 注解字符串数组 */
	List<String> annotationList = new ArrayList<String>();
	/** 连接点颜色 */
	Color pointColor = Color.BLACK;
	/** 方法内，指令集 */
	List<Statement> statementList = new ArrayList<Statement>();

	@Override
	public TransformGroup getFormGroup() {
		if (iMethod == null) return null;
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		/* 画出立方体 */
		group.addChild(getNodeBox());
		/* 画出线圈 */
		group.addChild(getNodeRoundWires(false));
		/* 画出前面的文字列表 */
		group.addChild(getFrontNode());
		/* 画出注解 */
		group.addChild(getAnnotation());
		/* 指令集 */
		group.addChild(getStatementNode());
		return group;
	}

	/**
	 * 自动计算出立方体的尺寸
	 */
	void autoSize() {
		/* 设置长度 */
		this.setLength(1.4f * this.getBlockCount());
		/* 设置宽度 找出最长的字段，计算宽度 */
		float width = 0;
		String str = "";
		if (frontName != null && frontName.length() > 0) str = frontName;
		for (int i = 0, len = paraNameList.size(); i < len; i++)
			if (paraNameList.get(i).length() > str.length()) str = paraNameList.get(i);
		width = 0.1f + str.length() * 0.14f;
		if (width < 4f) width = 4f;
		this.setWidth(width);
		/* 设置高度 */
		float height = 0;
		if (frontName != null && frontName.length() > 0) height += 0.8;
		height += paraNameList.size() * 0.35;
		setHeight(height);
	}

	@SuppressWarnings("unused")
	private MQMethod() {

	}

	public MQMethod(double[] location, IMethod iMethod) {
		this.location = location;
		this.iMethod = iMethod;
		Initialization();
	}

	/**
	 * 初始化
	 */
	void Initialization() {
		if (iMethod == null) return;
		this.frontName = iMethod.getElementName();
		/* 得到参数列表 */
		try {
			ILocalVariable[] arr = iMethod.getParameters();
			for (int i = 0; i < arr.length; i++) {
				ILocalVariable e = arr[i];
				paraNameList.add("[" + i + "]:" + e.getElementName() + "\t" + e.getElementType());
			}
		} catch (JavaModelException ee) {
			ee.printStackTrace();
		}
		/* 得到注解 */
		try {
			IAnnotation[] arr = iMethod.getAnnotations();
			for (int i = 0; i < arr.length; i++) {
				IAnnotation e = arr[i];
				annotationList.add(e.getElementName());
			}

		} catch (JavaModelException ee) {
			ee.printStackTrace();
		}
		/* 得到此方法的指令集 */
		this.statementList = MQUtilsJDT.getStatementsList(iMethod);
		/* 自动产生尺寸 */
		autoSize();
		/* 初始化线圈 */
		initialSquare();
	}

	@Override
	public int getBlockCount() {
		return statementList.size();
	}

	/**
	 * 前端有多少行。包括名称与参数列表行
	 * @return int
	 */
	public int getFrontRows() {
		return paraNameList.size() + (frontName == null ? 0 : 1);
	}

	/** 名称文字大小 */
	static final float ACC_fontNameSize = 0.026f;
	/** 名称文字颜色 */
	static final Color ACC_fontNameColor = Color.PINK;
	/** 参数文字大小 */
	static final float ACC_fontParaSize = 0.015f;
	/** 参数文字颜色 */
	static final Color ACC_fontParaColor = Color.BLACK;
	/** 注解文字大小 */
	static final float ACC_AnnotationSize = 0.018f;
	/** 注解文字颜色 */
	static final Color ACC_AnnotationColor = Color.BLUE;
	/** 注解行间距 */
	static final float ACC_AnnotationRowSpacing = 0.25f;
	/** 方法或属性与注解的间距 */
	static final double ACC_Annotationspace = 0.04;

	/**
	 * 画出前面的文字列表
	 * @return Node
	 */
	private Node getFrontNode() {
		int num = getFrontRows();
		squareFront.putPoint(num);
		List<Point> list = squareFront.getlPointList();
		Collections.reverse(list);
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		int i = 0;
		EnumAxisExt axisWidth = EnumAxisExt.X0;
		EnumAxisExt axisHeight = EnumAxisExt.Z0;
		if (frontName != null) {
			Point p = list.get(i++);
			p.setXoffset(ACC_fontOffset);
			group.addChild(MQFont.getFontText3D(p, frontName, ACC_fontNameColor, ACC_fontNameSize, axisWidth, axisHeight));
		}
		for (int ii = 0; i < list.size(); i++) {
			Point p = list.get(i);
			p.setXoffset(ACC_fontOffset);
			group.addChild(MQFont.getFontText3D(p, paraNameList.get(ii++), ACC_fontParaColor, ACC_fontParaSize, axisWidth, axisHeight));
		}
		return group;
	}

	/**
	 * 画出注解
	 * @return Node
	 */
	public Node getAnnotation() {
		int len = annotationList.size();
		if (len == 0) return null;
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		double z = location[2] + height + ACC_Annotationspace;
		for (int i = 0; i < len; i++) {
			Point p = new Point(location[0], location[1], z);
			p.setXoffset(ACC_fontOffset);
			group.addChild(MQFont.getFontText3D(p, annotationList.get(i), ACC_AnnotationColor, ACC_AnnotationSize, EnumAxisExt.X0, EnumAxisExt.Z0));
			z += ACC_AnnotationRowSpacing;
		}
		return group;
	}

	/**
	 * 在boxz正方向上显示指集
	 * @return Node
	 */
	public Node getStatementNode() {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		List<Square> list = getSquareListTop();
		for (int i = 0, len = list.size(); i < len; i++) {
			Square e = list.get(i);
			e.setTextList(MQUtilsJDT.getStatementText(statementList.get(i)));
			group.addChild(e.writeTextList(Const.ACC_StatementfontSize));
		}
		return group;
	}

	@Override
	public String toString() {
		return "MQMethod [frontName=" + frontName + ", iMethod=" + iMethod + ", paraNameList=" + paraNameList + ", annotationList=" + annotationList + ", pointColor=" + pointColor + ", statementList=" + statementList + ", roundColor="
				+ roundColor + ", squareFront=" + squareFront + ", squareBack=" + squareBack + ", squareList=" + squareList + ", location=" + Arrays.toString(location) + ", width=" + width + ", height=" + height + ", length=" + length
				+ ", bgColor=" + bgColor + ", CubeColorMap=" + CubeColorMap + "]";
	}
	/**
	 * 是否含有注释
	 * @return boolean
	 */
	public boolean isJavadoc() {
		return doc != null && doc.getLength() > 0;
	}

}
