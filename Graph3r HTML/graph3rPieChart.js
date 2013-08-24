function PieData(plotValues, plotNames, plotColor) {
	this.values = plotValues;
	this.names = plotNames;
	this.color = plotColor;
}

function PieChart(id, plot) {
	var canvas=document.getElementById(id);	
	//Auto set canvas size to fit window
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
	var context = canvas.getContext("2d");	
	/** Dynamic values based on canvas dimensions**/	
	var originX = canvas.width/2;
	var originY = canvas.height/2;
	var padding = canvas.width/12;
	var totalPlotValue = getTotalValue(plot.values);
	var radius = (Math.min(canvas.width, canvas.height)/2) - padding;
	// since we can drag from anywhere in a node
	// instead of just its x/y corner, we need to save
	// the offset of the mouse when we start dragging.
	var prevTranslateX = 0, prevTranslateY = 0;
	var translateX = 0, translateY = 0;
	var offsetX = 0, offsetY = 0;
	var isDrag = false;
	/** Getter setter variables **/
	var graphPlotValues = plot;
	var fontSize = 18;
	var fontName = "Arial";
	var textFontColor = "#FFFFFF";
	var fontStyle = "bold " + fontSize + "px " + fontName;
	var shadowColor = "#232323"
	var shadowAmount = 10;
		
	/** Public classes definations **/
	this.setPlotValue = function(values) {
		graphPlotValues = values;
		totalPlotValue = getTotalValue(graphPlotValues.values);
		invalidate();
	}
	
	this.getPlotValue = function() {
		return graphPlotValues;
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
	
	this.setLabelColor = function(color) {
		textFontColor = color;
		invalidate();
	}
	
	this.setShadowColor = function(color) {
		shadowColor = color;
		invalidate();
	}
	
	this.setSahdowAmount = function(amt) {
		shadowAmount = amt;
		invalidate();
	}
	
	/** Private classes definitions **/	
	function draw(){			
		context.font= fontStyle;
		//Draw the bg shadow layer.
		context.save();
		context.beginPath();
		context.fillStyle=textFontColor;
		context.arc(originX+translateX,originY+translateY,radius,0,2*Math.PI);
		context.shadowColor = shadowColor;
		context.shadowBlur = shadowAmount;
		context.shadowOffsetX = 0;
		context.shadowOffsetY = 0;
		context.fill();
		context.closePath();
		context.restore();
		//Variables used for drawing		
		var start = 0
		for(i=0; i < graphPlotValues.values.length; i++) {
			context.fillStyle=graphPlotValues.color[i];
			context.beginPath();
			context.moveTo(originX+translateX, originY+translateY);						
			context.arc(originX+translateX,originY+translateY,radius,start,
				toRadian(graphPlotValues.values[i]/totalPlotValue) + start, false);
			context.fill();
			context.closePath();
			context.save();
			context.fillStyle=textFontColor;
			context.translate(originX+translateX,originY+translateY);
			context.rotate(toRadian((graphPlotValues.values[i]/totalPlotValue)/2)+start);
			context.fillText(graphPlotValues.names[i],
				(radius/1.5) -(context.measureText(graphPlotValues.names[i]).width/2), 0);
			context.restore();
			start += toRadian(graphPlotValues.values[i]/totalPlotValue);
		}	
		context.beginPath();
		context.fillStyle=textFontColor;
		context.arc(originX+translateX,originY+translateY,radius/3,0,2*Math.PI);
		context.fill();
		context.closePath();
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
	function getTotalValue(values) {
		var total = 0;
		if(values.length > 0) {
			for(i=0; i<values.length; i++)
				total +=values[i];
		}
		return total;
	}
	
	function toRadian(degree) {
		return (degree * Math.PI * 2);
	}
}	