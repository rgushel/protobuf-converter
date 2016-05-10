package net.badata.protobuf.converter.writer;

import net.badata.protobuf.converter.exception.WriteException;

import java.lang.reflect.Field;

/**
 * Abstract class for performing writing data to the objects.
 *
 * @author jsjem
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
	 * @param field Field instance which value has to be written.
	 * @param value Value to write.
	 * @throws WriteException throws when some data writing errors happens. E.g.: destination setters is inaccessible,
	 *                        etc.
	 */
	public void write(final Field field, final Object value) throws WriteException {
		write(destination, field, value);
	}

	/**
	 * Write data to the destination object.
	 *
	 * @param destination Instance to which data has to be written.
	 * @param field       Field instance which value has to be written.
	 * @param value       Value to write.
	 * @throws WriteException throws when some data writing errors happens.
	 */
	protected abstract void write(final Object destination, final Field field, final Object value) throws
			WriteException;
}
