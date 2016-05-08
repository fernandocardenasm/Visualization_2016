package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.scatterplot.RectanglePlot;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import infovis.debug.Debug;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		// maybe it'd be better if this returns a boolean, and repaint() only if it returns true
		model.pointInTheLines(e.getX(), e.getY());
		view.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		Debug.p("mouseReleased");
		for (LinePlot l : model.getLines()) {
			l.changeStatusToOff();
		}
		view.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		// is it ok that several lines can get selected in one dragging action?
		model.pointInTheLines(e.getX(), e.getY());
		//Debug.p("Touched point: " + e.getX() + ", " + e.getY());
		view.repaint();
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
