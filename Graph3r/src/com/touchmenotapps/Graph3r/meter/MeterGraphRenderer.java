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

package com.touchmenotapps.Graph3r.meter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

/**
 * @version Graph3r Beta 1
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The MeterGraphRenderer class is used to render the meter graph view on screen
 * and customise its appearance.
 */
public class MeterGraphRenderer {

	private Context mContext;
	
	private double mCurrentMeterReading = 0;
	
	private int mGraphOrigin = 60; 
	
	private int mGraphSize = 320; 
	
	private double mGraphMinMedMax[] = new double[4]; // Default Values
	
	private int mGraphDialBorderGradient[] = new int[]{0xFF13C9F5 , 0xFF0464BB};
	
	private int mGraphDialBorderGradientThickness = 10;
	
	private int mGraphDialBackgroundColor = Color.WHITE;
	
	private int mGraphMinMedMaxColor[] = new int[] {Color.GREEN, Color.YELLOW, Color.RED};
	
	private int mGraphDialTextColor = Color.WHITE;
	
	private int mGraphDialTextSize = 10;
	
	private int mGraphDialTickerColor = Color.BLACK;
	
	private int mGraphDialCenterTextColor = Color.BLACK;
	
	private int mGraphDialTickerThickness = 1;
	
	/**
	 * 
	 * @param parentView
	 * @param graphMinMedMax
	 * @param currentReading
	 * @param graphOrigin
	 */
	public MeterGraphRenderer(View parentView, double[] graphMinMedMax, double currentReading, int graphOrigin) {
		this.mContext = parentView.getContext();
		this.mGraphMinMedMax = graphMinMedMax;
		this.mCurrentMeterReading = currentReading;
		this.mGraphOrigin = graphOrigin;
		/** Set the smallest as the graph size so that it fills the whole screen **/
		if(parentView.getWidth() < parentView.getHeight())
			this.mGraphSize = parentView.getWidth() - 20;
		else if(parentView.getWidth() > parentView.getHeight())
			this.mGraphSize = parentView.getHeight() - 20;
		else
			this.mGraphSize = parentView.getWidth() - 20;
	}
		
	/**
	 * 
	 * @param size
	 */
	public void setGraphSize(int size) {
		this.mGraphSize = size - 20;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public View renderGraph() throws Exception {
		return new MeterGraphView().getGraphView(mContext, this);
	}

	/**
	 * @return the mGraphDialBorderGradient
	 */
	public int[] getGraphDialBorderGradient() {
		return mGraphDialBorderGradient;
	}

	/**
	 * @param mGraphDialBorderGradient the mGraphDialBorderGradient to set
	 */
	public void setGraphDialBorderGradient(int[] mGraphDialBorderGradient) {
		this.mGraphDialBorderGradient = mGraphDialBorderGradient;
	}

	/**
	 * @return the mGraphDialBorderGradientThickness
	 */
	public int getGraphDialBorderGradientThickness() {
		return mGraphDialBorderGradientThickness;
	}

	/**
	 * @param mGraphDialBorderGradientThickness the mGraphDialBorderGradientThickness to set
	 */
	public void setGraphDialBorderGradientThickness(
			int mGraphDialBorderGradientThickness) {
		this.mGraphDialBorderGradientThickness = mGraphDialBorderGradientThickness;
	}

	/**
	 * @return the mGraphDialBackgroundColor
	 */
	public int getGraphDialBackgroundColor() {
		return mGraphDialBackgroundColor;
	}

	/**
	 * @param mGraphDialBackgroundColor the mGraphDialBackgroundColor to set
	 */
	public void setGraphDialBackgroundColor(int mGraphDialBackgroundColor) {
		this.mGraphDialBackgroundColor = mGraphDialBackgroundColor;
	}

	/**
	 * @return the mGraphMinMedMaxColor
	 */
	public int[] getGraphMinMedMaxColor() {
		return mGraphMinMedMaxColor;
	}

	/**
	 * @param mGraphMinMedMaxColor the mGraphMinMedMaxColor to set
	 */
	public void setGraphMinMedMaxColor(int[] mGraphMinMedMaxColor) {
		this.mGraphMinMedMaxColor = mGraphMinMedMaxColor;
	}

	/**
	 * @return the mGraphDialTextColor
	 */
	public int getGraphDialTextColor() {
		return mGraphDialTextColor;
	}

	/**
	 * @param mGraphDialTextColor the mGraphDialTextColor to set
	 */
	public void setGraphDialTextColor(int mGraphDialTextColor) {
		this.mGraphDialTextColor = mGraphDialTextColor;
	}

	/**
	 * @return the mGraphDialTextSize
	 */
	public int getGraphDialTextSize() {
		return mGraphDialTextSize;
	}

	/**
	 * @param mGraphDialTextSize the mGraphDialTextSize to set
	 */
	public void setGraphDialTextSize(int mGraphDialTextSize) {
		this.mGraphDialTextSize = mGraphDialTextSize;
	}

	/**
	 * @return the mGraphDialTickerColor
	 */
	public int getGraphDialTickerColor() {
		return mGraphDialTickerColor;
	}

	/**
	 * @param mGraphDialTickerColor the mGraphDialTickerColor to set
	 */
	public void setGraphDialTickerColor(int mGraphDialTickerColor) {
		this.mGraphDialTickerColor = mGraphDialTickerColor;
	}

	/**
	 * @return the mCurrentMeterReading
	 */
	public double getCurrentMeterReading() {
		return mCurrentMeterReading;
	}

	/**
	 * @return the mGraphOrigin
	 */
	public int getGraphOrigin() {
		return mGraphOrigin;
	}

	/**
	 * @return the mGraphWidth
	 */
	public int getGraphSize() {
		return mGraphSize;
	}

	/**
	 * @return the mGraphMinMedMax
	 */
	public double[] getGraphMinMedMax() {
		return mGraphMinMedMax;
	}

	/**
	 * @return the mGraphDialCenterTextColor
	 */
	public int getGraphDialCenterTextColor() {
		return mGraphDialCenterTextColor;
	}

	/**
	 * @param mGraphDialCenterTextColor the mGraphDialCenterTextColor to set
	 */
	public void setGraphDialCenterTextColor(int mGraphDialCenterTextColor) {
		this.mGraphDialCenterTextColor = mGraphDialCenterTextColor;
	}

	/**
	 * @return the mGraphDialTickerThickness
	 */
	public int getGraphDialTickerThickness() {
		return mGraphDialTickerThickness;
	}

	/**
	 * @param mGraphDialTickerThickness the mGraphDialTickerThickness to set
	 */
	public void setGraphDialTickerThickness(int mGraphDialTickerThickness) {
		this.mGraphDialTickerThickness = mGraphDialTickerThickness;
	}
}
