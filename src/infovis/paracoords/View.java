package infovis.paracoords;

import infovis.debug.Debug;
import infovis.scatterplot.Model;
import infovis.scatterplot.RectanglePlot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;
	private boolean initAxes = false;
	private boolean initLines = false;
	private Color lineColor = Color.BLACK;
	private Color color = Color.BLACK;

	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		int bottom = 620;
		int top = 20;
		int offsetX = 100;
		int correctOffsetX = 70;
		int i = 0;
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		offsetX = getWidth()/(model.getDim() + 1);
		// reactive initAxes if the window size changes!
		g2D.setColor(this.color);
		g2D.setStroke(new BasicStroke(2.0f));
		
		for(Axis a : model.getAxes()) {
			if (!initAxes) {
				a.setPosition(offsetX + i*offsetX - correctOffsetX);
				a.setTop(top);
				a.setBottom(bottom);
			}
			g2D.drawLine(a.getPosition(), a.getTop(), a.getPosition(), a.getBottom());
			g2D.drawString(model.getRanges().get(i).getMax() + "", a.getPosition() - 10, a.getTop() - 10);
			g2D.drawString(model.getRanges().get(i).getMin() + "", a.getPosition() - 10, a.getBottom() + 20);
			g2D.drawString(model.getLabels().get(i).toString() + "", a.getPosition() - 10, a.getBottom() + 30);
			i++;
		}
		initAxes = true; // to avoid redrawing it wrongly if they are moved by mouse

		g2D.setStroke(new BasicStroke(1.0f));
		if (!initLines) {
			addLines();
			initLines = true; // to avoid redrawing it wrongly if they are moved by mouse
		}
		
		for(LinePlot l : model.getLines()) {
			
			for(int j = 0; j < l.getList().size() - 1; j++){
				PointPlot p = l.getList().get(j);
				PointPlot pNext = l.getList().get(j + 1);
				//Debug.p("px:" + p.getPx());
				//Debug.p("py:" + p.getPy());
				
				if (l.getStatus() == "ON"){
					lineColor = Color.GREEN;
					//Debug.p("selected id: " + e.id + " (X: " + e.posX + " Y: " + e.posY + ") status: " + e.getStatus());
				} else {
					// otherwise black
					lineColor = Color.BLACK;
				}
				g2D.setColor(lineColor);
				g2D.drawLine(p.getPx(), p.getPy(), pNext.getPx(), pNext.getPy());
			}
		}
			
	}
	
	public void addLines(){
		double minY = 0, maxY = 0;
		double value;
		int dimensions = model.getDim();
		
		// for each dimension in the X axis
			// for each dimension in the Y axis
		for(int k = 0; k < model.getList().size(); k++){
			ArrayList<PointPlot> pointList = new ArrayList<PointPlot>();
			for (int j = 0; j < dimensions; j++){
				minY = model.getRanges().get(j).getMin();
				maxY = model.getRanges().get(j).getMax();
				// for each car/camera of the list
				value = model.getList().get(k).getValue(j);
					// element to plot
				PointPlot point = new PointPlot(value);
					// specific value of dimension j for Data element k
					
				Axis axis = model.getAxes().get(j);
				point.setPx(axis.getPosition());
				point.calculatePositionY(axis.getTop(), axis.getBottom(), minY, maxY);
					//rec.calculatePositionY(value, offSetY, plotSize, j, minY, maxY);
				pointList.add(j, point);;
			}
			LinePlot line = new LinePlot(k, pointList);
			model.getLines().add(line);
		}
		//posX = this.offSetX;
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
