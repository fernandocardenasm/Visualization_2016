package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;



public class View extends JPanel{
	private Model model = null;
	private Color color = Color.BLUE;
	private double scale = 1;
	private double overviewScale = 0.2;
	private double translateX = 0;
	private double translateY = 0;
	private Rectangle2D marker = new Rectangle2D.Double();
	private Rectangle2D overviewRect = new Rectangle2D.Double();   

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		Color colorOverview = Color.BLACK;
		Color colorBackground = Color.WHITE;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		// slider scale
		g2D.scale(scale, scale);
		paintDiagram(g2D);
		g2D.scale(1/scale, 1/scale); // to not apply zoom to the overview
		
		// position 1 for scale
		g2D.scale(overviewScale, overviewScale);
		
		//g2D.setStroke(new BasicStroke(10.0f));
		g2D.setColor(colorOverview);
		//overviewRect.setRect(0, 0, getWidth()*0.3, getHeight()*0.32);
		overviewRect.setRect(0, 0, 1400, 710); // is there a way to not hard code these values?
		g2D.draw(overviewRect);
		
		g2D.setColor(colorBackground);
		g2D.fill(overviewRect);
		
		// position 2
		//g2D.scale(0.2, 0.2);
		
		paintDiagram(g2D);
		
		// set marker to indicate which area of the main view is shown

		/*updateTranslation(x, y);
		updateMarker((int) translateX, (int) translateY);
		*/
		//marker = this.getBounds();
		//updateMarker2(this.getX(), this.getY(), (int) (this.getWidth() / scale), (int) (this.getHeight() / scale));
		updateMarker3();
		g2D.draw(marker);
		
	}
	private void paintDiagram(Graphics2D g2D){
		for (Element element: model.getElements()){
			element.paint(g2D);
		}
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale(){
		return scale;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double tansslateY) {
		this.translateY = tansslateY;
	}
	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}	
	public void updateMarker(int x, int y){
		marker.setRect(x, y, 16, 10);
	}
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(double x, double y){
		return marker.contains(x, y);
	}
	
	// added methods
	public void updateMarker2(int x, int y, int width, int height){
		marker.setRect(x, y, width, height);
	}
	public void updateMarker3(){
		marker.setRect(this.getX(), this.getY(), (int) (this.getWidth() / scale), (int) (this.getHeight() / scale));
	}
	public double getOverviewScale(){
		return overviewScale;
	}
}
 