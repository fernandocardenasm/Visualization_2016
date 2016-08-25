package infovis.heatmap;

import java.awt.Shape;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import infovis.debug.Debug;

public class Model {
	List<Shape> list = new ArrayList<Shape>();
	private ArrayList<String> labels = new ArrayList<String>();
	
	public Model(){
		readFile();
	}
	
	private void readFile(){
		File file = new File("berlin2015.csv");
	    Debug.p(file.getAbsoluteFile().toString());
	    
	    try {
	    	String thisLine = null;
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	
	    	thisLine = br.readLine();
			String l [] = thisLine.split(";");
			
			// file header: variable names
			for (int i = 0; i < l.length; i++) labels.add(l[i]);
			// probably should take out the " characters
			
			for (String label : labels) {
				Debug.print(label);
				Debug.print(",  ");
				Debug.println("");
			}
			 	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addShape(Shape s){
		list.add(s);
	}
	public void removeShape(Shape s){
		list.remove(s);
	}
	public Iterator<Shape> getIterator(){
		return list.iterator();
	}

}
