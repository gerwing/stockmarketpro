package stocks;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

/**
 * StockDataModel Class
 * Use this class to generate a tableModel that containes the data of a Stock Symbol retrieved from Yahoo  Historical Financial Data Service
 * Other methods are provided to get the title, average Adj. closing price and the maximum drawdown for the given stock symbol and period
 * @author Gerwin
 *
 */
public class StockDataModel extends DefaultTableModel {

	//url fields
	private String stockSymbol;
	private int dayFrom;
	private int monthFrom;
	private int yearFrom;
	private int dayTo;
	private int monthTo;
	private int yearTo;
	private String interval;
	private String intervalCode;
	final private String beginURL = "http://ichart.yahoo.com/table.csv?";
	private String finalURL;
	
	//order
	final static public int CHRONOLOGICAL = 0;
	final static public int REVERSE_CHRONOLOGICAL = 1;
	private int order;
	
	//data fields
	private Object[][] data;
	private String[] headers;
	
	/**
	 * Construct new StockDataModel given a period (from a date to a certain end date)
	 * @param s Stock symbol
	 * @param day Day from which to start looking up data
	 * @param month Month from which to start looking up data 
	 * @param year Year from which to start looking up data
	 * @param dayEnd Day to end lookup
	 * @param monthEnd Month to end lookup
	 * @param yearEnd Year to end lookup
	 * @param interval Periodic interval of data lookup
	 */
	public StockDataModel(String s,int day,int month,int year,int dayEnd, int monthEnd, int yearEnd, String interval, int order) {
		stockSymbol = s;
		dayFrom = day;
		monthFrom = month;
		yearFrom = year;
		dayTo = dayEnd;
		monthTo = monthEnd;
		yearTo = yearEnd;
		this.interval = interval;
		this.order = order;
		if(interval.equals("monthly"))
			intervalCode = "m";
		else if(interval.equals("weekly"))
			intervalCode = "w";
		else if(interval.equals("daily"))
			intervalCode = "d";
		finalURL = beginURL + "s=" + s + "&a=" + monthFrom + "&b=" + dayFrom + "&c=" + yearFrom + "&d=" + monthTo + "&e=" + dayTo + "&f=" + yearTo + "&g=" + intervalCode;
	}
	
	/**
	 * Get the data for the constructed url from the Yahoo service
	 * If the stock doesn't exist or there is no stock data found for the given period, an exception will be thrown
	 * Fire this method before assigning it to a table 
	 * @throws Exception Stock symbol invalid or there is no stack data for the given period
	 */
	public void getStockData() throws Exception {
		//get data for Stock
		String result = URLReader.readURL(finalURL);
		
		if(result != null)	//valid stock and period
		{
			ArrayList<String> rows = new ArrayList<String>(Arrays.asList(result.split("\n")));
			
			//initialize headers
			headers = rows.remove(0).split(",");
			
			//Check order and fill up data Array
			data = new String[rows.size()][7];//array containing all the stock data
			
			if (order == REVERSE_CHRONOLOGICAL)
			{
				for(int i = 0;i<rows.size();i++)
				{
					data[i] = rows.get(i).split(",");
				}
			}
			else if (order == CHRONOLOGICAL)
			{
				int b = 0;
				for(int i = rows.size()-1;i>=0;i--)
				{
					data[b++] = rows.get(i).split(",");
				}
			}
			
			//set Data to table model
			setDataVector(data, headers);
		}
		else	//invalid stock or period
			throw new Exception();
	}
	
	/**
	 * Returns the title string to identify the table or to use as title in the table window
	 * In the form of "GOOG: 2000-01-01 to 2012-02-24 (monthly)"
	 * @return The table title as a String
	 */
	public String getTableTitle() {
		//correct days and months for text
		String dayB = Integer.toString(dayFrom);
		String dayE = Integer.toString(dayTo);
		String monthB = Integer.toString(monthFrom+1);
		String monthE = Integer.toString(monthTo+1);
		String sOrder = "";
		if(order == CHRONOLOGICAL)
			sOrder = "chronological";
		else if (order == REVERSE_CHRONOLOGICAL)
			sOrder = "reverse chronological";
		if(dayFrom < 10)
			dayB = "0" + dayFrom;
		if(dayTo < 10)
			dayE = "0" +dayTo;
		if(monthFrom < 10)
			monthB = "0"  + (monthFrom+1);
		if(monthTo < 10)
			monthE = "0" + (monthTo+1);
		
		//return result
		return stockSymbol +": " + yearFrom +"-" + monthB + "-" + dayB + " to " + yearTo + "-" + monthE + "-" + dayE + " (" + interval + ", " + sOrder + ")";
	}
	
	/**
	 * Returns the Adjusted Closing price average for this stock data lookup
	 * @return The Adjusted Closing price average as a String
	 */
	public String getCloseAverage() {
		double total = 0;
		for(int i = 0; i<data.length;i++)
		{
			total += Double.parseDouble((String)data[i][6]);
		}
		DecimalFormat f = new DecimalFormat("#.##");
		return f.format(total/data.length);
	}
	
	/**
	 * Returns the Maximum Drawdown for this stock data lookup
	 * @return The Maximum Drawdown as a String
	 */
	public String getMaxDrawdown() {
		double maxDD = 0;
		double peak = -99999;
		if(order == REVERSE_CHRONOLOGICAL) //reverse chronological data
		{
			for(int i = data.length-1;i>=0; i--)
			{
				double close = Double.parseDouble((String)data[i][6]);
				if (close > peak) 
					peak = close;
				else
				{
					double dropd = (peak - close);
					if (dropd > maxDD)
						maxDD = dropd;
				}	
			}
		}
		else if(order == CHRONOLOGICAL) //Chronological data
		{
			for(int i = 0;i<data.length; i++)
			{
				double close = Double.parseDouble((String)data[i][6]);
				if (close > peak) 
					peak = close;
				else
				{
					double dropd = (100.0 * (peak - close) / peak);
					if (dropd > maxDD)
						maxDD = dropd;
				}	
			}
		}
		DecimalFormat f = new DecimalFormat("#.##");
		return f.format(maxDD);
	}
	
	/**
	 * Returns the order of the stock data; Chronological or Reverse Chronological
	 * @return Order as int
	 */
	public int getOrder() {
		return order;
	}
}
