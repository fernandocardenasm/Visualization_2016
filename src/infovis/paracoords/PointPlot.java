package infovis.paracoords;

public class PointPlot {
	private int px;
	private int py;
	private double value;
	public PointPlot(int px, int py, double value) {
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
		y = (this.value - min) / (max - min);
		
		this.py = (int) ((top - bottom) * y + bottom);
		//y + i*plotSize
	}
}
