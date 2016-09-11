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

	// Show cell value when user hovers over the cell
	@Override
	public void mouseMoved(MouseEvent arg0) {
		Rectangle2D rectangleTemp = new Rectangle2D.Double(0,0,0,0);
		
		boolean found = false;
		
		for(CellPlot cell: model.getCells()){
			rectangleTemp.setRect(cell.getPosX(), cell.getPosY(), view.getPlotSizeWidth(), view.getPlotSizeHeight());
			
			if(rectangleTemp.contains(new Point2D.Double(arg0.getX(), arg0.getY())) && (cell.getStatus() == "ON" || cell.isMain)){
				view.displayRectangle.setRect(cell.getPosX() + 10, cell.posY - view.getPlotSizeHeight() + 14, 45, 20);
				int val = (int) cell.getValue();
				view.displayText = Integer.toString(val);
				found = true;
			}
		}
		
		if (!found){
			view.displayRectangle = new Rectangle2D.Double(0,0,0,0);
			view.displayText = "";
		}
		
		view.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Debug.p(initPosX + "");
		
		Rectangle2D rectangleTemp = new Rectangle2D.Double(0,0,0,0);
		
		
		for(CellPlot cell: model.getCells()){
			
			int cont0 = 0;
			int cont1 = 0;
			
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
					idCell = cell.idReference;
					x = cell.getPosX();
				}
				
				int lastPosX = x;
				
				for (CellPlot inCell:model.getCells()){
					
					//Debug.p("idColumns: " + inCell.idReference + " isMain: " + inCell.isMain + " I: " + inCell.regionId + "J: " + inCell.posJ + " case: " + caseAction);
					
					
					if (caseAction == 1 && inCell.idReference == idCell && inCell.isMain){
						inCell.changeStatusToOn();
					}
					
					if ((caseAction == 1 && inCell.idReference == idCell && inCell.isMain == false)){
						
						if (inCell.regionId == 0){
							lastPosX += view.getPlotSizeWidth();
						}
						
						//Debug.p("idColumn: " + inCell.idReference + " posX: " + lastPosX + " posJ: " + inCell.posJ + " I: " + inCell.regionId + " initPos: " +x);
						
						inCell.posX = lastPosX;
						//Debug.p("J: " + inCell.posJ + " Id: " + inCell.idReference + " IsMain: " + inCell.isMain);
						inCell.changeStatusToOn();
						
						Debug.p("idColumn: " + inCell.idReference + " posX: " + inCell.posX + " posJ: " + inCell.posJ + " initPos: " +x);
						
						model.getColumnLabels().get(inCell.posJ).changeStatusToOn();
						model.getColumnLabels().get(inCell.posJ).posX = lastPosX;
						

					}
					
					
					if (caseAction == 1 && ((inCell.idReference > idCell && inCell.getStatus().equals("ON")) || (inCell.isMain && inCell.idReference > idCell))){
						//inCell.posX = x + (inCell.posJ) * view.getPlotSizeWidth();
						//Debug.p("J: " + inCell.posJ);
						
						if (inCell.regionId == 0){
							lastPosX += view.getPlotSizeWidth();
						}
						
						inCell.posX = lastPosX;

						//inCell.changeStatusToOn();
						//model.getColumnLabels().get(inCell.posJ).changeStatusToOn();
						
						model.getColumnLabels().get(inCell.posJ).posX = lastPosX;
						
						
					}
					
					if(caseAction == 2 && inCell.idReference == idCell){
						
						//if (inCell.regionId == 0){
							//lastPosX += view.getPlotSizeWidth();
						//}
						
						inCell.changeStatusToOff();						
						model.getColumnLabels().get(inCell.posJ).changeStatusToOff();
						
					}
					
					
					if (inCell.idReference == 0 && inCell.getStatus().equals("ON")){
						cont0++;
						//Debug.p("SW0 idR: " + inCell.idReference + " J: " + inCell.posJ + "main: " + inCell.isMain );
					}

					if (inCell.idReference == 1 && inCell.getStatus().equals("ON")){
						cont1++;
						//Debug.p("SW1 idR: " + inCell.idReference + " J: " + inCell.posJ + "main: " + inCell.isMain );
					}					
					
				}
				
				for (CellPlot visCell: model.getCells()){
					
					//Debug.p("Cont0: " + cont0);
					//Debug.p("Cont1: " + cont1);
					
					if (caseAction == 2 && idCell == 0 && visCell.idReference == 1 && cont0 == 0 && cont1 == 0){
						visCell.returnElementToInit();
						model.getColumnLabels().get(visCell.posJ).returnElementToInit();
						//Debug.p("Case0");
					}
					if (caseAction == 2 && idCell == 0 && visCell.idReference == 1 && cont0 == 0 && cont1 != 0){
						visCell.returnElementToInit();
						model.getColumnLabels().get(visCell.posJ).returnElementToInit();
						//Debug.p("Case1");
					}
					if (caseAction == 2 && idCell == 0 && visCell.idReference == 2 && cont0 == 0 && cont1 == 0){
						visCell.returnElementToInit();
						model.getColumnLabels().get(visCell.posJ).returnElementToInit();
						//Debug.p("Case2");
					}
					if (caseAction == 2 && idCell == 1 && visCell.idReference == 2 && cont0 == 0 && cont1 == 0){
						visCell.returnElementToInit();
						model.getColumnLabels().get(visCell.posJ).returnElementToInit();
						//Debug.p("Case3");
					}
					if (caseAction == 2 && idCell == 1 && visCell.idReference == 2 && cont0 !=0 && cont1 == 0){
						visCell.posX = 760;
						model.getColumnLabels().get(visCell.posJ).posX = visCell.posX;
						//Debug.p("Case5");
					}
					if (caseAction == 2 && idCell == 1 && visCell.idReference == 2 && cont0 !=0 && cont1 != 0){
						visCell.posX = 760;
						model.getColumnLabels().get(visCell.posJ).posX = visCell.posX;
						//Debug.p("Case6");
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
