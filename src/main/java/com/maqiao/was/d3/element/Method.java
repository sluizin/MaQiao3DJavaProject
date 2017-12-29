package com.maqiao.was.d3.element;

import java.util.ArrayList;
import java.util.List;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;

import com.maqiao.was.d3.unit.Square;


/**
 * 
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 *
 */
public class Method{
	/** 定位 */
	double[] location= {1,1,1};
	/** 方法名 */
	String methodName ="";
	/** 参数列表 */
	List<String> frontParaList=new ArrayList<String>();
	/** 注解字符串数组 */
	String[] annotationArr = {};
	/** 方法内，指令集 */
	List<String> statementList= new ArrayList<String>();
	
	public Node getNode() {
		TransformGroup group = new TransformGroup();
		int len=statementList.size();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		MethodBox methodBox=new MethodBox(methodName,frontParaList,location);
		methodBox.setBlockCount(len);
		methodBox.setLength(1.4f*len);
		methodBox.annotationArr=annotationArr;
		group.addChild(methodBox.getFormGroup());	
		group.addChild(methodBox.getSquareNode());
		group.addChild(methodBox.getAnnotation());
		group.addChild(getStatementNode(methodBox.getSquareListTop()));
		return group;
	}
	Node getStatementNode(List<Square> list) {
		TransformGroup group = new TransformGroup();
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		for(int i=0,len=list.size();i<len;i++) {
			Square e=list.get(i);
			e.setTextList(statementList.get(i));
			group.addChild(e.writeTextList());
		}
		return group;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void setFrontParaList(List<String> frontParaList) {
		this.frontParaList = frontParaList;
	}
	public void setAnnotationArr(String[] annotationArr) {
		this.annotationArr = annotationArr;
	}
	public void setStatementList(List<String> statementList) {
		this.statementList = statementList;
	}
	
}
