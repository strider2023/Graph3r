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

package com.touchmenotapps.Graph3r.pie;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @version Graph3r Beta 1
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The PieGraphRenderer class is used to render the pie graph view on screen
 * and customise its appearance.
 */
public class PieGraphRenderer {
	
	private final int ID_LEGENDS_HOLDER = 2341;

	private final int DEFAULT_PIE_SIZE = 200;

	private Context mContext;

	private ArrayList<PieGraphObject> graphData = new ArrayList<PieGraphObject>(0);

	private int mPiePadding = 10;

	private float mScaleFactor = 1.0f;

	private int mPieChartHeight = DEFAULT_PIE_SIZE;

	private int mPieChartWidth = DEFAULT_PIE_SIZE;

	private int mPieDividerLineColor = Color.BLACK;

	private float mPieDividerLineThickness = 0.5f;

	private int mBackgroundColor = Color.TRANSPARENT;

	private float MAX_ZOOM = 1.5f;
	
	private float MIN_ZOOM = 1;

	private boolean graphIsClickable = false;

	private boolean graphIsZoomable = false;

	private boolean graphIsPannable = false;

	/**
	 * 
	 * @param parentView
	 * @param data
	 */
	public PieGraphRenderer(View parentView, ArrayList<PieGraphObject> data) {
		mContext = parentView.getContext();
		graphData.addAll(data);
		mPieChartHeight = parentView.getHeight();
		mPieChartWidth = parentView.getWidth();
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setGraphWidthAndHeight(int width, int height) {
		mPieChartHeight = height;
		mPieChartWidth = width;
	}

	public View renderGraph() throws Exception  {
		/** Create the view holder **/
		RelativeLayout graphLayout = new RelativeLayout(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		graphLayout.setLayoutParams(params);
		/** Create the legends holder **/
		HorizontalScrollView legendsLayout = new HorizontalScrollView(mContext);
		legendsLayout.setHorizontalScrollBarEnabled(false);
		RelativeLayout.LayoutParams legendsHolderParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		legendsHolderParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		legendsLayout.setLayoutParams(legendsHolderParams);
		legendsLayout.setId(ID_LEGENDS_HOLDER);
		/** Add legends content **/
		if(graphData.size() > 0) {
			LinearLayout legendContent = new LinearLayout(mContext);
			legendContent.setOrientation(LinearLayout.HORIZONTAL);
			for(int i = 0; i < graphData.size(); i++) {
				TextView legendText = new TextView(mContext);
				LayoutParams contentParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				contentParams.setMargins(5, 5, 5, 5);
				legendText.setText(graphData.get(i).getLabel() + " " + graphData.get(i).getValue());
				legendText.setBackgroundColor(graphData.get(i).getColor());
				legendText.setTextColor(Color.WHITE);
				legendText.setLayoutParams(contentParams);
				legendText.setPadding(5, 5, 5, 5);
				legendContent.addView(legendText);
			}
			legendsLayout.addView(legendContent);
		}
		/** Add legends to the view holder **/
		graphLayout.addView(legendsLayout);
		/** Add graph to the view holder **/
		RelativeLayout.LayoutParams graphHolderParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		graphHolderParams.addRule(RelativeLayout.ABOVE, ID_LEGENDS_HOLDER);
		View graphView = new PieGraphView().getGraphView(mContext, this);
		graphView.setLayoutParams(graphHolderParams);
		graphLayout.addView(graphView);
		return graphLayout;
	}

	public int getTotalValue(ArrayList<PieGraphObject> data) {
		int total = 0;
		for (int counter = 0; counter < data.size(); counter++) {
			total += data.get(counter).getValue();
		}
		return total;
	}

	/**
	 * @return the graphData
	 */
	public ArrayList<PieGraphObject> getGraphData() {
		return graphData;
	}

	/**
	 * @return the mBackgroundColor
	 */
	public int getBackgroundColor() {
		return mBackgroundColor;
	}

	/**
	 * @param mBackgroundColor
	 *            the mBackgroundColor to set
	 */
	public void setBackgroundColor(int mBackgroundColor) {
		this.mBackgroundColor = mBackgroundColor;
	}

	/**
	 * @return the mPieChartHeight
	 */
	public int getPieChartHeight() {
		return mPieChartHeight;
	}

	/**
	 * @return the mPieChartWidth
	 */
	public int getPieChartWidth() {
		return mPieChartWidth;
	}

	/**
	 * @return the mPiePadding
	 */
	public int getPiePadding() {
		return mPiePadding;
	}

	/**
	 * @param mPiePadding
	 *            the mPiePadding to set
	 */
	public void setPiePadding(int mPiePadding) {
		this.mPiePadding = mPiePadding;
	}

	/**
	 * @return the mPieDividerLineColor
	 */
	public int getPieDividerLineColor() {
		return mPieDividerLineColor;
	}

	/**
	 * @param mPieDividerLineColor
	 *            the mPieDividerLineColor to set
	 */
	public void setPieDividerLineColor(int mPieDividerLineColor) {
		this.mPieDividerLineColor = mPieDividerLineColor;
	}

	/**
	 * @return the mPieDividerLineThickness
	 */
	public float getPieDividerLineThickness() {
		return mPieDividerLineThickness;
	}

	/**
	 * @param mPieDividerLineThickness
	 *            the mPieDividerLineThickness to set
	 */
	public void setPieDividerLineThickness(float mPieDividerLineThickness) {
		this.mPieDividerLineThickness = mPieDividerLineThickness;
	}
	
	/**
	 * @return the mScaleFactor
	 */
	public float getScaleFactor() {
		return mScaleFactor;
	}

	/**
	 * @param mScaleFactor the mScaleFactor to set
	 */
	public void setScaleFactor(float mScaleFactor) {
		this.mScaleFactor = mScaleFactor;
	}

	/**
	 * @return the mAX_ZOOM
	 */
	public float getMaxZoom() {
		return MAX_ZOOM;
	}

	/**
	 * @param mAX_ZOOM the mAX_ZOOM to set
	 */
	public void setMaxZoom(float mAX_ZOOM) {
		MAX_ZOOM = mAX_ZOOM;
	}

	/**
	 * @return the mIN_ZOOM
	 */
	public float getMinZoom() {
		return MIN_ZOOM;
	}

	/**
	 * @param mIN_ZOOM the mIN_ZOOM to set
	 */
	public void setMinZoom(float mIN_ZOOM) {
		MIN_ZOOM = mIN_ZOOM;
	}

	/**
	 * @return the graphIsClickable
	 */
	public boolean isGraphClickable() {
		return graphIsClickable;
	}

	/**
	 * @param graphIsClickable the graphIsClickable to set
	 */
	public void setGraphIsClickable(boolean graphIsClickable) {
		this.graphIsClickable = graphIsClickable;
	}

	/**
	 * @return the graphIsZoomable
	 */
	public boolean isGraphZoomable() {
		return graphIsZoomable;
	}

	/**
	 * @param graphIsZoomable the graphIsZoomable to set
	 */
	public void setGraphIsZoomable(boolean graphIsZoomable) {
		this.graphIsZoomable = graphIsZoomable;
	}

	/**
	 * @return the graphIsPannable
	 */
	public boolean isGraphPannable() {
		return graphIsPannable;
	}

	/**
	 * @param graphIsPannable the graphIsPannable to set
	 */
	public void setGraphIsPannable(boolean graphIsPannable) {
		this.graphIsPannable = graphIsPannable;
	}
}
