package uk.ac.kcl.inf._4ccs1pra.stockmarketpro.components;

import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * YearCombobox is a regular JCombobox that contains year numbers. Construct a new YearCombobox with the begin and end year as arguments
 * 
 * @author Gerwin Glorieux
 */
public class YearComboBox extends JComboBox {

	private DefaultComboBoxModel dataModel;
	private String[] years;
	static private int BEGIN_YEAR;
	static private int END_YEAR;
	
	/**
	 * Construct a new Year Combobox containing the years from begin_year until end_year
	 * @param begin_year Begin year of the Combobox
	 * @param end_year End year of the Combobox
	 */
	public YearComboBox(int b,int e) {
		BEGIN_YEAR = b;
		END_YEAR = e;
		
		years = new String[END_YEAR-BEGIN_YEAR+1];
		for(int i = 0;i<=(END_YEAR-BEGIN_YEAR);i++)
		{
			years[i] = Integer.toString(BEGIN_YEAR+i);
		}
		dataModel = new DefaultComboBoxModel(years);
		this.setModel(dataModel);
	}
	
	/**
	 * @return Selected year as an integer
	 */
	public int getYearAsInt()
	{
		return Integer.parseInt((String)getSelectedItem());
	}
	
	/**
	 * Set the selected year to the year that is passed as an argument
	 * @param year Year to set the combobox to
	 */
	public void setYear(int year)
	{
		int ind = year-BEGIN_YEAR;
		//has to be equal or greater than begin year
		if(ind >=0)
		{
			setSelectedIndex(ind);
		}
	}
	
	/**
	 * Set the selected item in the combobox to the current year
	 */
	public void setToday()
	{
		Calendar c = Calendar.getInstance();
		int ind = c.get(Calendar.YEAR) - BEGIN_YEAR;
		//check for errors
		if(ind >=0)
		setSelectedIndex(ind);
	}
}
