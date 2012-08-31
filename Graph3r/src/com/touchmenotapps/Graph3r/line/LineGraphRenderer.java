package com.touchmenotapps.Graph3r.line;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @version Graph3r Alpha 3
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The LineGraphRenderer class is used to render the line graph view on screen
 * and customize its appearance.
 * TODO Add option for legends text size.
 */
public class LineGraphRenderer {
	
	private final int ID_LEGENDS_HOLDER = 72638;
	
	public static final int LINE_GRAPH_STYLE_NORMAL = 0;
	
	public static final int LINE_GRAPH_STYLE_FILL = 1;
	
	private final int DEFAULT_SIZE = 240;
	
	private float mScaleFactor = 1.0f;

	private int mWidth = DEFAULT_SIZE;
	
	private int mHeight = DEFAULT_SIZE;
	
	private boolean  mFillGraph = false;
	
	private boolean  mShowAverageLine = false;
	
	private boolean mGraphIsZoomable = false;
	
	private boolean mGraphIsPannable = false;
	
	private int mGraphPadding = 5;
		
	private int mGridColor = Color.WHITE;
	
	private int mAxesColor = Color.WHITE;
	
	private int mOriginX = 50;
	
	private int mOriginY = 50;
	
	private int mMaxXAxesLabels = 6;
	
	private int mMaxYAxesLables = 6;
	
	private int mAverageLineColor = Color.RED;
	
	private int mXAxesTextSize = 8;
	
	private int mXAxesTextColor = Color.WHITE;
	
	private int mYAxesTextSize = 8;
	
	private String mYAxesText = "";
	
	private int mYAxesTextColor = Color.WHITE;
	
	private int mTitleTextColor = Color.WHITE;
	
	private int mTitleTextSize = 8;
	
	private String mGraphTitle = "";
	
	private boolean mShowLegends = false;
	
	private boolean mGridIsVisible = false;
	
	private float mGraphLineThickness = 1.5f;
	
	private ArrayList<String> mGraphXAxesLabels =  new ArrayList<String>(0);
	
	private ArrayList<LineGraphObject> mGraphPlotDeatils = new ArrayList<LineGraphObject>(0);
	
	private Context mContext;
	
	private float MAX_ZOOM = 1.5f;
	
	private float MIN_ZOOM = 1;
	
	private boolean runningOnTablet = false;

	/**
	 * @param mGraphPlotDeatils
	 */
	public LineGraphRenderer(View parentView, ArrayList<LineGraphObject> mGraphPlotDeatils) {
		mContext = parentView.getContext();
		this.mGraphPlotDeatils = mGraphPlotDeatils;
		this.mWidth = parentView.getWidth();
		this.mHeight = parentView.getHeight();
		this.mOriginX = parentView.getWidth()/9;
		this.mOriginY = parentView.getHeight()/6;
		//Set the max number of x-axes labels
		this.mMaxXAxesLabels = mGraphPlotDeatils.get(0).getPlotPoints().length;
	}	

	public View renderGraph() throws Exception {
		if(mShowLegends) {
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
			if(mGraphPlotDeatils.size() > 0) {
				LinearLayout legendContent = new LinearLayout(mContext);
				legendContent.setOrientation(LinearLayout.HORIZONTAL);
				for(int i = 0; i < mGraphPlotDeatils.size(); i++) {
					TextView legendText = new TextView(mContext);
					LayoutParams contentParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					contentParams.setMargins(5, 5, 5, 5);
					legendText.setText(mGraphPlotDeatils.get(i).getPlotDescription());
					legendText.setBackgroundColor(mGraphPlotDeatils.get(i).getPlotColor());
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
			/** Reset the graph height based on user legends layout height **/
			setGraphWidthAndHeight(getWidth(), getHeight() - legendsLayout.getHeight());
			View graphView = new LineGraphView().getGraphView(mContext, this); 
			graphView.setLayoutParams(graphHolderParams);
			graphLayout.addView(graphView);
			return graphLayout;
		} else
			return new LineGraphView().getGraphView(mContext, this);
	}
	
 	public void setGraphWidthAndHeight(int width, int height) {
		this.mWidth = width;
		this.mHeight = height;
		this.mOriginX = mWidth/9;
		this.mOriginY = mWidth/6;
	}
	
	public void setGraphOrigin(int posX, int posY) {
		this.mOriginX = posX;
		this.mOriginY = posY;
	}

	/**
	 * @param mStyle the mStyle to set
	 */
	public void setStyle(int mStyle) {
		switch(mStyle) {
			case LINE_GRAPH_STYLE_NORMAL:
				mFillGraph = false;
				break;
			case LINE_GRAPH_STYLE_FILL:
				mFillGraph = true;
				break;
		}
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
	 * @return the mShowAverageLine
	 */
	public boolean isShowAverageLine() {
		return mShowAverageLine;
	}

	/**
	 * @param mShowAverageLine the mShowAverageLine to set
	 */
	public void setShowAverageLine(boolean mShowAverageLine) {
		this.mShowAverageLine = mShowAverageLine;
	}

	/**
	 * @return the mGraphIsZoomable
	 */
	public boolean isGraphZoomable() {
		return mGraphIsZoomable;
	}

	/**
	 * @param mGraphIsZoomable the mGraphIsZoomable to set
	 */
	public void setGraphIsZoomable(boolean mGraphIsZoomable) {
		this.mGraphIsZoomable = mGraphIsZoomable;
	}

	/**
	 * @return the mGraphIsPannable
	 */
	public boolean isGraphPannable() {
		return mGraphIsPannable;
	}

	/**
	 * @param mGraphIsPannable the mGraphIsPannable to set
	 */
	public void setGraphIsPannable(boolean mGraphIsPannable) {
		this.mGraphIsPannable = mGraphIsPannable;
	}

	/**
	 * @return the mGraphPadding
	 */
	public int getGraphPadding() {
		return mGraphPadding;
	}

	/**
	 * @param mGraphPadding the mGraphPadding to set
	 */
	public void setGraphPadding(int mGraphPadding) {
		this.mGraphPadding = mGraphPadding;
	}

	/**
	 * @return the mGridColor
	 */
	public int getGridColor() {
		return mGridColor;
	}

	/**
	 * @param mGridColor the mGridColor to set
	 */
	public void setGridColor(int mGridColor) {
		this.mGridColor = mGridColor;
	}

	/**
	 * @return the mAxesColor
	 */
	public int getAxesColor() {
		return mAxesColor;
	}

	/**
	 * @param mAxesColor the mAxesColor to set
	 */
	public void setAxesColor(int mAxesColor) {
		this.mAxesColor = mAxesColor;
	}

	/**
	 * @return the mAverageLineColor
	 */
	public int getAverageLineColor() {
		return mAverageLineColor;
	}

	/**
	 * @param mAverageLineColor the mAverageLineColor to set
	 */
	public void setAverageLineColor(int mAverageLineColor) {
		this.mAverageLineColor = mAverageLineColor;
	}

	/**
	 * @return the mXAxesTextSize
	 */
	public int getXAxesTextSize() {
		return mXAxesTextSize;
	}

	/**
	 * @param mXAxesTextSize the mXAxesTextSize to set
	 */
	public void setXAxesTextSize(int mXAxesTextSize) {
		this.mXAxesTextSize = mXAxesTextSize;
	}

	/**
	 * @return the mXAxesTextColor
	 */
	public int getXAxesTextColor() {
		return mXAxesTextColor;
	}

	/**
	 * @param mXAxesTextColor the mXAxesTextColor to set
	 */
	public void setXAxesTextColor(int mXAxesTextColor) {
		this.mXAxesTextColor = mXAxesTextColor;
	}

	/**
	 * @return the mYAxesTextSize
	 */
	public int getYAxesTextSize() {
		return mYAxesTextSize;
	}

	/**
	 * @param mYAxesTextSize the mYAxesTextSize to set
	 */
	public void setYAxesTextSize(int mYAxesTextSize) {
		this.mYAxesTextSize = mYAxesTextSize;
	}

	/**
	 * @return the mYAxesTestColor
	 */
	public int getYAxesTextColor() {
		return mYAxesTextColor;
	}

	/**
	 * @param mYAxesTestColor the mYAxesTestColor to set
	 */
	public void setYAxesTextColor(int mYAxesTestColor) {
		this.mYAxesTextColor = mYAxesTestColor;
	}

	/**
	 * @return the mTitleTextColor
	 */
	public int getTitleTextColor() {
		return mTitleTextColor;
	}

	/**
	 * @param mTitleTextColor the mTitleTextColor to set
	 */
	public void setTitleTextColor(int mTitleTextColor) {
		this.mTitleTextColor = mTitleTextColor;
	}

	/**
	 * @return the mGraphTitle
	 */
	public String getGraphTitle() {
		return mGraphTitle;
	}

	/**
	 * @param mGraphTitle the mGraphTitle to set
	 */
	public void setGraphTitle(String mGraphTitle) {
		this.mGraphTitle = mGraphTitle;
	}

	/**
	 * @return the mShowLegends
	 */
	public boolean isShowLegends() {
		return mShowLegends;
	}

	/**
	 * @param mShowLegends the mShowLegends to set
	 */
	public void setShowLegends(boolean mShowLegends) {
		this.mShowLegends = mShowLegends;
	}

	/**
	 * @return the mGraphXAxesLabels
	 */
	public ArrayList<String> getGraphXAxesLabels() {
		return mGraphXAxesLabels;
	}

	/**
	 * @param mGraphXAxesLabels the mGraphXAxesLabels to set
	 */
	public void setGraphXAxesLabels(ArrayList<String> mGraphXAxesLabels) {
		this.mGraphXAxesLabels = mGraphXAxesLabels;
		this.mMaxXAxesLabels = mGraphXAxesLabels.size();
	}

	/**
	 * @return the mWidth
	 */
	public int getWidth() {
		return mWidth;
	}

	/**
	 * @return the mHeight
	 */
	public int getHeight() {
		return mHeight;
	}

	/**
	 * @return the mFillGraph
	 */
	public boolean isFillGraph() {
		return mFillGraph;
	}

	/**
	 * @return the mOriginX
	 */
	public int getOriginX() {
		return mOriginX;
	}

	/**
	 * @return the mOriginY
	 */
	public int getOriginY() {
		return mOriginY;
	}

	/**
	 * @return the mGraphPlotDeatils
	 */
	public ArrayList<LineGraphObject> getGraphPlotDeatils() {
		return mGraphPlotDeatils;
	}

	/**
	 * @return the mMaxXAxesLabels
	 */
	public int getMaxXAxesLabels() {
		return mMaxXAxesLabels;
	}

	/**
	 * @param mMaxXAxesLabels the mMaxXAxesLabels to set
	 */
	public void setMaxXAxesLabels(int mMaxXAxesLabels) {
		this.mMaxXAxesLabels = mMaxXAxesLabels;
	}

	/**
	 * @return the mMaxYAxesLables
	 */
	public int getMaxYAxesLables() {
		return mMaxYAxesLables;
	}

	/**
	 * @param mMaxYAxesLables the mMaxYAxesLables to set
	 */
	public void setMaxYAxesLables(int mMaxYAxesLables) {
		this.mMaxYAxesLables = mMaxYAxesLables;
	}

	
	/**
	 * @return the mGraphLineThickness
	 */
	public float getGraphLineThickness() {
		return mGraphLineThickness;
	}

	/**
	 * @param mGraphLineThickness the mGraphLineThickness to set
	 */
	public void setGraphLineThickness(float mGraphLineThickness) {
		this.mGraphLineThickness = mGraphLineThickness;
	}

	/**
	 * @return the mGridIsVisible
	 */
	public boolean isGridVisible() {
		return mGridIsVisible;
	}

	/**
	 * @param mGridIsVisible the mGridIsVisible to set
	 */
	public void setGridIsVisible(boolean mGridIsVisible) {
		this.mGridIsVisible = mGridIsVisible;
	}

	
	/**
	 * @return the mTitleTextSize
	 */
	public int getTitleTextSize() {
		return mTitleTextSize;
	}

	
	/**
	 * @param mTitleTextSize the mTitleTextSize to set
	 */
	public void setTitleTextSize(int mTitleTextSize) {
		this.mTitleTextSize = mTitleTextSize;
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
	 * @return the runningOnTablet
	 */
	public boolean isRunningOnTablet() {
		return runningOnTablet;
	}

	/**
	 * @param runningOnTablet the runningOnTablet to set
	 */
	public void setRunningOnTablet(boolean runningOnTablet) {
		this.runningOnTablet = runningOnTablet;
		if(runningOnTablet) {
			mXAxesTextSize = 15;
			mYAxesTextSize = 15;
			mTitleTextSize = 15;
			mGraphLineThickness = 2.5f;
		}
	}
	
	/**
	 * @return the mYAxesText
	 */
	public String getYAxesText() {
		return mYAxesText;
	}

	/**
	 * @param mYAxesText the mYAxesText to set
	 */
	public void setYAxesText(String mYAxesText) {
		this.mYAxesText = mYAxesText;
	}
}
