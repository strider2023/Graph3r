package com.touchmenotapps.Graph3r.pie;

/**
 * @version Graph3r Alpha 3
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The LineGraphObject defines a single pie graph plot. 
 */
public class PieGraphObject {
	
	private int mValue;
	private String mLabel;
	private int mColor;
	
	/**
	 * 
	 * @param mValue
	 * @param mLabel
	 * @param color
	 */
	public PieGraphObject(int mValue, String mLabel, int mColor) {
		this.mValue = mValue;
		this.mLabel = mLabel;
		this.mColor = mColor;
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
