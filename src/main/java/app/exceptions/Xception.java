package app.exceptions;

public class Xception extends Exception {

	private static final long serialVersionUID = 1L;
	public Xception(String message) {
		super(message);
	}
	public Xception(Throwable e) {
		initCause(e);
	}
	public Xception(String message,Throwable e) {
		super(message,e);
	}
}
