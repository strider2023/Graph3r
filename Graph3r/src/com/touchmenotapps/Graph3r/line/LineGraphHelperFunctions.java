package com.touchmenotapps.Graph3r.line;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @version Graph3r Alpha 3
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The LineGraphHelperFunctions contains important helper functions that are used to draw
 * the line graph.
 */
public class LineGraphHelperFunctions {
	
	private LineGraphRenderer mRenderer;
	private ArrayList<LineGraphObject> mGraphPlotDeatils = new ArrayList<LineGraphObject>(0);
	
	private int mMaxPlotPoints = 0;
	private int MAX_VALUE = 0;
	private int MIN_VALUE = 0;
	private int AVERAGE = 0;
	
	public LineGraphHelperFunctions(LineGraphRenderer renderer) {
		mRenderer = renderer;
		mGraphPlotDeatils.addAll(mRenderer.getGraphPlotDeatils());
		ArrayList<Integer> totalPlotPoints = new ArrayList<Integer>(0);
		ArrayList<Integer> plotPoints = new ArrayList<Integer>(0);
		int plotValuesSum = 0;
		for (int i = 0; i < mGraphPlotDeatils.size(); i++) {
			totalPlotPoints.add(mGraphPlotDeatils.get(i).getPlotPoints().length);
			for (int j = 0; j < mGraphPlotDeatils.get(i).getPlotPoints().length; j++) {
				plotPoints.add(mGraphPlotDeatils.get(i).getPlotPoints()[j]);
				plotValuesSum += mGraphPlotDeatils.get(i).getPlotPoints()[j];
			}
		}
		mMaxPlotPoints = Collections.max(totalPlotPoints);
		MAX_VALUE = Collections.max(plotPoints);
		MIN_VALUE = Collections.min(plotPoints);
		AVERAGE = plotValuesSum/plotPoints.size();
	}

	public int getMaxPointsCount() {
		ArrayList<Integer> totalValues = new ArrayList<Integer>(0);
		for (int i = 0; i < mGraphPlotDeatils.size(); i++) {
			totalValues.add(mGraphPlotDeatils.get(i).getPlotPoints().length);
		}
		return Collections.max(totalValues);
	}

	/**
	 * @return the mMaxPlotPoints
	 */
	public int getMaxPlotPoints() {
		return mMaxPlotPoints;
	}

	/**
	 * @return the mAX_VALUE
	 */
	public int getMaxValue() {
		return MAX_VALUE;
	}

	/**
	 * @return the mIN_VALUE
	 */
	public int getMinValue() {
		return MIN_VALUE;
	}

	/**
	 * @return the aVERAGE
	 */
	public int getAverage() {
		return AVERAGE;
	}
}