package tmCore;

import userInterface.StripCell;

public class Character {
	
	public char character;
	private Character prevChar;
	private Character nextChar;
	
	public StripCell STRIP_CELL;
	
	public Character(char c, StripCell s) {
		this.character = c;
		this.prevChar = null;
		this.nextChar = null;
		this.STRIP_CELL = s;
		this.STRIP_CELL.getCharacter().setText(this.character + "");
	}
	
	// Insertion method
	public boolean insertLeft(char c, StripCell s) {
		boolean success = true;
		
		Character newChar = new Character(c, s);
		newChar.STRIP_CELL.getCharacter().setText(c + "");
		if(this.prevChar != null) {
			newChar.nextChar = this;
			newChar.prevChar = this.prevChar;
			this.prevChar.nextChar = newChar;
			this.prevChar = newChar;
		} else {
			newChar.nextChar = this;
			this.prevChar = newChar;
		}
		
		return success;
	}
	
	public boolean insertRight(char c, StripCell s) {
		boolean success = true;
		
		Character newChar = new Character(c, s);
		newChar.STRIP_CELL.getCharacter().setText(c + "");
		if(this.nextChar != null) {
			newChar.prevChar = this;
			newChar.nextChar = this.nextChar;
			this.nextChar.prevChar = newChar;
			this.nextChar = newChar;
		} else {
			newChar.prevChar = this;
			this.nextChar = newChar;
		}
		
		return success;
	}
	
	// update method
	public void modifyValue(char c) {
		this.character = c;
		this.STRIP_CELL.getCharacter().setText(this.character + "");
	}
	
	// Navigation methods
	public Character stripPrevious() {
		return this.prevChar;
	}
	
	public Character stripNext() {
		return this.nextChar;
	}
}
