function RadialProgress(id) {
	var canvas=document.getElementById(id);	
	/** Auto set canvas size to fit window **/
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
	var context = canvas.getContext("2d");	
	/** Dynamic values based on canvas dimensions**/
	var originX = canvas.width/2;
	var originY = canvas.height/2;
	var padding = canvas.width/12;
	var radius = (Math.min(canvas.width, canvas.height)/2) - padding;
	var isDrag = false;
	/** Getter setter variables **/
	var progressValue = 0;
	var progressMaxValue = 100;
	var fontStyle = "bold " + (radius/1.5) + "px Arial";
	var centerFontStyle = (radius/5) + "px Arial";
	var progressColor = "#0099CC";
	var bgColor = "#AAAAAA";
	var scoreColor = "#D84949";
	var centerText = "Score";
	var isPercent = true;
	var isInteractive = false;
	
	/** Public classes definations **/
	this.setValue = function(value) {
		progressValue = value;
		invalidate();
	}
	
	this.getValue = function() {
		return progressValue;
	}
	
	this.setWidgetInteractive = function(interactive) {
		isInteractive = interactive;
	}
	
	this.isWidgetInteractive = function() {
		return isInteractive;
	}
	
	this.setMaxValue = function(max) {
		progressMaxValue = max;
		invalidate();
	}
	
	this.getMaxValue = function() {
		return progressMaxValue;
	}
	
	this.setFontName = function(fontName) {
		fontStyle = "bold " + (radius/1.5) + "px " + fontName;
		centerFontStyle = (radius/5) + "px " + fontName;
		invalidate();
	}
	
	this.setProgressColor = function(color) {
		progressColor = color;
		invalidate();
	}
	
	this.setSecondaryProgressColor = function(color) {
		bgColor = color;
		invalidate();
	}
	
	this.setScoreTextColor = function(color) {
		scoreColor = color;
		invalidate();
	}
	
	this.setCenterText = function(text) {
		centerText = text;
		invalidate();
	}
	
	this.getCenterText = function() {
		return centerText;
	}
	
	this.showAsPercent = function(show) {
		isPercent = show;
		invalidate();
	}
	
	this.isAsPercent = function() {
		return isPercent;
	}
	
	/** Private classes definitions **/
	function invalidate() {
		context.clearRect(0, 0, canvas.width, canvas.height);
		draw();
	}
	
	/** The draw function **/
	function draw(){			
		context.font= fontStyle;
		//Variables used for drawing
		var start = 1.5*Math.PI;
		//Draw the bg circle
		context.strokeStyle=bgColor;
		context.lineWidth = radius/12;	
		context.moveTo(originX, originY);
		context.beginPath();
		context.arc(originX,originY,radius,0,2*Math.PI);
		context.stroke();
		context.closePath();
		//Draw the progress circle
		context.strokeStyle=progressColor;
		context.lineWidth = radius/8;	
		context.beginPath();
		context.arc(originX,originY,radius,start,
			toRadian(progressValue/progressMaxValue) + start, false);
		context.stroke();
		context.closePath();
		//Draw the center text value
		context.fillStyle=scoreColor;
		if(isPercent) {
			//Round of value to an integer
			var per = Math.round(Number((progressValue/progressMaxValue) * 100));
			context.fillText(per, 
				originX -(context.measureText(per).width/2), 
				originY);
			context.font = centerFontStyle;
			context.fillText("%", 
				originX + (context.measureText(per).width/2) + ((context.measureText("%").width) * 2), 
				originY -((radius/1.75)/2));
		} else {
			context.fillText(progressValue, 
				originX -(context.measureText(progressValue).width/2), 
				originY);
		}
		//Draw the secondary text 
		context.font = centerFontStyle;
		context.fillStyle=bgColor;
		context.fillText(centerText, 
			originX -(context.measureText(centerText).width/2), 
			originY + (radius/3));
	};
	
	getDragValue = function(Cx,Cy) {
		var ABx = originX-originX;
		var ABy = originY-0;
		var CBx = originX-Cx;
		var CBy = originY-Cy;
		var dot = (ABx * CBx) + (ABy * CBy); // dot product
		var cross = (ABx * CBy) - (ABy * CBx); // cross product
		var alpha = Math.atan2(cross,dot);
		if(alpha < 0)
			alpha += (2*Math.PI);
		//Return the rounded of value
		return Math.round(Number((alpha*progressMaxValue)/(Math.PI*2)));
	}
	
	function toRadian(degree) {
		return (degree * Math.PI * 2);
	}
	
	//Init the menu with default values
	invalidate();
	
	//Handle mouse events;
	canvas.addEventListener('mousedown', function(evt) {
		isDrag = true;
	}, false);
					
	canvas.addEventListener('mouseup', function(evt) {
		isDrag = false;
	}, false);
				
	canvas.addEventListener('mousemove', function(evt) {
		if(isDrag && isInteractive) {
			progressValue = getDragValue(evt.clientX,evt.clientY);	
			invalidate();
		}
	}, false);
}