package tmCore;

import java.util.Vector;

import exceptions.WrongCommandException;
import userInterface.StripCell;
import userInterface.TmMainUi;

public class InitializeComputation {
	
	public Vector<Status> ALL_STATUSES = new Vector<Status>();
	public Computation compute;

	public InitializeComputation() throws Exception {
		// initialize the program here
		
		// get all the commands
		for(int i = 1; i < TmMainUi.states.length; i++) {
			Status s = new Status();
			for(int j = 1; j < TmMainUi.states[1].length; j++) {
				if(TmMainUi.states[i][j].getCommand() != null
						&& TmMainUi.states[i][j].getCommand() != ""
						&& TmMainUi.states[i][j].getCommand().length() < 4 
						&& TmMainUi.states[i][j].getCommand().length() > 0) {
					throw new WrongCommandException(TmMainUi.states[i][j].getCommand(), i - 1);
				}
				
				s.addCell(TmMainUi.states[i][j]);
				s.addCommand(TmMainUi.states[i][j].getCommand());
				System.out.println("Command '" + TmMainUi.states[i][j].getCommand()
						+ "' added to " + i + "q (" + (i - 1) + ") at position " + (j - 1));
			}
			this.ALL_STATUSES.addElement(s);
		}
		
		// reset strip
		TmMainUi.strip = new Character('$', new StripCell());
		TmMainUi.strip.insertRight('$', new StripCell());
		
		// add the new input to the strip
		TmMainUi.txtInput.selectAll();
		String input = TmMainUi.txtInput.getSelectedText();
		if(input.equals("Type an input")) {
			
		} else {
			for(int i = input.length() - 1; i >= 0; i = i - 1) {
				TmMainUi.strip.insertRight(input.charAt(i), new StripCell());
			}
		}
		
		TmMainUi.updateExec(TmMainUi.getExePanel());
		
		TmMainUi.bg = TmMainUi.strip.STRIP_CELL.getBackground();
		TmMainUi.strip.STRIP_CELL.setBackground(TmMainUi.color);
	}
	
	// start the algorithm
	public void start() {
		Thread algoThread = new Thread() {
			public void run() {
				compute = new Computation();
			}
		};
		algoThread.start();
	}
	
}
