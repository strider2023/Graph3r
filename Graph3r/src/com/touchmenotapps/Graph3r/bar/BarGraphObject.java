package com.touchmenotapps.Graph3r.bar;

import android.graphics.Rect;

/**
 * @version Graph3r Alpha 2
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	
 */
public class BarGraphObject {

	private Rect barBounds;
	private String value;

	/**
	 * @param barBounds
	 * @param value
	 */
	public BarGraphObject(Rect barBounds, String value) {
		this.barBounds = barBounds;
		this.value = value;
	}

	/**
	 * @return the barBounds
	 */
	public Rect getBarBounds() {
		return barBounds;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	public boolean isInBounds(int posX, int posY) {
		if(posX > barBounds.left && posX < barBounds.left + barBounds.width() && 
				posY > barBounds.top && posY < barBounds.top + barBounds.height())
			return true;
		return false;
	}
}
