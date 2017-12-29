package com.maqiao.was.d3;

import static com.maqiao.was.d3.unit.Point.getDefPoint;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import com.maqiao.was.d3.MQConst.EnumAxis;
import com.maqiao.was.d3.MQConst.EnumAxisExt;
import com.maqiao.was.d3.element.Method;
import com.maqiao.was.d3.element.MethodBox;
import com.maqiao.was.d3.element.Parameter;
import com.maqiao.was.d3.painting.MQFont;
import com.maqiao.was.d3.painting.MQLineSegment;
import com.maqiao.was.d3.painting.MQLineSegmentGroup;
import com.maqiao.was.d3.painting.MQRoundWire;
import com.maqiao.was.d3.painting.MQLineSegmentGroup.PointGroup;
import com.maqiao.was.d3.unit.Point;
import com.maqiao.was.d3.unit.Square;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class test extends Applet {
	static BranchGroup getBranchGroup() {
		BranchGroup branchGroup = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 1000.0);
		TransformGroup group = new TransformGroup();
		Init.initBranchGroup(branchGroup, group, bounds, Color.GRAY);
		Init.putMouse(group, bounds);

       // MQUtilsRandColor randColor = new MQUtilsRandColor();
		boolean t=false;

		{
			/* 坐标三条线 */
			Color[] colorArr = { Color.RED, Color.BLUE, Color.ORANGE };
			Init.putCoordinate(group, 22f, colorArr,false, 0.12f);
		}

		{
			/* 线段 */
			Point pointX = new Point(-4.0f, -1f, 4f);
			Point pointY = new Point(-1f, -2f, 1f);
			group.addChild(MQLineSegment.getDef(pointX, pointY));
			/* 多段线段 */
			Point apointX = new Point(-4f, -3f, 1f);
			Point apointY = new Point(-0.4f, -3f, 1f);
			//float[][] locationArr= {{-3.2f,2.8f,1f },{-2.6f,1f,1f },{-1.8f,-2.4f,1f }};
			Point[] locationArr = { new Point(-3.2f, -2.8f, 1f), new Point(-2.6f, -1f, 1f) };
			group.addChild(MQLineSegment.getDef(0 | 1 | 2, apointX, apointY, locationArr));
			/* 多段线段 */
			Point bpointX = new Point(-5f, -3f, 1f);
			Point bpointY = new Point(-2f, -4f, 3f);
			group.addChild(MQLineSegment.getDef(0 | 1 | 2, bpointX, bpointY, 1, 2));
		}
		if(t){
			int width=8;
			int height=6;

			Point a=Point.getDefPoint(Color.ORANGE, -6, -3, 2);
			Point b=Point.getDefPoint(Color.ORANGE,a.getX()-width, a.getY(), a.getZ());
			Point c=Point.getDefPoint(Color.ORANGE,b.getX(), b.getY(), b.getZ()+height);
			Point d=Point.getDefPoint(Color.ORANGE,a.getX(), a.getY(), a.getZ()+height);
			
			
			group.addChild(a.getNode());
			group.addChild(b.getNode());
			group.addChild(c.getNode());
			group.addChild(d.getNode());
			Square square=new Square(a,b,c,d);
			square.setColAxis(EnumAxis.Z);
			square.putPoint(6);
			group.addChild(square.getFormGroup());
		}
		if(t){
			//EnumAxisExt[] arrAxis=EnumAxisExt.values();
			Point p=new Point(10,4,4);
			EnumAxisExt axisWidth=EnumAxisExt.X0;
			EnumAxisExt axisHeight=EnumAxisExt.Z0;
			//group.addChild(MQFont.getFontTextDefault(p, text, axisE));
			//group.addChild(MQFont.getFontText3DDefault(p, "java.maqiao.was.d中国人3"));
			group.addChild(MQFont.getFontText3DDefault(p, "java.maqiao.was.d中国人3",axisWidth,axisHeight));
			//group.addChild(MQFont.getFontText3DDefault(p, "java.maqiao.was.d中国人3",-90,-90,180));
/*			int[] arr= {-90,0,90,180};
			for(int i=3;;) {
				for(int ii=0;;) {
					for(int iii=0;iii<arr.length;iii++) {
						String str="["+arr[i]+"]["+arr[ii]+"]["+arr[iii]+"]";
						group.addChild(MQFont.getFontText3DDefault(p, str,arr[i],arr[ii],arr[iii]));
					}
					break;
				}
				break;
			}*/
			
		}
		if(t){
			Point a=null;
			float pointSize=0.02f;
			for(float i=0.1f;i<20;i+=0.1) {
				int p=1;
				a=Point.getDefPoint(Color.ORANGE, i, p++, Math.E);
				group.addChild(a.getNode(pointSize));
				a=Point.getDefPoint(Color.ORANGE, i, p++, Math.sin(i));
				group.addChild(a.getNode(pointSize));
				a=Point.getDefPoint(Color.ORANGE, i, p++, Math.cos(i));
				group.addChild(a.getNode(pointSize));
				a=Point.getDefPoint(Color.ORANGE, i, p++, Math.tan(i));
				group.addChild(a.getNode(pointSize));
				a=Point.getDefPoint(Color.ORANGE, i, p++, Math.atan(i));
				group.addChild(a.getNode(pointSize));
				a=Point.getDefPoint(Color.ORANGE, i, p++, Math.ceil(i));
				group.addChild(a.getNode(pointSize));
				a=Point.getDefPoint(Color.ORANGE, i, p++, Math.sqrt(i));
				group.addChild(a.getNode(pointSize));
			}
			
		}
		if(t){
			Appearance appearance=new Appearance();
			{
				ColoringAttributes r = new ColoringAttributes();
				r.setColor(new Color3f(Color.BLUE));
				appearance.setColoringAttributes(r);
				
			}
			Appearance appearance2=new Appearance();
				{
					ColoringAttributes r = new ColoringAttributes();
					r.setColor(new Color3f(Color.WHITE));
					appearance2.setColoringAttributes(r);
				}
			Appearance appearance3=new Appearance();
				{
					ColoringAttributes r = new ColoringAttributes();
					r.setColor(new Color3f(Color.YELLOW));
					appearance3.setColoringAttributes(r);
				}
			Cylinder box=new Cylinder(1.2f,6.5f);
			box.getShape(0).setAppearance(appearance);
			box.getShape(1).setAppearance(appearance2);
			box.getShape(2).setAppearance(appearance3);
			
			group.addChild(box);
		}
		{
			PointGroup a =null;
			{
				Point[] pointArr= {getDefPoint(Color.GREEN,1.1f,-1.2f,2),getDefPoint(Color.GREEN,1.8f,-2.4f,1),getDefPoint(Color.GREEN,4.8f,-0.4f,3)};
				Point pointIntersect=getDefPoint(2.5f, -5.1f, 3f);
				pointIntersect.setColor(Color.RED);
				Point pGroup=getDefPoint(Color.ORANGE,4.5f, -3.8f, 3f);
				PointGroup pointGroupA=new PointGroup(pointArr,pointIntersect,pGroup);
				
				EnumAxis[] axisInnerArr= {EnumAxis.Y,EnumAxis.Z};
				pointGroupA.setAxisInnerArr(axisInnerArr);
				
				EnumAxis[] axisOuterArr= {EnumAxis.Y,EnumAxis.Z};
				pointGroupA.setAxisOuterArr(axisOuterArr);
				
				pointGroupA.setAxisISP(EnumAxis.X,EnumAxis.Z);
				pointGroupA.setAutoISP(true);
				
				pointGroupA.setPaintGP(true);
				pointGroupA.setAutoGP(true);
				pointGroupA.setRangAutoGP(1f);
				pointGroupA.setAxisGP(EnumAxis.Y);
				
				pointGroupA.Initialization();
				a = pointGroupA;
			}

			PointGroup b =null;
			{
				Point[] pointArr= {getDefPoint(Color.GREEN,1.4f,-8.1f,2),getDefPoint(Color.BLACK,4.8f,-8.8f,3),getDefPoint(Color.GREEN,1.8f,-9.5f,2)};
				Point pointIntersect=getDefPoint(2.5f, -5.1f, 3f);
				pointIntersect.setColor(Color.RED);
				Point pGroup=getDefPoint(Color.PINK,6.5f, -6.1f, 2f);
				PointGroup pointGroupA=new PointGroup(pointArr,pointIntersect,pGroup);
				
				EnumAxis[] axisInnerArr= {EnumAxis.Y,EnumAxis.Z};
				pointGroupA.setAxisInnerArr(axisInnerArr);
				
				EnumAxis[] axisOuterArr= {EnumAxis.Y,EnumAxis.Z};
				pointGroupA.setAxisOuterArr(axisOuterArr);
				
				pointGroupA.setAxisISP(EnumAxis.X,EnumAxis.Z);
				
				pointGroupA.setAutoISP(true);
				pointGroupA.setPaintGP(true);

				pointGroupA.setPaintGP(true);
				pointGroupA.setAutoGP(true);
				pointGroupA.setAxisGP(EnumAxis.Y);

				pointGroupA.Initialization();
				b = pointGroupA;
			}
			
			
			Point[] pointArr= {getDefPoint(Color.GREEN,1.8f,-4.1f,6)};
			
			Node f=MQLineSegmentGroup.mergePointGroup(a, b,pointArr);
			group.addChild(f);
			
		}
		{

			Point point1=Point.getDefPoint(Color.BLUE, 4, -3, 3);
			Point point2=Point.getDefPoint(Color.BLUE, 4, -3, 3+2);
			Point point3=Point.getDefPoint(Color.BLUE, 4+2, -3, 3+2);
			Point point4=Point.getDefPoint(Color.BLUE, 4+2, -3, 3);
			group.addChild(MQRoundWire.getRoundLine(Color.BLUE,false, point1,point2,point3,point4));
		}
		if(t){
			List<String> frontParaList=new ArrayList<String>();
			frontParaList.add("[0]:axis [com.maqiao.was.d3.MQConst.EnumAxis]");
			frontParaList.add("[1]:num [int]");
			frontParaList.add("[2]:str [String]");
			frontParaList.add("[3]:newList [java.util.List<String>]");
			frontParaList.add("[4]:angleX [double[]]");
			double[] location= {1,1,1};
			MethodBox methodBox=new MethodBox("Method:getBranchGroup",frontParaList,location);
			group.addChild(methodBox.getFormGroup());

			{/* 设置外围点 */
				methodBox.putPointLeftArr(2,4,5,6,2,7,4,1,2);				
				methodBox.putPointRightArr(4,1,2,0,3,3,4);
				methodBox.putPointTopArr(2,3,1,4,5);				
				methodBox.putPointBottomArr(1,2,3,4,1);
				Square squareFront=methodBox.getSquareFront();
				squareFront.putPoint(4);
				Square squareBack=methodBox.getSquareBack();
				squareBack.putPoint(3);
			}
			group.addChild(methodBox.getSquareNode());
		}
		{
			List<String> frontParaList=new ArrayList<String>();
			frontParaList.add("axis [com.maqiao.was.d3.MQConst.EnumAxis]");
			frontParaList.add("num [int]");
			frontParaList.add("[2]:str [String]");
			frontParaList.add("[3]:newList [java.util.List<String>]");
			frontParaList.add("[4]:angleX [double[]]");
			frontParaList.add("[3]:newList [java.util.List<String>]");
			frontParaList.add("[4]:angleX [double[]]");
			frontParaList.add("[3]:newList [java.util.List<String>]");
			frontParaList.add("[4]:angleX [double[]]");
			frontParaList.add("[3]:newList [java.util.List<String>]");
			frontParaList.add("[4]:angleX [double[]]");
			List<String> statementList= new ArrayList<String>();
			statementList.add("int i;");
			statementList.add("i++;");
			statementList.add("String str=\"ABC\"");
			double[] location= {1,1,1};
			Method method=new Method();
			method.setLocation(location);
			method.setMethodName("Method:method....");
			method.setFrontParaList(frontParaList);
			method.setStatementList(statementList);
			String[] annotationArr = {"@Deprecated","@SuppressWarnings","@Override"};
			method.setAnnotationArr(annotationArr);
			group.addChild(method.getNode());
			
		}
		for(int i=0;i<6;i++){
			List<String> frontParaList=new ArrayList<String>();
			frontParaList.add("Name: axis"+i);
			frontParaList.add("TYPE: [com.maqiao.was.d3.MQConst.EnumAxis]");
			double[] location= {-9.5,1,0.5+1.2*i};
			Parameter parameter=new Parameter(null,frontParaList,location);			
			group.addChild(parameter.getFormGroup());
		}
		branchGroup.addChild(group);
		PointLight p = new PointLight();
		branchGroup.addChild(p);
		return branchGroup;

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static GraphicsConfiguration configuration = SimpleUniverse.getPreferredConfiguration();
	static Canvas3D canvas3D = new Canvas3D(configuration);
	static void getLayoutManager() {
		SimpleUniverse universe = new SimpleUniverse(canvas3D);
		Point3d userPosition = new Point3d(0,0,20);
		ViewingPlatform vp = universe.getViewingPlatform();
	    TransformGroup steerTG = vp.getViewPlatformTransform();
	    Transform3D t3d = new Transform3D();
	    steerTG.getTransform(t3d);
	    t3d.lookAt(userPosition, new Point3d(0,0,0), new Vector3d(0,1,0));
	    t3d.invert();
	    steerTG.setTransform(t3d);
		universe.addBranchGraph(getBranchGroup());
	}
	
	public static Applet getApplet() {
		Applet app=new Applet();
		app.setLayout(new BorderLayout());
		getLayoutManager();
		app.add(canvas3D);
		return app;
	}

	public static void main(String[] argsSes) {
		//new MainFrame(getApplet(), 700, 700);
		new MainFrame(getApplet1(), 700, 700);
	}
	public static JApplet getApplet1() {
		JApplet app=new JApplet();
		app.setLayout(new BorderLayout());
		getLayoutManager();
		app.add(canvas3D);
		 JPanel panel = new JPanel();
	      panel.add(new JButton("I'm a swing button"));
	      panel.add(app);
		return app;
	}
}
