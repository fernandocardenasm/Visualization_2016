package infovis.heatmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import infovis.debug.Debug;
import infovis.scatterplot.Data;
import infovis.scatterplot.Range;

public class Model {
	
	private static Model model = null;
	private ArrayList<YearData> years = new ArrayList<YearData>();
	private ArrayList<CellPlot> cells  = new ArrayList<CellPlot>();
	private ArrayList<TextPlot> labels  = new ArrayList<TextPlot>();
	private int currentYear = 0;
	private boolean ageGroupsIncluded = true;
	
	public static Model getModelInstance(){
		if(model == null){
			model = new Model();
		}
		return model;
	}
	
	public ArrayList<YearData> getYears() {
		return years;
	}
	
	public ArrayList<CellPlot> getCells() {
		return cells;
	}
	
	public void addCell(CellPlot cell){
		cells.add(cell);
	}

	public ArrayList<TextPlot> getColumnLabels() {
		return labels;
	}
	
	public void addTextLabel(TextPlot label){
		labels.add(label);
	}

	public void setYears(ArrayList<YearData> years) {
		this.years = years;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public Model(){
		readFile("EWR_2007.csv", 2007);
		// Other years
		readFile("EWR_2008.csv", 2008);
		readFile("EWR_2009.csv", 2009);
		readFile("EWR_2010.csv", 2010);
		readFile("EWR_2011.csv", 2011);
		readFile("EWR_2012.csv", 2012);
		readFile("EWR_2013.csv", 2013);
		// unfortunately, the 2014 data has inconsistencies
		readFile("EWR_2014.csv", 2014);
		readFile("EWR_2015.csv", 2015);
		
		
		Debug.println("All year data saved:");
		for (YearData year: years){
			year.printLabels();
		}
	}
	
	private void readFile(String name, int yearID){
		File file = new File(name);
	    Debug.p(file.getAbsoluteFile().toString());
	    
	    YearData year = new YearData(yearID);
	    ArrayList<String> labels = new ArrayList<String>();
	    
	    try {
	    	String thisLine = null;
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	
	    	thisLine = br.readLine();
			String l [] = thisLine.split(";");
			
			// file header: variable names
			for (int i = 0; i < l.length; i++) {
				String label = l[i].replace("\"", ""); // remove quotations marks if present
				labels.add(label);
			}
			// probably should take out the " characters when they appear or pre-modify the data files with R
			year.setLabels(labels);
			
			 int offset = 0; // BEZ Values were deleted, but it is logical ordered from 1 to 12.
			// prepare ranges
			 double lowRanges [] = new double[l.length-offset];
			 for (int i = 0; i < lowRanges.length; i++) lowRanges[i] = Double.MAX_VALUE;
			 double highRanges [] = new double[lowRanges.length];
		     for (int i = 0; i < highRanges.length; i++) highRanges[i] = Double.MIN_VALUE;
        	 
        	 // import data and adapt ranges
			 while ((thisLine = br.readLine()) != null) {
				 String values [] = thisLine.split(";"); // entire line
				 double dValues [] = new double[values.length - offset];
				 
				 // treating one line
				 for (int j = offset; j < values.length; j++) {
					 dValues[j-offset] = Double.parseDouble(values[j]);
					 if (dValues[j-offset] <  lowRanges[j-offset]) lowRanges[j-offset] = dValues[j-offset];
					 if (dValues[j-offset] >  highRanges[j-offset]) highRanges[j-offset] = dValues[j-offset];
				 }
				// each Data element represents a Planungsraum (values[1] = RAUMID)
				 year.addData(new Data(dValues, values[1]));
   			}
			 
			 for (int i = 0; i < highRanges.length; i++) {
					year.addRange(new Range(lowRanges[i], highRanges[i]));
			 }
			
			// save year data
			years.add(year);
			 	    	
		} catch (FileNotFoundException e) {
			Debug.println("This file was not found: " + name);
			e.printStackTrace();
		} catch (IOException e) {
			Debug.println("IO Exception happened while reading file: " + name);
			e.printStackTrace();
		}
	}

	public boolean isAgeGroupsIncluded() {
		return ageGroupsIncluded;
	}

	public void setAgeGroupsIncluded(boolean ageGroupsIncluded) {
		this.ageGroupsIncluded = ageGroupsIncluded;
		Debug.println("ageGroupsIncluded = " + ageGroupsIncluded);
	}

}
