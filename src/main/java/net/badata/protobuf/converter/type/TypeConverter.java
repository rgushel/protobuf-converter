package net.badata.protobuf.converter.type;

/**
 * Interface for converting fields values if their types in the domain object and protobuf object different.
 *
 * @param <T> Field type in the domain object.
 * @param <E> Field type in the protobuf object.
 * @author jsjem
 * @author Roman Gushel
 */
public interface TypeConverter<T, E> {

	/**
	 * Convert instance from protobuf object type to domain object type.
	 *
	 * @param instance Instance for conversion.
	 * @return converted data.
	 */
	T toDomainValue(final Object instance);

	/**
	 * Convert instance from domain object type to protobuf object type.
	 *
	 * @param instance Instance for conversion.
	 * @return converted data.
	 */
	E toProtobufValue(final Object instance);

}
