package com.touchmenotapps.Graph3r.line;

/**
 * 
 * @author Arindam Nath
 *
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