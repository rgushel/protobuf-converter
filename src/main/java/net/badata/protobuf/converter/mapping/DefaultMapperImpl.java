package net.badata.protobuf.converter.mapping;

import com.google.protobuf.MessageLite;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.utils.FieldUtils;

import java.lang.reflect.InvocationTargetException;


/**
 * Implementation of {@link net.badata.protobuf.converter.mapping.Mapper Mapper} that is applied by default.
 * This implementation maps fields values directly from domain instance to related protobuf instance and vice versa.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class DefaultMapperImpl implements Mapper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends MessageLite> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T protobuf,
			final Object domain) throws MappingException {
		Object protobufFieldValue = getFieldValue(FieldUtils.createProtobufGetterName(fieldResolver), protobuf);
		if (FieldUtils.isComplexType(fieldResolver.getField())) {
			boolean hasFieldValue = true;
			try {
				String hasserName = FieldUtils.createProtobufHasserName(fieldResolver);
				if (hasserName != null) {
					hasFieldValue = hasFieldValue(hasserName, protobuf);
				}
			} catch (MappingException ignored) {} // not `has` method, continue
			if (hasFieldValue) {
				return new MappingResult(MappingResult.Result.NESTED_MAPPING, protobufFieldValue, domain);
			}
			return new MappingResult(MappingResult.Result.MAPPED, null, domain);
		}
		if (FieldUtils.isCollectionType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, protobufFieldValue, domain);
		}
		return new MappingResult(MappingResult.Result.MAPPED, protobufFieldValue, domain);
	}

	private boolean hasFieldValue(final String hasserName, final Object source) throws MappingException {
		Class<?> sourceClass = source.getClass();
		try {
			return (boolean) sourceClass.getMethod(hasserName).invoke(source);
		} catch (IllegalAccessException e) {
			throw new MappingException(
					String.format("Access denied. '%s.%s()'", sourceClass.getName(), hasserName));
		} catch (InvocationTargetException e) {
			throw new MappingException(
					String.format("Can't decide if field has value through '%s.%s()'", sourceClass.getName(), hasserName));
		} catch (NoSuchMethodException e) {
			throw new MappingException(
					String.format("Hasser not found. '%s.%s()'", sourceClass.getName(), hasserName));
		}
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
					String.format("Getter not found. '%s.%s()'", sourceClass.getName(), getterName));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends MessageLite.Builder> MappingResult mapToProtobufField(final FieldResolver fieldResolver, final
	Object domain, final T
			protobufBuilder) throws MappingException {
		Object domainFieldValue = getFieldValue(FieldUtils.createDomainGetterName(fieldResolver), domain);
		if (FieldUtils.isComplexType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.NESTED_MAPPING, domainFieldValue, protobufBuilder);
		}
		if (FieldUtils.isCollectionType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, domainFieldValue, protobufBuilder);
		}
		return new MappingResult(MappingResult.Result.MAPPED, domainFieldValue, protobufBuilder);
	}

}
