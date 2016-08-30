package infovis.heatmap;

import java.awt.Color;

public class CellPlot {
	String regionId; //It is not unique, it is used to know which RectanglePlot belongs to a car object
	int posX; //It is X real position on Screen
	int posY;
	private String status; //ON OR OFF, ON: It is selected, OFF: It is not selected
	private Color color;
	
	public CellPlot(String regionId, int posX, int posY) {
		super();
		this.regionId = regionId;
		this.posX = posX;
		this.posY = posY;
		this.color = Color.black;
		this.status = "OFF";
		
	}

	public String getId() {
		return regionId;
	}

	public void setId(String id) {
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
}
