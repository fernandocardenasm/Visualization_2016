/**
 * Main program for the heatmap visualization project
 * 
 * (first version based on the package infovis.example)
 */
package infovis.heatmap;

import javax.swing.SwingUtilities;

import infovis.example.Example;
import infovis.gui.GUI;

/**
 * @author gcmolinaleon
 *
 */
public class Heatmap {
	
	private Model model = null;
	private View view = null;
	private MouseController controller = null;
	
	public View getView(){
		if (view == null)
			generateHeatmap();
		return view;
	}
	
	private void generateHeatmap() {
		model = new Model();
		view = new View();
		controller = new MouseController();
		
		view.addMouseListener(controller);
		view.addMouseMotionListener(controller);
		view.setModel(model);
		
		controller.setModel(model);
		controller.setView(view);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI application = new GUI();
				application.setView(new Heatmap().getView());
				application.getJFrame().setVisible(true);
			}
		});

	}

}
