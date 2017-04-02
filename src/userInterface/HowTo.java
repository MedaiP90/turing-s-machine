package userInterface;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

public class HowTo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void createHowTo() {
		try {
			HowTo frame = new HowTo();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(TmMainUi.contentPane,
					"Can't open 'How to'\n" + e.getMessage(),
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Create the frame.
	 */
	public HowTo() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HowTo.class.getResource("/userInterface/icona.png")));
		setTitle("How to");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("Ok");
		panel.add(btnOk);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JEditorPane dtrpnHt = new JEditorPane();
		dtrpnHt.setContentType("text/html");
		dtrpnHt.setText("<div align=\"center\">\n<h1>HOW TO</h1>\n<h3>Instruction to use this "
				+ "Turing machine</h3>\n</div>\n<div style=\"padding-left:15px;padding-right:15px;\">"
				+ "Add statuses and symbols then fill the grid with the program. Commands have the "
				+ "form:\n<div align=\"center\"><b>QSD</b></div>\nwhere <b>Q</b> is the next status "
				+ "(ex.: 2q), <b>S</b> is the symble to place at the current position and <b>D</b> "
				+ "is a direction (it can be <b>l</b> for left or <b>r</b> for right). Here's an "
				+ "example of command:\n<div align=\"center\"><b>2q$r</b></div>\nIt's also possible "
				+ "to add comments in every command cell, just write something near the instruction, "
				+ "the machine will ignore the text:\n<div align=\"center\"><b>2q$r <u>this is a "
				+ "comment</u></b></div>\n<br />\nWhen finished press 'Initialize' and 'Start' to "
				+ "start the algorithm.\n</div><br />");
		dtrpnHt.setEditable(false);
		scrollPane.setViewportView(dtrpnHt);
		
		dtrpnHt.setCaretPosition(0);
		scrollPane.updateUI();
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HowTo.this.dispose();
			}
		});
	}

}
