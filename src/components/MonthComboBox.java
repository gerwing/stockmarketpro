package components;

import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * MonthComboBox is a regular JCombobox that contains months.
 * 
 * @author Gerwin Glorieux
 */
public class MonthComboBox extends JComboBox {

	private DefaultComboBoxModel dataModel;
	private String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};
	
	
	/**
	 * Constructs a new MonthComboBox with short month names ("Sep", "Aug", etc...)
	 */
	public MonthComboBox() {
		dataModel = new DefaultComboBoxModel(shortMonths);
		this.setModel(dataModel);
	}
	
	/**
	 * Returns number of the selected month as an integer.
	 * @return The number of the selected month in the year
	 */
	public int getMonthAsInt()
	{
		int sel = getSelectedIndex();
		return sel+1;
	}
	
	/**
	 * Set the selected item in the combobox to the current month
	 */
	public void setToday()
	{
		Calendar c = Calendar.getInstance();
		setSelectedIndex(c.get(Calendar.MONTH));
	}
}
