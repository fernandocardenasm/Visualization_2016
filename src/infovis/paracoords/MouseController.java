package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.scatterplot.RectanglePlot;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Iterator;

import infovis.debug.Debug;
import infovis.diagram.elements.Vertex;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	private double initPosX = 0;
	private double initPosY = 0;
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		// marker rectangle initial coordinates
		initPosX = e.getX();
		initPosY = e.getY();
		
		// selecting lines by clicking on them
		/*if (model.pointInTheLines(e.getX(), e.getY())) {
		// only repaint if line selected
		view.repaint();
		}*/
	}

	public void mouseReleased(MouseEvent e) {

		// erase the marker rectangle when the mouse is released
		view.getMarkerRectangle().setRect(0,0,0,0);
		
		// if no line selected, no need to turn off any or repaint
		if (!model.isLineSelected()) {
			return;
		}
		
		// deselect all lines
		for (Iterator<LinePlot> iter = model.getLines().iterator(); iter.hasNext();){
			iter.next().changeStatusToOff();
		}
		
		model.setLineSelected(false);
		view.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		double width = e.getX() - this.initPosX;
		double height = e.getY() - this.initPosY;
		boolean lineMarked = false;

		// selecting by passing the cursor close enough to any line (no rectangle)
		 /*if (model.pointInTheLines(e.getX(), e.getY())) {
			 // only repaint if line selected
			 view.repaint();
		 }*/
		
		// drawing selection rectangle in any direction
		if (width < 0 && height< 0){
			width = this.initPosX - e.getX();
			height = this.initPosY - e.getY();
			view.getMarkerRectangle().setRect(e.getX(), e.getY(), width, height);
		}
		else if (width < 0){
			width = this.initPosX - e.getX();
			view.getMarkerRectangle().setRect(e.getX(), this.initPosY, width, height);
		}
		else if (height < 0){
			height = this.initPosY - e.getY();
			view.getMarkerRectangle().setRect(this.initPosX, e.getY(), width, height);
		}
		else{
			view.getMarkerRectangle().setRect(this.initPosX, this.initPosY, width, height);
		}

		// select lines (turn on) if they intersect the marker rectangle
		for (LinePlot l : model.getLines()) {
			if (l.lineInRectangle(view.getMarkerRectangle())) {
				l.changeStatusToOn();
				lineMarked = true;
				model.setLineSelected(true);
			} else {
				l.changeStatusToOff();
			}
			
		}
		
		// only repaint if at least one line was selected
		if (lineMarked) {
			view.repaint();
		}
		
	}

	public void mouseMoved(MouseEvent e) {

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
