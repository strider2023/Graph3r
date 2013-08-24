/* 
Version : HTML5/JS
Coded By : strider2023@gmail.com
JS/jQuery Document
*/  

function BarData(plotYValues, plotColor, name) {
	this.values = plotYValues;
	this.color = plotColor;
	this.legendName = name;
}
			
function BarGraph(id, plot, xValues) {
	var canvas=document.getElementById(id);	
	//Auto set canvas size to fit window
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
	var context = canvas.getContext("2d");			
	/** Dynamic values based on canvas dimensions**/
	var originX = canvas.width/8;
	var originY = canvas.height - canvas.height/6;
	var padding = canvas.width/12;
	var barSpacing = canvas.width/12;
	var maxPlotValue = getMaxGroupValue(plot);
	// since we can drag from anywhere in a node
	// instead of just its x/y corner, we need to save
	// the offset of the mouse when we start dragging.
	var prevTranslateX = 0, prevTranslateY = 0;
	var translateX = 0, translateY = 0;
	var offsetX = 0, offsetY = 0;
	var isDrag = false;
	/** Getter setter variables **/
	var fontSize = 18;
	var fontName = "Arial";
	var marginColor = "#636363"
	var guideColor = "#AAAAAA"
	var strokeWidth = 2;
	var plotValues = plot;
	var plotXValues = xValues;
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
		//Draw the bars in the clipped viewspace
		context.save();
		context.beginPath();
		context.rect(originX,
		originY + fontSize + 5,
		canvas.width - originX - padding,
		-originY - fontSize - 5 + padding);
		context.clip();
		context.closePath();
		//Plot multiple graphs if present
		for(j=0; j < plotValues.length; j++) {
			//Variables used for drawing
			//We add translateX for panning
			var startX = originX + 
				(barSpacing*(j+1)) //We multiply it with the current group pointer
				+ translateX;
			context.font= fontStyle;
			var endY = 0;
			for(i=0; i < plotValues[j].values.length; i++) {
				endY = getGraphHeight(plotValues[j].values[i], maxPlotValue, (originY-padding));
				context.fillStyle = plotValues[j].color[i];
				context.beginPath();
				context.fillRect(startX, originY, barSpacing, -endY);
				if(showValues) {
					//Determine the font height and draw the value likewise
					if(endY > fontSize) {
						context.fillStyle = 'white';
						context.fillText(plotValues[j].values[i], 
						startX + (barSpacing/2) - (context.measureText(plotValues[j].values[i]).width/2), 
						originY - endY + fontSize);
					} else {
						context.fillStyle = marginColor;
						context.fillText(plotValues[j].values[i], 
						startX + (barSpacing/2) - (context.measureText(plotValues[j].values[i]).width/2), 
						(originY - endY) - fontSize);
					}
				}
				context.closePath();
				//Update the next bar start pos
				//We multiply it with the total group size +1 for the spacing element
				startX += (barSpacing*(plotValues.length+1));
			}			
		}
		//Reset the value
		var startX = originX + barSpacing + translateX;
		//Draw the xAxes content
		for(i=0; i < plotXValues.length; i++) {
			context.beginPath();
			context.fillStyle = marginColor;
			context.fillText(plotXValues[i], 
			startX + ((barSpacing*plotValues.length)/2) - (context.measureText(plotXValues[i]).width/2), 
			originY + fontSize);
			context.closePath();
			//Update the next bar start pos
			startX += (barSpacing*(plotValues.length+1));
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
		//context.moveTo(canvas.width/2, canvas.height/2);	
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