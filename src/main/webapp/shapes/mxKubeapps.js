// kubeapps icon. Extends mxShape.

function mxShapeKubeappsIcon(bounds, fill, stroke, strokewidth)
{
	mxShape.call(this);
	this.bounds = bounds;
	this.fill = fill;
	this.stroke = stroke;
	this.strokewidth = (strokewidth != null) ? strokewidth : 1;
};

mxUtils.extend(mxShapeKubeappsIcon, mxShape);

mxShapeKubeappsIcon.prototype.cst = {
		ICON : 'mxgraph.kubeapps.icon'
};

mxShapeKubeappsIcon.prototype.customProperties = [
	{name: 'prIcon1', dispName: 'Application', defVal: 'app', type: 'APP',
			enumList: [{val: 'app', dispName: 'app'},
				{val: 'drawio', dispName: 'drawio'},
				{val: 'znxd', dispName: 'znxd'},
				{val: 'nginx', dispName: 'nginx'},
				{val: 'bicenter', dispName: 'bicenter'}]}
];

//Function: paintVertexShape. Paints the vertex shape.

mxShapeKubeappsIcon.prototype.paintVertexShape = function(c, x, y, w, h)
{
	var prIcon1 = mxUtils.getValue(this.state.style, 'prIcon1', '');
	
	var fillColor = mxUtils.getValue(this.state.style, 'fillColor', '#ffffff');
	var strokeColor = mxUtils.getValue(this.state.style, 'strokeColor', '#ffffff');

	c.translate(x, y);
	
	var frame = mxStencilRegistry.getStencil('mxgraph.kubeapps.frame');
	
	c.setFillColor(strokeColor);
	frame.drawShape(c, this, 0, 0, w, h);

	c.setFillColor(fillColor);
	frame.drawShape(c, this, w * 0.03, h * 0.03, w * 0.94, h * 0.94);
	
	var prStencil = mxStencilRegistry.getStencil('mxgraph.kubeapps.' + prIcon1);
	
	if (prStencil != null)
	{
		c.setFillColor(strokeColor);
		prStencil.drawShape(c, this, w * 0.2, h * 0.2, w * 0.6, h * 0.6);
	}
};

mxCellRenderer.registerShape(mxShapeKubeappsIcon.prototype.cst.ICON, mxShapeKubeappsIcon);

mxShapeKubeappsIcon.prototype.getConstraints = function(style, w, h)
{
	var constr = [];
	var r = Math.min(h * 0.5, w * 0.5);
	
	constr.push(new mxConnectionConstraint(new mxPoint(0, 0.5), false));

	return (constr);
}

