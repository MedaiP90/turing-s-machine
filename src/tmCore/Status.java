package tmCore;

import java.util.Vector;

import userInterface.Cell;


public class Status {
	
	private Vector<String> nextCommands;
	public Vector<Cell> CELL;
	
	public Status() {
		this.nextCommands = new Vector<String>();
		this.CELL = new Vector<Cell>();
	}
	
	public void addCell(Cell c) {
		this.CELL.addElement(c);
	}
	
	public void addCommand(String cmd) {
		if(cmd == null) {
			
		} else {
			cmd = cmd.toLowerCase();
		}
		this.nextCommands.addElement(cmd);
	}
	
	public int getNewStatus(String symbol) throws Exception {
		int nextSt = 0;
		int i = 0;
		
		if(symbol.equals("$")) {
			i = 0;
		} else {
			Computation.exc = "Not a valid symbol (" + symbol 
					+ ") in the strip";
			i = (int) Double.parseDouble(symbol) + 1;
		}
		
		String cmd = this.nextCommands.elementAt(i);
		if(cmd == null || cmd.equals("")) {
			System.out.println("Stop Status");
			return -1;
		}
		int j = 0;
		char c = cmd.charAt(j);
		String ns = "";
		while(c != 'q') {
			ns = ns + c;
			j++;
			c = cmd.charAt(j);
		}
		nextSt = (int) Double.parseDouble(ns);
		
		return nextSt;
	}
	
	public String newSymbol(String symbol) throws Exception {
		int i = 0;
		
		if(symbol.equals("$")) {
			i = 0;
		} else {
			i = (int) Double.parseDouble(symbol) + 1;
		}
		
		String newS = "";
		String cmd = this.nextCommands.elementAt(i);
		int j = 0;
		char c = cmd.charAt(j);
		while(c != 'q') {
			j++;
			c = cmd.charAt(j);
		}
		j++;
		c = cmd.charAt(j);
		newS = c + "";
		
		return newS;
	}
	
	public char direction(String symbol) throws Exception {
		int i = 0;
		
		if(symbol.equals("$")) {
			i = 0;
		} else {
			i = (int) Double.parseDouble(symbol) + 1;
		}
		
		String cmd = this.nextCommands.elementAt(i);
		int j = 0;
		char c = cmd.charAt(j);
		while(c != 'r' && c != 'l') {
			j++;
			c = cmd.charAt(j);
		}
		
		return c;
	}
}
