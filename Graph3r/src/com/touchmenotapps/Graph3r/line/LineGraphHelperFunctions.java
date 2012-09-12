/*
 * Copyright (C) 2012 Touch Me Not Apps
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.touchmenotapps.Graph3r.line;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @version Graph3r Beta 1
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