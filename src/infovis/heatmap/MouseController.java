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
					caseAction = 1;
					idCell = cell.idReference;
					x = cell.getPosX();
					//Debug.p("idColumns: " + cell.idReference + " isMain: " + cell.isMain + " I: " + cell.regionId + "J: " + cell.posJ);

				}
				else if(cell.getStatus().equals("ON")){
					caseAction = 2;
				}
				
				
				int lastPosX = x;
				int lastJ = 0;
				
				for (CellPlot inCell:model.getCells()){
					
					//Debug.p("idColumns: " + inCell.idReference + " isMain: " + inCell.isMain + " I: " + inCell.regionId + "J: " + inCell.posJ + " case: " + caseAction);
					
					
					if (inCell.idReference == idCell && inCell.isMain){
						inCell.changeStatusToOn();
					}
					
					if ((caseAction == 1 && inCell.idReference == idCell && inCell.isMain == false)){
						
						if (inCell.regionId == 0){
							lastPosX += view.getPlotSizeWidth();
						}
						
						Debug.p("idColumn: " + inCell.idReference + " posX: " + lastPosX + " posJ: " + inCell.posJ + " I: " + inCell.regionId + " initPos: " +x);
						
						inCell.posX = lastPosX;
						//Debug.p("J: " + inCell.posJ + " Id: " + inCell.idReference + " IsMain: " + inCell.isMain);
						inCell.changeStatusToOn();
						
						//Debug.p("idColumn: " + inCell.idReference + " posX: " + inCell.posX + " posJ: " + inCell.posJ + " initPos: " +x);
						
						model.getColumnLabels().get(inCell.posJ).changeStatusToOn();
						model.getColumnLabels().get(inCell.posJ).posX = lastPosX;

					}
					
					/*int lastPosX0 = 0;
					int lastPosJ0 = 0;
					int lastPosX1 = 0;
					int lastPosJ1 = 0;
					
					if (inCell.regionId == 0 && inCell.idReference == 0){
						lastPosX0 = inCell.posX + view.getPlotSizeWidth();
						lastPosJ0 = inCell.posJ;
					}
					
					if (inCell.regionId == 0 && inCell.idReference == 1){
						lastPosX1 = inCell.posX + view.getPlotSizeWidth();
						lastPosJ1 = inCell.posJ;
					}
					*/
					
					
					if (caseAction == 1 && ((inCell.idReference > idCell && inCell.getStatus().equals("ON")) || inCell.isMain)){
						//inCell.posX = x + (inCell.posJ) * view.getPlotSizeWidth();
						//Debug.p("J: " + inCell.posJ);
						
						/*
						if (inCell.posJ - 1 == lastPosJ0 && inCell.idReference == 1){
							
							if (inCell.regionId == 0){
								lastPosJ0 = inCell.posJ;
								lastPosX0 += view.getPlotSizeWidth();
							}
							
							inCell.posX = lastPosX0;
							
							model.getColumnLabels().get(inCell.posJ).changeStatusToOn();
							model.getColumnLabels().get(inCell.posJ).posX = lastPosX;
						}
						
						if (inCell.posJ - 1 == lastPosJ1 && inCell.idReference == 2){
							
							if (inCell.regionId == 0){
								lastPosJ1 = inCell.posJ;
								lastPosX1 += view.getPlotSizeWidth();
							}
							
							inCell.posX = lastPosX1;
							
							model.getColumnLabels().get(inCell.posJ).changeStatusToOn();
							model.getColumnLabels().get(inCell.posJ).posX = lastPosX;
						}
						*/
						
					}
					
					else if(caseAction == 2 && inCell.idReference == idCell){
						inCell.changeStatusToOff();
						model.getColumnLabels().get(inCell.posJ).changeStatusToOff();

					}
					
				}
				
				/*
				for (TextPlot label:model.getColumnLabels()){
					if ((caseAction == 1 && label.getIdReference() == idCell && label.isMain == false) || (label.getIdReference() > idCell)){
						if (lastJ != label.posJ){
							lastJ = label.posJ;
							lastPosX += view.getPlotSizeWidth();
						}
						
						label.posX = lastPosX + view.getPlotSizeWidth();
						//Debug.p("J: " + inCell.posJ + " Id: " + inCell.idReference + " IsMain: " + inCell.isMain);
						label.changeStatusToOn();
					}
					else if(caseAction == 2 && label.getIdReference() == idCell){
						label.changeStatusToOff();
					}
				}
				*/
				
				
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
