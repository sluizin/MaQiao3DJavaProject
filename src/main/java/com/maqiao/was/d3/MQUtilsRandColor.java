package com.maqiao.was.d3;

import java.awt.Color;
import java.util.Random;

/**
 * 用HSV模型来实现颜色的随机，然后转为RGB模型
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQUtilsRandColor {

	private static Random random = new Random();
	private float k = random.nextFloat() * 360;

	public static void main(String[] args) {
		MQUtilsRandColor d = new MQUtilsRandColor();
		for (int i = 0; i < 10; i++) {
			Pcolor c = d.randomColor();
			System.out.println(((int) c.r) + " " + ((int) c.g) + " " + ((int) c.b));
		}
	}

	public Color rndColor() {
		Pcolor c = randomColor();
		return new Color((int) c.r, (int) c.g, (int) c.b);
	}

	public Pcolor randomColor() {
		return HSVtoRGB(randomH(), 0.8F, 0.8F);
	}

	private float randomH() {
		k += (1 - 0.618f) * 360;
		if (k > 360) k -= 360;
		return k;
	}

	private Pcolor HSVtoRGB(float h, float s, float v) {
		float f, p, q, t;
		if (s == 0)  return makeColor(v, v, v); 
		h /= 60;
		int i = (int) Math.floor(h);
		f = h - i;
		p = v * (1 - s);
		q = v * (1 - s * f);
		t = v * (1 - s * (1 - f));
		switch (i) {
		case 0:
			return makeColor(v, t, p);
		case 1:
			return makeColor(q, v, p);
		case 2:
			return makeColor(p, v, t);
		case 3:
			return makeColor(p, q, v);
		case 4:
			return makeColor(t, p, v);
		default:
			return makeColor(v, p, q);
		}
	}

	private Pcolor makeColor(float r, float g, float b) {
		return new Pcolor(r, g, b);
	}

	class Pcolor {
		float r, g, b;
		public Pcolor(float r, float g, float b) {
			super();
			this.r = r * 256;
			this.g = g * 256;
			this.b = b * 256;
		}

		public Pcolor() {
			super();
		}

		public float getR() {
			return r;
		}

		public void setR(float r) {
			this.r = r;
		}

		public float getG() {
			return g;
		}

		public void setG(float g) {
			this.g = g;
		}

		public float getB() {
			return b;
		}

		public void setB(float b) {
			this.b = b;
		}

	}
}
