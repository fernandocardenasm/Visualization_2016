package infovis.heatmap;

public class TextPlot {
	private String textLabel; //It is not unique, it is used to know which RectanglePlot belongs to a car object
	int posX; //It is X real position on Screen
	private int posY;
	int posJ;
	private int idReference;
	private String status;
	boolean isMain; //Only our 3 main references are the main ones. "True" for them.
	
	public TextPlot(String textLabel, int posX, int posY, boolean isMain, int idReference, int posJ) {
		super();
		this.textLabel = textLabel;
		this.posX = posX;
		this.posY = posY;
		this.status = "OFF";
		this.isMain = isMain;
		this.idReference = idReference;
		this.posJ = posJ;
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
	
	public void changeStatusToOn(){
		this.status = "ON";
	}
	public void changeStatusToOff(){
		this.status = "OFF";
	}
	public String getStatus(){
		return this.status;
	}
	public int getIdReference() {
		return idReference;
	}
	public void setIdReference(int idReference) {
		this.idReference = idReference;
	}
	public boolean isMain() {
		return isMain;
	}
	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	
}
