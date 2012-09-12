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

package com.touchmenotapps.Graph3r.bar;

import java.util.ArrayList;
import java.util.Collections;

import com.touchmenotapps.Graph3r.Graph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;

/**
 * @version Graph3r Beta 1
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The BarGraphHelperFunctions contains important helper functions that are used to draw
 * the bar graph.
 */
public class BarGraphHelperFunctions {
		
	/**
	 * 
	 * @param data
	 * @param groupContent
	 * @param style
	 * @return
	 */
	public int getMaximumSeriesValue(ArrayList<Double>data, int groupContent, int style) {
		int maxVal = 0;
		if (style == Graph.STYLE_BAR_NORMAL || style == Graph.STYLE_BAR_GROUPED) {
			maxVal = ((int) (Collections.max(data) / 10) + 1) * 10;
		} else {
			for (int counter = 0; counter < groupContent; counter++) {
				maxVal += data.get(counter);
			}
			int temp = 0;
			for (int counter = groupContent; counter < data.size(); counter++) {
				temp = (int) (temp + data.get(counter));
				if ((counter + 1) % groupContent == 0) {
					if (maxVal < temp) {
						maxVal = temp;
					}
					temp = 0;
				}
			}
		}
		return maxVal;
	}
	
	public void drawXAxisGrid(Canvas canvas, int color, int labelSize, int originX, int originY, int _width, int maxVal, float ratio, int numLines) {
		float scale = (float) maxVal * ratio / numLines;
		Paint mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mGridPaint.setColor(color);
		mGridPaint.setTextSize(labelSize);
		for (int counter = 1; counter <= numLines; counter++) {			
			if (maxVal < 10) 
				canvas.drawText(String.valueOf((maxVal * counter / numLines)), originX - 5,
						originY - (scale * counter) + 5, mGridPaint);
			else if (maxVal < 100) 
				canvas.drawText(String.valueOf((maxVal * counter / numLines)), originX - 15,
						originY - (scale * counter) + 5, mGridPaint);
			else 
				canvas.drawText(String.valueOf((maxVal * counter / numLines)), originX - 25,
						originY - (scale * counter) + 5, mGridPaint);
				
			Rect grid = new Rect(originX,
					(int) (originY - 1 - (scale * counter)), _width,
					(int) (originY - (scale * counter)));
			if (counter < numLines)
				canvas.drawRect(grid, mGridPaint);
		}
	}
	
	public void drawYAxisLabel(Canvas canvas, BarGraphRenderer renderer, int originX, int originY, int graphHeight, int viewHeight, int maxVal, boolean isTablet, float mScaleFactor) {
		Rect rect = new Rect();
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.getTextBounds(renderer.getYAxisLabel(), 0, renderer.getYAxisLabel().length(), rect);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextAlign(Align.RIGHT);
		//Check if the device is a tablet or not.
		if (isTablet) {
			paint.setTextSize(20);
		} else {
			paint.setTextSize(10);
		}
		canvas.save();
		if (!renderer.getDrawGridLines()) {
			canvas.rotate(-90, originX - (10*mScaleFactor), (viewHeight/2)); // rotating the text
			canvas.drawText(renderer.getYAxisLabel(), originX - 5, (viewHeight/2), paint);
		} else {
			if (maxVal < 10) {
				canvas.rotate(-90, originX - (15*mScaleFactor), (viewHeight/2)); // rotating the text
				canvas.drawText(renderer.getYAxisLabel(), originX - 10, (viewHeight/2), paint);
			} else if (maxVal < 100) {
				canvas.rotate(-90, originX - (25*mScaleFactor), (viewHeight/2)); // rotating the text
				canvas.drawText(renderer.getYAxisLabel(), originX - 20, (viewHeight/2), paint);
			} else {
				canvas.rotate(-90, originX - (35*mScaleFactor), (viewHeight/2)); // rotating the text
				canvas.drawText(renderer.getYAxisLabel(), originX - 30, (viewHeight/2), paint);
			}
		}
		canvas.restore();
	}
}
