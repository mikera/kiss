package kiss.lang.impl;

@SuppressWarnings("serial")
public class KissException extends RuntimeException {
	public KissException(String s) {
		super(s);
	}

	public KissException(String s, Throwable t) {
		super(s,t);
	}
}
