package infovis.diagram;

import infovis.debug.Debug;
import infovis.diagram.elements.DrawingEdge;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Element;
import infovis.diagram.elements.GroupingRectangle;
import infovis.diagram.elements.None;
import infovis.diagram.elements.Vertex;
import infovis.diagram.layout.Fisheye;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MouseController implements MouseListener, MouseMotionListener {
	 private Model model;
	 private View view;
	 private Element selectedElement = new None();
	 private Fisheye fish = new Fisheye();
	 private double mouseOffsetX;
	 private double mouseOffsetY;
	 private double overviewOffsetX;
	 private double overviewOffsetY;
	 private boolean edgeDrawMode = false;
	 private DrawingEdge drawingEdge = null;
	 private boolean fisheyeMode;
	 private GroupingRectangle groupRectangle;
	 private boolean markerSelected = false;
	 private boolean overviewSelected = false;
	 
	 List<Vertex> firstVertexes;
	 
	 private boolean firstIteration = false;
	/*
	 * Getter And Setter
	 */
	 public Element getSelectedElement(){
		 return selectedElement;
	 }
    public Model getModel() {
		return model;
	}
	public void setModel(Model diagramModel) {
		this.model = diagramModel;
	}
	public View getView() {
		return view;
	}
	public void setView(View diagramView) {
		this.view = diagramView;
	}
	/*
     * Implements MouseListener
     */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		double scale = view.getScale();
		
		
		
		if (e.getButton() == MouseEvent.BUTTON3){
			/*
			 * add grouped elements to the model
			 */
			Vertex groupVertex = (Vertex)getElementContainingPosition(x/scale,y/scale);
			for (Iterator<Vertex> iter = groupVertex.getGroupedElements().iteratorVertices();iter.hasNext();){
				model.addVertex(iter.next());
			}
			for (Iterator<Edge> iter = groupVertex.getGroupedElements().iteratorEdges();iter.hasNext();){
				model.addEdge(iter.next());
			}
			/*
			 * remove elements
			 */
			List<Edge> edgesToRemove = new ArrayList<Edge>();
			for (Iterator<Edge> iter = model.iteratorEdges(); iter.hasNext();){
				Edge edge = iter.next();
				if (edge.getSource() == groupVertex || edge.getTarget() == groupVertex){
					edgesToRemove.add(edge);
				}
			}
			model.removeEdges(edgesToRemove);
			model.removeElement(groupVertex);
			
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		double scale = view.getScale();
		double overviewScale = view.getOverviewScale();
		
		//Debug.p("mousePressed: x = " + x + ", y = " + y);
	   
	   if (edgeDrawMode){
			drawingEdge = new DrawingEdge((Vertex)getElementContainingPosition(x/scale,y/scale));
			model.addElement(drawingEdge);
		} else if (fisheyeMode){
			/*
			 * do handle interactions in fisheye mode
			 */
			// focus point is selected by clicking (normal coordinates)
			// each vertex has assigned a Visual Worth (VW) based on its distance to the focus point (normal coordinates)
			view.repaint();
		} else {
			
			// coordinate + getTranslate to include marker offset
			selectedElement = getElementContainingPosition((x/scale)  + view.getTranslateX(), (y/scale)  + view.getTranslateY());
			/*
			 * calculate offset
			 */
			mouseOffsetX = x - selectedElement.getX() * scale ;
			mouseOffsetY = y - selectedElement.getY() * scale ;
			
			//markerSelected = view.markerContains((x + view.getTranslateOverviewX())/overviewScale , (y + view.getTranslateOverviewY())/overviewScale);
			//Debug.p("Overview translation: x = " + view.getTranslateOverviewX() + ", y = " + view.getTranslateOverviewY());
			markerSelected = view.markerContains((x/overviewScale) + view.getTranslateOverviewX() , (y/overviewScale) + view.getTranslateOverviewY());
			if (markerSelected) {
				// click in marker
				//Debug.p("marker hit");
				mouseOffsetX = x - view.getMarker().getX() * overviewScale ;
				mouseOffsetY = y - view.getMarker().getY() * overviewScale ;
				overviewSelected = false;
			} else {
				overviewSelected = view.overviewContains(x/overviewScale , y/overviewScale);
				// click in overview
				if (overviewSelected) {
					//Debug.p("overview hit");
					overviewOffsetX = x - view.getOverview().getX() * overviewScale ;
					overviewOffsetY = y - view.getOverview().getY() * overviewScale ;
					//Debug.p("Overview offset: x = " + overviewOffsetX + ", y = " + overviewOffsetY);
				}
			}	
			
		}
		
	}
	// this method needs to be adapted for main view translation with marker
	public void mouseReleased(MouseEvent arg0){
		int x = arg0.getX();
		int y = arg0.getY();
		
		if (drawingEdge != null){
			Element to = getElementContainingPosition(x, y);
			model.addEdge(new Edge(drawingEdge.getFrom(),(Vertex)to));
			model.removeElement(drawingEdge);
			drawingEdge = null;
		}
		if (groupRectangle != null){
		    Model groupedElements = new Model();
			for (Iterator<Vertex> iter = model.iteratorVertices(); iter.hasNext();) {
				Vertex vertex = iter.next();
				if (groupRectangle.contains(vertex.getShape().getBounds2D())){
					Debug.p("Vertex found");
					groupedElements.addVertex(vertex);	
				}
			}
			if (!groupedElements.isEmpty()){
				model.removeVertices(groupedElements.getVertices());
				
				Vertex groupVertex = new Vertex(groupRectangle.getCenterX(),groupRectangle.getCenterX());
				groupVertex.setColor(Color.ORANGE);
				groupVertex.setGroupedElements(groupedElements);
				model.addVertex(groupVertex);
				
				List<Edge> newEdges = new ArrayList(); 
				for (Iterator<Edge> iter = model.iteratorEdges(); iter.hasNext();) {
					Edge edge =  iter.next();
				    if (groupRectangle.contains(edge.getSource().getShape().getBounds2D()) 
				    	&& groupRectangle.contains(edge.getTarget().getShape().getBounds2D())){
				    		groupVertex.getGroupedElements().addEdge(edge);
                            Debug.p("add Edge to groupedElements");	
                            //iter.remove(); // Warum geht das nicht!
				    } else if (groupRectangle.contains(edge.getSource().getShape().getBounds2D())){
				    	groupVertex.getGroupedElements().addEdge(edge);
				    	newEdges.add(new Edge(groupVertex,edge.getTarget()));
				    } else if (groupRectangle.contains(edge.getTarget().getShape().getBounds2D())){
				    	groupVertex.getGroupedElements().addEdge(edge);
				    	newEdges.add(new Edge(edge.getSource(),groupVertex));
				    }
				}
				model.addEdges(newEdges);
				model.removeEdges(groupedElements.getEdges());
			}
			model.removeElement(groupRectangle);
			groupRectangle = null;
		}
		view.repaint();
	}
	
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		double scale = view.getScale();
		double overviewScale = view.getOverviewScale();
		
		//Debug.p("mouseDragged: x = " + x + ", y = " + y);
		/*
		 * Aufgabe 1.2: Navigate the main area by dragging the marker
		 */
		if (markerSelected) {
			view.updateMarker2((int) ((x - mouseOffsetX)/overviewScale), (int) ((y - mouseOffsetY)/overviewScale), (int) (view.getHeight()/scale), (int) (view.getWidth()/scale));
			// translation of the elements
			view.updateTranslation((x - mouseOffsetX)/overviewScale, (y - mouseOffsetY)/overviewScale);
		}
		
		// Optional task 1.3
		if (overviewSelected) {
			//view.updateOverview((e.getX()-overviewOffsetX)/scale, (e.getY()-overviewOffsetY) /scale);
			view.updateOverview((x - overviewOffsetX)/overviewScale, (y - overviewOffsetY)/overviewScale);
			view.updateOverviewTranslation((x - overviewOffsetX)/overviewScale, (y - overviewOffsetY)/overviewScale);
		}
		
		if (fisheyeMode){
			
			view.setpFocusX(x);
			view.setpFocusY(y);
			
			//The idea is to save the initial vertices in the model, in this way
			//we could still modify the current model, but considering the initial point
			//of reference.
			//It needs to be check if it implemented correctly
			if (!firstIteration){
				firstVertexes = model.getVertices();
				model = fish.transform(model, view);
				firstIteration = true;
			}
			else{
				
				//Here I send the model, but we work with the firstVertexes
				model = fish.transform(model, view, firstVertexes);
				
			}
			
			
			/*
			 * handle fisheye mode interactions
			 */
			// focus changes according to dragging movement
			// each vertex is defined by its position, size and amount of detail
			// important vertices are bigger, non important vertices need to get smaller (whole graph should still fit the screen)
			view.repaint();
		} else if (edgeDrawMode){
			drawingEdge.setX(e.getX());
			drawingEdge.setY(e.getY());
		}else if(selectedElement != null){
			selectedElement.updatePosition((e.getX()-mouseOffsetX)/scale, (e.getY()-mouseOffsetY) /scale);
		}
		view.repaint();
	}
	public void mouseMoved(MouseEvent e) {
	}
	public boolean isDrawingEdges() {
		return edgeDrawMode;
	}
	public void setDrawingEdges(boolean drawingEdges) {
		this.edgeDrawMode = drawingEdges;
	}
	
	public void setFisheyeMode(boolean b) {
		fisheyeMode = b;
		if (b){
			Debug.p("new Fisheye Layout");
			/*
			 * handle fish eye initial call
			 */
			view.repaint();
		} else {
			Debug.p("new Normal Layout");
			view.setModel(model);
			view.repaint();
		}
	}
	
	/*
	 * private Methods
	 */
	private Element getElementContainingPosition(double x,double y){
		Element currentElement = new None();
		Iterator<Element> iter = getModel().iterator();
		while (iter.hasNext()) {
		  Element element =  iter.next();
		  if (element.contains(x, y)) currentElement = element;  
		}
		return currentElement;
	}
	
    
}
