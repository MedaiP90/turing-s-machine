package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import javax.swing.JToggleButton;

public class Settings extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static String sep = System.getProperty("file.separator");
	private static String home = System.getProperty("user.home");
	private static String path = home + sep + "tm" + sep + "settings.tms";
	
	private static boolean sty = TmMainUi.style;
	private static String color = TmMainUi.colo;

	/**
	 * Launch the application.
	 */
	public void createSettings() {
		try {
			Settings frame = new Settings();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(TmMainUi.contentPane,
					"Can't open settings\n" + e.getMessage(),
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Create the frame.
	 */
	public Settings() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/userInterface/icona.png")));
		setTitle("Preferences");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 520, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Delay", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_2.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblDelaylbl = new JLabel("Delay: ");
		panel_1.add(lblDelaylbl);
		
		JLabel lblDelay = new JLabel(TmMainUi.delay + "");
		panel_1.add(lblDelay);
		
		JLabel lblMax = new JLabel("/ 20");
		panel_1.add(lblMax);
		
		JSlider slider = new JSlider();
		panel_2.add(slider, BorderLayout.CENTER);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setValue(TmMainUi.delay);
		slider.setMaximum(20);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Application style", null, tabbedPane_1, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Color", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblActualColorCyan = new JLabel("Actual color: " + color);
		lblActualColorCyan.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblActualColorCyan, BorderLayout.NORTH);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6, BorderLayout.CENTER);
		
		JButton btnCyan = new JButton("BLUE");
		panel_6.add(btnCyan);
		
		JButton btnYellow = new JButton("YELLOW");
		panel_6.add(btnYellow);
		
		JButton btnRed = new JButton("RED");
		panel_6.add(btnRed);
		
		JButton btnGreen = new JButton("GREEN");
		panel_6.add(btnGreen);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("UI style", null, panel_5, null);
		
		JLabel lblForChangesTo = new JLabel("For changes to take effect you need to restart the Turing machine");
		panel_5.add(lblForChangesTo);
		lblForChangesTo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_5.add(panel_4);
		panel_4.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Enable system UI style:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblNewLabel);
		
		JToggleButton tglbtnUistyle = new JToggleButton("UiStyle");
		panel_4.add(tglbtnUistyle);
		
		tglbtnUistyle.setSelected(sty);
		
		tglbtnUistyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!sty) {
					sty = true;
				} else {
					sty = false;
				}
				if(sty) {
					tglbtnUistyle.setText("System UI");
				} else {
					tglbtnUistyle.setText("Java UI");
				}
			}
		});
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int d = slider.getValue();
				lblDelay.setText(d + "");
			}
		});
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("Ok");
		panel.add(btnOk);
		
		if(sty) {
			tglbtnUistyle.setText("System UI");
		} else {
			tglbtnUistyle.setText("Java UI");
		}
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TmMainUi.delay = slider.getValue();
				
				SavePreferences(TmMainUi.delay, sty, color);
				
				Settings.this.dispose();
			}
		});
		
		btnCyan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = "BLUE";
				lblActualColorCyan.setText("Actual color: " + color);
				TmMainUi.colo = "BLUE";
				TmMainUi.color = Color.BLUE;
			}
		});
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = "YELLOW";
				lblActualColorCyan.setText("Actual color: " + color);
				TmMainUi.colo = "YELLOW";
				TmMainUi.color = Color.YELLOW;
			}
		});
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = "RED";
				lblActualColorCyan.setText("Actual color: " + color);
				TmMainUi.colo = "RED";
				TmMainUi.color = Color.RED;
			}
		});
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = "GREEN";
				lblActualColorCyan.setText("Actual color: " + color);
				TmMainUi.colo = "GREEN";
				TmMainUi.color = Color.GREEN;
			}
		});
	}
	
	public static void SavePreferences(int del, boolean st, String colo) {
		// save settings
		try {
			FileWriter filew = new FileWriter(path);
			
			filew.write(del + "\n" + sty + "\n" + colo);
			filew.flush();
			filew.close();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(TmMainUi.contentPane,
					"Unable to save settings\n" + ex.getMessage(),
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

}
