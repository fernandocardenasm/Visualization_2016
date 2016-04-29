package infovis.scatterplot;

import infovis.debug.Debug;

public class RectanglePlot {
	int id; //It is not unique, it is used to know which RectanglePlot belongs to a car object
	int posX; //It is X real position on Screen
	int posY;
	String status; //ON OR OFF, ON: It is selected, OFF: It is not selected
	
	public RectanglePlot(int id){
		this.id = id;
		this.status = "OFF";
	}
	
	public void calculatePositionX(double value, int offSet, int plotSize, int posLabel, double min, double max) {
		double aux = (value - min) / (max - min);
		posLabel = posLabel + 1; //If not it will start always in 0 and then it would 0
		
		this.posX = (int) (aux * plotSize * posLabel + offSet); 
	}
	public void calculatePositionY(double value, int offSet, int plotSize, int posLabel, double min, double max) {
		double aux = (value - min) / (max - min);
		posLabel = posLabel + 1;
		this.posY = (int) (aux * plotSize * posLabel + offSet); 
	}
}
