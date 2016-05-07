package infovis.paracoords;

import infovis.debug.Debug;

public class PointPlot {
	private int px;
	private int py;
	private double value;
	public PointPlot(double value) {
		super();
		this.value = value;
	}
	public int getPx() {
		return px;
	}
	public void setPx(int px) {
		this.px = px;
	}
	public int getPy() {
		return py;
	}
	public void setPy(int py) {
		this.py = py;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public void calculatePositionY(int top, int bottom, double min, double max) {
		double y;
		//Debug.p("Value: "+ this.value);
		//Debug.p("Min: "+ min);
		//Debug.p("Max: "+ max);
		//Debug.p("Top: "+ top);
		//Debug.p("Bottom: "+ bottom);
		y = (this.value - min) / (max - min);
		
		
		this.py = (int) ((bottom - top) * y + top);
		//y + i*plotSize
	}
}
