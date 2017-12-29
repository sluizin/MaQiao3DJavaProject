package com.maqiao.was.d3;

import java.awt.Color;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Box;

public final class MQConst {
	/** X轴标志 */
	public static final int ACC_Axis_X = 1;
	/** Y轴标志 */
	public static final int ACC_Axis_Y = 2;
	/** Z轴标志 */
	public static final int ACC_Axis_Z = 4;
	/** 黑色 */
	public static final Color ACC_Color_BLACK = Color.BLACK;
	/** 黑色 */
	public static final Color3f ACC_Color3f_BLACK = new Color3f(Color.BLACK);
	/** 蓝色 */
	public static final Color3f ACC_Color3f_BLUE = new Color3f(Color.BLUE);
	/** 端点开始 */
	public static final int ACC_Endpoint_A = 1;
	/** 端点结束 */
	public static final int ACC_Endpoint_B = 2;
	/** 端点之间 */
	public static final int ACC_Endpoint_AB = 4;
	/** 直线的宽度 */
	public static final float ACC_LineWidth = 2f;
	/** 端点球大小 */
	public static final float ACC_EndPointSize = 0.1f;
	/** 依次按照某轴自动折叠 */
	public static final EnumAxis[] ACC_AxisArr = { EnumAxis.X, EnumAxis.Y };

	/**
	 * 依次按照某轴自动折叠
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.8
	 */
	public static enum EnumAxis {
		/** X轴标志 */
		X(1, 0),
		/** Y轴标志 */
		Y(2, 1),
		/** Z轴标志 */
		Z(4, 2);
		private int value;
		/**
		 * 下标<br>
		 * X:0<br>
		 * Y:1<br>
		 * Z:2<br>
		 */
		private int suffix;

		private EnumAxis(int value, int suffix) {
			this.value = value;
			this.suffix = suffix;
		}

		/**
		 * 1,2,4
		 * @return int
		 */
		public int getValue() {
			return this.value;
		}

		public static EnumAxis getEnumAxis(int axisValue) {
			for (EnumAxis e : EnumAxis.values())
				if (e.value == axisValue) return e;
			return null;
		}

		public boolean isEnumAxis(int axis) {
			return MQUtils.contain(axis, value);
		}

		/**
		 * 得到下标<br>
		 * X:0<br>
		 * Y:1<br>
		 * Z:2<br>
		 * @return
		 */
		public int getSuffix() {
			return suffix;
		}

		/**
		 * 得到正方向
		 * @return EnumAxisExt
		 */
		public EnumAxisExt getForward() {
			switch (this) {
			case X:
				return EnumAxisExt.X0;
			case Y:
				return EnumAxisExt.Y0;
			default:
				return EnumAxisExt.Z0;
			}
		}
	}

	/**
	 * 方向，含有某轴的前后，0为正数，1为负数
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.8
	 */
	public static enum EnumAxisExt {
		/** X轴正方向 */
		X0(EnumAxis.X, false, Box.RIGHT),
		/** X轴负方向 */
		X1(EnumAxis.X, true, Box.LEFT),
		/** Y轴正方向 */
		Y0(EnumAxis.Y, false, Box.TOP),
		/** Y轴负方向 */
		Y1(EnumAxis.Y, true, Box.BOTTOM),
		/** Z轴正方向 */
		Z0(EnumAxis.Z, false, Box.FRONT),
		/** Z轴负方向 */
		Z1(EnumAxis.Z, true, Box.BACK);
		/** 某轴 */
		private EnumAxis axis = EnumAxis.X;
		/** 是否负数 */
		private boolean isNegative = false;
		/** Box方向 */
		private int boxdire = 0;

		private EnumAxisExt(EnumAxis axis, boolean isNegative, int boxdire) {
			this.axis = axis;
			this.isNegative = isNegative;
			this.boxdire = boxdire;
		}

		public EnumAxis getAxis() {
			return axis;
		}

		public void setAxis(EnumAxis axis) {
			this.axis = axis;
		}

		public boolean isNegative() {
			return isNegative;
		}

		public void setNegative(boolean isNegative) {
			this.isNegative = isNegative;
		}

		public int getBoxdire() {
			return boxdire;
		}

		public void setBoxdire(int boxdire) {
			this.boxdire = boxdire;
		}

	}
}
