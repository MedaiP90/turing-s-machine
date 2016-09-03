package mdt;

public class WrongNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6361356590284400391L;
	
	private String errorMsg;
	
	public WrongNumberException(String s) {
		this.errorMsg = s;
	}
	
	public String getMessage() {
		return this.errorMsg;
	}
}
