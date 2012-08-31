package com.touchmenotapps.Graph3r.line;

/**
 * @version Graph3r Alpha 3
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The LineGraphObject defines a line graph plot. 
 */
public class LineGraphObject {

	private int[] mPlotPoints;
	private String mPlotDescription;
	private int mPlotColor;
	
	/**
	 * @param mPlotPoints
	 * @param mPlotDescription
	 * @param mPlotColor
	 */
	public LineGraphObject(int[] mPlotPoints, String mPlotDescription,
			int mPlotColor) {
		this.mPlotPoints = mPlotPoints;
		this.mPlotDescription = mPlotDescription;
		this.mPlotColor = mPlotColor;
	}

	/**
	 * @return the mPlotPoints
	 */
	public int[] getPlotPoints() {
		return mPlotPoints;
	}

	/**
	 * @return the mPlotDescription
	 */
	public String getPlotDescription() {
		return mPlotDescription;
	}

	/**
	 * @return the mPlotColor
	 */
	public int getPlotColor() {
		return mPlotColor;
	}
}
