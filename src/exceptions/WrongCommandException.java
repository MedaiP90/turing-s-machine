package exceptions;

public class WrongCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongCommandException(String er, int s) {
		super("Invalid command sequence: " + er + " at status " + s + "q");
	}

}
