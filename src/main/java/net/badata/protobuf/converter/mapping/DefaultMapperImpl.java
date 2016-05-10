package net.badata.protobuf.converter.mapping;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


/**
 * Implementation of {@link net.badata.protobuf.converter.mapping.Mapper Mapper} that is applied by default.
 *
 * This implementation maps fields values directly from domain instance to related protobuf instance and vice versa.
 *
 * @author jsjem
 */
public class DefaultMapperImpl implements Mapper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Message> MappingResult mapToDomainField(final Field field, final T protobuf, final Object
			domain) throws MappingException {
		Object protobufFieldValue = getFieldValue(FieldUtils.createProtobufGetterName(field), protobuf);
		if (FieldUtils.isComplexType(field)) {
			return new MappingResult(MappingResult.Result.NESTED_MAPPING, protobufFieldValue, domain);
		}
		if (FieldUtils.isCollectionType(field)) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, protobufFieldValue, domain);
		}
		return new MappingResult(MappingResult.Result.MAPPED, protobufFieldValue, domain);
	}

	private Object getFieldValue(final String getterName, final Object source) throws MappingException {
		Class<?> sourceClass = source.getClass();
		try {
			return sourceClass.getMethod(getterName).invoke(source);
		} catch (IllegalAccessException e) {
			throw new MappingException(
					String.format("Access denied. '%s.%s()'", sourceClass.getName(), getterName));
		} catch (InvocationTargetException e) {
			throw new MappingException(
					String.format("Can't set field value through '%s.%s()'", sourceClass.getName(), getterName));
		} catch (NoSuchMethodException e) {
			throw new MappingException(
					String.format("Access denied. '%s.%s()'", sourceClass.getName(), getterName));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Message.Builder> MappingResult mapToProtobufField(final Field field, final Object domain, final T
			protobufBuilder) throws MappingException {
		Object domainFieldValue = getFieldValue(FieldUtils.createDomainGetterName(field), domain);
		if (FieldUtils.isComplexType(field)) {
			return new MappingResult(MappingResult.Result.NESTED_MAPPING, domainFieldValue, protobufBuilder);
		}
		if (FieldUtils.isCollectionType(field)) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, domainFieldValue, protobufBuilder);
		}
		return new MappingResult(MappingResult.Result.MAPPED, domainFieldValue, protobufBuilder);
	}

}
