package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import tmCore.Character;
import tmCore.Computation;
import tmCore.InitializeComputation;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class TmMainUi extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
	private static JPanel panel;
	private static JPanel panel_1;
	
	public static Character strip;
	public static JTextField txtInput;
	private static JButton btnStart;
	private static JButton btnStop;
	private static JButton btnAddSymbol;
	private static JButton btnAddStatus;
	
	private static Cell[][] old;
	private static JButton btnInit;
	
	public static int col;
	public static  int row;
	
	public static Vector<String> symbols = new Vector<String>();
	public static Cell[][] states;
	
	public static Color bg;
	public static InitializeComputation init;
	private static JButton btnEdit;
	
	private static String sep = System.getProperty("file.separator");
	private static String home = System.getProperty("user.home");
	private static String path = home + sep + "tm" + sep + "settings.tms";
	
	public static int delay = 18;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OpenSave.createFolderTm();
			// load settings
			try {
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line = br.readLine();
				delay = (int) Double.parseDouble(line);
				br.close();
			} catch (Exception e) {
				delay = 18;
				JOptionPane.showMessageDialog(null,
						"No setting found, delay set to 18.\nOpen 'File > Settings' "
						+ "to set preferred delay\n" + e.getMessage(),
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
			}
			TmMainUi frame = new TmMainUi();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Can't start the Turing's machine\n" + e.getMessage(),
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Create the frame.
	 */
	public TmMainUi() {
		strip = new Character('$', new StripCell());
		strip.insertRight('$', new StripCell());
		
		symbols.addElement("");
		symbols.addElement("$");
		
		col = 2;
		row = 2;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(TmMainUi.class.getResource("/userInterface/icona.png")));
		setTitle("The Turing Machine");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 430);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.removeAll();
				panel_1.updateUI();
				
				strip = new Character('$', new StripCell());
				strip.insertRight('$', new StripCell());
				
				symbols = new Vector<String>();
				symbols.addElement("");
				symbols.addElement("$");
				
				col = 2;
				row = 2;
				
				states = null;
				
				updateExec(panel);
				updateProg(panel_1);
			}
		});
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenSave.Save();
			}
		});
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mnFile.add(mntmLoad);
		
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenSave.Open();
			}
		});
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnFile.add(mntmSettings);
		
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings s = new Settings();
				s.createSettings();
			}
		});
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHowTo = new JMenuItem("How to");
		mnHelp.add(mntmHowTo);
		
		mntmHowTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HowTo ht = new HowTo();
				ht.createHowTo();
			}
		});
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(TmMainUi.this,
						"Author: Cossutta Leonardo\n"
						+ "Email: bolzenxl@outlook.it\n"
						+ "Copyright (c) 2017, Cossutta Leonardo\n\n"
						+ "A Turing machine is an abstract machine that manipulates symbols on a strip\n"
						+ "of tape according to a table of rules; to be more exact, it is a mathematical\n"
						+ "model of computation that defines such a device. Despite the model's\n"
						+ "simplicity, given any computer algorithm, a Turing machine can be constructed\n"
						+ "that is capable of simulating that algorithm's logic.\n"
						+ "(Wikipedia)",
					    "About",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.9);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		JLabel lblExecutionlabel = new JLabel("Execution:");
		scrollPane.setColumnHeaderView(lblExecutionlabel);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);
		
		panel_1 = new JPanel();
		scrollPane_1.setViewportView(panel_1);
		
		updateExec(panel);
		updateProg(panel_1);
		
		JLabel lblSetlabel = new JLabel("Programming panel:");
		scrollPane_1.setColumnHeaderView(lblSetlabel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnStart = new JButton("Start");
		toolBar.add(btnStart);
		
		btnInit = new JButton("Initialize");
		toolBar.add(btnInit);
		
		btnEdit = new JButton("Edit");
		toolBar.add(btnEdit);
		
		btnStop = new JButton("Stop");
		toolBar.add(btnStop);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		toolBar.add(panel_2);
		
		btnAddStatus = new JButton("Add status");
		panel_2.add(btnAddStatus);
		
		btnAddSymbol = new JButton("Add symbol");
		panel_2.add(btnAddSymbol);
		
		txtInput = new JTextField();
		txtInput.setHorizontalAlignment(SwingConstants.CENTER);
		txtInput.setText("Type an input");
		panel_2.add(txtInput);
		txtInput.setColumns(25);
		
		btnEnabler(false, true, false, true, true, true);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEnabler(false, false, true, false, false, false);
				
				// start computation
				Thread algoThread = new Thread() {
					public void run() {
						init.start();
					}
				};
				algoThread.start();
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Computation.STOP = true;
				
				btnEnabler(false, true, false, true, true, true);
			}
		});
		
		btnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					init = new InitializeComputation();
					
					btnEnabler(true, false, false, false, false, false);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(TmMainUi.this,
							ex.getMessage(),
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnAddSymbol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSy();
			}
		});
		
		btnAddStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSt();
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEnabler(false, true, false, true, true, true);
			}
		});
		
		txtInput.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtInput.setText("");
			}
			public void focusLost(FocusEvent e) {
				txtInput.selectAll();
				String input = txtInput.getSelectedText();
				if(input == null) {
					txtInput.setText("Type an input");
				}
		    }

		});
	}
	
	public static void addSt() {
		row++;
		updateProg(panel_1);
	}
	
	public static void addSy() {
		if(col == 12) {
			JOptionPane.showMessageDialog(contentPane,
					"Maximum 10 symbols",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		col++;
		symbols.addElement((col - 3) + "");
		updateProg(panel_1);
	}
	
	public static void updateProg(JPanel p) {
		p.removeAll();
		p.updateUI();
		
		old = states;
		states = new Cell[row][col];
		
		p.setLayout(new GridLayout(row, col));
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				int[] coord = {i, j};
				String txt = "";
				if(i == 0) {
					txt = symbols.elementAt(j);
				} else if(j == 0) {
					txt = (i - 1) + "q";
				}
				Cell c = null;
				try {
					c = old[i][j];
				} catch(Exception e) {
					c = new Cell(coord, i - 1, txt);
				}
				p.add(c.getCell());
				states[i][j] = c;
				
				p.updateUI();
			}
		}
	}
	
	public static void updateExec(JPanel p) {
		Character tmp = strip;
		p.removeAll();
		p.updateUI();

		while(tmp != null) {
			p.add(tmp.STRIP_CELL);
			tmp = tmp.stripNext();

			p.updateUI();
		}
	}
	
	public static void btnEnabler(boolean start, boolean init,
			boolean stop, boolean symbol, boolean state, boolean input) {
		btnStart.setEnabled(start);
		btnInit.setEnabled(init);
		btnEdit.setEnabled(start);
		btnStop.setEnabled(stop);
		btnAddSymbol.setEnabled(symbol);
		btnAddStatus.setEnabled(state);
		txtInput.setEditable(input);
		Component[] cmps = getProgPanel().getComponents();
		for(Component cmp : cmps) {
			cmp.setEnabled(init);
		}
	}

	public static JPanel getExePanel() {
		return panel;
	}
	public static JPanel getProgPanel() {
		return panel_1;
	}
	public static JButton getBtnStart() {
		return btnStart;
	}
	public static JButton getBtnStop() {
		return btnStop;
	}
	public static JButton getBtnAdSt() {
		return btnAddStatus;
	}
	public static JButton getBtnAdSy() {
		return btnAddSymbol;
	}
	public static JButton getBtnInit() {
		return btnInit;
	}
	public static JButton getBtnEdit() {
		return btnEdit;
	}
}
