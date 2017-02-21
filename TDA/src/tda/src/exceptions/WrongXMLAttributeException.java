package tda.src.exceptions;

public class WrongXMLAttributeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6082910519611169383L;

	public WrongXMLAttributeException() {
		super();
	}

	public WrongXMLAttributeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongXMLAttributeException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongXMLAttributeException(String message) {
		super(message);
	}

	public WrongXMLAttributeException(Throwable cause) {
		super(cause);
	}
	
}
