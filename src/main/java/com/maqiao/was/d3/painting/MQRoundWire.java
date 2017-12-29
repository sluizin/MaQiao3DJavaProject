package com.maqiao.was.d3.painting;

import java.awt.Color;

import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;

import com.maqiao.was.d3.unit.Point;

/**
 * 环线
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQRoundWire {
	/**
	 * 环线。直线
	 * @param colorLine Color
	 * @param isCircle boolean
	 * @param points Point[]
	 * @return Node
	 */
	public static Node getRoundLine(Color colorLine, boolean isCircle, Point... points) {
		if (points.length < 3) return null;
		int sort = 0;
		for (int i = 0; i < points.length; i++)
			if (points[i] != null) sort++;
		if (sort < 3) return null;
		TransformGroup group = new TransformGroup();
		group.addChild(Point.getNode(points));
		Point start = points[0];
		for (int i = 1; i < points.length; i++) {
			group.addChild(MQLine.getLine(start, points[i], colorLine));
			start = points[i];
		}
		if (isCircle) group.addChild(MQLine.getLine(points[0], start, colorLine));
		return group;
	}
}
