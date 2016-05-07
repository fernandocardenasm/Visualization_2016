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
	
	
}
