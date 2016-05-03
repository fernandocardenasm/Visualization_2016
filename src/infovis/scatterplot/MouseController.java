package infovis.scatterplot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import infovis.debug.Debug;

public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;
	private double initPosX = 0;
	private double initPosY = 0;

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
		//Iterator<Data> iter = model.iterator();
		//view.getMarkerRectangle().setRect(x,y,w,h);
		//view.repaint();
		initPosX = arg0.getX();
		initPosY = arg0.getY();
	}

	public void mouseReleased(MouseEvent arg0) {
		//Add in case we want to erase the marker rectangle
		//when the mouse is released
		view.getMarkerRectangle().setRect(0,0,0,0);
		for (RectanglePlot e : model.getRectangles()) {
			e.changeStatusToOff();
		}
		view.repaint();
	}

	public void mouseDragged(MouseEvent arg0) {
		double width = arg0.getX() - this.initPosX;
		double height = arg0.getY() - this.initPosY;
		view.getMarkerRectangle().setRect(this.initPosX, this.initPosY, width, height);
		
		for (RectanglePlot e : model.getRectangles()) {
			if(view.getMarkerRectangle().contains(new Point2D.Double(e.posX, e.posY))){
				e.changeStatusToOn();
				//Debug.p("Si");
			}
			else {
				e.changeStatusToOff();
			}
		}
		
		view.repaint();
		
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void setModel(Model model) {
		this.model  = model;	
	}

	public void setView(View view) {
		this.view  = view;
	}

}
