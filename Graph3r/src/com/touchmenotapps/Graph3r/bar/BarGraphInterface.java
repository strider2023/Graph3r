package com.touchmenotapps.Graph3r.bar;

import java.util.ArrayList;

/**
 * 
 * @author Arindam Nath
 *
 */
public interface BarGraphInterface {
	public float getScaleFactor();
	public ArrayList<Double> getPlotData();
	public ArrayList<int[]> getPlotColor();
	public ArrayList<String> getStackGroupLabels();
	public int getGraphWidth();
	public int getGraphHeight();
	public int getGraphOriginX();
	public int getGraphOriginY();
	public boolean getDrawGridLines();
	public boolean getDrawYAxis();
	public boolean getDrawXAxis();
	public int getXAxisColor();
	public int getYAxisColor();
	public String getYAxisLabel();
	public int getYAxisLabelSize();
	public int getXAxisLabelSize();
	public int getXAxisLabelRotation();
	public ArrayList<String> getBarLabels();
	public int getStyle();
	public int getGraphGrouping();
	public int getXAxisLabelDistance();
	public float getMaxZoom();
	public float getMinZoom();
	public boolean getGraphIsClickable();
	public boolean getGraphIsZoomable();
	public boolean getGraphIsPannable();
}
