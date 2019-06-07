package net.badata.protobuf.converter.domain;

import com.google.protobuf.Message;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoClasses;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.converter.proto.MultiMappingProto;
import net.badata.protobuf.converter.resolver.AnnotatedFieldResolverFactoryImpl;
import net.badata.protobuf.converter.resolver.DefaultFieldResolverImpl;
import net.badata.protobuf.converter.resolver.FieldResolver;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class MultiMappingDomain {

	@ProtoClass(MultiMappingProto.MultiMappingTest.class)
	public static class MultiMappingOwner {
		@ProtoField
		private MultiMappingChild multiMappingValue;
		@ProtoField
		private List<MultiMappingChild> multiMappingListValue;

		public MultiMappingChild getMultiMappingValue() {
			return multiMappingValue;
		}

		public void setMultiMappingValue(final MultiMappingChild multiMappingValue) {
			this.multiMappingValue = multiMappingValue;
		}

		public List<MultiMappingChild> getMultiMappingListValue() {
			return multiMappingListValue;
		}

		public void setMultiMappingListValue(final List<MultiMappingChild> multiMappingListValue) {
			this.multiMappingListValue = multiMappingListValue;
		}
	}

	@ProtoClasses({@ProtoClass(MultiMappingProto.MultiMappingFirst.class),
						  @ProtoClass(value = MultiMappingProto.MultiMappingSecond.class,
									  mapper = MultiMappingMapperImpl.class,
									  fieldFactory = FieldResolverFactoryImpl.class)})
	public static class MultiMappingChild {
		@ProtoField
		private int intValue;
		@ProtoField
		private long longValue;

		public int getIntValue() {
			return intValue;
		}

		public void setIntValue(final int intValue) {
			this.intValue = intValue;
		}

		public long getLongValue() {
			return longValue;
		}

		public void setLongValue(final long longValue) {
			this.longValue = longValue;
		}
	}

	public static class FieldResolverFactoryImpl extends AnnotatedFieldResolverFactoryImpl {

		public static final String FIELD_INT_VALUE = "intValue";
		public static final String FIELD_LONG_VALUE = "longValue";

		@Override
		public FieldResolver createResolver(final Field field, final Object domain) {
			if (FIELD_INT_VALUE.equals(field.getName())) {
				return super.createResolver(field, domain);
			}
			if (FIELD_LONG_VALUE.equals(field.getName())) {
				DefaultFieldResolverImpl fieldResolver = (DefaultFieldResolverImpl) super.createResolver(field, domain);
				fieldResolver.setProtobufName("longValueChanged");
				return fieldResolver;
			}
			return new DefaultFieldResolverImpl(field);
		}
	}

	public static class MultiMappingMapperImpl extends DefaultMapperImpl {

		@Override
		public <T extends Message.Builder> MappingResult mapToProtobufField(final FieldResolver fieldResolver,
				final Object domain, final T protobufBuilder) throws MappingException {
			if (FieldResolverFactoryImpl.FIELD_INT_VALUE.equals(fieldResolver.getDomainName()) ||
					FieldResolverFactoryImpl.FIELD_LONG_VALUE.equals(fieldResolver.getDomainName())) {
				return super.mapToProtobufField(fieldResolver, domain, protobufBuilder);
			}
			return new MappingResult(MappingResult.Result.MAPPED, null, protobufBuilder);
		}

		@Override
		public <T extends Message> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T
				protobuf, final Object domain) throws MappingException {
			if (FieldResolverFactoryImpl.FIELD_INT_VALUE.equals(fieldResolver.getDomainName()) ||
					FieldResolverFactoryImpl.FIELD_LONG_VALUE.equals(fieldResolver.getDomainName())) {
				return super.mapToDomainField(fieldResolver, protobuf, domain);
			}
			return new MappingResult(MappingResult.Result.MAPPED, null, domain);
		}
	}
}
