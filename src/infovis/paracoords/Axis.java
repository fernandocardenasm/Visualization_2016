package infovis.paracoords;

import java.awt.Color;

import infovis.scatterplot.Range;

public class Axis {
	private int id;
	private String name = "";
	private int position = 0;
	private int top;
	private int bottom;
	private Range range;
	private Color color = Color.BLACK;
	
	public Axis(int id, String name, int position, int top, int bottom , Range range) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.top = top;
		this.bottom = bottom;
		this.range = range;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void setTop(int top){
		this.top = top;
	}
	public void setBottom(int bottom){
		this.bottom = bottom;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public Range getRange() {
		return range;
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}
	
	

}
