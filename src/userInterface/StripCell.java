package userInterface;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class StripCell extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField character;

	public StripCell() {
		Font font = new Font("SansSerif", Font.BOLD, 20);
		
		this.character = new JTextField(1);
		this.character.setFont(font);
		this.character.setEditable(false);
		this.add(this.character);
	}
	
	public JPanel getStripCell() {
		return this;
	}
	
	public JTextField getCharacter() {
		return this.character;
	}
}
