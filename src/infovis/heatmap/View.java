package infovis.heatmap;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

public class View extends JPanel {

	private Model model = null;
    private Rectangle2D labelRectangle = new Rectangle2D.Double(0,0,0,0);
    private Color color = Color.BLACK;
    
    private int plotSizeWidth = 40; // edge size for each scatter plot cell
    private int plotSizeHeight = 20; // edge size for each scatter plot cell
 
    //Please, upgrade these two values if needed
	 private int offSetX = 50; // offset at the beginning of X axis
	 private int offSetY = 30; // offset at the beginning of Y axis
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.scale(1, 1);
		
		int x = 10;
		int y = 20 + plotSizeHeight;
		int cont = 0;
		
		g2D.clearRect(0, 0, getWidth(), getHeight());
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		
		x = 10;
		y = 20;
        
		//Probably the labels should be part of the Model, not from the Class YearData
		int numRows = 10;
		int numColumnsTotal = model.getYears().get(0).getLabels().size();
		int numColumnsTemp = 3;
		
		for (int j = 0; j < numColumnsTemp; j++){
			
			for (int i = 0; i < numRows; i++) {
				
				CellPlot cell = new CellPlot(i, j, x, y + i*plotSizeHeight, true);
				labelRectangle.setRect(cell.getPosX(), cell.getPosY(), plotSizeWidth, plotSizeHeight);
				g2D.draw(labelRectangle);
				
			}
			
			//String text = model.getYears().get(0).getLabels().get(j);
			//TextPlot textLabel = new TextPlot(text, x, y + 20 + numRows * plotSizeHeight);
			
			String text = "";
			
			if (j == 0){
				text = "E_E";
			}
			else if (j==1){
				text = "E_A";
			}
			else if (j==2){
				text = "MH_E";
			}
			
			TextPlot textLabel = new TextPlot(text, x, y + 20 + numRows * plotSizeHeight);

			g2D.drawString(textLabel.getTextLabel(), textLabel.getPosX(), textLabel.getPosY());
			
			x = x + plotSizeWidth;
			y = 20;
		}
		
		
        //Rectangle2D rect = new Rectangle2D.Double(50, 50, 50, 50);
        
        //Color color = Color.GREEN;
        //g2D.setColor(color);
        //g2D.fill(rect);
        //g2D.draw(rect);
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
