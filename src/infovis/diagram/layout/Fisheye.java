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

	private int focusX;
	private int focusY;
	
	// Setting the focus point according to the mouse coordinates
	public void setMouseCoords(int x, int y, View view) {
		
		this.focusX = x;
		this.focusY = y;
		// do i need view also?
	}

	public Model transform(Model model, View view) {
		
		double d = 5;
		double ratio =  Vertex.STD_HEIGHT / Vertex.STD_WIDTH;
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
	
	// Transforms normal position coordinates into Fisheye coordinates
	public double transformF1(double pNorm, double pFocus, double pBoundary, double d){
		double dMax = 0;
		double dNorm = 0;
		double gx = 0;
		
		dMax = dMax(pBoundary, pFocus, pNorm);
		// distance between point being transformed and the focus
		dNorm = Math.abs(pNorm - pFocus);
		
		gx = GX(dNorm/dMax, d);
		
		return pFocus + gx * dMax;
	}
	
	private double dMax(double pBoundary, double pFocus, double pNorm){
		double dMax = 1;
		if (pNorm > pFocus){
			dMax = pBoundary - pFocus;
		}
		else{
			dMax = 0 - pFocus;
		}
		
		return dMax;
	}
	
	private double GX(double x, double d){
		return (d + 1) * x / (d * x + 1);
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

