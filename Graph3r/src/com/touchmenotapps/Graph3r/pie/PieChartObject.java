package com.touchmenotapps.Graph3r.pie;

import java.util.Random;

public class PieChartObject {
	
	private int mValue;
	private String mLabel;
	private int mColor;
	private Random mColorGen  = new Random();
	
	/**
	 * @param mValue
	 * @param mLabel
	 */
	public PieChartObject(int mValue, String mLabel) {
		this.mValue = mValue;
		this.mLabel = mLabel;
		this.mColor = 0xff000000 + 256*256*mColorGen.nextInt(256) + 256*mColorGen.nextInt(256) + mColorGen.nextInt(256);
	}
	
	/**
	 * @return the mValue
	 */
	public int getValue() {
		return mValue;
	}
	
	/**
	 * @return the mLabel
	 */
	public String getLabel() {
		return mLabel;
	}
	
	/**
	 * @return the mColor
	 */
	public int getColor() {
		return mColor;
	}
}
