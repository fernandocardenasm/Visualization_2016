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
		//model.pointInTheLines(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		for (LinePlot l : model.getLines()) {
			l.changeStatusToOff();
		}
		view.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		/*if (model.pointInTheLines(e.getX(), e.getY()) != -1){
			Debug.p("Si");
		}
		else{
			Debug.p("No");
		}
		*/
		model.pointInTheLines(e.getX(), e.getY());
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
