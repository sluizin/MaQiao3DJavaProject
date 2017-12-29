package com.maqiao.was.d3;

import com.maqiao.was.d3.MQConst.EnumAxisExt;

/**
 * 工具类 - 通过 不同的轴进行角度转向
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQUtilsMatrix3d {
	/**
	 * 通过 不同的轴进行角度转向
	 * @param axisWidth EnumAxisExt
	 * @param axisHeight EnumAxisExt
	 * @return double[]
	 */
	public static final double[] getEnumAxis3dDouble(EnumAxisExt axisWidth, EnumAxisExt axisHeight) {
		double[] arrNull = { 0, 0, 0 };
		if (axisWidth == axisHeight || axisWidth == null || axisHeight == null || axisWidth.getAxis() == axisHeight.getAxis()) return arrNull;
		double x = 0, y = 0, z = 0;
		switch (axisWidth) {
		case X0:
			switch (axisHeight) {
			case Y0:
				break;
			case Y1:
				x = 180;
				break;
			case Z0:
				x = 90;
				break;
			default:
				x = -90;
			}
			break;
		case X1:
			switch (axisHeight) {
			case Y0:
				y = 180;
				break;
			case Y1:
				z = 180;
				break;
			case Z0:
				x = -90;
				y = 180;
				break;
			default:
				x = 90;
				y = 180;
			}
			break;
		case Y0:
			switch (axisHeight) {
			case X0:
				x = 180;
				z = 90;
				break;
			case X1:
				z = 90;
				break;
			case Z0:
				x = 90;
				z = 90;
				break;
			default:
				x = -90;
				z = 90;
			}
			break;
		case Y1:
			switch (axisHeight) {
			case X0:
				z = -90;
				break;
			case X1:
				x = 180;
				z = -90;
				break;
			case Z0:
				x = 90;
				z = -90;
				break;
			default:
				x = -90;
				z = -90;
			}
			break;
		case Z0:
			switch (axisHeight) {
			case X0:
				x = -90;
				y = -90;
				break;
			case X1:
				x = 90;
				y = -90;
				break;
			case Y0:
				y = -90;
				break;
			default:
				x = 180;
				y = -90;
			}
			break;
		default:/* Z1 */
			switch (axisHeight) {
			case X0:
				y = 90;
				z = -90;
				break;
			case X1:
				y = 90;
				z = 90;
				break;
			case Y0:
				y = 90;
				break;
			default:
				y = 90;
				z = 180;
			}
		}
		double[] arr = { x, y, z };
		return arr;
	}
}
