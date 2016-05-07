package infovis.paracoords;

import java.util.ArrayList;

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
	
	/*
	public boolean pointIsInTheLine(int mx1, int my1){
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
