package mdt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class MdtInterfaccia extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2675225035822520940L;
	
	public static JFrame frame;
	public static JTextField nSimboli;
	public static JButton invio;
	public static JLabel didascalia;
	public static JPanel p0;
	public static JButton apri;
	public static int numero;
	public static String path;
	public static int numStati;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new MdtInterfaccia();
	}
	
	public MdtInterfaccia() {
		frame = new JFrame("The Turing's Machine");
		//frame.setIconImage((new ImageIcon("icona.png")).getImage());
		frame.setIconImage((new ImageIcon(getClass().getResource("/mdt/icona.png"))).getImage());
		
		frame.add(GUI(), "Center");
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(350,110);
		int h = frame.getHeight()/2;
		int w = frame.getWidth()/2;
		Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		frame.setLocation(( screenSize.width / 2 ) - w , ( screenSize.height / 2 ) - h);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private JComponent GUI() {
		p0 = new JPanel();
		
		nSimboli = new JTextField(10);
		nSimboli.setText("1");
		invio = new JButton("Next");
		didascalia = new JLabel("Number of symbols in addition to $:");
		
		apri = new JButton("Or load an existing project");
		apri.addActionListener(new open());
		
		invio.addActionListener(new mainInt());
		
		p0.add(didascalia);
		p0.add(nSimboli);
		p0.add(invio);
		p0.add(apri);
		
		JLabel label = new JLabel("Cossutta, Bernardis, Innocente");
		label.setForeground(Color.gray);
		frame.add(label, BorderLayout.SOUTH);
		
		return p0;
	}
	
	final JFileChooser fc = new JFileChooser();
	
	public class open implements ActionListener {
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent j) {
			path = "";
			
			fc.addChoosableFileFilter(new fileFilter());
			fc.setAcceptAllFileFilterUsed(false);
			
			fc.setMultiSelectionEnabled(false);
			
			int returnVal = fc.showOpenDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            path = file.getAbsolutePath();
	            //This is where a real application would open the file.
	            System.out.println("Opening: " + path);
	            
	            try {
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(path));
					
				    String line;
				    
					line = br.readLine();

				    numero = (int) Double.parseDouble(line);
				    
				    line = br.readLine();
				    line = br.readLine();
				    
				    numStati = (int) Double.parseDouble(line);
				    
				    System.out.println("Stati: " + numStati + "\nSimboli: " + numero);
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
							"It was impossible to load " + path,
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				    return;
				}
	            
	            InterfacciaEsecuzione ie = new InterfacciaEsecuzione(numero, true);
	            frame.dispose();
	        } else {
	        	System.out.println("Open command cancelled by user");
	        }
		}
	}
	
	public class mainInt implements ActionListener {
		public void actionPerformed(ActionEvent j) {
			nSimboli.selectAll();
			if (nSimboli.getSelectedText() == null) {
				JOptionPane.showMessageDialog(frame,
					    "You must insert at least 0.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				numero = (int) Double.parseDouble(nSimboli.getSelectedText());
				if (numero < 0 || numero > 10) {
					JOptionPane.showMessageDialog(frame,
						    "You must insert at least 0 and almost 10.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame,
					    "Invalid character! Insert a number.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			@SuppressWarnings("unused")
			InterfacciaEsecuzione ie = new InterfacciaEsecuzione(numero, false);
			frame.dispose();
		}
	}
}
