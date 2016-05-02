package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	 	 private int offSetX = 50; // offset at the beginning of X axis
	 	 private int offSetY = 30; // offset at the beginning of Y axis
	 	 private int elementSize = 3; // edge size of each square element in plot
	 	 
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

			//g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
			g2D.setStroke(new BasicStroke(1.0f));
			g2D.setColor(Color.GREEN);
			g2D.draw(markerRectangle);

			//g2D.setStroke(new BasicStroke(2.0f));
			
			for (RectanglePlot e : model.getRectangles()) {
				
				// if element is selected, draw red
				if (e.getStatus() == "ON"){
					elementColor = Color.RED;
					//Debug.p("selected id: " + e.id + " (X: " + e.posX + " Y: " + e.posY + ") status: " + e.getStatus());
				} else {
					// otherwise black
					elementColor = Color.BLACK;
				}
				
				g2D.setColor(elementColor);
				rectangle.setRect(e.posX, e.posY, elementSize, elementSize);
				g2D.draw(rectangle);
				//g2D.fill(rectangle);
			}
		}
		
		public void setModel(Model model) {
			this.model = model;
		}
		
		// adding the rectangles/elements that will be plotted in the scatter plot
		public void addRectangles(){
			double minX = 0, maxX = 0;
			double minY = 0, maxY = 0;
			double value;
			int dimensions = model.getDim();
			int posX = this.offSetX; // 50
			int posY = this.offSetY; // 30
			int plotSpace = plotSize-elementSize;
			
			// for each dimension in the X axis
			for (int i = 0; i < dimensions; i++){
				minX = model.getRanges().get(i).getMin();
				maxX = model.getRanges().get(i).getMax();
				// for each dimension in the Y axis
				for (int j = 0; j < dimensions; j++){
					minY = model.getRanges().get(j).getMin();
					maxY = model.getRanges().get(j).getMax();
					// for each car/camera of the list
					for(int k = 0; k < model.getList().size(); k++){
						// element to plot
						RectanglePlot rec = new RectanglePlot(k);
						// specific value of dimension i for Data element k
						value = model.getList().get(k).getValue(i);
						//rec.calculatePositionX(value, offSetX, plotSize, i, minX, maxX);
						rec.calculatePositionX(value, posX, plotSpace, i, minX, maxX);
						// specific value of dimension j for Data element k
						value = model.getList().get(k).getValue(j);
						//rec.calculatePositionY(value, offSetY, plotSize, j, minY, maxY);
						rec.calculatePositionY(value, posY, plotSpace, j, minY, maxY);
						
						model.addRectangle(rec);
					}
					posY = posY + plotSize;
				}
				posY = this.offSetY;
				posX = posX + plotSize;
			}
			//posX = this.offSetX;
		}
		
		public int getElementSize(){
			return this.elementSize;
		}
		
}
