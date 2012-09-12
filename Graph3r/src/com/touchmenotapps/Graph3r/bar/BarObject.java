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

import android.graphics.Rect;

/**
 * @version Graph3r Beta 1
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description This class is used to handle user interaction and return values via onBarClickedListener.
 * @see com.touchmenotapps.Graph3r.bar.BarGraphView.BarGraphInterface
 */
public class BarObject {

	private Rect barBounds;
	private String value;

	/**
	 * Class constructor.
	 * @param barBounds - Bar graph single bar bounds.
	 * @param value - Value displayed by the bar.
	 */
	public BarObject(Rect barBounds, String value) {
		this.barBounds = barBounds;
		this.value = value;
	}

	/**
	 * @return the value of the selected bar.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * This function checks if the click has occurred within the given bar bounds.
	 * @param posX - Click event position X.
	 * @param posY - Click event position Y. 
	 * @return boolean value if the click is within the bar bounds.
	 */
	public boolean isInBounds(int posX, int posY) {
		if(posX > barBounds.left && posX < barBounds.left + barBounds.width() && 
				posY > barBounds.top && posY < barBounds.top + barBounds.height())
			return true;
		return false;
	}
}
