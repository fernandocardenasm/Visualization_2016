package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;


public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	 	 private Color color = Color.BLACK;
	 	 private int plotSize = 90;

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
				Debug.print(range.toString());
				Debug.print(",  ");
				Debug.println("");
			}
			for (Data d : model.getList()) {
				Debug.print(d.toString());
				Debug.println("");
			}
	        
			
		}
		public void setModel(Model model) {
			this.model = model;
		}
}
