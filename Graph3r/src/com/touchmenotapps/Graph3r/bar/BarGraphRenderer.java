package com.touchmenotapps.Graph3r.bar;

import java.util.ArrayList;

import com.touchmenotapps.Graph3r.Graph;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

/**
 * 
 * @author Arindam Nath
 *
 */
public class BarGraphRenderer implements BarGraphInterface {
	
	private Context mContext;
	
	private float mScaleFactor = 1.0f;

	private ArrayList<Double> data = new ArrayList<Double>();

	private ArrayList<int[]> color = new ArrayList<int[]>();

	private ArrayList<String> stackGroupLabels = new ArrayList<String>();

	private int graphWidth = 240; // Default Width of graph

	private int graphHeight = 240; // Default graph height

	private int graphOriginX = 60; // Default Origin coordinates X and Y

	private int graphOriginY = 50;

	private boolean drawGridLines = true;
	
	private boolean drawYAxis = true;
	
	private boolean drawXAxis = true;
	
	private int xAxisColor = Color.BLACK;
	
	private int yAxisColor = Color.BLACK;

	private String yAxisLabel = "Y Axis"; // Default label of Y axis

	private int yAxisLabelSize = 8;

	private int xAxisLabelSize = 8;
	
	private int xAxisLabelRotation = 0;

	private ArrayList<String> barLabels = new ArrayList<String>();

	private int style = Graph.STYLE_BAR_NORMAL;

	private int graphGrouping = 0;
	
	private int xAxisLabelDistance = 10;
	
	private float MAX_ZOOM = 1.5f;
	
	private float MIN_ZOOM = 1;
	
	private boolean graphIsClickable = false;
	
	private boolean graphIsZoomable = false;
	
	private boolean graphIsPannable = false;
	
	private boolean runningOnTablet = false;
	
	/**
	 * 
	 * @param parentView
	 * @param graphData
	 * @param graphColors
	 * @param axesLabels
	 */
	public BarGraphRenderer(View parentView, ArrayList<Double> graphData, ArrayList<int[]> graphColors, ArrayList<String> axesLabels) {
		this.mContext = parentView.getContext();
		this.data = graphData;
		this.color = graphColors;
		this.barLabels = axesLabels;
		this.graphWidth = parentView.getWidth();
		this.graphHeight = parentView.getHeight();
		this.graphOriginX = parentView.getWidth()/10;
		this.graphOriginY = parentView.getHeight()/6;
	}
	
	/**
	 * 
	 * @return
	 */
	public View renderGraph() {
		return new BarGraphView(mContext, this);
	}
		
	/**
	 * @param mScaleFactor the mScaleFactor to set
	 */
	public void setScaleFactor(float mScaleFactor) {
		this.mScaleFactor = mScaleFactor;
	}

	/**
	 * @param stackGroupLabels the stackGroupLabels to set
	 */
	public void setStackGroupLabels(ArrayList<String> stackGroupLabels) {
		this.stackGroupLabels = stackGroupLabels;
	}

	/**
	 * @param drawGridLines the drawGridLines to set
	 */
	public void setDrawGridLines(boolean drawGridLines) {
		this.drawGridLines = drawGridLines;
	}

	/**
	 * @param drawYAxis the drawYAxis to set
	 */
	public void setDrawYAxis(boolean drawYAxis) {
		this.drawYAxis = drawYAxis;
	}

	/**
	 * @param drawXAxis the drawXAxis to set
	 */
	public void setDrawXAxis(boolean drawXAxis) {
		this.drawXAxis = drawXAxis;
	}

	/**
	 * @param xAxisColor the xAxisColor to set
	 */
	public void setXAxisColor(int xAxisColor) {
		this.xAxisColor = xAxisColor;
	}

	/**
	 * @param yAxisColor the yAxisColor to set
	 */
	public void setYAxisColor(int yAxisColor) {
		this.yAxisColor = yAxisColor;
	}

	/**
	 * @param yAxisLabel the yAxisLabel to set
	 */
	public void setYAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}

	/**
	 * @param yAxisLabelSize the yAxisLabelSize to set
	 */
	public void setYAxisLabelSize(int yAxisLabelSize) {
		this.yAxisLabelSize = yAxisLabelSize;
	}

	/**
	 * @param xAxisLabelSize the xAxisLabelSize to set
	 */
	public void setXAxisLabelSize(int xAxisLabelSize) {
		this.xAxisLabelSize = xAxisLabelSize;
	}

	/**
	 * @param xAxisLabelRotation the xAxisLabelRotation to set
	 */
	public void setXAxisLabelRotation(int xAxisLabelRotation) {
		this.xAxisLabelRotation = xAxisLabelRotation;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(int style) {
		this.style = style;
	}

	/**
	 * @param graphGrouping the graphGrouping to set
	 */
	public void setGraphGrouping(int graphGrouping) {
		this.graphGrouping = graphGrouping;
	}

	/**
	 * @param xAxisLabelDistance the xAxisLabelDistance to set
	 */
	public void setXAxisLabelDistance(int xAxisLabelDistance) {
		this.xAxisLabelDistance = xAxisLabelDistance;
	}

	/**
	 * @param mAX_ZOOM the mAX_ZOOM to set
	 */
	public void setMAX_ZOOM(int mAX_ZOOM) {
		MAX_ZOOM = mAX_ZOOM;
	}

	/**
	 * @param mIN_ZOOM the mIN_ZOOM to set
	 */
	public void setMIN_ZOOM(int mIN_ZOOM) {
		MIN_ZOOM = mIN_ZOOM;
	}

	/**
	 * @param graphIsClickable the graphIsClickable to set
	 */
	public void setGraphIsClickable(boolean graphIsClickable) {
		this.graphIsClickable = graphIsClickable;
	}

	/**
	 * @param graphIsZoomable the graphIsZoomable to set
	 */
	public void setGraphIsZoomable(boolean graphIsZoomable) {
		this.graphIsZoomable = graphIsZoomable;
	}

	/**
	 * @param graphIsPannable the graphIsPannable to set
	 */
	public void setGraphIsPannable(boolean graphIsPannable) {
		this.graphIsPannable = graphIsPannable;
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
	}

	/*****************************************************************************************************************************
	 * Getter Methods
	 *****************************************************************************************************************************/	

	@Override
	public float getScaleFactor() {
		return mScaleFactor;
	}

	@Override
	public ArrayList<Double> getPlotData() {
		return data;
	}

	@Override
	public ArrayList<int[]> getPlotColor() {
		return color;
	}

	@Override
	public ArrayList<String> getStackGroupLabels() {
		return stackGroupLabels;
	}

	@Override
	public int getGraphWidth() {
		return graphWidth;
	}

	@Override
	public int getGraphHeight() {
		return graphHeight;
	}

	@Override
	public int getGraphOriginX() {
		return graphOriginX;
	}

	@Override
	public int getGraphOriginY() {
		return graphOriginY;
	}

	@Override
	public boolean getDrawGridLines() {
		return drawGridLines;
	}

	@Override
	public boolean getDrawYAxis() {
		return drawYAxis;
	}

	@Override
	public boolean getDrawXAxis() {
		return drawXAxis;
	}

	@Override
	public int getXAxisColor() {
		return xAxisColor;
	}

	@Override
	public int getYAxisColor() {
		return yAxisColor;
	}

	@Override
	public String getYAxisLabel() {
		return yAxisLabel;
	}

	@Override
	public int getYAxisLabelSize() {
		return yAxisLabelSize;
	}

	@Override
	public int getXAxisLabelSize() {
		return xAxisLabelSize;
	}

	@Override
	public int getXAxisLabelRotation() {
		return xAxisLabelRotation;
	}

	@Override
	public ArrayList<String> getBarLabels() {
		return barLabels;
	}

	@Override
	public int getStyle() {
		return style;
	}

	@Override
	public int getGraphGrouping() {
		return graphGrouping;
	}

	@Override
	public int getXAxisLabelDistance() {
		return xAxisLabelDistance;
	}

	@Override
	public float getMaxZoom() {
		return MAX_ZOOM;
	}

	@Override
	public float getMinZoom() {
		return MIN_ZOOM;
	}

	@Override
	public boolean getGraphIsClickable() {
		return graphIsClickable;
	}

	@Override
	public boolean getGraphIsZoomable() {
		return graphIsZoomable;
	}

	@Override
	public boolean getGraphIsPannable() {
		return graphIsPannable;
	}
}
