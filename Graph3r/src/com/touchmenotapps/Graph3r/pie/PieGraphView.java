package com.touchmenotapps.Graph3r.pie;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * @version Graph3r Alpha 2
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The PieGraphView class is used to render the pie graph.
 */
public class PieGraphView {

	/**
	 * 
	 * @param context
	 * @param renderer
	 * @return
	 */
	public View getGraphView(Context context, PieGraphRenderer renderer) {
		return new PieGraph(context, renderer);
	}
	
	private class PieGraph extends View {
			
		private PieGraphRenderer mRenderer;
	
		private ScaleGestureDetector mScaleDetector;
	
		private float mScaleFactor;
	
		private final float START_INC = 30;
	
		private Paint mPiePaint = new Paint();
	
		private Paint mPieDividerLinePaints = new Paint();
	
		private int mWidth;
	
		private int mHeight;
	
		private int mBgColor;
	
		private float mStart;
	
		private float mSweep;
	
		private int mMaxConnection;
	
		private ArrayList<PieGraphObject> mDataArray;
	
		private RectF mOvals;
	
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
	
		public PieGraph(Context context, PieGraphRenderer renderer) {
			super(context);
			mRenderer = renderer;
			mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
			initChartPlotData(renderer);
		}
	
		private void initChartPlotData(PieGraphRenderer renderer) {
			mDataArray = renderer.getGraphData();
			mMaxConnection = renderer.getTotalValue(renderer.getGraphData());
			mWidth = renderer.getPieChartWidth();
			mHeight = renderer.getPieChartHeight();
			mBgColor = renderer.getBackgroundColor();
			mOvals = setPadding(renderer.getPiePadding());
			mScaleFactor = renderer.getScaleFactor();
			setChartPaintObjects(renderer);
		}
	
		private void setChartPaintObjects(PieGraphRenderer renderer) {
			mPiePaint.setAntiAlias(true);
			mPiePaint.setStyle(Paint.Style.FILL);
			mPiePaint.setColor(0x88FF0000);
			mPiePaint.setStrokeWidth(renderer.getPieDividerLineThickness());
	
			mPieDividerLinePaints.setAntiAlias(true);
			mPieDividerLinePaints.setStyle(Paint.Style.STROKE);
			mPieDividerLinePaints.setColor(renderer.getPieDividerLineColor());
			mPieDividerLinePaints.setStrokeWidth(renderer
					.getPieDividerLineThickness());
		}
	
		private RectF setPadding(int internalPadding) {
			if (mWidth > mHeight) {
				int horizontalPadding = ((mWidth - mHeight) / 2) + internalPadding;
				return new RectF(horizontalPadding, internalPadding, mWidth
						- horizontalPadding, mHeight - internalPadding);
			} else {
				int verticalPadding = ((mHeight - mWidth) / 2) + internalPadding;
				return new RectF(internalPadding, verticalPadding, mWidth
						- internalPadding, mHeight - verticalPadding);
			}
		}
	
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			/** Scale the canvas **/
			canvas.save();
			/** Set canvas zoom **/
			canvas.scale(mScaleFactor, mScaleFactor, mWidth / 2, mHeight / 2);
			/** Set canvas translate **/
			canvas.translate(translateX / mScaleFactor, translateY / mScaleFactor);
			/** Start drawing  the pie **/
			canvas.drawColor(mBgColor);
			mStart = START_INC;
			PieGraphObject plotDetail;
			for (int i = 0; i < mDataArray.size(); i++) {
				plotDetail = (PieGraphObject) mDataArray.get(i);
				mPiePaint.setColor(plotDetail.getColor());
				mSweep = (float) 360
						* ((float) plotDetail.getValue() / (float) mMaxConnection);
				canvas.drawArc(mOvals, mStart, mSweep, true, mPiePaint);
				canvas.drawArc(mOvals, mStart, mSweep, true, mPieDividerLinePaints);
				mStart += mSweep;
			}
			/** Restore canvas after zooming and translating **/
			canvas.restore();
		}
	
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (mRenderer.isGraphPannable()) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_UP:
					// All fingers went up, so let�s save the value of translateX
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
					break;
				}
				invalidate();
			}
			
			if (mRenderer.isGraphZoomable()) {
				mScaleDetector.onTouchEvent(event);
			}
			return true;
		}
	
		private class ScaleListener extends
				ScaleGestureDetector.SimpleOnScaleGestureListener {
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