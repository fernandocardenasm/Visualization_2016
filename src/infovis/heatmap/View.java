package infovis.heatmap;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import infovis.debug.Debug;
import infovis.scatterplot.RectanglePlot;

import java.awt.BasicStroke;
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
	int yearSelection = 0;
	
	double min = 0;
	double max = 0;
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.scale(1, 1);
		
		//int minPeople = model.getYears().get(0).getRanges()
		
		int x = 10;
		int y = 20 + plotSizeHeight + offSetY;
		int cont = 0;
		
		g2D.clearRect(0, 0, getWidth(), getHeight());
		g2D.setFont(new Font("Arial", Font.PLAIN, 11));
		
		x = 10;
		y = 20 + offSetY;
		
		numRows = Constants.mainNamesRow.length;
		numColumns = model.getYears().get(yearSelection).getLabels().size();
		
		Debug.p("Num Columns: " + numColumns);
		
		// Writing district names
		for (int i = 0; i < numRows; i++){
			String text = Constants.mainNamesRow[i];
			//Debug.println(text); // maybe put in bold letters the selected one?
			//TextPlot textLabel = new TextPlot(text, x, y + 20 + i * plotSizeHeight);
			g2D.drawString(text, x, y + 20 + i * plotSizeHeight);
		}
		
		x += plotSizeWidth + 30;
		
		if (!this.initialized){
			initCellsAndLabels(x, y, yearSelection); // 0 indicates first year, change value for showing a different year
			this.initialized = true;
			
			drawLegend(g2D);
		}
		
		// drawing heatmap grid, each cell is a rectangle
		for(CellPlot cell: model.getCells()){
			if (cell.getStatus().equals("ON") || cell.isMain == true){
				g2D.setColor(cell.getColor());
				if (cell.isSelected()) {
					g2D.setStroke(new BasicStroke(4.0f));
				} else {
					g2D.setStroke(new BasicStroke(1.0f));
				}
				// Debug.println("columna = " + cell.getId()); from 0 to 11
				labelRectangle.setRect(cell.getPosX(), cell.getPosY(), plotSizeWidth, plotSizeHeight);
				g2D.fill(labelRectangle);
				g2D.draw(labelRectangle);
			} else {
			// otherwise black
			}
		}
		
		g2D.setColor(color);
		
		// writing variable names under the grid
		for(TextPlot label: model.getColumnLabels()){
			if (label.getStatus().equals("ON") || label.isMain == true){
				g2D.drawString(label.getTextLabel(), label.getPosX(), label.getPosY());
			} else {
				// otherwise black
			}
		}
		
	}
	
	// Creating the heatmap grid and saving to the model, according to year data
	public void initCellsAndLabels(int x, int y, int year){
		
		int intJ = 0;
		
		min = getMinRange(year);
		max = getMaxRange(year);
		
		Debug.p("Max Value: " + max);
		Debug.p("Min Value: " + min);
		
		for (int j = 0; j < numColumns; j++){
			
			String label = model.getYears().get(year).getLabels().get(j).toString();
			boolean isMain = false;
			
			if (label.equalsIgnoreCase("MH_EM") || label.equalsIgnoreCase("MH_EW") || label.equalsIgnoreCase("E_AM") ||
					label.equalsIgnoreCase("E_AW") || label.equalsIgnoreCase("E_EM") || label.equalsIgnoreCase("E_EW")){
				// Ignore these cases: maennlich and weiblich
			}
			else {
				// 3 main labels, always present
				if (label.equalsIgnoreCase("MH_E") || label.equalsIgnoreCase("E_A") || label.equalsIgnoreCase("E_E")){
					Debug.p("label: " + label);
					isMain = true;
				}
				else {
					isMain = false;
				}
				
				int caseIdColumn = 0;
				
				// columns: 1 = MH_E, 2 = E_A, 3 = E_E
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

					// i = row, each row = each district
					CellPlot cell = new CellPlot(i, caseIdColumn, x, y + i*plotSizeHeight, isMain, intJ);
					
					// assign cell color according to numerical value
					
					double value = model.getYears().get(year).getList().get(i).getValue(intJ);
					
					double normalizedValue = cell.normalizeValue(value, min, max);
					Debug.p("No Value: " + normalizedValue);
					cell.setColorInterpolation(normalizedValue);
					
					model.addCell(cell);
				}
				
				// all variable names
				// TODO: we should substitute the variable names for normal words when showing
				TextPlot textLabel = new TextPlot(label, x, y + 20 + numRows * plotSizeHeight, isMain, caseIdColumn, intJ);
				model.addTextLabel(textLabel);
				
				if (isMain){
					x += plotSizeWidth;
					y = 20  + offSetY;
				}
				
				intJ++;
			}

		}
	}
	
	public double getMaxRange(int year){
		
		double max = Double.NEGATIVE_INFINITY;
		
		for (int j = 0; j < numColumns; j++){
			double valueMax = model.getYears().get(year).getRanges().get(j).getMax();
			
			if (valueMax > max){
				max = valueMax;
			}
		}
		
		return max;
		
	}
	
	public double getMinRange(int year){
		
		double min = Double.POSITIVE_INFINITY;
		
		for (int j = 0; j < numColumns; j++){
			double valueMin = model.getYears().get(year).getRanges().get(j).getMin();
			
			if (valueMin < min){
				min = valueMin;
			}
		}
		
		return min;
	}
	
	public Color getColorCellLegend(double normalizedValue) {
	    return new Color(0,0,255, (int)(normalizedValue * 255));
	}

	public void drawLegend(Graphics2D g2D){
		g2D.setColor(getColorCellLegend(0.3));
		labelRectangle.setRect(20, 460, 60, 20);
		g2D.fill(labelRectangle);
		g2D.draw(labelRectangle);
		
		g2D.setColor(color);
		g2D.drawString("" + (int) max * 0.3 , 20, 500);
		
		g2D.setColor(getColorCellLegend(0.6));
		labelRectangle.setRect(80, 460, 60, 20);
		g2D.fill(labelRectangle);
		g2D.draw(labelRectangle);
		
		g2D.setColor(color);
		g2D.drawString("" + (int) max * 0.6 , 80, 500);
		
		g2D.setColor(getColorCellLegend(0.99));
		labelRectangle.setRect(140, 460, 60, 20);
		g2D.fill(labelRectangle);
		g2D.draw(labelRectangle);
		
		g2D.setColor(color);
		g2D.drawString("" + (int) max * 0.6 , 140, 500);
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
