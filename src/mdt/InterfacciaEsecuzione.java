package mdt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
//import mdt.Esegui;
import javax.swing.*;

public class InterfacciaEsecuzione extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1515871915300417720L;
	
	public static JFrame mdtFrame;
	public static int nSimboli;
	public static JButton nuovo;
	public static JButton avvia;
	public static JButton reset;
	public static JButton stop;
	public static JPanel p0;
	public static JPanel p1;
	public static JPanel pb;
	public static JScrollPane scroll;
	public static JTextField uInput;
	public static JLabel input = new JLabel(" Input:");
	public static JMenuItem salva;
	public static JMenu file;
	public static JMenu options;
	public static JMenuBar menuBar;
	public static JTextField vel;
	public static JLabel velox = new JLabel(" Delay:");
	public static JCheckBox passaggi;
	
	public static Vector<JTextField[]> celle;
	
	public static int nStati = 0;
	
	public static String[] listaSimboli;
	
	public static Thread thread;
	
	public InterfacciaEsecuzione(int numero, boolean carica) {
		
		nSimboli = numero + 1;
		
		celle = new Vector<JTextField[]>();
		
		mdtFrame = new JFrame("The Turing's Machine");
		mdtFrame.setIconImage((new ImageIcon(getClass().getResource("/mdt/icona.png"))).getImage());
		
		mdtFrame.add(GUImdt(), "Center");
		
		mdtFrame.pack();
		mdtFrame.setVisible(true);
		mdtFrame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		
		mdtFrame.setSize((screenSize.width - 5),450);
		int h = mdtFrame.getHeight()/2;
		//int w = mdtFrame.getWidth()/2;
		
		mdtFrame.setLocation(5, screenSize.height - (h * 2) - 10);
		mdtFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mdtFrame.addWindowListener(new winListner());
		
		if (carica) {
			int ns = MdtInterfaccia.numStati;
			for (int i = 1; i < ns; i++) {
				aggiungi();
			}
			
			try {
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(new FileReader(MdtInterfaccia.path));
				
			    String line;
			    
				line = br.readLine();
				line = br.readLine();
			    
				int cells = (int) Double.parseDouble(line);
				
				line = br.readLine();
				line = br.readLine();
				
			    int v = 1;
			    int a = 1;
				
			    while (line != null) {
			    	if (a == cells + 1) {
			        	v ++;
			        	a = 1;
			        }
			    	String temp;
			    	if (line.charAt(0) != '-') {
			    		temp = line;
			    	} else {
			    		temp = null;
			    	}
			        celle.elementAt(v)[a].setText(temp);
			        a ++;
			        line = br.readLine();
			    }
			    
			} catch (IOException e) {
				JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
						"It was impossible to load " + MdtInterfaccia.path,
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			    return;
			}
		}
	}
	
	private JComponent GUImdt() {
		menuBar = new JMenuBar();
		file = new JMenu("File");
		salva = new JMenuItem("Save project");
		options = new JMenu("Options");
		
		salva.addActionListener(new save());
		
		passaggi = new JCheckBox("Show passages");
		passaggi.setSelected(false);
		
		passaggi.setIcon(new ImageIcon(getClass().getResource("/mdt/defaultIcon.png")));
		passaggi.setPressedIcon(new ImageIcon(getClass().getResource("/mdt/pressedIcon.png")));
		passaggi.setSelectedIcon(new ImageIcon(getClass().getResource("/mdt/pressedIcon.png")));
		
		options.add(passaggi);
		
		file.add(salva);
		menuBar.add(file);
		menuBar.add(options);
		mdtFrame.setJMenuBar(menuBar);
		
		p0 = new JPanel();
		p1 = new JPanel(new GridLayout(2, 1));
		
		pb = new JPanel();
		
		p0.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		nuovo = new JButton("Add status");
		nuovo.addActionListener(new nuovo());
		
		avvia = new JButton("Start");
		avvia.addActionListener(new avviaMdt());
		
		reset = new JButton("Delate grid");
		reset.addActionListener(new resetMdt());
		
		stop = new JButton("Stop");
		stop.addActionListener(new stopMdt());
		stop.setEnabled(false);
		
		uInput = new JTextField(20);
		vel = new JTextField(4);
		vel.setText("0");
		
		JTextField[] cella1 = new JTextField[nSimboli+1];
		JTextField[] cella2 = new JTextField[nSimboli+1];
		celle.addElement(cella1);
		celle.addElement(cella2);
		
		listaSimboli = new String[nSimboli+1];
		
		JPanel temp;
		
		JTextField txtEmpty = new JTextField("");
		txtEmpty.setEditable(false);
		celle.elementAt(0)[0] = txtEmpty;
		listaSimboli[1] = "$";
		JTextField txt$ = new JTextField("$");
		txt$.setEditable(false);
		celle.elementAt(0)[1] = txt$;
		for(int i = 2; i < nSimboli + 1; i++) {
			JTextField txt = new JTextField((i - 2) + "");
			listaSimboli[i] = (i - 2) + "";
			txt.setEditable(false);
			celle.elementAt(nStati)[i] = txt;
		}
		temp = new JPanel(new GridLayout(1, nSimboli+1));
		for(int i = 0; i < nSimboli + 1; i++) {
			temp.add(celle.elementAt(nStati)[i]);
		}
		p1.add(temp);
		nStati ++;
		JTextField txtq0 = new JTextField("0q");
		txtq0.setEditable(false);
		celle.elementAt(1)[0] = txtq0;
		for(int i = 1; i < nSimboli + 1; i++) {
			JTextField txt = new JTextField(8);
			celle.elementAt(nStati)[i] = txt;
		}
		temp = new JPanel(new GridLayout(1, nSimboli+1));
		for(int i = 0; i < nSimboli + 1; i++) {
			temp.add(celle.elementAt(nStati)[i]);
		}
		p1.add(temp);
		nStati ++;

		pb.add(avvia);
		pb.add(stop);
		pb.add(nuovo);
		pb.add(reset);
		pb.add(input);
		pb.add(uInput);
		pb.add(velox);
		pb.add(vel);
		
		
		mdtFrame.add(pb, BorderLayout.NORTH);
		p0.add(p1, "center");
		
		scroll = new JScrollPane (p0);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JLabel label = new JLabel("Cossutta, Bernardis, Innocente");
		label.setForeground(Color.gray);
		mdtFrame.add(label, BorderLayout.SOUTH);
		
		return scroll;
	}
	
	public class save implements ActionListener {
		public void actionPerformed(ActionEvent j) {
			String path;
			JFileChooser saveFile = new JFileChooser();
			
            saveFile.showSaveDialog(null);
            
            try {
		        path = saveFile.getSelectedFile().getAbsolutePath() + ".mdt";
		        System.out.println("Saveing: " + path);
		        
		        File fileName = new File(path);
		        
		        String svOut = "";
		        
		        svOut = (nSimboli - 1) + "\n" + (celle.elementAt(0).length - 1) + "\n" + (nStati - 1) + "\n";
		        
		        for (int i = 1; i < celle.size(); i ++) {
		        	for (int k = 1; k < celle.elementAt(i).length; k ++) {
		        		celle.elementAt(i)[k].selectAll();
		        		if (celle.elementAt(i)[k].getSelectedText() == null) {
		        			svOut = svOut + "-\n";
		        		} else {
		        			svOut = svOut + celle.elementAt(i)[k].getSelectedText() + "\n";
		        		}
		        	}
		        }
		        
		        try {
		        	FileWriter filew = new FileWriter(path);
					
					filew.write(svOut);
					filew.flush();
					filew.close();
			
				} catch (IOException e) {
					JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
							"It was impossible to save " + fileName.getName(),
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				}
		        
            } catch (NullPointerException e) {
            	System.out.println("Save command cancelled by user");
            }
		}
	}
	
	public class nuovo implements ActionListener {
		public void actionPerformed(ActionEvent j) {
			aggiungi();
		}
	}
	
	void aggiungi () {
			int h = mdtFrame.getHeight();
			int w = mdtFrame.getWidth();
			
			JPanel temp;
			
			Component[] inception = new Component[nStati];
			inception = p1.getComponents();
			
			JTextField[] cella1 = new JTextField[nSimboli+1];
			celle.addElement(cella1);
			
			JTextField txtqn = new JTextField((nStati - 1) + "q");
			txtqn.setEditable(false);
			celle.elementAt(nStati)[0] = txtqn;
			
			for(int i = 1; i < nSimboli+1; i++) {
				JTextField txt = new JTextField(8);
				celle.elementAt(nStati)[i] = txt;
			}
			temp = new JPanel(new GridLayout(1, nSimboli+1));
			for(int i = 0; i < nSimboli+1; i++) {
				temp.add(celle.elementAt(nStati)[i]);
			}
			nStati ++;
			
			p0.remove(p1);
			p1 = new JPanel(new GridLayout(nStati, 1));
			
			for (int i = 0; i < inception.length; i++) {
				p1.add(inception[i]);
			}
			p1.add(temp);
			p1.update(p1.getGraphics());
			p0.add(p1);
			p0.update(p0.getGraphics());
			
			mdtFrame.pack();
			mdtFrame.setSize(w,h);
	}
	
	public class resetMdt implements ActionListener {
		public void actionPerformed(ActionEvent j) {
			nStati = 0;
			
			mdtFrame.remove(pb);
			mdtFrame.remove(scroll);
			
			mdtFrame.add(GUImdt(), "Center");
			
			mdtFrame.pack();
			mdtFrame.setSize(650,450);
		}
	}
	
	public class stopMdt implements ActionListener {
		//@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent j) {
			thread.interrupt();
			avvia.setEnabled(true);
			stop.setEnabled(false);
		}
	}
	
	public class avviaMdt implements ActionListener {
		//@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent j) {
			//uInput.selectAll();
			try {
				mac.dispose();
			} catch (NullPointerException e) {
				
			}
			vel.selectAll();
			try {
				int delay = (int)Double.parseDouble(vel.getText());
				checkNumber(delay);
			} catch (NumberFormatException | WrongNumberException e) {
				JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
						"The delay must be a number between 0 and 100.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			interfacciaMacchina();
			Runnable runnable = new ValidateThread();
            thread = new Thread(runnable);
            thread.start();
			//Esegui exe = new Esegui(uInput.getSelectedText());
            avvia.setEnabled(false);
			stop.setEnabled(true);
		}

		private void checkNumber(int delay) throws WrongNumberException {
			if (delay < 0 || delay > 100) {
				throw new WrongNumberException("The number " + delay + " is invalid");
			}
		}
	}
	
	//finestra di esecuzione
	public static JFrame mac;
	public static JTextArea esecuzione;
	
	public void interfacciaMacchina() {
		mac = new JFrame("Execution");
		mac.setIconImage((new ImageIcon(getClass().getResource("/mdt/icona.png"))).getImage());
		
		esecuzione = new JTextArea();
		
		esecuzione.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (esecuzione);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		scroll.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		mac.add(scroll);
		
		mac.pack();
		mac.setVisible(true);
		mac.setResizable(false);
		mac.setLocation(5, 20);
		Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		mac.setSize((screenSize.width - 5),270);
		mac.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
}
