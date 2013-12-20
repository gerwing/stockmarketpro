

import gui.Main_GUI;
import windows.WindowManager;

/**
 * Main Class StockMarketPro Application
 * @author Gerwin
 *
 */
public class MainApp {

	/**
	 * Main method for StockMarketPro Application
	 * The application starts here
	 * @param args
	 */
	public static void main(String[] args) {
		new Main_GUI(new WindowManager());
	}

}
