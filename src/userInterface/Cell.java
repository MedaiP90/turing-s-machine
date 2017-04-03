package userInterface;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Cell extends JTextField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] adress;
	private int state;
	
	@SuppressWarnings("unused")
	private String COMMAND = "";
	public boolean ACTIVE = true;
	
	public Cell(int[] a, int s, String c) {
		this.adress = a;
		this.state = s;
		
		Font font = new Font("SansSerif", Font.PLAIN, 20);
		this.setFont(font);
		this.setColumns(6);
		
		if(this.adress[0] == 0) {
			this.setEditable(false);
			this.setText(c);
			this.ACTIVE = false;
		}
		if(this.adress[1] == 0) {
			this.setEditable(false);
			this.ACTIVE = false;
			if(this.adress[0] != 0) {
				this.setText(this.state + "q");
			}
		}
		if(this.adress[0] != 0 && this.adress[1] != 0) {
			this.setEditable(true);
		}
		
		this.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				//System.out.println("[" + a[0] + ", " + a[1] + "]");
			}
			public void focusLost(FocusEvent e) {
				Cell.this.selectAll();
				Cell.this.COMMAND = Cell.this.getCommand();
				Cell.this.setCaretPosition(0);
				Cell.this.updateUI();
		    }

		});
	}
	
	public JTextField getCell() {
		return this;
	}
	
	public int[] getAdress() {
		return this.adress;
	}
	
	public int getState() {
		return this.state;
	}
	
	public String getCommand() {
		this.selectAll();
		return this.getSelectedText();
	}
}
