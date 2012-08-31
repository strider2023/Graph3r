package com.touchmenotapps.Graph3r.line;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * @version Graph3r Alpha 3
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The LineGraphView class is used to render the line graph.
 * TODO Check grid and average line issue.
 */
public class LineGraphView {
	
	/**
	 * 
	 * @param context
	 * @param renderer
	 * @return
	 */
	public View getGraphView(Context context, LineGraphRenderer renderer) {
		return new LineGraph(context, renderer);
	}
	
	private class LineGraph extends View {
		
		private final int LINE_HANDLER = 213;
		
		private final int X_AXES_LABEL_HANDLER = 9809;
		
		private final int Y_AXES_LABEL_HANDLER = 6876;

		private LineGraphHelperFunctions mHelper;
		
		private ScaleGestureDetector mScaleDetector;
		
		private float mScaleFactor = 1.0f;
	
		private Canvas mCanvas = null;
		
		private Paint mGraphLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mGridPaintFull = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mGridPaintDot = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mAveragePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mAverageTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mXAxesLabelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private Paint mYAxesLabelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
		private int mWidth;
		
		private int mHeight;
	
		private int mGapLeft;
		
		private int mGapRight;
		
		private int mGapTop;
		
		private int mGapBottom;
	
		private ArrayList<String> mGraphXAxesLabels = new ArrayList<String>(0);
	
		private ArrayList<LineGraphObject> mGraphPlotDeatils = new ArrayList<LineGraphObject>(0);
	
		private LineGraphRenderer mRenderer;
		
		// These two variables keep track of the X and Y coordinate of the finger
		// when it first
		// touches the screen
		private float startX = 0f;
		private float startY = 0f;
	
		// These two variables keep track of the amount we need to translate the
		// canvas along the X
		// and the Y coordinate
		private float translateX = 0f;
		private float translateY = 0f;
	
		// These two variables keep track of the amount we translated the X and Y
		// coordinates, the last time we
		// panned.
		private float previousTranslateX = 0f;
		private float previousTranslateY = 0f;
	
		public LineGraph(Context context, LineGraphRenderer renderer) {
			super(context);
			mRenderer = renderer;
			mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
			mHelper = new LineGraphHelperFunctions(renderer);
			initView(renderer);
			initPaint(renderer);
		}
	
		private void initView(LineGraphRenderer renderer) {
			mGraphPlotDeatils = renderer.getGraphPlotDeatils();
			mGraphXAxesLabels = renderer.getGraphXAxesLabels();
			mScaleFactor = renderer.getScaleFactor();
			mWidth = renderer.getWidth();
			mHeight = renderer.getHeight();
			mGapLeft = renderer.getOriginX();
			mGapBottom = renderer.getOriginY();
			mGapRight = renderer.getGraphPadding();
			mGapTop = renderer.getGraphPadding();
		}
	
		private void initPaint(LineGraphRenderer renderer) {
			mGridPaintDot.setColor(renderer.getGridColor());
			mGridPaintDot.setStyle(Paint.Style.FILL);
			mGridPaintDot.setStrokeWidth(0.5f);
			mGridPaintDot
					.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			
			mGridPaintFull.setColor(renderer.getAxesColor());
			mGridPaintFull.setStyle(Paint.Style.FILL);
			mGridPaintFull.setStrokeWidth(renderer.getGraphLineThickness());
			
			mTitlePaint.setTextAlign(Paint.Align.CENTER);
			mTitlePaint.setColor(renderer.getTitleTextColor());
			mTitlePaint.setTextSize(renderer.getTitleTextSize());
			
			mYAxesLabelTextPaint.setTextAlign(Paint.Align.RIGHT);
			mYAxesLabelTextPaint.setColor(renderer.getYAxesTextColor());
			mYAxesLabelTextPaint.setTextSize(renderer.getYAxesTextSize());
			
			mXAxesLabelTextPaint.setTextAlign(Paint.Align.CENTER);
			mXAxesLabelTextPaint.setColor(renderer.getXAxesTextColor());
			mXAxesLabelTextPaint.setTextSize(renderer.getXAxesTextSize());
	
			mAveragePaint.setColor(renderer.getAverageLineColor());
			mAveragePaint.setStyle(Paint.Style.FILL);
			mAveragePaint.setStrokeWidth(1.5f);
			mAveragePaint
					.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			
			mAverageTextPaint.setStyle(Paint.Style.FILL);
			mAverageTextPaint.setTextAlign(Paint.Align.LEFT);
			mAverageTextPaint.setColor(renderer.getAverageLineColor());
			mAverageTextPaint.setTextSize(10);
			
			if (mRenderer.isFillGraph()) {
				mGraphLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
			} else {
				mGraphLinePaint.setStyle(Paint.Style.FILL);
			}
			mGraphLinePaint.setStrokeWidth(renderer.getGraphLineThickness());
		}
	
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (mRenderer.isGraphPannable()) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_UP:
					// All fingers went up, so let’s save the value of translateX
					// and translateY into previousTranslateX and
					// previousTranslate
					previousTranslateX = translateX;
					previousTranslateY = translateY;
					break;
	
				case MotionEvent.ACTION_DOWN:
					// We assign the current X and Y coordinate of the finger to
					// startX and startY minus the previously translated
					// amount for each coordinates This works even when we are
					// translating the first time because the initial
					// values for these two variables is zero.
					startX = event.getX() - previousTranslateX;
					startY = event.getY() - previousTranslateY;
					break;
	
				case MotionEvent.ACTION_MOVE:
					if(((event.getY() - startY)/ mScaleFactor) >= 0 
					&& ((event.getX() - startX)/ mScaleFactor) <= 5
					&& ((event.getY() - startY)/ mScaleFactor) <= (mHeight - mGapBottom - (70 * mScaleFactor))
					&& ((event.getX() - startX)/ mScaleFactor) >= -(mWidth - mGapLeft - (70 * mScaleFactor))) {
						translateX = event.getX() - startX;
						translateY = event.getY() - startY;
						// We cannot use startX and startY directly because we have
						// adjusted their values using the previous translation values.
						// This is why we need to add those values to startX and startY
						// so that we can get the actual coordinates of the finger.
						double distance = Math
								.sqrt(Math.pow(event.getX()
										- (startX + previousTranslateX), 2)
										+ Math.pow(event.getY()
												- (startY + previousTranslateY), 2));
						if (distance > 0) {
							distance *= mScaleFactor;
						}
					}
					break;
				}
				invalidate();
			}
			
			if (mRenderer.isGraphZoomable()) {
				mScaleDetector.onTouchEvent(event);
			}
			return true;
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			mCanvas = canvas;
			Rect TextRect = new Rect();
			String StrLablel = "";
			LineGraphObject mLineGraphItem;
			float y;
			int Index, value;
			float OldX, OldY, NewX, NewY;
			int maxY = mHelper.getMaxValue();
			int minY = mHelper.getMinValue();
			float graphAverage = mHelper.getAverage();
			
			/** Draw Y Axes **/
			mCanvas.drawLine((float) (mGapLeft), 
					(float) (mHeight - mGapBottom), 
					(float) (mWidth - mGapRight), 
					(mHeight - mGapBottom), 
					mGridPaintFull);
			
			/** Draw the X Axes **/
			mCanvas.drawLine((float) mGapLeft, 
					mGapTop, 
					(float) mGapLeft, 
					(float) (mHeight - mGapBottom), 
					mGridPaintFull);
			
			/** Draw the Y axes label **/
			Rect rect = new Rect();
			mYAxesLabelTextPaint.getTextBounds(mRenderer.getYAxesText(), 0, mRenderer.getYAxesText().length(), rect);
			canvas.rotate(-90, 10, (mHeight - mGapBottom) / 2); // rotating the text
			canvas.drawText(mRenderer.getYAxesText(), 5, (mHeight - mGapBottom) / 2, mYAxesLabelTextPaint);
			canvas.restore();
						
			/** Plot Y Axes labels **/
			canvas.save();
			graphInputHandler(canvas, maxY, Y_AXES_LABEL_HANDLER);
			float step = (float) (maxY - minY)
					/ (float) (mRenderer.getMaxYAxesLables());
			float stepY = (float) (mHeight - mGapTop - mGapBottom)
					/ (float) (maxY - minY);
			for (int counter = 0; counter <= mRenderer.getMaxYAxesLables(); counter++) {
				y = (float) ((counter * step) * stepY);
				OldX = (float) (mGapLeft);
				OldY = (float) (mHeight - mGapBottom - y);
				NewX = (float) (mWidth - mGapRight);
				NewY = (float) (mHeight - mGapBottom - y);
				if (counter > 0) 
					if(mRenderer.isGridVisible())
						mCanvas.drawLine(OldX, OldY, NewX, NewY, mGridPaintDot);
				
				value = (int) minY + (int) (counter * step);
				StrLablel = String.valueOf(value);
				mCanvas.drawText(StrLablel, mGapLeft - 10, NewY, mYAxesLabelTextPaint);
			}
			canvas.restore();
	
			/** Plot X Axes labels **/
			float stepX = (float) (mWidth - mGapLeft - mGapRight)
					/ (float) (mRenderer.getMaxXAxesLabels() - 1);
			float IndexStep = (float) mGraphXAxesLabels.size()
					/ (float) mRenderer.getMaxXAxesLabels();
			NewY = (float) (mHeight - mGapBottom);
	
			canvas.save();
			graphInputHandler(canvas, maxY, X_AXES_LABEL_HANDLER);
			for (int counter = 0; counter < mRenderer.getMaxXAxesLabels(); counter++) {
				Index = (int) ((float) (counter) * (float) IndexStep);
				if (Index >= mGraphXAxesLabels.size())
					Index = mGraphXAxesLabels.size() - 1;
				if (counter == mRenderer.getMaxXAxesLabels() - 1)
					Index = mGraphXAxesLabels.size() - 1;
				NewX = (float) ((float) mGapLeft + (float) (counter * stepX));
				
				if (counter > 0)
					if(mRenderer.isGridVisible())
						mCanvas.drawLine(NewX, mGapTop, NewX, NewY + 3, mGridPaintDot);
				
				/** Check if the user has passed the list of custom labels for x-axes**/
				if (mGraphXAxesLabels != null && mGraphXAxesLabels.size() > 0) {
					mXAxesLabelTextPaint.getTextBounds(mGraphXAxesLabels.get(counter), 0,
							mGraphXAxesLabels.get(counter).length(), TextRect);
					NewX = NewX - TextRect.right / 2;
					mCanvas.drawText(mGraphXAxesLabels.get(counter), NewX,
							(mHeight - mGapBottom) + 13, mXAxesLabelTextPaint);
				} else {
					mXAxesLabelTextPaint.getTextBounds(String.valueOf(counter), 0,
							String.valueOf(counter).length(), TextRect);
					NewX = NewX - TextRect.right / 2;
					mCanvas.drawText(String.valueOf(counter), NewX,
							(mHeight - mGapBottom) + 13, mXAxesLabelTextPaint);
				}
			}
			canvas.restore();
			
			canvas.save();
			graphInputHandler(canvas, maxY, LINE_HANDLER);
			/** Plot the line graph **/
			float drawOffsetX = 0.0f, drawOffsetY = 0.0f, pointStepX = 0.0f, pointStepY = 0.0f;
			/** First loop for the number of line graph that needs to be drawn **/
			for (int linesCounter = 0; linesCounter < mGraphPlotDeatils.size(); linesCounter++) {
				mLineGraphItem = mGraphPlotDeatils.get(linesCounter);
				pointStepX = (float) (mWidth - mGapLeft - mGapRight)
						/ (float) (mLineGraphItem.getPlotPoints().length - 1);
				pointStepY = (float) (mHeight - mGapTop - mGapBottom)
						/ (float) (maxY - minY);
				drawOffsetX = (float) mGapLeft;
				drawOffsetY = (float) (mHeight - mGapBottom);
	
				OldX = drawOffsetX;
				OldY = drawOffsetY - pointStepY
						* (float) (mLineGraphItem.getPlotPoints()[0] - minY);
				Path mPath = new Path();
				/** Loop through the plot points of the current line graph **/
				for (int k = 0; k < mLineGraphItem.getPlotPoints().length; k++) {
					int plotValue = mLineGraphItem.getPlotPoints()[k];
					NewX = drawOffsetX + (pointStepX * (float) k);
					NewY = drawOffsetY - (pointStepY * (float) (plotValue - minY));
					mGraphLinePaint.setColor(mLineGraphItem.getPlotColor());
					if (!mRenderer.isFillGraph()) {
						mCanvas.drawLine(OldX, OldY, NewX, NewY, mGraphLinePaint);
					} else {
						mPath.rewind();
						mPath.moveTo(OldX, OldY);
						mPath.lineTo(NewX, NewY);
						mPath.lineTo(NewX, drawOffsetY);
						mPath.lineTo(OldX, drawOffsetY);
						mPath.close();
						mCanvas.drawPath(mPath, mGraphLinePaint);
					}
					OldX = NewX;
					OldY = NewY;
				}
			}
			
			/** Draw the average line if passed so by the user **/
			if (mRenderer.isShowAverageLine()) {
				mAverageTextPaint.getTextBounds(String.valueOf(graphAverage), 0, String.valueOf(graphAverage).length(),
						TextRect);
				NewX = (float) (mWidth - mGapRight - TextRect.right) - 3.0f;
				NewY = (float) (mGapTop - TextRect.top) + 3.0f;
				mCanvas.drawText(String.valueOf(graphAverage), NewX, NewY, mAverageTextPaint);
				NewY = drawOffsetY - (pointStepY * (float) (graphAverage - minY));
				mCanvas.drawLine(mGapLeft, NewY, mWidth - mGapRight, NewY,
						mAveragePaint);
			}
			canvas.restore();
			
			/** Set the graph title **/
			if (mRenderer.getGraphTitle() != null
					&& mRenderer.getGraphTitle().length() > 0) {
				mTitlePaint.getTextBounds(mRenderer.getGraphTitle(), 0, mRenderer
						.getGraphTitle().length(), TextRect);
				mCanvas.drawText(mRenderer.getGraphTitle(),
						(mWidth - TextRect.right) / 2, 10, mTitlePaint);
			}
			invalidate();
		}
		
		private void graphInputHandler(Canvas canvas, int maxY, int HANDLER_CASE) {
			switch(HANDLER_CASE) {
				case LINE_HANDLER:
					if(mRenderer.isGraphZoomable() || mRenderer.isGraphPannable())
						canvas.clipRect(mGapLeft, 
								mGapTop, (mWidth - mGapRight), 
								(mHeight - mGapBottom), 
								android.graphics.Region.Op.REPLACE);
					canvas.scale(mScaleFactor, mScaleFactor, mGapLeft, mHeight - mGapBottom);
					canvas.translate(translateX / mScaleFactor, translateY / mScaleFactor);
					break;
				case X_AXES_LABEL_HANDLER:
					if(mRenderer.isGraphZoomable() || mRenderer.isGraphPannable())
						canvas.clipRect(mGapLeft, 
								(mHeight - mGapBottom), 
								(mWidth - mGapRight), 
								mHeight, android.graphics.Region.Op.REPLACE);
					canvas.scale(mScaleFactor, mScaleFactor, mGapLeft, mHeight - mGapBottom);
					canvas.translate(translateX / mScaleFactor, 0);
					break;
				case Y_AXES_LABEL_HANDLER:
					if(mRenderer.isGraphZoomable() || mRenderer.isGraphPannable())
						canvas.clipRect(0, 
								mGapTop, 
								mGapLeft, 
								(mHeight - mGapBottom), 
								android.graphics.Region.Op.REPLACE);
					canvas.scale(mScaleFactor, mScaleFactor, mGapLeft, mHeight - mGapBottom);
					canvas.translate(0, translateY / mScaleFactor);
					break;
			}
		}
	
		private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		
			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				return super.onScaleBegin(detector);
			}
			
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				mScaleFactor *= detector.getScaleFactor();
				// Don't let the object get too small or too large.
				mScaleFactor = Math.max(
						Math.min(mScaleFactor, mRenderer.getMaxZoom()),
						mRenderer.getMinZoom());
				invalidate();
				return true;
			}
			
			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {
				super.onScaleEnd(detector);
			}
		}
	}	
}
