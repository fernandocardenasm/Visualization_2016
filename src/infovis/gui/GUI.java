package infovis.gui;

import infovis.debug.Debug;
import infovis.diagram.Diagram;
import infovis.diagram.MenuController;
import infovis.heatmap.CellPlot;
import infovis.heatmap.Model;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.InteractorAdapter;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;


public class GUI {

	private JFrame jFrame = null;

	private JPanel jContentPane = null;

	private JMenuBar jJMenuBar = null;

	private JMenu fileMenu = null;

	private JMenu editMenu = null;

	private JMenu helpMenu = null;

	private JMenuItem exitMenuItem = null;

	private JMenuItem aboutMenuItem = null;

	private JMenuItem cutMenuItem = null;

	private JMenuItem copyMenuItem = null;

	private JMenuItem pasteMenuItem = null;

	private JMenuItem saveMenuItem = null;

	private JDialog aboutDialog = null;

	private JPanel aboutContentPane = null;

	private JLabel aboutVersionLabel = null;

	private JToolBar jJToolBarBar = null;

	private JButton newNodeButton = null;

	private JPanel view = null;

	private JButton newLabelButton = null;

	private JSlider jSlider = null;

	private JToggleButton drawToggleButton = null;

	private JToggleButton fisheyeToggleButton = null;

	private boolean showToolbar = false;

	private boolean showMap = false;
	
	private JSVGCanvas canvas = new JSVGCanvas();
	
	private Document svgDocument;
	
	private String[] districtColors = {"#E8F2AD", "#c6fdb0", "#fcb3a4", "#EBCEF2", "#ecc7b4", "#ff8fa6",
			                           "#ecb1cf", "#ffd697", "#fdf5a2", "#feecd4", "#d7f9d0", "#ffb8ea"};

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	public JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(1000, 750);
			jFrame.setContentPane(getJContentPane());
			// SVG Canvas
			if (showMap) {
				try {
					String uri = new File("map.svg").toURI().toString();
					String parser = XMLResourceDescriptor.getXMLParserClassName();
					SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
					svgDocument = f.createDocument(uri);
					canvas.setDocumentState(JSVGCanvas.ALWAYS_INTERACTIVE);
					canvas.setSVGDocument((SVGDocument) svgDocument);
					svgDocument = canvas.getSVGDocument();
					registerListeners();
				} catch (IOException e) {
					Debug.println("IOException happened while creating SVG document.");
					e.printStackTrace();
				}
				canvas.setRecenterOnResize(false);
				/*
				canvas.getInteractors().add(new InteractorAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						Debug.println("User clicked!");
					}
					// Based on code in http://mcc.id.au/2007/09/batik-course/
					protected boolean isClick(InputEvent ie){
						if (ie instanceof MouseEvent) {
							MouseEvent me = (MouseEvent) ie;
							return me.getID() == MouseEvent.MOUSE_CLICKED
									&& me.getButton() == MouseEvent.BUTTON1;
						}
						return false;
					}
					@Override
					public boolean startInteraction(InputEvent ie) {
						return isClick(ie);
					}
				});*/
				JPanel panel = new JPanel(new BorderLayout());
				panel.setPreferredSize(new Dimension(jFrame.getWidth()/2, jFrame.getHeight()/2));
				panel.add(canvas, BorderLayout.CENTER);
				jContentPane.add(panel, BorderLayout.EAST);
			}
			jFrame.setTitle("InfoVisEditor");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			if (showToolbar) jContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
			jContentPane.add(getView(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version 1.0");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		return saveMenuItem;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getNewNodeButton());
			jJToolBarBar.add(getNewLabelButton());
			jJToolBarBar.add(getDrawToggleButton());
			jJToolBarBar.add(getFisheyeToggleButton());
			jJToolBarBar.add(getJSlider());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes newNodeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNewNodeButton() {
		if (newNodeButton == null) {
			newNodeButton = new JButton();
			newNodeButton.setText("new Node");
			newNodeButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//Debug.print("GUI: new Node clicked"); 
					MenuController.getMenuController().newVertex();
					//Controller.getController().newVertex();
				}
			});
		}
		return newNodeButton;
	}

	/**
	 * This method initializes view	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getView() {
		if (view == null) {
			view = new Diagram().getView();
			//view = new Scatterplot().getView();
		}
		return view;
	}

	/**
	 * This method initializes newLabelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNewLabelButton() {
		if (newLabelButton == null) {
			newLabelButton = new JButton();
			newLabelButton.setText("new Label");
			newLabelButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//MenuController.getMenuController().newLabel();
					//Debug.print("GUI: newLabel clicked"); 
				}
			});
		}
		return newLabelButton;
	}

	/**
	 * This method initializes jSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider();
			jSlider.setMaximum(10);
			jSlider.setMajorTickSpacing(5);
			jSlider.setMinorTickSpacing(1);
			jSlider.setValue(0);
			jSlider.setPaintTicks(true); 
			jSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					//Debug.print("stateChanged() with Value:" + jSlider.getValue()); 
					MenuController.getInstance().setScale(Math.pow(10,jSlider.getValue()/ 10.0));
					
				}
			});
		}
		return jSlider;
	}

	/**
	 * This method initializes drawToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getDrawToggleButton() {
		if (drawToggleButton == null) {
			drawToggleButton = new JToggleButton();
			drawToggleButton.setText("Edge Draw");
			drawToggleButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED){
						MenuController.getInstance().startEdgeDrawingMode();
					}else if (e.getStateChange() == ItemEvent.DESELECTED){
						MenuController.getInstance().stopEdgeDrawingMode();
					}
				}
			});
		}
		return drawToggleButton;
	}

	/**
	 * This method initializes fisheyeToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getFisheyeToggleButton() {
		if (fisheyeToggleButton == null) {
			fisheyeToggleButton = new JToggleButton();
			fisheyeToggleButton.setText("Fisheye");
//			fisheyeToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
			fisheyeToggleButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED){
						MenuController.getInstance().startFisheyeMode();
					}else if (e.getStateChange() == ItemEvent.DESELECTED){
						MenuController.getInstance().stopFisheyeMode();
					}
				}
			});
//				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					  AbstractButton abstractButton = (AbstractButton) e.getSource();
//				        ButtonModel buttonModel = abstractButton.getModel();
//				       
//				        //boolean armed = buttonModel.isArmed();
//				        //boolean pressed = buttonModel.isPressed();
//				        if (buttonModel.isSelected() && ! buttonModel.isRollover()){
//				        	MenuController.getInstance().startFisheyeLayout();
//				        } else {
//				        	MenuController.getInstance().stopFisheyeLayout();
//				        }
//				       }
//				
//			});
			
		}
		return fisheyeToggleButton;
	}

	/**
	 * Launches this application
	 */
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				GUI application = new GUI();
//				application.showToolbar(true);
//				application.getJFrame().setVisible(true);
//			}
//		});
//	}

	public void setView(JPanel view){
		this.view = view;
	}
	public void showToolbar(boolean showToolbar){
		this.showToolbar  = showToolbar;
	}
	
	/**
	 * SVG Berlin map methods
	 */
	public void showMap(boolean showMap){
		this.showMap  = showMap;
	}
	
	// Adds mouse listeners to map
	public void registerListeners() {
		// set background to white
		Element rectangle = svgDocument.getElementById("Brandenburg");
		rectangle.setAttribute("fill", "white");
		
		// Add a click event listener to each district polygon
		NodeList districtsPolygons = svgDocument.getElementById("DISTRICTS").getElementsByTagName("polygon");
		
		for (int i = 0; i < districtsPolygons.getLength(); i++){
			EventTarget district = (EventTarget) districtsPolygons.item(i);
			// Assign a color to the polygons of each district
			Element polygon = (Element) districtsPolygons.item(i);
			
			switch(i){
			case 0: case 14: case 50: case 58: case 60: case 78: case 80:
				// Charlottenburg-Wilmersdorf
				district.addEventListener("mousedown", new OnClickListener(i, 3), false);
				polygon.setAttribute("fill", districtColors[3]);
				break;
			case 1: case 7: case 8: case 26: case 35: case 52: case 53: case 59: case 95:
				// Spandau
				district.addEventListener("mousedown", new OnClickListener(i, 4), false);
				polygon.setAttribute("fill", districtColors[8]);
				break;
			case 2: case 11: case 33: case 37: case 39: case 62: case 65: case 68: case 72: case 83: case 90: case 94: case 102:
				// Pankow
				district.addEventListener("mousedown", new OnClickListener(i, 2), false);
				polygon.setAttribute("fill", districtColors[7]);
				break;
			case 3: case 4: case 9: case 13: case 27: case 30: case 32: case 40: case 46: case 61: case 67: case 69: case 81: case 91: case 92:
				// Treptow-Koepenick
				district.addEventListener("mousedown", new OnClickListener(i, 8), false);
				polygon.setAttribute("fill", districtColors[6]);
				break;
			case 5: case 19: case 20: case 23: case 24: case 42: case 70:
				// Steglitz-Zehlendorf
				district.addEventListener("mousedown", new OnClickListener(i, 5), false);
				polygon.setAttribute("fill", districtColors[4]);
				break;
			case 6: case 16: case 21: case 31: case 45: case 51:
				// Mitte
				district.addEventListener("mousedown", new OnClickListener(i, 0), false);
				polygon.setAttribute("fill", districtColors[11]);
				break;
			case 10: case 15: case 25: case 41: case 56: case 57: case 66: case 73: case 79: case 84:
				// Reinickendorf
				district.addEventListener("mousedown", new OnClickListener(i, 11), false);
				polygon.setAttribute("fill", districtColors[0]);
				break;
			case 12: case 17: case 29: case 47: case 54: case 87:
				// Neukoelln
				district.addEventListener("mousedown", new OnClickListener(i, 7), false);
				polygon.setAttribute("fill", districtColors[10]);
				break;
			case 18: case 63:
				// Friedrichshain-Kreuzberg
				district.addEventListener("mousedown", new OnClickListener(i, 1), false);
				polygon.setAttribute("fill", districtColors[5]);
				break;
			case 28: case 34: case 36: case 43: case 76: case 85: case 86: case 89: case 100: case 101:
				// Lichtenberg
				district.addEventListener("mousedown", new OnClickListener(i, 10), false);
				polygon.setAttribute("fill", districtColors[2]);
				break;
			case 44: case 55: case 71: case 77: case 88: case 93:
				// Tempelhof-Schoeneberg
				district.addEventListener("mousedown", new OnClickListener(i, 6), false);
				polygon.setAttribute("fill", districtColors[9]);
				break;
			case 74: case 96: case 97: case 98: case 99:
				// Marzahn-Hellersdorf
				district.addEventListener("mousedown", new OnClickListener(i, 9), false);
				polygon.setAttribute("fill", districtColors[1]);
				break;
			default:
				break;
			}
		}
	}
	
	public class OnClickListener implements EventListener {

		private int polygonID;
		private int districtID;
		
		public OnClickListener(int polygonID, int districtID) {
			super();
			this.polygonID = polygonID;
			this.districtID = districtID;
		}

		@Override
		public void handleEvent(org.w3c.dom.events.Event arg0) {
			Debug.println("District " + districtID + " clicked on map");
			if (showMap) {
				for(CellPlot cell: Model.getModelInstance().getCells()){
					if (cell.getId() == districtID){
						cell.setSelected(true);
					}
				}
				view.repaint();
			}
		}
	}
}
