package mdt;

public class WrongCharException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882089508324675592L;
	
	private String errorMsg;
	
	public WrongCharException(String s) {
		this.errorMsg = s;
	}
	
	public String getMessage() {
		return this.errorMsg;
	}
	
}
