package infovis.heatmap;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import infovis.debug.Debug;
import infovis.scatterplot.RectanglePlot;

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
	 
 	 private boolean initialized = false;
 	 
 	int numRows;
	int numColumns;
	
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
		
		numRows = Constants.mainNamesRow.length;
		numColumns = model.getYears().get(0).getLabels().size();
		
		for (int i = 0; i < numRows; i++){
			String text = Constants.mainNamesRow[i];
			//TextPlot textLabel = new TextPlot(text, x, y + 20 + i * plotSizeHeight);
			g2D.drawString(text, x, y + 20 + i * plotSizeHeight);
		}
		
		x += plotSizeWidth + 30;
		
		if (!this.initialized){
			initCellsAndLabels(x, y);
			this.initialized = true;
		}
		
		for (CellPlot cell : model.getCells()) {
			
			
			
			// if element is selected, draw red
			if (cell.getStatus().equals("ON") || cell.isMain == true){
				g2D.setColor(cell.getColor());
		        labelRectangle.setRect(cell.getPosX(), cell.getPosY(), plotSizeWidth, plotSizeHeight);
		        g2D.fill(labelRectangle);
		        g2D.draw(labelRectangle);
				//Debug.p("selected id: " + e.id + " (X: " + e.posX + " Y: " + e.posY + ") status: " + e.getStatus());
			} else {
				// otherwise black
			}
			
		}
		
		g2D.setColor(color);
		
		for (TextPlot label : model.getColumnLabels()) {
			
			// if element is selected, draw red
			if (label.getStatus().equals("ON") || label.isMain == true){
				g2D.drawString(label.getTextLabel(), label.getPosX(), label.getPosY());
				//Debug.p("selected id: " + e.id + " (X: " + e.posX + " Y: " + e.posY + ") status: " + e.getStatus());
			} else {
				// otherwise black
			}
			
		}
		
	}
	
	public void initCellsAndLabels(int x, int y){
		
		Debug.p("Size: " + numColumns);
		
		for (int j = 0; j < numColumns; j++){
			
			String label = model.getYears().get(0).getLabels().get(j);
			
			boolean isMain = false;
			
			
			if (label.toString().equalsIgnoreCase("MH_EM") || label.toString().equalsIgnoreCase("MH_EW") || label.toString().equalsIgnoreCase("E_AM") || label.toString().equalsIgnoreCase("E_AW") || label.toString().equalsIgnoreCase("E_EM") || label.toString().equalsIgnoreCase("E_EW")){
				//Ignore these cases
			}
			else {
				if (label.toString().equalsIgnoreCase("MH_E") || label.toString().equalsIgnoreCase("E_A") || label.toString().equalsIgnoreCase("E_E")){
					Debug.p("label: " + label);
					isMain = true;
				}
				else {
					isMain = false;
				}
				
					
				for (int i = 0; i < numRows; i++) {
					
					//Ignore label related to Men and Women
					
					
					CellPlot cell = new CellPlot(i, j, x, y + i*plotSizeHeight, isMain);
					
					double min = model.getYears().get(0).getRanges().get(j).getMin();
					double max = model.getYears().get(0).getRanges().get(j).getMax();
					double value = model.getYears().get(0).getList().get(i).getValue(j);
					
					double normalizedValue = cell.normalizeValue(value, min, max);
					
					cell.setColorInterpolation(normalizedValue);
					
					model.addCell(cell);
					
				}

				
				TextPlot textLabel = new TextPlot(label, x, y + 20 + numRows * plotSizeHeight, isMain);
				
				model.addTextLabel(textLabel);

				x += plotSizeWidth;
				y = 20;
			}

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
