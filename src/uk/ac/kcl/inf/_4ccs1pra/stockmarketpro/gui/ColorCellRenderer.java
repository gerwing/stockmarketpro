package uk.ac.kcl.inf._4ccs1pra.stockmarketpro.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import uk.ac.kcl.inf._4ccs1pra.stockmarketpro.stocks.StockDataModel;

/**
 * The ColorCellRender provides a column cell renderer that compares stock prices in the same column and gives them a green (rise in price) or red (decrease in price) color
 * Assign this class as the CellRenderer for a column containing prices in a JTable
 * @author Gerwin
 *
 */
public class ColorCellRenderer extends DefaultTableCellRenderer {
	
	private int order;
	
	/**
	 * Constructor method, constructs a new ColorCellrender given the order in which the prices appear
	 * @param order Order of the prices, obtained from StockDataModel.CHRONOLOGICAL or StockDataModel.REVERSE_CHRONOLOGICAL
	 */
	public ColorCellRenderer(int order)
	{
		this.order = order;
	}
	
	/* (non-Javadoc)
	 * Color the cells by comparing the cell in the row above or below, depending on the order of the prices
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		double close = Double.parseDouble(table.getModel().getValueAt(row, 6).toString());
		double prevClose = -1;
		if(order == StockDataModel.CHRONOLOGICAL&&row!=0)
			prevClose = Double.parseDouble(table.getModel().getValueAt(row-1, 6).toString());
		else if(order == StockDataModel.REVERSE_CHRONOLOGICAL&&row!=table.getRowCount()-1)
			prevClose = Double.parseDouble(table.getModel().getValueAt(row+1, 6).toString());	
		
		if(prevClose == -1)
			c.setForeground(Color.BLACK);
		else if(prevClose<close)
			c.setForeground(new Color(20,160,20));
		else if(prevClose>close)
			c.setForeground(Color.RED);
		
		return c;
	}
	
	/* (non-Javadoc)
	 * Center text in the column
	 * @see javax.swing.JLabel#setHorizontalAlignment(int)
	 */
	@Override
	public void setHorizontalAlignment(int alignment) {
		// TODO Auto-generated method stub
		super.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	}
}
