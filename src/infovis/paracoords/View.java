package infovis.paracoords;

import infovis.scatterplot.Model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;
	private boolean initAxes = false;

	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		int bottom = 620;
		int top = 20;
		int offsetX = 100;
		int i = 0;
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		offsetX = getWidth()/(model.getDim() + 1);
		// reactive initAxes if the window size changes!
		
		for(Axis a : model.getAxes()) {
			if (!initAxes) {
				a.setPosition(offsetX + i*offsetX);
				initAxes = true; // to avoid redrawing it wrongly if they are moved by mouse
			}
			g2D.drawLine(a.getPosition(), top, a.getPosition(), bottom);
			i++;
		}
		
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
