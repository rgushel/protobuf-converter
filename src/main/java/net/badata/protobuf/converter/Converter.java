package net.badata.protobuf.converter;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.exception.ConverterException;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.exception.TypeRelationException;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.mapping.Mapper;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.converter.utils.AnnotationUtils;
import net.badata.protobuf.converter.utils.FieldUtils;
import net.badata.protobuf.converter.writer.DomainWriter;
import net.badata.protobuf.converter.writer.ProtobufWriter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Converts data from Protobuf messages to domain model objects and vice versa.
 *
 * @author jsjem
 */
public final class Converter {

	private final FieldsIgnore fieldsIgnore;

	/**
	 * Create default converter.
	 *
	 * @return Converter instance.
	 */
	public static Converter create() {
		return create(new FieldsIgnore());
	}

	/**
	 * Create converter with map of ignored fields.
	 *
	 * @param fieldsIgnore Map of fields that has to be ignored by this converter instance.
	 * @return Converter instance.
	 */
	public static Converter create(final FieldsIgnore fieldsIgnore) {
		return new Converter(fieldsIgnore);
	}

	/**
	 * Create object that performing conversion from protobuf object to domain model object and vice versa.
	 *
	 * @param fieldsIgnore Map of fields that has to be ignored by this converter instance while converting
	 *                     objects.
	 */
	protected Converter(final FieldsIgnore fieldsIgnore) {
		if (fieldsIgnore == null) {
			throw new IllegalArgumentException("ignoredFieldsMap can't be null.");
		}
		this.fieldsIgnore = fieldsIgnore;
	}

	/**
	 * Create domain object list from Protobuf dto list.
	 *
	 * @param domainClass        Expected domain object type.
	 * @param protobufCollection Source instance of Protobuf dto collection.
	 * @param <T>                Domain type.
	 * @param <E>                Protobuf dto type.
	 * @return Domain objects list filled with data stored in the Protobuf dto list.
	 */
	public <T, E extends Message> List<T> toDomain(final Class<T> domainClass, final Collection<E>
			protobufCollection) {
		return toDomain(List.class, domainClass, protobufCollection);

	}

	private <T, E extends Message, K extends Collection> K toDomain(final Class<K> collectionClass,
			final Class<T> domainClass, final Collection<E> protobufCollection) {
		Collection<T> domainList = List.class.isAssignableFrom(collectionClass) ? new ArrayList<T>() : new
				HashSet<T>();
		for (E protobuf : protobufCollection) {
			domainList.add(toDomain(domainClass, protobuf));
		}
		return (K) domainList;
	}

	/**
	 * Create domain object from Protobuf dto.
	 *
	 * @param domainClass Expected domain object type.
	 * @param protobuf    Source instance of Protobuf dto bounded to domain.
	 * @param <T>         Domain type.
	 * @param <E>         Protobuf dto type.
	 * @return Domain instance filled with data stored in the Protobuf dto.
	 */
	public <T, E extends Message> T toDomain(final Class<T> domainClass, final E protobuf) {
		if (protobuf == null) {
			return null;
		}
		testDataBinding(domainClass, protobuf.getClass());
		try {
			T domain = createDomain(domainClass);
			fillDomain(domain, protobuf);
			return domain;
		} catch (MappingException e) {
			throw new ConverterException("Field mapping error", e);
		} catch (WriteException e) {
			throw new ConverterException("Domain field value setting error", e);
		}
	}

	private void testDataBinding(final Class<?> domainClass, final Class<? extends Message> protobufClass) {
		if (!AnnotationUtils.extractMessageType(domainClass).isAssignableFrom(protobufClass)) {
			throw new ConverterException(new TypeRelationException(domainClass, protobufClass));
		}
	}

	private <T> T createDomain(final Class<T> domainClass) {
		try {
			return domainClass.newInstance();
		} catch (InstantiationException e) {
			throw new ConverterException("Default constructor not found for " + domainClass.getSimpleName(), e);
		} catch (IllegalAccessException e) {
			throw new ConverterException("Make default constructor of " + domainClass.getSimpleName() + " public", e);
		}
	}

	private <E extends Message> void fillDomain(final Object domain, final E protobuf) throws MappingException,
			WriteException {
		Class<?> domainClass = domain.getClass();
		Mapper fieldMapper = AnnotationUtils.createMapper(domainClass.getAnnotation(ProtoClass.class));
		for (Field field : domainClass.getDeclaredFields()) {
			if (fieldsIgnore.ignored(field)) {
				continue;
			}
			fillDomainField(field, fieldMapper.mapToDomainField(field, protobuf, domain));
		}
	}

	private void fillDomainField(final Field field, final MappingResult mappingResult) throws WriteException {
		DomainWriter fieldWriter = new DomainWriter(mappingResult.getDestination());
		Object mappedValue = mappingResult.getValue();
		switch (mappingResult.getCode()) {
			case NESTED_MAPPING:
				fieldWriter.write(field, create(fieldsIgnore).toDomain(field.getType(), (Message) mappedValue));
				break;
			case COLLECTION_MAPPING:
				Class<?> collectionType = FieldUtils.extractCollectionType(field);
				if (FieldUtils.isComplexType(collectionType)) {
					mappedValue = createDomainValueList(collectionType, mappedValue);
				}
			case MAPPED:
			default:
				fieldWriter.write(field, mappedValue);
		}
	}

	private <T> List<T> createDomainValueList(final Class<T> type, final Object protobufCollection) {
		return create(fieldsIgnore).toDomain(type, (List<? extends Message>) protobufCollection);
	}

	/**
	 * Create Protobuf dto list from domain object list.
	 *
	 * @param protobufClass    Expected Protobuf class.
	 * @param domainCollection Source domain collection.
	 * @param <T>              Domain type.
	 * @param <E>              Protobuf dto type.
	 * @return Protobuf dto list filled with data stored in the domain object list.
	 */
	public <T, E extends Message> List<E> toProtobuf(final Class<E> protobufClass, final Collection<T>
			domainCollection) {
		return toProtobuf(List.class, protobufClass, domainCollection);
	}

	private <T, E extends Message, K extends Collection> K toProtobuf(final Class<K> collectionClass,
			final Class<E> protobufClass, final Collection<T> domainCollection) {
		Collection<E> protobufCollection = List.class.isAssignableFrom(collectionClass) ? new ArrayList<E>() : new
				HashSet<E>();
		for (T domain : domainCollection) {
			protobufCollection.add(toProtobuf(protobufClass, domain));
		}
		return (K)protobufCollection;
	}

	/**
	 * Create Protobuf dto from domain object.
	 *
	 * @param protobufClass Expected Protobuf class.
	 * @param domain        Source domain instance to which protobufClass is bounded.
	 * @param <T>           Domain type.
	 * @param <E>           Protobuf dto type.
	 * @return Protobuf dto filled with data stored in the domain object.
	 */
	public <T, E extends Message> E toProtobuf(final Class<E> protobufClass, final T domain) {
		if (domain == null) {
			return null;
		}
		testDataBinding(domain.getClass(), protobufClass);
		try {
			E.Builder protobuf = createProtobuf(protobufClass);
			fillProtobuf(protobuf, domain);
			return (E) protobuf.build();
		} catch (MappingException e) {
			throw new ConverterException("Field mapping error", e);
		} catch (WriteException e) {
			throw new ConverterException("Protobuf field value setting error", e);
		}
	}

	private <E extends Message> E.Builder createProtobuf(final Class<E> protobufClass) {
		try {
			return (E.Builder) protobufClass.getDeclaredMethod("newBuilder").invoke(null);
		} catch (IllegalAccessException e) {
			throw new ConverterException("Can't access 'newBuilder()' method for " + protobufClass.getName(), e);
		} catch (InvocationTargetException e) {
			throw new ConverterException("Can't instantiate protobuf builder for " + protobufClass.getName(), e);
		} catch (NoSuchMethodException e) {
			throw new ConverterException("Method 'newBuilder()' not found in " + protobufClass.getName(), e);
		}
	}

	private <E extends Message.Builder> void fillProtobuf(final E protobuf, final Object domain) throws
			MappingException, WriteException {
		Class<?> domainClass = domain.getClass();
		Mapper fieldMapper = AnnotationUtils.createMapper(domainClass.getAnnotation(ProtoClass.class));
		for (Field field : domainClass.getDeclaredFields()) {
			if (fieldsIgnore.ignored(field)) {
				continue;
			}
			fillProtobufField(field, fieldMapper.mapToProtobufField(field, domain, protobuf));
		}
	}

	private void fillProtobufField(final Field field, final MappingResult mappingResult) throws WriteException {
		ProtobufWriter fieldWriter = new ProtobufWriter((Message.Builder) mappingResult.getDestination());
		Object mappedValue = mappingResult.getValue();
		switch (mappingResult.getCode()) {
			case NESTED_MAPPING:
				Class<? extends Message> protobufClass = AnnotationUtils.extractMessageType(field.getType());
				fieldWriter.write(field, create(fieldsIgnore).toProtobuf(protobufClass, mappedValue));
				break;
			case COLLECTION_MAPPING:
				Class<?> collectionType = FieldUtils.extractCollectionType(field);
				if (FieldUtils.isComplexType(collectionType)) {
					Class<? extends Message> protobufCollectionClass = AnnotationUtils.extractMessageType(
							collectionType);
					mappedValue = createProtobufValueList(protobufCollectionClass, (Collection)mappedValue);
				}
			case MAPPED:
			default:
				fieldWriter.write(field, mappedValue);
		}
	}

	private <E extends Message> Collection<?> createProtobufValueList(final Class<E> type, final Collection<?>
			domainCollection) {
		return create(fieldsIgnore).toProtobuf(domainCollection.getClass(), type, domainCollection);
	}

}
