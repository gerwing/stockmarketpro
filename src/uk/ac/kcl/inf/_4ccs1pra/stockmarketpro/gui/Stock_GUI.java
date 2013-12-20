package uk.ac.kcl.inf._4ccs1pra.stockmarketpro.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import uk.ac.kcl.inf._4ccs1pra.stockmarketpro.stocks.StockDataModel;

/**
 * Stock GUI builds the user interface for the stock data that is retrieved
 * The data is shown in a JTable and a status bar provides information about the average Adj. Close price and the Maximum Drawdown
 * This class needs a StockDataModel object to retrieve all it's stock data
 * @author Gerwin
 *
 */
public class Stock_GUI extends JFrame {

	private StockDataModel dataModel;
	private JLabel status;
	
	/**
	 * Construct a new table with stock data coming from the StockDataModel
	 * @param stockDataModel Object of the StockDataModel class that has been preconstructed with the appropriate values to fill up the table window
	 */
	public Stock_GUI(StockDataModel dm) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		dataModel = dm;
		setTitle(dataModel.getTableTitle());//set the title of the window
		
		/*
		 * Try to get stock data (if valid date and period) and construct widgets
		 */
		try {
			dm.getStockData(); //fill the data model with the data for the stock
			initWidgets();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "The given stock or period is invalid ", "Woops, Error Occured", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}
	}
	
	private void initWidgets() {
		/*
		 * JTable
		 */
		JTable tblData = new JTable(dataModel);
		//set cellrenderers
		tblData.setDefaultRenderer(Object.class, new DefaultCellRenderer());
		ColorCellRenderer cRenderer = new ColorCellRenderer(dataModel.getOrder());
		tblData.getColumnModel().getColumn(6).setCellRenderer(cRenderer);
		//set color for mac
		tblData.setGridColor(Color.lightGray);
		
		/*
		 * Status bar
		 */
		status = new JLabel();
		status.setText("Adj. Close Average: " + dataModel.getCloseAverage() + "     Max Drawdown: " + dataModel.getMaxDrawdown());
		
		/*
		 * Content Pane
		 */
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(tblData), BorderLayout.CENTER);
		getContentPane().add(status,BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
}
