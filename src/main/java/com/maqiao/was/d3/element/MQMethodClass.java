package com.maqiao.was.d3.element;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Statement;

import com.maqiao.was.d3.MQUtilsJDT;

/**
 * IMethod 分解
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQMethodClass {
	/** 方法名 */
	String methodName = null;
	/** 方法注释 */
	Javadoc doc = null;
	/** 方法 */
	IMethod iMethod = null;
	/** 参数名称 */
	List<String> paraNameList = new ArrayList<String>();
	/** 注解字符串数组 */
	List<String> annotationList = new ArrayList<String>();
	/** 方法内，指令集 */
	List<Statement> statementList = new ArrayList<Statement>();

	/**
	 * 初始化
	 */
	void Initialization() {
		if (iMethod == null) return;
		this.methodName = iMethod.getElementName();
		try {
			/* 得到参数列表 */
			ILocalVariable[] arr = iMethod.getParameters();
			for (int i = 0; i < arr.length; i++) {
				ILocalVariable e = arr[i];
				paraNameList.add("[" + i + "]:" + e.getElementName() + "   " + e.getTypeSignature());
			}
			/* 得到注解 */
			IAnnotation[] arr1 = iMethod.getAnnotations();
			for (int i = 0; i < arr1.length; i++) {
				IAnnotation e = arr1[i];
				annotationList.add(e.getElementName());
			}
		} catch (JavaModelException ee) {
			ee.printStackTrace();
		}
		/* 得到此方法的指令集 */
		this.statementList = MQUtilsJDT.getStatementsList(iMethod);
	}

	@SuppressWarnings("unused")
	private MQMethodClass() {

	}

	public MQMethodClass(IMethod iMethod) {
		this.iMethod = iMethod;
		Initialization();
	}

}
