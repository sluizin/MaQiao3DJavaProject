package com.maqiao.was.d3;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JApplet;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import org.eclipse.jdt.core.IType;

import com.maqiao.was.d3.element.MQClass;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
/**
 * 
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 *
 */
public class MQMaster {
	/**
	 * 
	 * @param iType IType
	 * @return Canvas3D
	 */
	private static final Canvas3D getCanvas3D(IType iType) {
		GraphicsConfiguration configuration = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(configuration);
		SimpleUniverse universe = new SimpleUniverse(canvas3D);
		Point3d userPosition = new Point3d(0, -10, 15);
		ViewingPlatform vp = universe.getViewingPlatform();
		TransformGroup steerTG = vp.getViewPlatformTransform();
		Transform3D t3d = new Transform3D();
		steerTG.getTransform(t3d);
		t3d.lookAt(userPosition, new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
		t3d.invert();
		steerTG.setTransform(t3d);
		MQClass mqClass=new MQClass(iType);
		universe.addBranchGraph(mqClass.getBranchGroup());
		return canvas3D;
	}
	/**
	 * 
	 * @param iType IType
	 * @return JApplet
	 */
	public static JApplet getJApplet(IType iType) {
		JApplet app = new JApplet();
		app.setLayout(new BorderLayout());
		Canvas3D canvas3D = getCanvas3D(iType);
		app.add(canvas3D);
		return app;
	}
}
