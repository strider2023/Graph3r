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

package com.touchmenotapps.Graph3r.pie;

/**
 * @version Graph3r Beta 1
 * @author Arindam Nath (strider2023@gmail.com)
 * @Description	The LineGraphObject defines a single pie graph plot. 
 */
public class PieGraphObject {
	
	private int mValue;
	private String mLabel;
	private int mColor;
	
	/**
	 * 
	 * @param mValue
	 * @param mLabel
	 * @param color
	 */
	public PieGraphObject(int mValue, String mLabel, int mColor) {
		this.mValue = mValue;
		this.mLabel = mLabel;
		this.mColor = mColor;
	}
	
	/**
	 * @return the mValue
	 */
	public int getValue() {
		return mValue;
	}
	
	/**
	 * @return the mLabel
	 */
	public String getLabel() {
		return mLabel;
	}
	
	/**
	 * @return the mColor
	 */
	public int getColor() {
		return mColor;
	}
}
