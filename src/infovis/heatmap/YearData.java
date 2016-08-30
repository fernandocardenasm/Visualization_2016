/**
 * Class that stores all Heatmap data related to a specific calendar year
 */
package infovis.heatmap;

import java.util.ArrayList;

import infovis.debug.Debug;
import infovis.scatterplot.Data;
import infovis.scatterplot.Range;

public class YearData {

	private int year;
	private ArrayList<Data> list  = new ArrayList<Data>();
	private ArrayList<Range> ranges = new ArrayList<Range>();
	private ArrayList<String> labels = new ArrayList<String>();
	
	public YearData(int year) {
		super();
		this.year = year;
	}
	
	public void printLabels(){
		Debug.println("YEAR " + year);
		
		Debug.println("Labels: ");
		for (String label : labels) {
			Debug.print(label);
			Debug.print(",  ");
		}
		Debug.println("");
		
		Debug.println("Ranges: ");
		for (Range range : ranges) {
			Debug.print(range.toString());
			Debug.print(",  ");
		}
		/*Debug.println("");
		
		Debug.println("Data: ");
		for (Data d : list) {
			Debug.print(d.toString());
			Debug.println("");
		}*/
	}
	
	/*
	 * Getters and setters
	 */
	
	
	
	public int getYear() {
		return year;
	}
	
	public ArrayList<Data> getList() {
		return list;
	}

	public void setList(ArrayList<Data> list) {
		this.list = list;
	}

	public ArrayList<Range> getRanges() {
		return ranges;
	}

	public void setRanges(ArrayList<Range> ranges) {
		this.ranges = ranges;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void addData(Data d) {
		this.list.add(d);
	}
	
	public void addRange(Range r) {
		this.ranges.add(r);
	}
	
	public ArrayList<String> getLabels() {
		return labels;
	}
	
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}
}
