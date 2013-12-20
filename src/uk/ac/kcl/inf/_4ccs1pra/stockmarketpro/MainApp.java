package uk.ac.kcl.inf._4ccs1pra.stockmarketpro;

import uk.ac.kcl.inf._4ccs1pra.stockmarketpro.gui.Main_GUI;
import uk.ac.kcl.inf._4ccs1pra.stockmarketpro.windows.WindowManager;

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
