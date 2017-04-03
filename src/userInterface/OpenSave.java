package userInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import tmCore.Character;

public class OpenSave {
	
	private static String sep = System.getProperty("file.separator");
	private static String home = System.getProperty("user.home");
	
	public static void createFolderTm() {
		String path = home + sep + "tm" + sep;
		try {
			File file = new File(path);
			if(!file.exists()) {
				file.mkdir();
			} else {
				System.out.println(path + " already exists");
			}
		} catch (Exception e) {
			System.out.println("Can't create folder " + path + "\n" + e.getMessage());
		}
	}
	
	public static void Save() {
		String path = home + sep + "tm" + sep;
		JFileChooser saveFile = new JFileChooser();
		
		saveFile.setCurrentDirectory(new File(path));
		saveFile.addChoosableFileFilter(new fileFilter());
		saveFile.setAcceptAllFileFilterUsed(false);
		int returnVal = saveFile.showSaveDialog(TmMainUi.contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = saveFile.getSelectedFile().getAbsolutePath() + ".mdt";
			try {
				String svOut = "";

				svOut = svOut + (TmMainUi.symbols.size() - 2) + "\n" + (TmMainUi.symbols.size() - 1) + "\n";
				svOut = svOut + (TmMainUi.states.length - 1) + "\n";

				for(int i = 1; i < TmMainUi.states.length; i++) {
					for(int j = 1; j < TmMainUi.states[i].length; j++) {
						String cmd = TmMainUi.states[i][j].getCommand();
						if(cmd == null || cmd.equals("")) {
							svOut = svOut + "-\n";
						} else {
							svOut = svOut + cmd + "\n";
						}
					}
				}
				
				// write file
				FileWriter filew = new FileWriter(path);
				
				filew.write(svOut);
				filew.flush();
				filew.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(TmMainUi.contentPane,
						"Can't save " + path + "\n" + e.getMessage(),
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		} else {
			System.out.println("Save command cancelled by user");
		}
	}
	
	public static void Open() {
		String path = home + sep + "tm" + sep;
		
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new fileFilter());
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File(path));
		
		fc.setMultiSelectionEnabled(false);
		
		int returnVal = fc.showOpenDialog(TmMainUi.contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
            path = file.getAbsolutePath();
            try {
            	BufferedReader br = new BufferedReader(new FileReader(path));
            	String line;
            	
            	TmMainUi.getProgPanel().removeAll();
            	TmMainUi.getProgPanel().updateUI();
				
            	TmMainUi.strip = new Character('$', new StripCell());
            	TmMainUi.strip.insertRight('$', new StripCell());
				
            	TmMainUi.symbols = new Vector<String>();
            	TmMainUi.symbols.addElement("");
            	TmMainUi.symbols.addElement("$");
				
            	TmMainUi.col = 2;
            	TmMainUi.row = 2;
				
            	TmMainUi.states = null;
            	
            	// get numbers of symbols
            	line = br.readLine();
            	int symbols = (int) Double.parseDouble(line);
            	// get numbers of statuses
            	line = br.readLine();
            	line = br.readLine();
            	int statuses = (int) Double.parseDouble(line);
            	
            	// update UI
            	for(int i = 0; i < symbols; i++) {
            		TmMainUi.addSy();
            	}
            	for(int i = 0; i < statuses - 1; i++) {
            		TmMainUi.addSt();
            	}
            	
            	// write commands on the grid
            	for(int i = 1; i < TmMainUi.states.length; i++) {
            		for(int j = 1; j < TmMainUi.states[i].length; j++) {
            			line = br.readLine();
            			if(!line.equals("-")) {
            				TmMainUi.states[i][j].setText(line);
            				TmMainUi.states[i][j].setCaretPosition(0);
            				TmMainUi.states[i][j].updateUI();
            			}
            		}
            	}
            	
            	// close buffer
            	br.close();
            } catch(Exception e) {
            	JOptionPane.showMessageDialog(TmMainUi.contentPane,
						"Can't open " + path + "\n" + e.getMessage(),
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
            }
		} else {
			System.out.println("Open command cancelled by user");
		}
	}
}
