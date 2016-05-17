package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/*
 * 
 */

public class Fisheye implements Layout{

	private int focusX;
	private int focusY;
	private View view;
	private double d = 1.0; // distortion factor
	private double ratio =  Vertex.STD_HEIGHT / Vertex.STD_WIDTH;
	private List<Vertex>  originalVertices  =  new ArrayList<Vertex>();
	
	// Setting the focus point according to the mouse coordinates
	public void setMouseCoords(int x, int y, View view) {
		
		this.focusX = x;
		this.focusY = y;
		this.view = view;
	}

	// default transform function
	public Model transform(Model model, View view) {

		return model;
	}
	
	// called in all mouseDragged() events except the first
	public Model transform(Model model, View view, java.util.List<Vertex> firstVertices) {
		
		// transform each vertex into its fisheye version
		for (Iterator<Vertex> iter = firstVertices.iterator(); iter.hasNext();) {
			Vertex vertex = iter.next();
			//PFishX
			vertex.setX(this.transformF1(vertex.getX(), this.focusX, view.getWidth()));
			//PFishY
			vertex.setY(this.transformF1(vertex.getY(), this.focusY, view.getHeight()));
			
			//SGeomX
			//vertex.setWidth(vertex.transformSize(vertex.getX(), vertex.getY(), view.getpFocusX(), view.getpFocusY(), view.getWidth(), view.getHeight(), this.d));
			//SGeomY: It multiplies for the variable ratio, to make it smaller than the width
			//vertex.setHeight(ratio * vertex.transformSize(vertex.getX(), vertex.getY(), view.getpFocusX(), view.getpFocusY(), view.getWidth(), view.getHeight(), this.d));

		}
		// delete previous vertices
		model.clearVertices();
		if (model.isEmpty()) {
			Debug.p("empty");
		} else {
			Debug.p("full");
		}
		// add Fisheye version of the vertices
		model.addVertices(firstVertices);
		return model;
		
	}
	
	// Transforms normal position coordinates into Fisheye coordinates
	public double transformF1(double pNorm, double pFocus, double pBoundary){
		double dMax = 0;
		double dNorm = 0;
		double gx = 0;
		
		dMax = dMax(pBoundary, pFocus, pNorm);
		
		// distance between point being transformed and the focus
		dNorm = pNorm - pFocus;
		
		gx = G(dNorm/dMax);
		
		// maybe needs +/-?
		return (pFocus + gx * dMax);
	}
	
	//public double transformWidth(double pNorm, double pFocus, double pBoundary, double width, double d){
	//double qNorm = qNorm(pNorm, width);
	//double qFish = qFish(qNorm, pFocus, pBoundary,d);
	//Math.min(a, b)
	//}
	
	//Return the new size of the object.
	public double transformSize(double pNormX, double pNormY , double pFocusX, double pFocusY, double pBoundaryX, double pBoundaryY, double d){
		double qNormX = qNorm(pNormX, pBoundaryX);
		double qFishX = qFish(qNormX, pFocusX,pBoundaryX);
		double pFishX = transformF1(pNormX, pFocusX, pBoundaryX);
		
		double qNormY = qNorm(pNormY, pBoundaryY);
		double qFishY = qFish(qNormY, pFocusX,pBoundaryY);
		double pFishY = transformF1(pNormY, pFocusY, pBoundaryY);
		 
		
		double sGeom = sGeom(pFishX, pFishY, qFishX, qFishY);
		
		return sGeom;
	}
	
	private double dMax(double pBoundary, double pFocus, double pNorm){
		double dMax = 1.0;
		
		if (pNorm > pFocus){
			dMax = pBoundary - pFocus;
		}
		else{
			dMax = 0.0 - pFocus;
		}
		
		return dMax;
	}
	
	private double G(double x){
		return ((this.d + 1) * x) / (this.d * x + 1);
	}

	private double qNorm(double pNorm, double sNorm){
		return pNorm + sNorm/2;
	}
	
	private double qFish(double qNorm, double pFocus, double pBoundary){
		return transformF1 (qNorm, pFocus, pBoundary);
	}
	private double sGeom(double pFishX, double pFishY, double qFishX, double qFishY){
		return 2 * Math.min(Math.abs(qFishX - pFishX), Math.abs(qFishY - pFishY));
	}
	
}

