package infovis.heatmap;

import java.awt.Color;

import infovis.debug.Debug;

public class CellPlot {
	int regionId; //It is not unique, it is used to know which RectanglePlot belongs to a car object
	int idReference; // Only 3.
	int posJ;
	boolean isMain; //Only our 3 main references are the main ones. "True" for them.
	int posX; //It is X real position on Screen
	int posY;
	private String status; //ON OR OFF, ON: It is selected, OFF: It is not selected
	private Color color;
	int initPosX;
	private double value;
	private boolean selected; // if cell should be drawn selected or not
	
	public CellPlot(int regionId, int idReference, int posX, int posY, boolean isMain, int posJ) {
		super();
		this.regionId = regionId; // Id for the Row. //A row sub i has only 1 id.
		this.idReference = idReference; //
		this.posX = posX;
		this.posY = posY;
		this.color = Color.black;
		this.status = "OFF";
		this.isMain = isMain;
		this.posJ = posJ;
		this.initPosX = posX;
		this.selected = false;
	}

	public int getId() {
		return regionId;
	}

	public void setId(int id) {
		this.regionId = id;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public void returnElementToInit(){
		this.posX = this.initPosX;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setColorInterpolation(double normalizedValue) {
		// it seems more informative to just use a gradient of a certain color
		int r = 168;
		int g = 119;
		int b = 168;
		//Debug.println("normalizedValue = " + normalizedValue);
		/*
		if (normalizedValue >= 0 && normalizedValue < 0.01){
			r = 63;
			g = 75;
			b = 86;
		}
		else if (normalizedValue >= 0.01 && normalizedValue < 0.15){
			r = 85;
			g = 77;
			b = 102;
		}
		else if (normalizedValue >= 0.15 && normalizedValue < 0.30){
			r = 127;
			g = 97;
			b = 135;
		}
		else if (normalizedValue >= 0.30 && normalizedValue < 0.48){
			r = 168;
			g = 119;
			b = 168;
		}
		else if (normalizedValue >= 0.48 && normalizedValue < 0.65){
			r = 211;
			g = 139;
			b = 202;
		}
		else if (normalizedValue >= 0.65) {
			r = 254;
			g = 161;
			b = 235;
		}
		this.color = new Color(r, g, b);*/
		this.color = new Color(r, g, b, (int) (normalizedValue * 255));
	}

	public double normalizeValue(double value, double min, double max){
		double normalValue = (value - min) / (max - min);
		return normalValue;
	}
	
	public void changeStatusToOn(){
		this.status = "ON";
	}
	public void changeStatusToOff(){
		this.status = "OFF";
	}
	public String getStatus(){
		return this.status;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
