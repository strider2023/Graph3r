function LineData(plotYValues, plotColor, lineWidth, name) {
	this.values = plotYValues;
	this.color = plotColor;
	this.width = lineWidth;
	this.legendName = name;
}

function LineGraph(id, plot, xValues) {
	var canvas=document.getElementById(id);	
	//Auto set canvas size to fit window
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
	var context = canvas.getContext("2d");	
	/** Dynamic values based on canvas dimensions**/
	var originX = canvas.width/8;
	var originY = canvas.height - canvas.height/6;
	var padding = canvas.width/12;
	var plotSpacing = canvas.width/10;
	var maxPlotValue = getMaxGroupValue(plot);
	// since we can drag from anywhere in a node
	// instead of just its x/y corner, we need to save
	// the offset of the mouse when we start dragging.
	var prevTranslateX = 0, prevTranslateY = 0;
	var translateX = 0, translateY = 0;
	var offsetX = 0, offsetY = 0;
	var isDrag = false;
	/** Getter setter variables **/
	var plotValues = plot;
	var plotXValues = xValues;
	var marginColor = "#636363"
	var guideColor = "#AAAAAA"
	var strokeWidth = 2;
	var fontSize = 18;
	var fontName = "Arial";
	var fontStyle = "bold " + fontSize + "px " + fontName;
	var xAxisName = "X - Axis";
	var yAxisName = "Y - Axis";
	var showValues = false;
	var showGuideLines = true;
	
	/** Public classes definations **/
	this.setPlotValue = function(values) {
		plotValues = values;
		maxPlotValue = getMaxGroupValue(plotValues);
		invalidate();
	}
	
	this.getPlotValue = function() {
		return plotValues;
	}
	
	this.setPlotXValues = function(values) {
		plotXValues = values;
		invalidate();
	}
	
	this.getPlotXValues = function() {
		return plotXValues;
	}
		
	this.setFontName = function(name) {
		fontName = name;
		fontStyle = "bold " + fontSize + "px " + fontName;
		invalidate();
	}
	
	this.setFontSize = function(size) {
		fontSize = size;
		fontStyle = "bold " + fontSize + "px " + fontName;
		invalidate();
	}
	
	this.setMarginColor = function(color) {
		marginColor = color;
		invalidate();
	}
	
	this.setGuideColor = function(color) {
		guideColor = color;
		invalidate();
	}
	
	this.setAxisLineWidth = function(width) {
		strokeWidth = width;
		invalidate();
	}
	
	this.showGuide = function(show) {
		showGuideLines = show;
		invalidate();
	}
	
	this.isGuideShown = function() {
		return showGuideLines;
	}
	
	this.setXAxisName = function(name) {
		xAxisName = name;
		invalidate();
	}
	
	this.getXAxisName = function() {
		return xAxisName;
	}
	
	this.setYAxisName = function(name) {
		yAxisName = name;
		invalidate();
	}
	
	this.getYAxisName = function() {
		return yAxisName;
	}
	
	this.setShowPlotValues = function(show) {
		showValues = show;
		invalidate();
	}
	
	this.isShowPlotValues = function() {
		return showValues;
	}
	
	/** Private classes definitions **/	
	function draw(){	
		//Draw the guide lines
		if(showGuideLines) {
			context.strokeStyle = guideColor;
			context.fillStyle = marginColor;
			context.lineWidth = strokeWidth;	
			var interval = getIntervalFactor(maxPlotValue);
			var posY = 0;
			for(i=1; i<maxPlotValue/interval;i++) {
				context.beginPath();
				posY = originY - getGraphHeight(interval*i, maxPlotValue, (originY-padding));
				context.moveTo(originX, posY);
				context.lineTo(canvas.width - padding,posY);
				context.stroke();
				context.fillText(interval*i, 
					originX - (context.measureText(interval*i).width) - 5, 
					posY);
				context.closePath();
			}
		}
		//Draw the line graph in the clipped viewspace
		context.save();
		context.beginPath();
		context.rect(originX,
		originY + fontSize + 5,
		canvas.width - originX - padding,
		-originY - fontSize - 5 + padding);
		context.clip();
		context.closePath();
		//Variables used for drawing
		//We add translateX for panning
		var startX = originX + plotSpacing + translateX;
		var endY = 0;
		var startY = originY;
		context.font= fontStyle;
		//Plot multiple graphs if present
		for(j=0; j < plotValues.length; j++) {
			startX = originX + plotSpacing + translateX;
			endY = 0;
			startY = originY;
			//Draw the a set of line plots
			for(i=0; i < plotValues[j].values.length; i++) {
				endY = getGraphHeight(plotValues[j].values[i], maxPlotValue, (originY-padding-(plot[j].width*3)));
				context.strokeStyle = plotValues[j].color;
				context.lineWidth = plotValues[j].width;
				context.fillStyle = plotValues[j].color;
				//Draw the plot point
				context.beginPath();
				context.arc(startX,originY - endY,(plotValues[j].width*3),0,2*Math.PI);
				context.fill();
				context.closePath();
				//Draw the line
				context.beginPath();
				//Start and end are the same for the very first plot
				if(i==0)
					context.lineTo(startX,originY - endY);
				else
					context.moveTo(startX-plotSpacing, originY - startY);
				context.lineTo(startX,originY - endY);
				context.stroke();
				context.closePath();
				//Determine the font height and draw the value likewise
				if(showValues) {
					context.beginPath();
					context.fillStyle = marginColor;
					if(endY > fontSize) {
						context.fillText(plotValues[j].values[i], 
						startX - (context.measureText(plotValues[j].values[i]).width/2), 
						originY - endY + fontSize + plotValues[j].width*3);
					} else {
						context.fillText(plotValues[j].values[i], 
						startX - (context.measureText(plotValues[j].values[i]).width/2), 
						(originY - endY) - fontSize - plotValues[j].width*3);
					}
					context.closePath();
				}
				//Update the next plot start pos
				startX += plotSpacing;
				startY = endY;
			}				
		}
		//Draw the X Axes names
		startX = originX + plotSpacing + translateX;
		for(i=0; i<plotXValues.length; i++) {
			context.beginPath();
			context.fillStyle = marginColor;
			context.fillText(plotXValues[i], 
				startX -(context.measureText(plotXValues[i]).width/2), 
				originY + fontSize);
			context.closePath();
			//Update the next start pos
			startX += plotSpacing;
		}
		context.restore();
		//draw the axis lines
		context.strokeStyle = marginColor;
		context.lineWidth = strokeWidth;	
		context.moveTo(originX, originY);
		context.lineTo(canvas.width - padding,originY);
		context.stroke();
		context.moveTo(originX, originY);
		context.lineTo(originX, padding);
		context.stroke();
		//draw the axis labels
		context.font= fontStyle;
		context.fillStyle = marginColor;
		context.fillText(xAxisName, 
				((canvas.width - originX)/2)
				-(context.measureText(xAxisName).width/2)
				+padding, 
				canvas.height - fontSize);
		context.save();
		context.moveTo(canvas.width/2, canvas.height/2);	
		context.rotate(Math.PI*1.5);
		context.fillText(yAxisName, 
				-(canvas.height/2), 
				fontSize);
		context.restore();
	};
			
	function invalidate() {
		context.clearRect(0, 0, canvas.width, canvas.height);
		draw();
	}
						
	invalidate();
			
	//Handle mouse-events
	canvas.addEventListener('mousedown', function(evt) {
		isDrag = true;
		offsetX = evt.clientX - prevTranslateX;
		offsetY = evt.clientY - prevTranslateY;
		invalidate();
	}, false);
			
	canvas.addEventListener('mouseup', function(evt) {
		isDrag = false;
		prevTranslateX = translateX;
		prevTranslateY = translateY;
		invalidate();
	}, false);
			
	canvas.addEventListener('mousemove', function(evt) {
		if(isDrag) {
			translateX = evt.clientX - offsetX;
			translateY = evt.clientY - offsetY;
			invalidate();					
		}
	}, false);
	
	/** Support functions **/
	function getMaxGroupValue(values) {
		var max = 0;
		if(values.length > 0) {
			for(j=0; j<values.length; j++) {
				for(i=0; i<values[j].values.length; i++) {
					max = Math.max(max, values[j].values[i]);
				}
			}
		}
		max = getIntervalFactor(max) * Math.round(max / getIntervalFactor(max))
		return max;
	};
	
	function getGraphHeight(value, maxValue, plotHeight) {
		var height = ((value/maxValue) * plotHeight);
		return height;
	};
	
	function getIntervalFactor(maxValue) {
		var interval = 1;
		if(maxValue.toString().length-1 > 0) {
			interval = (maxValue.toString().length-1) * 10;
		}
		return interval;
	}
}	