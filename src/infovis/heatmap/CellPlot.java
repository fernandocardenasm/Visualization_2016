package infovis.heatmap;

import java.awt.Color;

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
	    this.color = new Color(0,0,255, (int)(normalizedValue * 255));
	}

	public double normalizeValue(double value, double min, double max){
		double normalValue = (value - min) / (max - min);
		
		return normalValue;
	}
	
	// element position value on X axis in the screen
	public void calculatePositionX(double value, int offset, int plotSize, int posLabel, double min, double max) {
		double x;
		if (value >= max){
			x = 0.99;
		}
		else {
			x = (value - min) / (max - min);
		}
		// posLabel = posLabel + 1; // to start in 1
		
		//this.posX = (int) (x * plotSize * posLabel + offset);
		this.posX = (int) (x * plotSize + offset);
		//y + i*plotSize
	}
	
	// element position value on Y axis in the screen
	public void calculatePositionY(double value, int offset, int plotSize, int posLabel, double min, double max) {
		double y;
		if (value >= max){
			y = 0.99;
		}
		else {
			y = (value - min) / (max - min);
		}
		//posLabel = posLabel + 1;
		//this.posY = (int) (y * plotSize * posLabel + offset); 
		this.posY = (int) (y * plotSize + offset); 
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
