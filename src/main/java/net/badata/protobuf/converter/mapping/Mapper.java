package net.badata.protobuf.converter.mapping;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.exception.MappingException;

import java.lang.reflect.Field;

/**
 * Interface for mapping field values between domain and protobuf objects.
 *
 * @author jsjem
 */
public interface Mapper {

	/**
	 * Maps data from Protobuf field to domain field.
	 *
	 * @param field    Field for which mapping is performed.
	 * @param protobuf Protobuf dto instance that holds data.
	 * @param domain   Domain object waiting for data mapping.
	 * @param <T>      Type of Protobuf dto.
	 * @return Result of the data mapping from Protobuf dto to domain object.
	 * @throws net.badata.protobuf.converter.exception.MappingException throws when Protobuf dto field data access
	 * problems is occurred.
	 */
	<T extends Message> MappingResult mapToDomainField(final Field field, final T protobuf, final Object domain)
			throws MappingException;

	/**
	 * Maps data from domain field to Protobuf field.
	 *
	 * @param field           Field for which mapping is performed.
	 * @param domain          Domain object that holds data.
	 * @param protobufBuilder Builder for Protobuf dto that waiting for data mapping.
	 * @param <T>             Type of Protobuf dto.
	 * @return Result of the data mapping from domain object to Protobuf dto.
	 * @throws MappingException throws when domain object field data access problems is occurred.
	 */
	<T extends Message.Builder> MappingResult mapToProtobufField(final Field field, final Object domain, final T
			protobufBuilder)
			throws MappingException;
}
