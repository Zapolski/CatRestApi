package by.zapolski.rest;

public class CatNotFoundException extends RuntimeException{

	public CatNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CatNotFoundException(String message) {
		super(message);
	}

	public CatNotFoundException(Throwable cause) {
		super(cause);
	}

}
