package infovis.heatmap;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import java.awt.Color;

public class View extends JPanel {

	private Model model = null;
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.scale(1, 1);
        
        Rectangle2D rect = new Rectangle2D.Double(50, 50, 50, 50);
        
        Color color = Color.GREEN;
        g2D.setColor(color);
        g2D.fill(rect);
        g2D.draw(rect);
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
