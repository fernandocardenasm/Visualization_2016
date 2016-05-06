package infovis.paracoords;

import java.awt.Color;

import infovis.scatterplot.Range;

public class Axis {
	private int id;
	private String name = "";
	private int position = 0;
	private Range range;
	private Color color = Color.BLACK;
	
	public Axis(int id, String name, int position, Range range) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.range = range;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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
	
	

}
