package com.touchmenotapps.Graph3r.meter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Align;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.View;

/**
 * @version Graph3r Alpha 3
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The MeterGraphView class is used to render the meter graph.
 */
public class MeterGraphView {
	
	/**
	 * 
	 * @param context
	 * @param renderer
	 * @return
	 */
	public View getGraphView(Context context, MeterGraphRenderer renderer) {
		return new MeterGraph(context, renderer);
	}
	
	private class MeterGraph extends View {
	
		private MeterGraphRenderer mRenderer;
		
		private double mMeterReading;
		
		private int graphOrigin; 
		
		private int graphSize; 
			
		private double MinMedMax[]; 
		
		private Paint mDialBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mDialCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mDialColorScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mDialTickerPaint = new Paint(Paint.ANTI_ALIAS_FLAG );
		
		private Paint mDialTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mDialNeedlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mDialCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
		/**
		 * 
		 * @param context
		 * @param renderer
		 */
		public MeterGraph(Context context, MeterGraphRenderer renderer) {
			super(context);
			mRenderer = renderer;
			graphSize = renderer.getGraphSize();
			MinMedMax = renderer.getGraphMinMedMax();
			graphOrigin = renderer.getGraphOrigin();
			mMeterReading = renderer.getCurrentMeterReading();
			initGraphPaint(renderer);
		}
		
		/**
		 * 
		 * @param renderer
		 */
		private void initGraphPaint(MeterGraphRenderer renderer) {
			//Check if there is a defination for a gradient or a solid colour.
			if(renderer.getGraphDialBorderGradient().length >= 2) {
				mDialBorderPaint.setShader(new RadialGradient(graphSize / 2, 
						graphSize / 2, 
						renderer.getGraphDialBorderGradientThickness(), 
						renderer.getGraphDialBorderGradient()[1], 
						renderer.getGraphDialBorderGradient()[0], 
						Shader.TileMode.MIRROR));
			} else if (renderer.getGraphDialBorderGradient().length == 1) {
				mDialBorderPaint.setColor(renderer.getGraphDialBorderGradient()[0]);
			}
			
			mDialCenterPaint.setColor(renderer.getGraphDialBackgroundColor());
			mDialTickerPaint.setColor(renderer.getGraphDialTickerColor());
			mDialTickerPaint.setStyle(Style.STROKE);
			mDialTickerPaint.setStrokeWidth(renderer.getGraphDialTickerThickness());
			mDialTextPaint.setColor(renderer.getGraphDialTextColor());
			mDialTextPaint.setTextSize(renderer.getGraphDialTextSize());
			mDialTextPaint.setTextAlign(Align.CENTER);
			mDialCenterTextPaint.setColor(renderer.getGraphDialCenterTextColor());
			mDialCenterTextPaint.setTextAlign(Align.CENTER);
		}
	
		@Override
		public void onDraw(Canvas canvas) {
			double ratio = 300 / (MinMedMax[3] - MinMedMax[0]);
			// Dial Width
			double diameter = graphSize - graphOrigin; 
			double per = .95;
			/***************************************************************************
			 * Drawing Outer Ring
			 ***************************************************************************/
			canvas.drawCircle(graphSize / 2, graphSize / 2, (float) (diameter) / 2, mDialBorderPaint);
			canvas.drawCircle(graphSize / 2, graphSize / 2, (float) (diameter * .88 * .95) / 2, mDialCenterPaint);
			/***************************************************************************
			 * Drawing Colored Scale
			 ***************************************************************************/
			Rect rect = new Rect((int) (graphOrigin / 2 + diameter * (1 - per)), (int) (graphOrigin / 2 + diameter
					* (1 - per)), (int) (graphOrigin / 2 + diameter * (per)), (int) (graphOrigin / 2 + diameter
					* per));
			RectF scaleRect = new RectF(rect);
			mDialColorScalePaint.setColor(mRenderer.getGraphMinMedMaxColor()[2]);
			canvas.drawArc(scaleRect, 120, 300, true, mDialColorScalePaint);
			mDialColorScalePaint.setColor(mRenderer.getGraphMinMedMaxColor()[1]);
			canvas.drawArc(scaleRect, 120, (float) (ratio * (MinMedMax[2]-MinMedMax[0])), true, mDialColorScalePaint);
			mDialColorScalePaint.setColor(mRenderer.getGraphMinMedMaxColor()[0]);
			canvas.drawArc(scaleRect, 120, (float) (ratio * (MinMedMax[1]-MinMedMax[0])), true, mDialColorScalePaint);
			/***************************************************************************
			 * Drawing ticks
			 ***************************************************************************/
			//getting initial tick position 
			double oldX = graphSize / 2;
			double oldY = (graphOrigin / 2 + diameter * (1 - per));
			double newX = graphSize / 2;
			double newY = graphSize / 2;
			oldX -= graphSize / 2;
			oldY -= graphSize / 2;
			newX = (oldX * Math.cos(Math.toRadians(-150))) - oldY * Math.sin(Math.toRadians(-150));
			newY = (oldX * Math.sin(Math.toRadians(-150)) + oldY * Math.cos(Math.toRadians(-150)));
			oldX = newX + graphSize / 2;
			oldY = newY + graphSize / 2;
			//System.out.println(graphWidth);
			canvas.drawLine((int) oldX, (int) oldY, graphSize / 2, graphSize / 2, mDialTickerPaint);
			// Drawing remaining ticks
			for (int i = 0; i < (MinMedMax[3]-MinMedMax[0]); i++) {
				oldX -= graphSize / 2;
				oldY -= graphSize / 2;
	
				newX = (oldX * Math.cos(Math.toRadians(ratio)) - oldY
						* Math.sin(Math.toRadians(ratio)));
				newY = (oldX * Math.sin(Math.toRadians(ratio)) + oldY
						* Math.cos(Math.toRadians(ratio)));
				oldX = newX + graphSize / 2;
				oldY = newY + graphSize / 2;
				canvas.drawLine((int) oldX, (int) oldY, graphSize / 2, graphSize / 2, mDialTickerPaint);
			}
			
			drawMeterNeedle(canvas, diameter, per, ratio);
			/***************************************************************************
			 * Draw scale values
			 ***************************************************************************/
			int adjust = 0;
			double textOldX = graphSize / 2 - adjust;
			double textOldY = graphOrigin / 4 + adjust;
	
			textOldX -= (graphSize / 2 - adjust);
			textOldY -= (graphSize / 2 + adjust);
			newX = (textOldX * Math.cos(Math.toRadians(-150))) - textOldY
					* Math.sin(Math.toRadians(-150));
			newY = (textOldX * Math.sin(Math.toRadians(-150)) + textOldY
					* Math.cos(Math.toRadians(-150)));
			textOldX = newX + (graphSize / 2 - adjust);
			textOldY = newY + (graphSize / 2 + adjust);
			canvas.drawText(String.valueOf((int)MinMedMax[0]), (int) textOldX, (int) textOldY, mDialTextPaint);
			double sum = 0;
			for (int i = 1; i < MinMedMax.length; i++) {
				textOldX -= (graphSize / 2 - adjust);
				textOldY -= (graphSize / 2 + adjust);
				newX = (textOldX * Math.cos(Math.toRadians(((MinMedMax[i]-MinMedMax[0]) - sum) * ratio))) - textOldY
						* Math.sin(Math.toRadians(((MinMedMax[i]-MinMedMax[0]) - sum) * ratio));
				newY = (textOldX * Math.sin(Math.toRadians(((MinMedMax[i]-MinMedMax[0]) - sum) * ratio)) + textOldY
						* Math.cos(Math.toRadians(((MinMedMax[i]-MinMedMax[0]) - sum) * ratio)));
				sum = MinMedMax[i]-MinMedMax[0];
				textOldX = newX + (graphSize / 2 - adjust);
				textOldY = newY + (graphSize / 2 + adjust);
				canvas.drawText(String.valueOf((int) MinMedMax[i]), (int) textOldX, (int) textOldY, mDialTextPaint);
			}
			/***************************************************************************
			 * Drawing Needle Value at dial's centre
			 ***************************************************************************/
			canvas.drawText(String.valueOf(mMeterReading), (float) ((graphSize / 2)),
					(int) (graphSize * ((float) 3 / (float) 4)), mDialCenterTextPaint);
		}
		
		/**
		 * 
		 * @param canvas
		 * @param diameter
		 * @param per
		 * @param ratio
		 */
		private void drawMeterNeedle(Canvas canvas, double diameter, double per, double ratio) {
			//Add a layer of meter background colour on the drawn ticker lines and scale colours.
			canvas.drawCircle(graphSize / 2, graphSize / 2, (float) (diameter * .8 * .95) / 2, mDialCenterPaint);
			double meterReading = mMeterReading;
			//Check if the reading exceeded the limits
			if(mMeterReading < MinMedMax[0])
				meterReading = MinMedMax[0];
			else if(mMeterReading > MinMedMax[3])
				meterReading = MinMedMax[3];
			double oldX = graphSize / 2;
			double oldY = (graphOrigin / 2 + diameter * (1 - (per - .1)));
			oldX -= graphSize / 2;
			oldY -= graphSize / 2;
			double newX = (oldX * Math.cos(Math.toRadians(-150))) - oldY * Math.sin(Math.toRadians(-150));
			double newY = (oldX * Math.sin(Math.toRadians(-150)) + oldY * Math.cos(Math.toRadians(-150)));
			oldX = newX + graphSize / 2;
			oldY = newY + graphSize / 2;
			double oldX1 = oldX;
			double oldY1 = oldY;
			oldX1 -= graphSize / 2;
			oldY1 -= graphSize / 2;
			//Deduct the meter reading value from the original starting value..
			double newX1 = (oldX1 * Math.cos(Math.toRadians((meterReading - MinMedMax[0]) * ratio)) - oldY1
					* Math.sin(Math.toRadians((meterReading - MinMedMax[0]) * ratio)));
			double newY1 = (oldX1 * Math.sin(Math.toRadians((meterReading - MinMedMax[0]) * ratio)) + oldY1
					* Math.cos(Math.toRadians((meterReading - MinMedMax[0]) * ratio)));
			oldX1 = newX1 + graphSize / 2;
			oldY1 = newY1 + graphSize / 2;
			if (mMeterReading <= (MinMedMax[1]-MinMedMax[0]))
				mDialNeedlePaint.setColor(mRenderer.getGraphMinMedMaxColor()[0]);
			else if (mMeterReading <= (MinMedMax[2]-MinMedMax[0]))
				mDialNeedlePaint.setColor(mRenderer.getGraphMinMedMaxColor()[1]);
			else
				mDialNeedlePaint.setColor(mRenderer.getGraphMinMedMaxColor()[2]);
			mDialNeedlePaint.setStrokeWidth(3);
			mDialNeedlePaint.setStrokeJoin(Join.MITER);
			mDialNeedlePaint.setStyle(Style.FILL_AND_STROKE);
			canvas.drawLine((int) oldX1, (int) oldY1, (graphSize / 2) + 4, (graphSize / 2), mDialNeedlePaint);
			canvas.drawLine((int) oldX1, (int) oldY1, (graphSize / 2), (graphSize / 2) + 4, mDialNeedlePaint);
			canvas.drawLine((int) oldX1, (int) oldY1, graphSize / 2, graphSize / 2, mDialNeedlePaint);
			canvas.drawLine((int) oldX1, (int) oldY1, (graphSize / 2) - 4, (graphSize / 2), mDialNeedlePaint);
			canvas.drawLine((int) oldX1, (int) oldY1, (graphSize / 2), (graphSize / 2) - 4, mDialNeedlePaint);
			mDialNeedlePaint.setColor(Color.BLACK);
			canvas.drawCircle(graphSize / 2, graphSize / 2, (float) (diameter * .08) / 2, mDialNeedlePaint);
		}
	}
}
