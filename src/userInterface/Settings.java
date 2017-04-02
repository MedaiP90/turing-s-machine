package userInterface;

import java.awt.BorderLayout;
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

public class Settings extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String sep = System.getProperty("file.separator");
	private String home = System.getProperty("user.home");
	private String path = this.home + this.sep + "tm" + this.sep + "settings.tms";

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
		setTitle("Settings");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSlider slider = new JSlider();
		contentPane.add(slider, BorderLayout.CENTER);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setValue(TmMainUi.delay);
		slider.setMaximum(20);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblDelaylbl = new JLabel("Delay: ");
		panel_1.add(lblDelaylbl);
		
		JLabel lblDelay = new JLabel(TmMainUi.delay + "");
		panel_1.add(lblDelay);
		
		JLabel lblMax = new JLabel("/ 20");
		panel_1.add(lblMax);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("Ok");
		panel.add(btnOk);
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int d = slider.getValue();
				lblDelay.setText(d + "");
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TmMainUi.delay = slider.getValue();
				
				// save settings
				try {
					FileWriter filew = new FileWriter(Settings.this.path);
					
					filew.write(TmMainUi.delay + "");
					filew.flush();
					filew.close();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(TmMainUi.contentPane,
							"Unable to save settings\n" + ex.getMessage(),
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				
				Settings.this.dispose();
			}
		});
	}

}
