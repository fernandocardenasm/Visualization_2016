package infovis.heatmap;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import infovis.debug.Debug;

import java.awt.Color;
import java.awt.Font;

public class View extends JPanel {

	private Model model = null;
    private Rectangle2D labelRectangle = new Rectangle2D.Double(0,0,0,0);
    private Color color = Color.BLACK;
    
    private int plotSizeWidth = 60; // edge size for each scatter plot cell
    private int plotSizeHeight = 30; // edge size for each scatter plot cell
    
    //Please, upgrade these two values if needed
	 private int offSetX = 50; // offset at the beginning of X axis
	 private int offSetY = 30; // offset at the beginning of Y axis
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.scale(1, 1);
		
		//int minPeople = model.getYears().get(0).getRanges()
		
		int x = 10;
		int y = 20 + plotSizeHeight;
		int cont = 0;
		
		g2D.clearRect(0, 0, getWidth(), getHeight());
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		
		x = 10;
		y = 20;
        
		//Probably the labels should be part of the Model, not from the Class YearData
		int numRows = Constants.mainNamesRow.length;
		int numColumns = Constants.mainNamesCol.length;
		
		Debug.print(model.getYears().get(0).getList().get(1).getValue(0) + ": AA");
		Debug.print(model.getYears().get(0).getList().get(0).getValue(0) + ": BB");
		
		y = 20;
		
		for (int i = 0; i < numRows; i++){
			String text = Constants.mainNamesRow[i];
			TextPlot textLabel = new TextPlot(text, x, y + 20 + i * plotSizeHeight);
			g2D.drawString(textLabel.getTextLabel(), textLabel.getPosX(), textLabel.getPosY());
		}
		
		x += plotSizeWidth + 30;
		
		for (int j = 0; j < numColumns; j++){
			
			for (int i = 0; i < numRows; i++) {
				
				
				CellPlot cell = new CellPlot(i, j, x, y + i*plotSizeHeight, true);
		        labelRectangle.setRect(cell.getPosX(), cell.getPosY(), plotSizeWidth, plotSizeHeight);

				
				int currentPos = 0;
				if (j == 0){
					currentPos = 0;
				}
				else if (j == 1) {
					currentPos = 21;
				}
				else if (j == 2) {
					currentPos = 33;
				}
				
				double min = model.getYears().get(0).getRanges().get(currentPos).getMin();
				double max = model.getYears().get(0).getRanges().get(currentPos).getMax();
				double value = model.getYears().get(0).getList().get(i).getValue(currentPos);
				
				double normalizedValue = cell.normalizeValue(value, min, max);
				
				cell.setColorInterpolation(normalizedValue);
				
				g2D.setColor(cell.getColor());
		        g2D.fill(labelRectangle);
		        g2D.draw(labelRectangle);

			}
			
			g2D.setColor(color);
			
			//String text = model.getYears().get(0).getLabels().get(j);
			//TextPlot textLabel = new TextPlot(text, x, y + 20 + numRows * plotSizeHeight);
			
			
			String text = Constants.mainNamesCol[j];
			
			TextPlot textLabel = new TextPlot(text, x, y + 20 + numRows * plotSizeHeight);

			g2D.drawString(textLabel.getTextLabel(), textLabel.getPosX(), textLabel.getPosY());
			
			x += plotSizeWidth;
			y = 20;
		}
		
		x += 10;
		
		
		
		
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
