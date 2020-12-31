package com.shennarwp.app.entity;

/**
 * This class represent the Color object / constants
 */
public class Color
{
	public static final String BLAU = "blau";
	public static final String GRUEN = "grün";
	public static final String VIOLETT = "violett";
	public static final String ROT = "rot";
	public static final String GELB = "gelb";
	public static final String TUERKIS = "türkis";
	public static final String WEISS = "weiß";

	/* return color as string based on color code */
	public static String pickColor(Integer colorCode) {
		return switch (colorCode) {
			case 1 -> Color.BLAU;
			case 2 -> Color.GRUEN;
			case 3 -> Color.VIOLETT;
			case 4 -> Color.ROT;
			case 5 -> Color.GELB;
			case 6 -> Color.TUERKIS;
			case 7 -> Color.WEISS;
			default -> throw new IllegalStateException("Invalid color choice: " + colorCode);
		};
	}
}
