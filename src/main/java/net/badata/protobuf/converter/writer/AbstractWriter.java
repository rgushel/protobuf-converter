package net.badata.protobuf.converter.writer;

import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.resolver.FieldResolver;

/**
 * Abstract class for performing data writing to the objects.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public abstract class AbstractWriter {

	private final Object destination;

	/**
	 * Constructor.
	 *
	 * @param destination Destination object to which data has to be written.
	 */
	protected AbstractWriter(final Object destination) {
		this.destination = destination;
	}

	/**
	 * Write data to the destination object.
	 *
	 * @param fieldResolver Field resolver which value has to be written.
	 * @param value         Value to write.
	 * @throws WriteException throws when some data writing errors happens. E.g.: destination setters is inaccessible,
	 *                        etc.
	 */
	public void write(final FieldResolver fieldResolver, final Object value) throws WriteException {
		write(destination, fieldResolver, value);
	}

	/**
	 * Write data to the destination object.
	 *
	 * @param destination   Instance to which data has to be written.
	 * @param fieldResolver Field resolver which value has to be written.
	 * @param value         Value to write.
	 * @throws WriteException throws when some data writing errors happens.
	 */
	protected abstract void write(final Object destination, final FieldResolver fieldResolver, final Object value)
			throws WriteException;
}
