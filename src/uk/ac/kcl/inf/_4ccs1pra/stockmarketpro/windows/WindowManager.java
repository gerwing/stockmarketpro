package uk.ac.kcl.inf._4ccs1pra.stockmarketpro.windows;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import uk.ac.kcl.inf._4ccs1pra.stockmarketpro.gui.Stock_GUI;

/**
 * Class WindowManager
 * This class is an Arraylists that can hold Stock_GUI objects and provides methods to check whether a given lookup is already open on the computer
 * When a window is closed, the reference will be removed from this class
 * @author Gerwin
 *
 */
public class WindowManager extends ArrayList<Stock_GUI> implements WindowListener {

	/**
	 * Get the index in the Arraylist of this class of an existing Window
	 * The title of the window is used to check wether the window already exists
	 * If the window doesn't exist, this method returns -1
	 * @param title Title of the window to check
	 * @return
	 */
	public int getOpenWindowIndex(String s) {
		int result = -1;
		for(Stock_GUI sg:this)
		{
			if(sg.getTitle().equals(s))
				result = this.indexOf(sg);
		}
		return result;
	}
	/**
	 * Bring the window at a given index to the Front
	 * @param index Index of the window in the Arraylist
	 */
	public void bringWindowToFront(int i) {
		get(i).toFront();
	}
	
	/*
	 * Add a WindowEventListener (this class) to every object that is added to handle closing events
	 */
	@Override
	public boolean add(Stock_GUI e) {
		e.addWindowListener(this);
		return super.add(e);
	}
	
	/* 
	 * Closing the window event handlers
	 * Remove the window from the arraylist when it's closed
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		remove(indexOf(e.getSource()));
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
