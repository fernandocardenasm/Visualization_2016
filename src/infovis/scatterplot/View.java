package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	     private Rectangle2D rectangle = new Rectangle2D.Double(0,0,0,0);
	     private Rectangle2D labelRectangle = new Rectangle2D.Double(0,0,0,0);
	 	 private Color color = Color.BLACK;
	 	 private Color elementColor = Color.BLACK;
	 	 private int plotSize = 90; // edge size for each scatter plot cell
	 	 //Please, upgrade these two values if needed
	 	 private int offSetX = 50; //Offset for the rectangles, I am not sure about the exact value
	 	 private int offSetY = 30; //Offset for the rectangles, I am not sure about the exact value
	 	 
	 	 private boolean initialized = false;
	 	 
		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		 }
		 public Rectangle2D getRectanglePlot() {
				return rectangle;
			 }
		 
		@Override
		public void paint(Graphics g) {
			
			Graphics2D g2D = (Graphics2D) g;
			int x = 10;
			int y = 20 + plotSize;
			int aux = - plotSize - 30;
			int cont = 0;
			
			g2D.clearRect(0, 0, getWidth(), getHeight());
			
			// Writing labels
			g2D.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			g2D.setColor(this.color);
			g2D.rotate(-Math.toRadians(90));
			for (String l : model.getLabels()) {
				if (cont == 6){
					aux = aux -20;
				}
				//Debug.print(l);
				//Debug.print(",  ");
				//Debug.println("");
				
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
				//Debug.print(l);
				//Debug.print(",  ");
				//Debug.println("");
				
				g2D.drawString(l, x, y);
				y = y + 10;
				
				//markerRectangle.setRect(x, y + 10, 50, 50);
				//g2D.draw(markerRectangle);
				// draw each scatter plot box
				for (int i = 0; i < model.getDim(); i++) {
					labelRectangle.setRect(x, y + i*plotSize, plotSize, plotSize);
					g2D.draw(labelRectangle);
				}
				
				x = x + plotSize;
				y = 20;
			}
			//for (Range range : model.getRanges()) {
				//Debug.print(range.toString());
				//Debug.print(",  ");
				//Debug.println("");
			//}
			//for (Data d : model.getList()) {
				//Debug.print(d.toString());
				//Debug.println(d.toString() + "");
				//Debug.p(d.getValue(0) + "");
			//}
			
			if (!this.initialized){
				addRectangles();
				this.initialized = true;
			}
			
			// selection rectangle
			g2D.setStroke(new BasicStroke(2.0f));
			g2D.setColor(Color.GREEN);
			g2D.draw(markerRectangle);

			//g2D.setStroke(new BasicStroke(2.0f));
			
			for (RectanglePlot e : model.getRectangles()) {
				
				// if element is selected, draw red
				if (e.getStatus() == "ON"){
					elementColor = Color.RED;
					Debug.p("selected id: " + e.id + " (X: " + e.posX + " Y: " + e.posY + ") status: " + e.getStatus());
				} else {
					// otherwise black
					elementColor = Color.BLACK;
				}
				if (elementColor == Color.RED) {
					Debug.p("Color was changed to RED! for " + e.id);
				}
				g2D.setColor(elementColor);
				rectangle.setRect(e.posX, e.posY, 3, 3);
				g2D.draw(rectangle);
			}
		}
		
		public void setModel(Model model) {
			this.model = model;
		}
		
		// Adding the rectangles that will be plotted in the scatter plot
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
