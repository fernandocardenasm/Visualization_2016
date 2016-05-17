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
	private double d = 7.0; // distortion factor
	private double sNormX = Vertex.STD_WIDTH;
	private double sNormY = Vertex.STD_HEIGHT;
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
			
			double pNormX = vertex.getX();
			double pNormY = vertex.getY();
			
			//PFishX
			double fishX = this.transformF1(pNormX, this.focusX, view.getWidth());
			//PFishY
			double fishY = this.transformF1(pNormY, this.focusY, view.getHeight());
			
			vertex.setX(fishX);
			vertex.setY(fishY);
			//SGeomX
			vertex.setWidth(this.transformSizeW(pNormX, pNormY, focusX, focusY, view.getWidth(), view.getHeight(), fishX, fishY));
			//SGeomY: It multiplies for the variable ratio, to make it smaller than the width
			vertex.setHeight(this.transformSizeH(pNormX, pNormY, focusX, focusY, view.getWidth(), view.getHeight(), fishX, fishY));

		}
		// delete previous vertices
		model.clearVertices();
				
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
	
	//Return the new size of the object.
	public double transformSizeW(double pNormX, double pNormY, double pFocusX, double pFocusY, double pBoundaryX, double pBoundaryY, double fishX, double fishY){
		double qNormX = qNorm(pNormX, sNormX);
		double qFishX = transformF1 (qNormX, this.focusX, pBoundaryX);
		double qNormY = qNorm(pNormY, sNormY);
		double qFishY = transformF1 (qNormY, this.focusY, pBoundaryY);
		
		double sGeom = sGeomW(fishX, fishY, qFishX, qFishY);
		
		return sGeom;
	}
	
	public double transformSizeH(double pNormX, double pNormY, double pFocusX, double pFocusY, double pBoundaryX, double pBoundaryY, double fishX, double fishY){
		double qNormX = qNorm(pNormX, sNormX);
		double qFishX = transformF1 (qNormX, this.focusX, pBoundaryX);
		double qNormY = qNorm(pNormY, sNormY);
		double qFishY = transformF1 (qNormY, this.focusY, pBoundaryY);
		
		double sGeom = sGeomH(fishX, fishY, qFishX, qFishY);
		
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
	
	private double sGeomW(double pFishX, double pFishY, double qFishX, double qFishY){
		return 2 * Math.min(Math.abs(qFishX - pFishX), Math.abs(qFishY - pFishY));
	}
	private double sGeomH(double pFishX, double pFishY, double qFishX, double qFishY){
		return 2 * ratio * Math.min(Math.abs(qFishX - pFishX), Math.abs(qFishY - pFishY));
	}
	
}

