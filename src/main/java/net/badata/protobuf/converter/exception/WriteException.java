package net.badata.protobuf.converter.exception;

/**
 * Exception notifies that errors is occurred when setting value extracted from domain object to protobuf object and
 * vice versa.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class WriteException extends Exception {

	/**
	 * Constructs a new WriterException with {@code null} as its detail message.
	 * The cause is not initialized.
	 */
	public WriteException() {
		super();
	}

	/**
	 * Constructs a new WriterException with the specified detail message.  The
	 * cause is not initialized.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public WriteException(String message) {
		super(message);
	}

	/**
	 * Constructs a new WriterException with the specified detail message and
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
	public WriteException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new WriterException with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A <tt>null</tt> value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 */
	public WriteException(Throwable cause) {
		super(cause);
	}
}
