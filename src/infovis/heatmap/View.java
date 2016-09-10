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
		
		for(CellPlot cell: model.getCells()){
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
		
		for(TextPlot label: model.getColumnLabels()){
			if (label.getStatus().equals("ON") || label.isMain == true){
				g2D.drawString(label.getTextLabel(), label.getPosX(), label.getPosY());
				//Debug.p("selected id: " + e.id + " (X: " + e.posX + " Y: " + e.posY + ") status: " + e.getStatus());
			} else {
				// otherwise black
			}
		}
		
	}
	
	public void initCellsAndLabels(int x, int y){
		
		int intJ = 0;
		
		for (int j = 0; j < numColumns; j++){
			
			String label = model.getYears().get(0).getLabels().get(j).toString();
			
			boolean isMain = false;
			
			
			if (label.equalsIgnoreCase("MH_EM") || label.equalsIgnoreCase("MH_EW") || label.equalsIgnoreCase("E_AM") || label.equalsIgnoreCase("E_AW") || label.equalsIgnoreCase("E_EM") || label.equalsIgnoreCase("E_EW")){
				//Ignore these cases
			}
			else {
				if (label.equalsIgnoreCase("MH_E") || label.equalsIgnoreCase("E_A") || label.equalsIgnoreCase("E_E")){
					Debug.p("label: " + label);
					isMain = true;
				}
				else {
					isMain = false;
				}
				
				int caseIdColumn = 0;
				
				if (label.contains("MH_")){
					caseIdColumn = 0;
				}
				else if (label.contains("E_A")){
					caseIdColumn = 1;
				}
				else {
					caseIdColumn = 2;
				}
				
				//Debug.p("case: " + caseIdColumn);
					
				for (int i = 0; i < numRows; i++) {
					
					//Ignore label related to Men and Women
					
					
					CellPlot cell = new CellPlot(i, caseIdColumn, x, y + i*plotSizeHeight, isMain, intJ);
					
					double min = model.getYears().get(0).getRanges().get(intJ).getMin();
					double max = model.getYears().get(0).getRanges().get(intJ).getMax();
					double value = model.getYears().get(0).getList().get(i).getValue(intJ);
					
					double normalizedValue = cell.normalizeValue(value, min, max);
					
					cell.setColorInterpolation(normalizedValue);
					
					model.addCell(cell);
					
				}
				
				TextPlot textLabel = new TextPlot(label, x, y + 20 + numRows * plotSizeHeight, isMain, caseIdColumn, intJ);
				
				model.addTextLabel(textLabel);
				
				if (isMain){
					x += plotSizeWidth;
					y = 20;
				}
				
				intJ++;
			}

		}
	}
	
	public int getPlotSizeWidth(){
		return plotSizeWidth;
	}
	
	public int getPlotSizeHeight(){
		return plotSizeHeight;
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
