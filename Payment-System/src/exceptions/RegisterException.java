package exceptions;

/**
 * Class used to have a custom exception.
 * 
 * @author Mircea Solovastru
 */
public class RegisterException extends PayServException {
	/**
	 * Serial of the exception.
	 */
	private static final long serialVersionUID = 3L;

	/**
	 * Throwing an exception with a message.
	 * 
	 * @param message
	 */
	public RegisterException(String message) {
		super(message);
	}

	/**
	 * Throwing the exception.
	 * 
	 * @param cause
	 */
	public RegisterException(Throwable cause) {
		super(cause);
	}

	/**
	 * Both of the above.
	 * 
	 * @param message
	 * @param cause
	 */
	public RegisterException(String message, Throwable cause) {
		super(message, cause);
	}
}
