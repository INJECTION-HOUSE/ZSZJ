var treeChart = {
	width : 650,
	height : 500,
	series : [],
	treeIndex : 0,
	treeCanvas : null,
	chartCanvas : null,
	context : null,
	selectable : true,
	title : "Tree Chart",
	levelgap : 30,
	nodegap : 10,
	nodewidth : 10,
	nodeheight : 70,
	paddingTop : 50,
	paddingLeft : 10,
	tooltips : {
		enable : true,
		tooltipCanvas : null,
		width : 150,
		height: 100
	},
	levelColors : ["RGBA(22,28,180, 0.8)", "RGBA(22,88,200,0.8)", "RGBA(22,188,220,0.8)", "RGBA(22,200,248,0.8)"],
	showInfo : true,
	initSettings : function(config) {
		this.treeCanvas = config.treeCanvas;
		this.treeCanvas.width = config.width;
		this.treeCanvas.height = config.height;
		this.width = config.width;
		this.height = config.height;
		this.title = config.title;
		this.series = config.series;
		this.treeIndex = config.treeIndex;
	},
	
	getContext : function() {
		return this.context;
	},
	
	getTreeIndex : function() {
		return this.treeIndex;
	},
	
	render : function() {
		var ctx = this.treeCanvas.getContext("2d"); 
		this.renderTree(ctx);
		ctx.drawImage(this.chartCanvas, 0, 0, this.width, this.height, 0, 0, this.width, this.height);
	},
	
	renderTree : function(pcontext) {
		this.tooltips.tooltipCanvas = document.createElement("canvas");
		this.tooltips.tooltipCanvas.width = 150;
		this.tooltips.tooltipCanvas.height = 100;
		this.chartCanvas = document.createElement("canvas");
		this.chartCanvas.width = this.width;
		this.chartCanvas.height = this.height;
		this.context = this.chartCanvas.getContext("2d");  
		var ctx = this.context;
		ctx.fillStyle="white";  
		ctx.fillRect(0, 0, this.treeCanvas.width, this.treeCanvas.height);
		// 初始化层数与节点宽度与高度
		var size = this.series.length;
		var levels = 0;
		if(size <= 1) {
			levels = 1;
			this.nodegap = 20;
			this.nodeheight = 100;
		}
		else if(size <= 4) {
			levels = 2;
			this.nodegap = 20;
			this.nodeheight = 100;
		}
		else if(size <= 13) {
			levels = 3;
			this.nodegap = 20;
			this.nodeheight = 100;
		} else {
			levels = 4;
		}
		
		// render title
        ctx.font = '14pt Calibri';  
        ctx.fillStyle = 'black';  
        ctx.fillText(this.title + "-" + (this.treeIndex + 1), this.width / 2 - 40, 30);
        
        // render tree node
        for(var index=0; index<size; index++) {
        	this.renderTreeNode(ctx, this.series[index], levels);
        }
        
        // render lines to link each nodes
        var centerStart = this.width / 2;
        for(var h = 1; h < levels; h++) {
        	var lineYPos = this.paddingTop + (this.nodeheight + this.levelgap) * (h) - this.levelgap / 2;
        	var startX = 0; 
        	var endX = 0;
        	if(h == 1) {
        		endX = centerStart + (this.nodewidth + this.nodegap) * 9;
        		startX= centerStart - (this.nodewidth + this.nodegap) * 9;
            	ctx.save();
            	ctx.lineWidth = 1;  
            	ctx.beginPath();  
            	ctx.moveTo(startX - this.nodewidth/2, lineYPos);  
            	ctx.lineTo(endX - this.nodewidth/2, lineYPos);
            	ctx.strokeStyle = "RGBA(255, 23, 55, 1)"; 
            	ctx.stroke();  
            	ctx.restore();
    		}
        	else if(h == 2) {
				startX = centerStart - ((this.nodewidth + this.nodegap) * 12);
				endX = centerStart - ((this.nodewidth + this.nodegap) * 6);
				ctx.save();
				ctx.lineWidth = 1;
				ctx.beginPath();
				ctx.moveTo(startX - this.nodewidth / 2, lineYPos);
				ctx.lineTo(endX - this.nodewidth / 2, lineYPos);
				ctx.strokeStyle = "RGBA(255, 23, 55, 1)";
				ctx.stroke();
				ctx.restore();
				
				startX = centerStart - ((this.nodewidth + this.nodegap) * 6);
				endX = centerStart - ((this.nodewidth + this.nodegap) * 3);
				ctx.save();
				ctx.lineWidth = 1;
				ctx.beginPath();
				ctx.moveTo(startX - this.nodewidth / 2, lineYPos);
				ctx.lineTo(endX - this.nodewidth / 2, lineYPos);
				ctx.strokeStyle = "RGBA(255, 255, 255, 1)";
				ctx.stroke();
				ctx.restore();
				
				startX = centerStart - ((this.nodewidth + this.nodegap) * 3);
				endX = centerStart + ((this.nodewidth + this.nodegap) * 3);
				ctx.save();
				ctx.lineWidth = 1;
				ctx.beginPath();
				ctx.moveTo(startX - this.nodewidth / 2, lineYPos);
				ctx.lineTo(endX - this.nodewidth / 2, lineYPos);
				ctx.strokeStyle = "RGBA(255, 23, 55, 1)";
				ctx.stroke();
				ctx.restore();
				
				startX = centerStart + ((this.nodewidth + this.nodegap) * 3);
				endX = centerStart + ((this.nodewidth + this.nodegap) * 6);
				ctx.save();
				ctx.lineWidth = 1;
				ctx.beginPath();
				ctx.moveTo(startX - this.nodewidth / 2, lineYPos);
				ctx.lineTo(endX - this.nodewidth / 2, lineYPos);
				ctx.strokeStyle = "RGBA(255, 255, 255, 1)";
				ctx.stroke();
				ctx.restore();
				
				startX = centerStart + ((this.nodewidth + this.nodegap) * 6);
				endX = centerStart + ((this.nodewidth + this.nodegap) * 12);
				ctx.save();
				ctx.lineWidth = 1;
				ctx.beginPath();
				ctx.moveTo(startX - this.nodewidth / 2, lineYPos);
				ctx.lineTo(endX - this.nodewidth / 2, lineYPos);
				ctx.strokeStyle = "RGBA(255, 23, 55, 1)";
				ctx.stroke();
				ctx.restore();
    		}
        	else if(h == 3) {
        		for(var k=-13; k<13; k+=3) {
        			startX = centerStart + ((this.nodewidth + this.nodegap) * k);
        			endX = centerStart + ((this.nodewidth + this.nodegap) * (k+2));
        			ctx.save();
        			ctx.lineWidth = 1;  
        			ctx.beginPath();  
        			ctx.moveTo(startX - this.nodewidth/2, lineYPos);  
        			ctx.lineTo(endX - this.nodewidth/2, lineYPos);
        			ctx.strokeStyle = "RGBA(255, 23, 55, 1)"; 
        			ctx.stroke();  
        			ctx.restore();        			
        		}
    		}
        }
        
        // setup mouse over listener
        var parent = this;
        this.treeCanvas.addEventListener('mousemove', function(event) {  
            var x = event.pageX;  
            var y = event.pageY;  
            var canvas = event.target;  
            var bbox = canvas.getBoundingClientRect();  
            var loc = { x: x - bbox.left * (canvas.width  / bbox.width),  
                    y: y - bbox.top  * (canvas.height / bbox.height)};  
              
            parent.showTooltips(loc, pcontext);  
        }, false);
	},
	
	showTooltips : function(loc, pcontext) {
		var levels = this.getLevels();
		// render tree node
		var findIndex = -1;
		var size = this.series.length;
        for(var index=0; index<size; index++) {
        	var nodelocation = this.getNodeXYPoint(this.series[index], levels);
        	// this.nodewidth, this.nodeheight
        	if((nodelocation.x < loc.x && loc.x < (nodelocation.x + this.nodewidth)) && 
        			(nodelocation.y<loc.y && loc.y < (nodelocation.y + this.nodeheight))) {
        		findIndex = index;
        		break;
        	}
        }
        
        if(findIndex >=0) {
        	this.clearTooltips(pcontext);
        	
        	var m_context = this.tooltips.tooltipCanvas.getContext("2d");
            m_context.save();  
            m_context.clearRect(0, 0, this.tooltips.tooltipCanvas.width, this.tooltips.tooltipCanvas.height);  
            m_context.lineWidth = 2;  
            m_context.strokeStyle = "RGBA(0,255,0,1)";
            m_context.fillStyle = "RGBA(255,255,255,0.7)";    
            m_context.strokeRect(2, 2, this.tooltips.tooltipCanvas.width-4, this.tooltips.tooltipCanvas.height-4);
            m_context.fillRect(2, 2, this.tooltips.tooltipCanvas.width-4, this.tooltips.tooltipCanvas.height-4);
            m_context.font="14px Arial";  
            m_context.fillStyle="RGBA(0,0,0,1)";  
            m_context.fillText("当前位置:" + this.series[findIndex].nodeIndex, 5, 20);  
            m_context.fillText("购买人:" + this.series[findIndex].pname, 5, 40);  
            m_context.fillText("推荐人:" + this.series[findIndex].recommendName, 5, 60);  
            m_context.restore();
            pcontext.drawImage(this.tooltips.tooltipCanvas, 0, 0, this.tooltips.tooltipCanvas.width, this.tooltips.tooltipCanvas.height, 
            		loc.x, loc.y-this.tooltips.tooltipCanvas.height, this.tooltips.tooltipCanvas.width, this.tooltips.tooltipCanvas.height);
        }
        else {
        	this.clearTooltips(pcontext);  
        }
	},
	
	clearTooltips : function(pctx) {
		pctx.clearRect(0,0,this.width, this.height);
		pctx.drawImage(this.chartCanvas, 0, 0, this.width, this.height, 0, 0, this.width, this.height);
	},
	
	getNodeXYPoint : function(node, levels) {
		var level = node.level;
		var viewIndex = node.viewIndex;
		var positionInLevel = 0;
		if(1==level) {
			positionInLevel = viewIndex;
		}
		else if(2==level) {
			positionInLevel = viewIndex - 1;
		}
		
		else if(3==level) {
			positionInLevel = viewIndex - 4;
		}
		
		else if(4==level) {
			positionInLevel = viewIndex - 13;
		}
		
		var startY = this.paddingTop + (this.nodeheight + this.levelgap) * (level - 1);
		// calculate offset for 1, 2, 3
		var centerStart = this.width / 2 - this.nodewidth;
		if(level == 1) {
			startX = centerStart;
		}
		else if(level == 2) {
			if(positionInLevel == 1) {
				startX = centerStart;				
			}
			else if(positionInLevel == 0) {
				startX = centerStart - (this.nodewidth + this.nodegap)*9;
			}
			else if(positionInLevel == 2) {
				startX = centerStart + (this.nodewidth + this.nodegap)*9;
			}
		}
		else if(level == 3) {
			if(positionInLevel == 4) {
				startX = centerStart;
			}
			else if(positionInLevel > 4) {
				startX = centerStart + ((this.nodewidth + this.nodegap) * (positionInLevel - 4)*3);
			}
			else {
				startX = centerStart - ((this.nodewidth + this.nodegap) * (4 - positionInLevel)*3);
			}
		}
		else if(level == 4) {
			if(positionInLevel == 13) {
				startX = centerStart;
			}
			else if(positionInLevel > 13) {
				startX = centerStart + ((this.nodewidth + this.nodegap) * (positionInLevel - 13));
			}
			else {
				startX = centerStart - ((this.nodewidth + this.nodegap) * (13 - positionInLevel));
			}
		}
		
		var loc = { x: startX, y: startY};  
		return loc;
	},
	
	renderTreeNode : function(ctx, node, levels) {
		// draw node
		ctx.save();
		var loc = this.getNodeXYPoint(node, levels);
		ctx.fillStyle=this.levelColors[node.level-1];  
		ctx.fillRect(loc.x, loc.y, this.nodewidth, this.nodeheight); 
		ctx.restore();
		// draw line
		if(node.level != 1) {
			// draw up line
	    	ctx.save();
	    	ctx.lineWidth = 1;  
	    	ctx.beginPath();  
	    	ctx.moveTo(loc.x + this.nodewidth/2, loc.y - this.levelgap / 2);  
	    	ctx.lineTo(loc.x + this.nodewidth/2, loc.y);
	    	ctx.strokeStyle = "RGBA(255, 23, 55, 1)"; 
	    	ctx.stroke();  
	    	ctx.restore();
		}
		else {
	    	ctx.save();
	    	ctx.lineWidth = 1;  
	    	ctx.beginPath();  
	    	ctx.moveTo(loc.x + this.nodewidth/2, loc.y + this.nodeheight);  
	    	ctx.lineTo(loc.x + this.nodewidth/2, loc.y + this.nodeheight + this.levelgap / 2);
	    	ctx.strokeStyle = "RGBA(255, 23, 55, 1)"; 
	    	ctx.stroke();  
	    	ctx.restore();
		}
		
    	// draw down line
		var level = node.level;
    	if((level + 1) <= levels) {
	    	ctx.save();
	    	ctx.lineWidth = 1;  
	    	ctx.beginPath();  
	    	ctx.moveTo(loc.x + this.nodewidth/2, loc.y + this.nodeheight);  
	    	ctx.lineTo(loc.x + this.nodewidth/2, loc.y + this.nodeheight + this.levelgap / 2);
	    	ctx.strokeStyle = "RGBA(255, 23, 55, 1)"; 
	    	ctx.stroke();  
	    	ctx.restore();
    	}
    	
    	// show info
    	if(this.showInfo) {
    		ctx.save();
    		ctx.font="10px Arial";  
    		ctx.fillStyle="RGBA(0,0,255,1.0)"; 
    		if(node.level == 4) {
    			ctx.fillText(""+node.nodeIndex, loc.x-5, loc.y + this.nodeheight/2); 
    		}
    		else {
    			ctx.fillText(""+node.nodeIndex, loc.x, loc.y + this.nodeheight/2); 
    		}
    		ctx.restore();
    	}
    	
	},
	
	getLevels : function() {
		var size = this.series.length;
		var levels = 0;
		if(size <= 1) {
			levels = 1;
		}
		else if(size <= 4) {
			levels = 2;
		}
		else if(size <= 13) {
			levels = 3;
		} else {
			levels = 4;
		}
	},
	
	hightlightNode : function(ctx, node, levels) {
		var loc = this.getNodeXYPoint(node, levels);
		ctx.save();
		ctx.fillStyle="RGBA(255,0,0,0.5)";  
		ctx.fillRect(loc.x, loc.y, this.nodewidth, this.nodeheight); 
		ctx.lineWidth = 1;  
        ctx.strokeStyle = "RGBA(255, 255, 0,1)";  
        ctx.strokeRect(loc.x, loc.y, this.nodewidth, this.nodeheight);
        ctx.restore();
        
    	// show info
    	if(this.showInfo) {
    		ctx.save();
    		ctx.font="10px Arial";  
    		ctx.fillStyle="RGBA(0,255,0,1.0)"; 
    		if(node.level == 4) {
    			ctx.fillText(""+node.nodeIndex, loc.x-5, loc.y + this.nodeheight/2); 
    		}
    		else {
    			ctx.fillText(""+node.nodeIndex, loc.x, loc.y + this.nodeheight/2); 
    		}
    		ctx.restore();
    	}
    	
    	// redraw it
    	var ctx = this.treeCanvas.getContext("2d"); 
    	this.clearTooltips(ctx);
	}
}