package infovis.paracoords;

import infovis.debug.Debug;
import infovis.scatterplot.Model;
import infovis.scatterplot.RectanglePlot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;
	private boolean initAxes = false;
	private boolean initLines = false;
	private Color lineColor = Color.BLACK;
	private Color color = Color.BLACK;
    private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		int bottom = 620;
		int top = 20;
		int offsetX = 100;
		int correctOffsetX = 70;
		int i = 0;
		int labelOffset = 40;
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		offsetX = getWidth()/(model.getDim() + 1);
		// reactive initAxes if the window size changes!
		g2D.setColor(this.color);
		g2D.setStroke(new BasicStroke(2.0f));
		
		for(Axis a : model.getAxes()) {
			if (!initAxes) {
				a.setPosition(offsetX + i*offsetX);
				a.setTop(top);
				a.setBottom(bottom);
			}
			g2D.drawLine(a.getPosition(), a.getTop(), a.getPosition(), a.getBottom());
			// Axis max value
			g2D.drawString(model.getRanges().get(i).getMax() + "", a.getPosition() - 10, a.getTop() - 10);
			// Axis min value
			g2D.drawString(model.getRanges().get(i).getMin() + "", a.getPosition() - 10, a.getBottom() + 20);
			if (model.getDim() > 8) {
				if ((i % 2) == 0) {
					labelOffset = 40;
				} else {
					labelOffset = 60;
				}
			}
			// Axis name
			g2D.drawString(model.getLabels().get(i).toString() + "", a.getPosition() - 10, a.getBottom() + labelOffset);
			i++;
		}
		initAxes = true; // to avoid redrawing it wrongly if they are moved by mouse

		g2D.setStroke(new BasicStroke(0.5f));
		if (!initLines) {
			addLines();
			initLines = true; // to avoid redrawing it wrongly if they are moved by mouse
		}
		
		// draw data line
		for(LinePlot l : model.getLines()) {
			
			for(int j = 0; j < l.getList().size() - 1; j++){
				PointPlot p = l.getList().get(j);
				PointPlot pNext = l.getList().get(j + 1);
				
				// selected lines are drawn green
				if (l.getStatus() == "ON"){
					lineColor = Color.GREEN;
				} else {
					// otherwise black
					lineColor = Color.BLACK;
				}
				g2D.setColor(lineColor);
				g2D.drawLine(p.getPx(), p.getPy(), pNext.getPx(), pNext.getPy());
			}
		}
		
		// selection rectangle
		g2D.setColor(Color.BLUE);
		g2D.draw(markerRectangle);
			
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
	 
	 public Rectangle2D getMarkerRectangle() {
		return markerRectangle;
	 }
	
}
