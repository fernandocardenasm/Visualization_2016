package infovis.paracoords;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import infovis.debug.Debug;

public class LinePlot {
	private int id;
	private String status;
	private ArrayList<PointPlot> list;
	
	
	public LinePlot(int id, ArrayList<PointPlot> list) {
		super();
		this.id = id;
		this.list = list;
		this.status = "OFF";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<PointPlot> getList() {
		return list;
	}
	public void setList(ArrayList<PointPlot> list) {
		this.list = list;
	}
	
	public boolean lineContainsPoint(int mx, int my){
		
		double distance = 0.0;
		for (int i = 0; i < this.list.size() - 1; i++){
			PointPlot p = this.list.get(i);
			PointPlot pNext = this.list.get(i + 1);
	
			distance = Line2D.ptSegDist(p.getPx(), p.getPy(), pNext.getPx(), pNext.getPy(), mx, my);

			if (distance < 1.0) {
				//Debug.p("Distance between point and line:" + distance);
				return true;
			}
		}
		return false;
		
	}
	
	// check if the line intersects the marker rectangle
	public boolean lineInRectangle(Rectangle2D markerRectangle){
		
		for (int i = 0; i < this.list.size() - 1; i++){
			PointPlot p = this.list.get(i);
			PointPlot pNext = this.list.get(i + 1);
			
			Line2D line = new Line2D.Double(p.getPx(), p.getPy(), pNext.getPx(), pNext.getPy());
			
			if (line.intersects(markerRectangle)) {
				return true;
			}
		}
		return false;
		
	}
	
	public void changeStatusToOn(){
		this.status = "ON";
	}
	public void changeStatusToOff(){
		this.status = "OFF";
	}
	/*public boolean pointIsInTheLine(int mx1, int my1){
		for (int i = 0; i < this.list.size() - 1; i++){
			PointPlot p = this.list.get(i);
			PointPlot pNext = this.list.get(i + 1);
			
			int d1 = distance(p.getPx(), p.getPy(), mx1, my1);
			int d2 = distance(pNext.getPx(), pNext.getPy(), mx1, my1);
			int d3 = distance(p.getPx(), p.getPy(), pNext.getPx(), pNext.getPy());
			
			if (d1 + d2 == d3){
				return true;
			}
		}
		return false;
	}
	public int distance(int x1, int y1, int x2, int y2){
		return (int) Math.sqrt((Math.pow(x1 + x2, 2) + Math.pow(y1 + y2, 2)));
	}
	*/
	
}
