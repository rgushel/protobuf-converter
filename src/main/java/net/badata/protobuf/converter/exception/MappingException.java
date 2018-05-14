package net.badata.protobuf.converter.exception;

/**
 * Exception that notifies about field mapping errors during conversion.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class MappingException extends Exception {

	/**
	 * Constructs a new MappingException with {@code null} as its detail message.
	 * The cause is not initialized.
	 */
	public MappingException() {
		super();
	}

	/**
	 * Constructs a new MappingException with the specified detail message.  The
	 * cause is not initialized.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public MappingException(String message) {
		super(message);
	}

	/**
	 * Constructs a new MappingException with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 *                by the {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method).  (A <tt>null</tt> value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 */
	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new MappingException with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A <tt>null</tt> value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 */
	public MappingException(Throwable cause) {
		super(cause);
	}
}
