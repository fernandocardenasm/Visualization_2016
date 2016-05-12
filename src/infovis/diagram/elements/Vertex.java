package infovis.diagram.elements;

import infovis.diagram.Model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Vertex implements Element {
	
	public static final double STD_WIDTH = 60;
	public static final double STD_HEIGHT = 20;

	private String text = "Neues Label";
	private Color color = Color.BLUE;
	private Color background = Color.WHITE;
	private Font font = new Font("sansserif", Font.BOLD, 12);
	RectangularShape shape ;
	private int id = 0;
	private Model groupedElements;
	
	
	public Vertex (Vertex vertex){
		this.shape = new Ellipse2D.Double(vertex.getX(),vertex.getY(),vertex.getWidth(),vertex.getHeight());
		this.text = vertex.text;
		this.color = vertex.color;
		this.background = vertex.background;
		this.font = vertex.font;
		this.id = vertex.getID();
	}
	public Vertex(double x, double y, double width, double height){
		//this.shape = new Rectangle2D.Double(x,y,width,height);
		this.shape =  new Ellipse2D.Double(x,y,width,height);
		this.id = Model.generateNewID();
	}
	public Vertex(double x, double y){
		//this.shape = new Rectangle2D.Double(x,y,width,height);
		this.shape =  new Ellipse2D.Double(x,y,STD_WIDTH,STD_HEIGHT);
		this.id = Model.generateNewID();
	}
	public boolean contains(double x, double y) {
		//Debug.print("Vertex CONTAINS POINT");
		return shape.contains(x, y);
	}

	public void paint(Graphics2D g2D) {
		//Debug.print("paint Vertex");
		g2D.setColor(color);
		g2D.fill(shape);
		g2D.setColor(Color.BLACK);
		g2D.draw(shape);
	}

	public double getX() {
		return shape.getX();
	}

	public double getY() {
		return shape.getY();
	}

	public void setX(double x) {
		shape.setFrame(x, getY(),getWidth() , getHeight());	
	}

	public void setY(double y) {
		shape.setFrame(getX(), y, getWidth() , getHeight());	
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getWidth() {
		//return shape.getWidth();
		return shape.getWidth();
	}

	public void setWidth(double width) {
		shape.setFrame(getX(), getY(), width, getHeight());
	}

	public double getHeight() {
		//return shape.getHeight();
		return shape.getHeight();
	}

	public void setHeight(double height) {
		shape.setFrame(getX(), getY(), getWidth(), height);
	}
	public void updatePosition(double x, double y) {
		shape.setFrame(x, y, getWidth(), getHeight());

		//Debug.print("updatePosition");
		
	}
	public double getCenterX(){
		return shape.getCenterX();
	}
	public double getCenterY(){
		return shape.getCenterY();
	}
	public RectangularShape getShape() {
		return shape;
	}
	public void setShape(RectangularShape shape) {
		this.shape = shape;
	}
	public int getID() {
		return id;
	}
	public void setFrame(double x, double y, double width, double height){
		shape.setFrame(x, y, width, height);
	}
	public Model getGroupedElements() {
		return groupedElements;
	}
	public void setGroupedElements(Model groupedElements) {
		this.groupedElements = groupedElements;
	}
	
	//Return the PFish of the object, its new position
	public double transformF1(double pNorm, double pFocus, double pBoundary, double d){
		double dMax = 0;
		double dNorm = 0;
		double gx = 0;
		
		dMax = dMax(pBoundary, pFocus, pNorm);
		dNorm = pNorm - pFocus;
		
		gx = GX(dNorm/dMax, d);
		
		return pFocus + gx * dMax;
	}
	//public double transformWidth(double pNorm, double pFocus, double pBoundary, double width, double d){
		//double qNorm = qNorm(pNorm, width);
		//double qFish = qFish(qNorm, pFocus, pBoundary,d);
		//Math.min(a, b)
	//}
	
	
	//Return the new size of the object.
	public double transformSize(double pNormX, double pNormY , double pFocusX, double pFocusY, double pBoundaryX, double pBoundaryY, double d){
		double qNormX = qNorm(pNormX, pBoundaryX);
		double qFishX = qFish(qNormX, pFocusX,pBoundaryX, d);
		double pFishX = transformF1(pNormX, pFocusX, pBoundaryX, d);
		
		double qNormY = qNorm(pNormY, pBoundaryY);
		double qFishY = qFish(qNormY, pFocusX,pBoundaryY, d);
		double pFishY = transformF1(pNormY, pFocusY, pBoundaryY, d);
		 
		
		double sGeom = sGeom(pFishX, pFishY, qFishX, qFishY);
		
		return sGeom;
	}
	
	private double dMax(double pBoundary, double pFocus, double pNorm){
		double dMax = 1;
		if (pNorm > pFocus){
			dMax = pBoundary - pFocus;
		}
		else{
			dMax = 0 - pFocus;
		}
		
		return dMax;
	}
	
	private double GX(double x, double d){
		return (d + 1) * x / (d * x + 1);
	}
	
	private double qNorm(double pNorm, double sNorm){
		return pNorm + sNorm/2;
	}
	
	private double qFish(double qNorm, double pFocus, double pBoundary, double d){
		return transformF1 (qNorm, pFocus, pBoundary, d);
	}
	private double sGeom(double pFishX, double pFishY, double qFishX, double qFishY){
		return 2 * Math.min(Math.abs(qFishX - pFishX), Math.abs(qFishY - pFishY));
	}
	
}
