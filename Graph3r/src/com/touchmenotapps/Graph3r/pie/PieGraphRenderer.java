package com.touchmenotapps.Graph3r.pie;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class PieGraphRenderer {

	private final int DEFAULT_PIE_SIZE = 200;

	private Context mContext;

	private ArrayList<PieChartObject> graphData = new ArrayList<PieChartObject>();

	private int mPiePadding = 0;

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

	public PieGraphRenderer(View parentView, ArrayList<PieChartObject> data) {
		mContext = parentView.getContext();
		graphData.addAll(data);
		mPieChartHeight = parentView.getHeight();
		mPieChartWidth = parentView.getWidth();
	}

	public View renderGraph() {
		/** Create the view holder **/
		LinearLayout graphLayout = new LinearLayout(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		graphLayout.setLayoutParams(params);
		graphLayout.setOrientation(LinearLayout.VERTICAL);
		/** Create the legends holder **/
		HorizontalScrollView legendsLayout = new HorizontalScrollView(mContext);
		legendsLayout.setHorizontalScrollBarEnabled(false);
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
		graphLayout.addView(new PieChartView(mContext, this));
		return graphLayout;
	}

	public int getTotalValue(ArrayList<PieChartObject> data) {
		int total = 0;
		for (int counter = 0; counter < data.size(); counter++) {
			total += data.get(counter).getValue();
		}
		return total;
	}

	/**
	 * @return the graphData
	 */
	public ArrayList<PieChartObject> getGraphData() {
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
