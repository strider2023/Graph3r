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

/**
 * @version Graph3r Beta 1
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
