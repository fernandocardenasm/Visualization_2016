package infovis.heatmap;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import infovis.debug.Debug;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	
	private double initPosX = 0;
	private double initPosY = 0;

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Debug.p(initPosX + "");
		
		Rectangle2D rectangleTemp = new Rectangle2D.Double(0,0,0,0);
		
		for(CellPlot cell: model.getCells()){
			
			rectangleTemp.setRect(cell.getPosX(), cell.getPosY(), view.getPlotSizeWidth(), view.getPlotSizeHeight());
			if(rectangleTemp.contains(new Point2D.Double(e.getX(), e.getY()))){
				//e.changeStatusToOn();
				
				int caseAction = 0;
				int idCell = cell.idReference;
				int x = cell.getPosX();
				
				if (cell.isMain && cell.getStatus().equals("OFF")){
					caseAction = 0;
				}
				else if(cell.getStatus().equals("ON")){
					caseAction = 1;
				}
				
				
				
				for (CellPlot inCell:model.getCells()){
					
					if (inCell.idReference == idCell && inCell.isMain){
						inCell.changeStatusToOn();
					}
					
					if ((caseAction == 0 && inCell.idReference == idCell && inCell.isMain == false)){
						inCell.posX = x + (inCell.posJ) * view.getPlotSizeWidth();
						Debug.p("J: " + inCell.posJ + " Id: " + inCell.idReference + " IsMain: " + inCell.isMain);
						inCell.changeStatusToOn();
					}
					
					if (caseAction == 0 && inCell.idReference > idCell && inCell.getStatus().equals("ON")){
						//inCell.posX = x + (inCell.posJ) * view.getPlotSizeWidth();
						//Debug.p("J: " + inCell.posJ);
					}
					
					else if(caseAction == 1 && inCell.idReference == idCell){
						inCell.changeStatusToOff();
					}
					
				}
				
				for (TextPlot label:model.getColumnLabels()){
					if ((caseAction == 0 && label.getIdReference() == idCell && label.isMain == false) || (label.getIdReference() > idCell)){
						label.setPosX(x + (label.posJ) * view.getPlotSizeWidth());
						label.changeStatusToOn();
					}
					else if(caseAction == 1 && label.getIdReference() == idCell){
						label.changeStatusToOff();
					}
				}
				
				
			}
			else {
				//e.changeStatusToOff();
				
			}
			
		}
		
		view.repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
