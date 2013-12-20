package components;

import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * DayCombobox is a regular JCombobox that contains days of the month. After initialization 31 days are shown.
 * 
 * @author Gerwin Glorieux
 */
public class DayComboBox extends JComboBox {

	private DefaultComboBoxModel dataModel;
	static private String[] LONG = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
									"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	static private String[] SHORT = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
									"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
	static private String[] FEBRUARY = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
										"16","17","18","19","20","21","22","23","24","25","26","27","28"};
	static private String[] LEAP_FEBRUARY = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
											"16","17","18","19","20","21","22","23","24","25","26","27","28","29"};
	
	/**
	 * Constructs a new DayCombobox with 31 days
	 */
	public DayComboBox() {
		dataModel = new DefaultComboBoxModel(LONG);
		this.setModel(dataModel);
	}
	
	/**
	 * Set the amount of days to be shown, after the month and year provided. This also checks for leap year
	 * 
	 * @param month Month to determine the amount of days
	 * @param year Year to determine the amount of days in february
	 */
	public void setDays(int month,int year)
	{
		int selected = getSelectedIndex();
		
		//February
		if(month == 2)
		{
			//leap year
			if(((year % 4 == 0)&&(year % 100 != 0))|| (year % 400 == 0)){
				dataModel = new DefaultComboBoxModel(LEAP_FEBRUARY);
				this.setModel(dataModel);
				//set back old selection
				if(selected>28)
					setSelectedIndex(28);
				else
					setSelectedIndex(selected);
			}
			else
			{
				dataModel = new DefaultComboBoxModel(FEBRUARY);
				this.setModel(dataModel);
				//set back old selection
				if(selected>27)
					setSelectedIndex(27);
				else
					setSelectedIndex(selected);
			}
		}
		//Other months
		else if(month==4||month==6||month==9||month==11)
		{
			dataModel = new DefaultComboBoxModel(SHORT);
			this.setModel(dataModel);
			//set back old selection
			if(selected!=30)
				setSelectedIndex(selected);
			else
				setSelectedIndex(29);
		}
		else
		{
			dataModel = new DefaultComboBoxModel(LONG);
			this.setModel(dataModel);
			setSelectedIndex(selected);
		}
	}
	
	/**
	 * Set the selected item in the combobox to the current day
	 */
	public void setToday()
	{
		Calendar c = Calendar.getInstance();
		setSelectedIndex(c.get(Calendar.DAY_OF_MONTH)-1);
	}
}
