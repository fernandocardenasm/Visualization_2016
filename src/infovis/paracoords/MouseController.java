package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.scatterplot.RectanglePlot;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.Iterator;

import infovis.debug.Debug;
import infovis.diagram.elements.Vertex;

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
		if (model.pointInTheLines(e.getX(), e.getY())) {
			// only repaint if line selected
			view.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		//Debug.p("mouseReleased");
		if (!model.isLineSelected()) {
			return;
		}
		for (Iterator<LinePlot> iter = model.getLines().iterator(); iter.hasNext();){
			iter.next().changeStatusToOff();
		}
		model.setLineSelected(false);
		view.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		// is it ok that several lines can get selected in one dragging action?
		//Debug.p("Touched point: " + e.getX() + ", " + e.getY());
		 if (model.pointInTheLines(e.getX(), e.getY())) {
			 // only repaint if line selected
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
