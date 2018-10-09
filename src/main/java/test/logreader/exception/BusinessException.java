package test.logreader.exception;

public class BusinessException extends Exception {

	/**
	 * Default serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 */
	public BusinessException() {
		super();
	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param error
	 *            the error message.
	 */
	public BusinessException(String error) {
		super(error);
	}

}
