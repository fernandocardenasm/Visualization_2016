package infovis.heatmap;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
	List<Shape> list = new ArrayList<Shape>();
	
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
