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
 * 
 * @author Arindam Nath
 *
 */
public class LineChartView extends View {

	private LineGraphHelper mHelper;
	
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

	private ArrayList<String> mGraphXAxesLabels = new ArrayList<String>();

	private ArrayList<LineGraphObject> mGraphPlotDeatils = new ArrayList<LineGraphObject>();

	private LineGraphRenderer mRenderer;

	public LineChartView(Context context, LineGraphRenderer renderer) {
		super(context);
		mRenderer = renderer;
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		initView(renderer);
		initPaint(renderer);
	}

	private void initView(LineGraphRenderer renderer) {
		mGraphPlotDeatils = renderer.getGraphPlotDeatils();
		mHelper = new LineGraphHelper(mGraphPlotDeatils);
		mGraphXAxesLabels = renderer.getGraphXAxesLabels();
		mScaleFactor = renderer.getScaleFactor();
		mWidth = renderer.getWidth();
		mHeight = renderer.getHeight();
		mGapLeft = renderer.getOriginX();
		mGapBottom = renderer.getOriginY();
		this.setPadding(renderer.getGraphPadding(), renderer.getGraphPadding(), renderer.getGraphPadding(), renderer.getGraphPadding());
		mGapRight = 0;
		mGapTop = 0;
	}

	private void initPaint(LineGraphRenderer renderer) {
		mGridPaintDot.setColor(renderer.getGridColor());
		mGridPaintDot.setStyle(Paint.Style.FILL);
		mGridPaintDot.setStrokeWidth(0.5f);
		mGridPaintDot
				.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
		
		mGridPaintFull.setColor(renderer.getAxesColor());
		mGridPaintFull.setStyle(Paint.Style.FILL);
		mGridPaintFull.setStrokeWidth(1.0f);
		
		mTitlePaint.setTextAlign(Paint.Align.CENTER);
		mTitlePaint.setColor(renderer.getTitleTextColor());
		mTitlePaint.setTextSize(renderer.getTitleTextSize());
		
		mYAxesLabelTextPaint.setTextAlign(Paint.Align.CENTER);
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
		
		/** Plot Y Axes and labels **/
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
			if (counter == 0)
				mCanvas.drawLine(OldX, OldY, NewX, NewY, mGridPaintFull);
			else {
				if(mRenderer.isGridVisible())
					mCanvas.drawLine(OldX, OldY, NewX, NewY, mGridPaintDot);
			}  
			
			value = (int) minY + (int) (counter * step);
			StrLablel = String.valueOf(value);
			mCanvas.drawText(StrLablel, mGapLeft - 10, NewY, mYAxesLabelTextPaint);
		}

		/** Plot X Axes and labels **/
		float stepX = (float) (mWidth - mGapLeft - mGapRight)
				/ (float) (mRenderer.getMaxXAxesLabels() - 1);
		float IndexStep = (float) mGraphXAxesLabels.size()
				/ (float) mRenderer.getMaxXAxesLabels();
		NewY = (float) (mHeight - mGapBottom);

		for (int j = 0; j < mRenderer.getMaxXAxesLabels(); j++) {
			Index = (int) ((float) (j) * (float) IndexStep);
			if (Index >= mGraphXAxesLabels.size())
				Index = mGraphXAxesLabels.size() - 1;
			if (j == mRenderer.getMaxXAxesLabels() - 1)
				Index = mGraphXAxesLabels.size() - 1;
			NewX = (float) ((float) mGapLeft + (float) (j * stepX));
			
			if (j == 0)
				mCanvas.drawLine(NewX, mGapTop, NewX, NewY, mGridPaintFull);
			else {
				if(mRenderer.isGridVisible())
					mCanvas.drawLine(NewX, mGapTop, NewX, NewY + 3, mGridPaintDot);
			}
			/** Check if the user has passed the list of custom labels for x-axes**/
			if (mGraphXAxesLabels != null && mGraphXAxesLabels.size() > 0) {
				mXAxesLabelTextPaint.getTextBounds(mGraphXAxesLabels.get(j), 0,
						mGraphXAxesLabels.get(j).length(), TextRect);
				NewX = NewX - TextRect.right / 2;
				mCanvas.drawText(mGraphXAxesLabels.get(j), NewX,
						(mHeight - mGapBottom) + 13, mXAxesLabelTextPaint);
			} else {
				mXAxesLabelTextPaint.getTextBounds(String.valueOf(j), 0,
						String.valueOf(j).length(), TextRect);
				NewX = NewX - TextRect.right / 2;
				mCanvas.drawText(String.valueOf(j), NewX,
						(mHeight - mGapBottom) + 13, mXAxesLabelTextPaint);
			}
		}
		
		/** Scale the canvas **/
		canvas.save();
		/** Set canvas zoom **/
		canvas.scale(mScaleFactor, mScaleFactor, mRenderer.getOriginX(), mRenderer.getOriginY());
		
		/** Plot the line grpah **/
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
		
		// Restore canvas after zooming and translating
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
