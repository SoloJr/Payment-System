package exceptions;

public class PayServException extends Exception {
	private static final long serialVersionUID = 1L;

	public PayServException(String message) {
		super(message);
	}

	public PayServException(Throwable cause) {
		super(cause);
	}

	public PayServException(String message, Throwable cause) {
		super(message, cause);
	}
}
