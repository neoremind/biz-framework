package net.neoremind.bizframework.commons.exception;

public class OperationNotSupportException extends RuntimeException {

	private static final long serialVersionUID = -6138595796119511714L;

	public OperationNotSupportException() {
	}

	public OperationNotSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationNotSupportException(String message) {
		super(message);
	}

	public OperationNotSupportException(Throwable cause) {
		super(cause);
	}

}
