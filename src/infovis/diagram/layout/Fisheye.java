package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * 
 */

public class Fisheye implements Layout{

	public void setMouseCoords(int x, int y, View view) {
		// TODO Auto-generated method stub
	}

	public Model transform(Model model, View view) {
		
		double d = 5;
		
		double ratio = Vertex.STD_WIDTH / Vertex.STD_HEIGHT;
		
		// TODO Auto-generated method stub
		ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
		
		for (Iterator<Vertex> iter = model.iteratorVertices(); iter.hasNext();) {
			Vertex vertex = iter.next();
			
			vertex.setX(vertex.transformF1(vertex.getX(), view.getpFocusX(), view.getWidth(), d));
			vertex.setY(vertex.transformF1(vertex.getY(), view.getpFocusY(), view.getHeight(), d));
			//vertex.setWidth(vertex.transformF1(vertex.getX(), view.getpFocusX(), view.getWidth(), d));
			//vertex.setHeight(vertex.transformF1(vertex.getY(), view.getpFocusY(), view.getHeight(), d));

		}
		
		//Vertex vertex = iter.next();
		
		
		return null;
	}
	
	
	
}
