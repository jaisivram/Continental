package app.exceptions;

public class UIException extends Exception {

	private static final long serialVersionUID = 1L;
	public UIException(String message) {
		super(message);
	}
	public UIException(Throwable e) {
		initCause(e);
	}
	public UIException(String message,Throwable e) {
		super(message,e);
	}
}
