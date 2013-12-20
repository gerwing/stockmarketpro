package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import components.DayComboBox;
import components.MonthComboBox;
import components.YearComboBox;
import stocks.StockDataModel;
import windows.WindowManager;

/**
 * Class that constructs and shows the main graphical user interface of the program
 * The graphical user interface consists of forms that allow the users to lookup historical data from a given stock symbol
 * Closing this window will close the program
 * @author Gerwin
 *
 */
public class Main_GUI extends JFrame implements ActionListener {

	private JTextField tfStock;
	private DayComboBox cbBeginDay;
	private MonthComboBox cbBeginMonth;
	private YearComboBox cbBeginYear;
	private DayComboBox cbEndDay;
	private MonthComboBox cbEndMonth;
	private YearComboBox cbEndYear;
	private JComboBox cbInterval;
	private ButtonGroup bgOrder;
	private WindowManager windowManager;
	
	/**
	 * Constructor method, Constructs a new Main GUI window
	 * This GUI uses a WindowManager to manage the open StockTables
	 * @param windowmanager WindowManager object that manages opened windows, closing windows
	 */
	public Main_GUI(WindowManager wm){
		super("Stock Market Pro");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		windowManager = wm;
		initWidgets();
	}

	private void initWidgets() {
		/*
		 * Initialize forms
		 */
		//stock textfield
		tfStock = new JTextField(10);
		
		//date forms
		//begin date
		cbBeginDay = new DayComboBox();
		cbBeginMonth = new MonthComboBox();
		cbBeginYear = new YearComboBox(1970,2012);
		cbBeginYear.setYear(2000);
		
		//end date
		cbEndDay = new DayComboBox();
		cbEndDay.setToday();//set to current day
		cbEndMonth = new MonthComboBox();
		cbEndMonth.setToday();//set to current month
		cbEndYear = new YearComboBox(1970, Calendar.getInstance().get(Calendar.YEAR));
		cbEndYear.setToday();//set to current year
		
		//interval form
		String[] intervals = {"daily", "weekly", "monthly"};
		cbInterval = new JComboBox(intervals);
		cbInterval.setSelectedIndex(2);
		
		//order buttons
		bgOrder = new ButtonGroup();
		JRadioButton rb1 = new JRadioButton("Reverse Chronological");
		rb1.setActionCommand(StockDataModel.REVERSE_CHRONOLOGICAL + "");
		rb1.setSelected(true);
		bgOrder.add(rb1);
		JRadioButton rb2 = new JRadioButton("Chronological");
		rb2.setActionCommand(StockDataModel.CHRONOLOGICAL + "");
		bgOrder.add(rb2);
		
		//set Item changed Event Listeners for Date Comboboxes
		//to allow automatic updates of the amount of days
		ItemListener il1 = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				cbBeginDay.setDays(cbBeginMonth.getMonthAsInt(), cbBeginYear.getYearAsInt());
			}
		};
		cbBeginMonth.addItemListener(il1);
		cbBeginYear.addItemListener(il1);
		
		ItemListener il2 = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				cbEndDay.setDays(cbEndMonth.getMonthAsInt(), cbEndYear.getYearAsInt());
			}
		};
		cbEndMonth.addItemListener(il2);
		cbEndYear.addItemListener(il2);
		
		/*
		 * Labels Panel
		 */
		JPanel pnlLabels = new JPanel(new GridLayout(6,1));
		pnlLabels.setPreferredSize(new Dimension(100,0));
		JLabel lb1 = new JLabel("Stock Symbol:",JLabel.RIGHT);
		JLabel lb2 = new JLabel("Begin:",JLabel.RIGHT);
		JLabel lb3 = new JLabel("End:",JLabel.RIGHT);
		JLabel lb4 = new JLabel("Interval:",JLabel.RIGHT);
		pnlLabels.add(lb1);
		pnlLabels.add(lb2);
		pnlLabels.add(lb3);
		pnlLabels.add(lb4);
		
		/*
		 * Forms Panel
		 */
		JPanel pnlForms = new JPanel();
		pnlForms.setLayout(new GridLayout(6,1));
		
		JPanel pnlTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		//stock textfield
		pnlTemp.add(tfStock);
		pnlForms.add(pnlTemp);
		
		//begin date
		pnlTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTemp.add(cbBeginDay);
		pnlTemp.add(cbBeginMonth);
		pnlTemp.add(cbBeginYear);
		pnlForms.add(pnlTemp);
		
		//end date
		pnlTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTemp.add(cbEndDay);
		pnlTemp.add(cbEndMonth);
		pnlTemp.add(cbEndYear);
		pnlForms.add(pnlTemp);
		
		//interval
		pnlTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTemp.add(cbInterval);
		pnlForms.add(pnlTemp);
		 
		//order Selection
		pnlTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTemp.add(rb1);
		pnlTemp.add(rb2);
		pnlForms.add(pnlTemp);
		
		//lookup button
		pnlTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton btnLookup = new JButton("Lookup");
		btnLookup.addActionListener(this);	
		pnlTemp.add(btnLookup);
		pnlForms.add(pnlTemp);
				
		/*
		 * Center Panel
		 */
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new BorderLayout());
		pnlCenter.add(pnlLabels,BorderLayout.WEST);
		pnlCenter.add(pnlForms,BorderLayout.CENTER);
		
		/*
		 * Content Pane
		 */
		getContentPane().add(pnlCenter);
		
		
		pack();
		setSize(420, 230);
		setResizable(false);
		setVisible(true);
		
	}

	/*
	 * LOOKUP BUTTON is clicked
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//check period and stock symbol
		if(isValidStockSymbol(tfStock.getText())&&isValidPeriod()) {
			//new StockDataModel that takes all the arguments from the form
			StockDataModel dm = new StockDataModel(tfStock.getText(),cbBeginDay.getSelectedIndex()+1,
													cbBeginMonth.getSelectedIndex(),cbBeginYear.getYearAsInt(),
													cbEndDay.getSelectedIndex()+1,cbEndMonth.getSelectedIndex(),
													cbEndYear.getYearAsInt(), (String)cbInterval.getSelectedItem(),
													Integer.parseInt(bgOrder.getSelection().getActionCommand()));
			
			//check for open windows
			if(windowManager.getOpenWindowIndex(dm.getTableTitle())!=-1)	//window exists, bring to front
				windowManager.bringWindowToFront(windowManager.getOpenWindowIndex(dm.getTableTitle()));
			else	//window doesnt exist, make new window
				windowManager.add(new Stock_GUI(dm));
		}
		//invalid period or stock symbol
		else { 
			if(!isValidStockSymbol(tfStock.getText()))	//invalid stock symbol
				JOptionPane.showMessageDialog(this, ("'" + tfStock.getText() + "' must be maximum 8 characters in length (uppercase, digits and periods)"), "Invalid Input", JOptionPane.ERROR_MESSAGE);
			else	//invalid period
				JOptionPane.showMessageDialog(this, "Make sure the begin date is before the end date", "Invalid Input", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	/*
	 * Check stock symbol for length, type of characters
	 */
	private boolean isValidStockSymbol(String s){
		Pattern p = Pattern.compile("[A-Z0-9.]{0,8}");
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	/*
	 * Check if the dates don't overlap
	 */
	private boolean isValidPeriod() {
		if(cbEndYear.getYearAsInt()>cbBeginYear.getYearAsInt())//earlier year
			return true;
		else if(cbEndYear.getYearAsInt()==cbBeginYear.getYearAsInt())//same year
		{
			if(cbEndMonth.getMonthAsInt()>cbBeginMonth.getMonthAsInt())//earlier month
				return true;
			else if (cbEndMonth.getMonthAsInt()==cbBeginMonth.getMonthAsInt())//same month
			{
				if(cbEndDay.getSelectedIndex()>cbBeginDay.getSelectedIndex())//earlier day
					return true;
				else 
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}


}
