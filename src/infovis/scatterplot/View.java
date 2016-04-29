package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	 	 private Color color = Color.BLACK;
	 	 private int plotSize = 90;
	 	 //Please, upgrade this two values if needed
	 	 private int offSetX = 50; //Offset for the rectangles, I am not sure about the exact value
	 	 private int offSetY = 30; //Offset for the rectangles, I am not sure about the exact value
	 	 
		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {
			
			Graphics2D g2D = (Graphics2D) g;
			int x = 10;
			int y = 20 + plotSize;
			int aux = - plotSize - 30;
			int cont = 0;
			g2D.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			g2D.setColor(this.color);
			g2D.rotate(-Math.toRadians(90));
			for (String l : model.getLabels()) {
				if (cont == 6){
					aux = aux -20;
				}
				Debug.print(l);
				Debug.print(",  ");
				Debug.println("");
				
				g2D.drawString(l, aux, 30);
				//y = y + plotSize;
				aux = aux - plotSize;
				cont++;
				if (cont == 5){
					aux = aux + 20;
				}
			}
			g2D.rotate(Math.toRadians(90));
			x = x + 40;
			y = 20;
			
	        for (String l : model.getLabels()) {
				Debug.print(l);
				Debug.print(",  ");
				Debug.println("");
				
				g2D.drawString(l, x, y);
				y = y + 10;
				
				//markerRectangle.setRect(x, y + 10, 50, 50);
				//g2D.draw(markerRectangle);
				
				for (int i = 0; i < model.getDim(); i++) {
					markerRectangle.setRect(x, y + i*plotSize, plotSize, plotSize);
					g2D.draw(markerRectangle);
				}
				
				x = x + plotSize;
				y = 20;
			}
			for (Range range : model.getRanges()) {
				//Debug.print(range.toString());
				//Debug.print(",  ");
				//Debug.println("");
			}
			for (Data d : model.getList()) {
				//Debug.print(d.toString());
				//Debug.println(d.toString() + "");
				//Debug.p(d.getValue(0) + "");
			}
			
			addRectangles();
			
			for (RectanglePlot e : model.getRectangles()) {
				//Debug.p("id: "+e.id);
				//Debug.print(" X: " + e.posX);
				//Debug.print(" Y: "+ e.posY);
				if (e.status == "OFF"){
					//The color should be Black
				}
				else {
					//The color should be Red
				}
				markerRectangle.setRect(e.posX, e.posY, 5, 5);
				g2D.draw(markerRectangle);
			}
			
	        
			
		}
		public void setModel(Model model) {
			this.model = model;
		}
		
		//Add the rectangles that will be plotted in the Scatterplot
		public void addRectangles(){
			double minX = 0, maxX = 0;
			double minY = 0, maxY = 0;
			double value;
			for (int i = 0; i < model.getLabels().size(); i++){
				minX = model.getRanges().get(i).getMin();
				maxX = model.getRanges().get(i).getMax();
				
				for (int j = 0; j < model.getLabels().size(); j++){
					minY = model.getRanges().get(j).getMin();
					maxY = model.getRanges().get(j).getMax();
					
					for(int k = 0; k < model.getList().size(); k++){
						//Get specific value
						
						value = model.getList().get(k).getValue(i);
						RectanglePlot rec = new RectanglePlot(k);
						
						rec.calculatePositionX(value, offSetX, plotSize, i, minX, maxX);
						
						value = model.getList().get(k).getValue(j);
						rec.calculatePositionY(value, offSetY, plotSize, j, minY, maxY);
						
						model.addRectangle(rec);

					}
				}

			}
		}
		
		
		
}
