package tmCore;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JOptionPane;

import userInterface.StripCell;
import userInterface.TmMainUi;

public class Computation {
	
	public static String exc = "";
	public int n = 0;
	public static boolean STOP = false;

	@SuppressWarnings("static-access")
	public Computation() {
		System.out.println("Program running");
		
		Character sc = TmMainUi.strip;
		Vector<Status> sts = TmMainUi.init.ALL_STATUSES;
		
		int iter = 1;
		int del = TmMainUi.delay;
		double delTime = Math.pow(3, del);
		int i = 0;
		int old = 0;
		
		this.STOP = false;
		
		// execute the algorithm until Stop set to true
		while(!this.STOP) {
			String sym = sc.character + "";
			System.out.println("Iteration " + iter + ":");
			System.out.println("\tFound: " + sym);
			try {
				// verify that the status exists
				sts.elementAt(this.n);
				// coloring the cell
				i = 0;
				if(sym.equals("$")) {
					i = 0;
				} else {
					Computation.exc = "Not a valid symbol (" + sym 
							+ ") in the strip";
					i = (int) Double.parseDouble(sym) + 1;
				}
				sts.elementAt(this.n).CELL.elementAt(i).setBackground(TmMainUi.color);
				sts.elementAt(this.n).CELL.elementAt(i).updateUI();
				TmMainUi.getProgPanel().scrollRectToVisible(sts.elementAt(this.n).
						CELL.elementAt(i).getBounds());
				TmMainUi.getProgPanel().updateUI();
				this.exc = "Not a number "
						+ "at status " + (n /*+ 1*/) + "q";
				// get the next status
				int nc = sts.elementAt(this.n).getNewStatus(sym);
				if(nc > -1) {
					this.exc = "Not a valid symbol "
							+ "at status " + (n /*+ 1*/) + "q";
					// get the new symbol
					String ns = sts.elementAt(this.n).newSymbol(sym);
					System.out.println("\tNew symbol: " + ns);
					this.exc = "Not a valid direction "
							+ "at status " + (n /*+ 1*/) + "q";
					// get the direction to move to
					char d = sts.elementAt(this.n).direction(sym);
					System.out.println("\tDirection: " + d);
					sc.modifyValue(ns.charAt(0));
					sc.STRIP_CELL.setBackground(TmMainUi.bg);
					if(d == 'l') {
						// add a new blank character to the strip if
						// there are no more character
						if(sc.stripPrevious() == null) {
							sc.insertLeft('$', new StripCell());
							TmMainUi.strip = sc.stripPrevious();
							TmMainUi.updateExec(TmMainUi.getExePanel());
						} 
						sc = sc.stripPrevious();
					} else if(d == 'r') {
						// add a new blank character to the strip if
						// there are no more character
						if(sc.stripNext() == null) {
							sc.insertRight('$', new StripCell());
							TmMainUi.updateExec(TmMainUi.getExePanel());
						} 
						sc = sc.stripNext();
					}
					sc.STRIP_CELL.setBackground(TmMainUi.color);
					TmMainUi.getExePanel().scrollRectToVisible(sc.STRIP_CELL.getBounds());
					TmMainUi.getExePanel().updateUI();
					old = this.n;
					this.n = nc; // - 1;
					System.out.println("\tNext status: " + this.n);
					this.exc = "Not a valid status (" + nc 
							+ "q) at status " + (old /*+ 1*/) + "q";
					iter++;
					// delay
					for(int delay = 0; delay < delTime; delay++) {
						// delay the execution to see passages 
					}
					sts.elementAt(old).CELL.elementAt(i).setBackground(Color.WHITE);
					sts.elementAt(old).CELL.elementAt(i).updateUI();
				} else {
					// found a null command: end of algorithm
					this.STOP = true;
					TmMainUi.btnEnabler(false, true, false, true, true, true);
					sts.elementAt(this.n).CELL.elementAt(i).setBackground(Color.WHITE);
					sts.elementAt(this.n).CELL.elementAt(i).updateUI();
					System.out.println("Computation finished successfully");
					JOptionPane.showMessageDialog(TmMainUi.contentPane,
							"Computation finished successfully\nIterations: " + iter,
						    "Success",
						    JOptionPane.PLAIN_MESSAGE);
				}
			} catch (Exception e) {
				// catched some error: stopping execution
				this.STOP = true;
				TmMainUi.btnEnabler(false, true, false, true, true, true);
				sts.elementAt(old).CELL.elementAt(i).setBackground(Color.WHITE);
				sts.elementAt(old).CELL.elementAt(i).updateUI();
				System.out.println(exc);
				e.printStackTrace();
				JOptionPane.showMessageDialog(TmMainUi.contentPane,
						exc,
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
