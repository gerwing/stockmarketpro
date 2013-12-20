package uk.ac.kcl.inf._4ccs1pra.stockmarketpro.gui;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * CellRenderer that centers text in the Cells
 * @author Gerwin
 *
 */
public class DefaultCellRenderer extends DefaultTableCellRenderer {
	/* (non-Javadoc)
	 * Center text in the Cells
	 * @see javax.swing.JLabel#setHorizontalAlignment(int)
	 */
	@Override
	public void setHorizontalAlignment(int alignment) {
		// TODO Auto-generated method stub
		super.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	}
}
