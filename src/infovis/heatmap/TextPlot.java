package infovis.heatmap;

public class TextPlot {
	private String textLabel; //It is not unique, it is used to know which RectanglePlot belongs to a car object
	private int posX; //It is X real position on Screen
	private int posY;
	public TextPlot(String textLabel, int posX, int posY) {
		super();
		this.textLabel = textLabel;
		this.posX = posX;
		this.posY = posY;
	}
	public String getTextLabel() {
		return textLabel;
	}
	public void setTextLabel(String textLabel) {
		this.textLabel = textLabel;
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
	
	
	
}
