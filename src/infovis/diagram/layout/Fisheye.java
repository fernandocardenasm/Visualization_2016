package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

import java.awt.List;
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
		
		double ratio =  Vertex.STD_HEIGHT / Vertex.STD_WIDTH;
		
		// TODO Auto-generated method stub
		//List vertexList = new List();
		
		for (Iterator<Vertex> iter = model.iteratorVertices(); iter.hasNext();) {
			Vertex vertex = iter.next();
			
			//PFishX
			vertex.setX(vertex.transformF1(vertex.getX(), view.getpFocusX(), view.getWidth(), d));
			//PFishY
			vertex.setY(vertex.transformF1(vertex.getY(), view.getpFocusY(), view.getHeight(), d));
			//SGeomX
			vertex.setWidth(vertex.transformSize(vertex.getX(), vertex.getY(), view.getpFocusX(), view.getpFocusY(), view.getWidth(), view.getHeight(), d));
			//SGeomY: It multiplies for the variable ratio, to make it smaller than the width
			vertex.setHeight(ratio * vertex.transformSize(vertex.getX(), vertex.getY(), view.getpFocusX(), view.getpFocusY(), view.getWidth(), view.getHeight(), d));

		}
		
		return model;
		
		
	}
	
public Model transform(Model model, View view, java.util.List<Vertex> firstVertexes) {
		
		double d = 5;
		
		double ratio =  Vertex.STD_HEIGHT / Vertex.STD_WIDTH;
		
		// TODO Auto-generated method stub
		//List vertexList = new List();
		
		for (Iterator<Vertex> iter = firstVertexes.iterator(); iter.hasNext();) {
			Vertex vertex = iter.next();
			//PFishX
			vertex.setX(vertex.transformF1(vertex.getX(), view.getpFocusX(), view.getWidth(), d));
			//PFishY
			vertex.setY(vertex.transformF1(vertex.getY(), view.getpFocusY(), view.getHeight(), d));
			//SGeomX
			vertex.setWidth(vertex.transformSize(vertex.getX(), vertex.getY(), view.getpFocusX(), view.getpFocusY(), view.getWidth(), view.getHeight(), d));
			//SGeomY: It multiplies for the variable ratio, to make it smaller than the width
			vertex.setHeight(ratio * vertex.transformSize(vertex.getX(), vertex.getY(), view.getpFocusX(), view.getpFocusY(), view.getWidth(), view.getHeight(), d));

		}
		
		return model;
		
		
	}
	
	
	
}

