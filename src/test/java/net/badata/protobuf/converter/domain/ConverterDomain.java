package net.badata.protobuf.converter.domain;

import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.inspection.DefaultValue;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.converter.proto.ConverterProto;
import net.badata.protobuf.converter.resolver.AnnotatedFieldResolverFactoryImpl;
import net.badata.protobuf.converter.resolver.DefaultFieldResolverImpl;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.type.DateLongConverterImpl;
import net.badata.protobuf.converter.type.EnumStringConverter;
import net.badata.protobuf.converter.type.SetListConverterImpl;
import net.badata.protobuf.converter.type.TypeConverter;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class ConverterDomain {

	@ProtoClass(ConverterProto.ConverterTest.class)
	public static class Test {

		@ProtoField
		private Long longValue;
		@ProtoField
		private Integer intValue;
		@ProtoField
		private Float floatValue;
		@ProtoField
		private Double doubleValue;
		@ProtoField(name = "booleanValue")
		private Boolean boolValue;
		@ProtoField
		private String stringValue;
		@ProtoField
		private PrimitiveTest primitiveValue;
		@ProtoField
		private FieldConverterTest fieldConversionValue;
		@ProtoField
		private NullDefaultTest nullDefaultValue;
		@ProtoField(name = "stringListValue")
		private List<String> simpleListValue;
		@ProtoField
		private List<PrimitiveTest> complexListValue;
		@ProtoField(converter = SetListConverterImpl.class)
		private Set<PrimitiveTest> complexSetValue;
		@ProtoField
		private List<PrimitiveTest> complexNullableCollectionValue;
		@ProtoField
		private ByteString bytesValue;
		@ProtoField
		private Test recursiveValue;


		public Long getLongValue() {
			return longValue;
		}

		public void setLongValue(final Long longValue) {
			this.longValue = longValue;
		}

		public Integer getIntValue() {
			return intValue;
		}

		public void setIntValue(final Integer intValue) {
			this.intValue = intValue;
		}

		public Float getFloatValue() {
			return floatValue;
		}

		public void setFloatValue(final Float floatValue) {
			this.floatValue = floatValue;
		}

		public Double getDoubleValue() {
			return doubleValue;
		}

		public void setDoubleValue(final Double doubleValue) {
			this.doubleValue = doubleValue;
		}

		public Boolean getBoolValue() {
			return boolValue;
		}

		public void setBoolValue(final Boolean boolValue) {
			this.boolValue = boolValue;
		}

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(final String stringValue) {
			this.stringValue = stringValue;
		}

		public PrimitiveTest getPrimitiveValue() {
			return primitiveValue;
		}

		public void setPrimitiveValue(final PrimitiveTest primitiveValue) {
			this.primitiveValue = primitiveValue;
		}

		public FieldConverterTest getFieldConversionValue() {
			return fieldConversionValue;
		}

		public void setFieldConversionValue(final FieldConverterTest fieldConversionValue) {
			this.fieldConversionValue = fieldConversionValue;
		}

		public NullDefaultTest getNullDefaultValue() {
			return nullDefaultValue;
		}

		public void setNullDefaultValue(final NullDefaultTest nullDefaultValue) {
			this.nullDefaultValue = nullDefaultValue;
		}

		public List<String> getSimpleListValue() {
			return simpleListValue;
		}

		public void setSimpleListValue(final List<String> simpleListValue) {
			this.simpleListValue = simpleListValue;
		}


		public List<PrimitiveTest> getComplexListValue() {
			return complexListValue;
		}

		public void setComplexListValue(final List<PrimitiveTest> complexListValue) {
			this.complexListValue = complexListValue;
		}

		public Set<PrimitiveTest> getComplexSetValue() {
			return complexSetValue;
		}

		public void setComplexSetValue(final Set<PrimitiveTest> complexSetValue) {
			this.complexSetValue = complexSetValue;
		}

		public List<PrimitiveTest> getComplexNullableCollectionValue() {
			return complexNullableCollectionValue;
		}

		public void setComplexNullableCollectionValue(final List<PrimitiveTest> complexNullableCollectionValue) {
			this.complexNullableCollectionValue = complexNullableCollectionValue;
		}

		public ByteString getBytesValue() {
			return bytesValue;
		}

		public void setBytesValue(ByteString bytesValue) {
			this.bytesValue = bytesValue;
		}

		public Test getRecursiveValue() {
			return recursiveValue;
		}

		public void setRecursiveValue(Test recursiveValue) {
			this.recursiveValue = recursiveValue;
		}
	}

	@ProtoClass(ConverterProto.PrimitiveTest.class)
	public static class PrimitiveTest {

		@ProtoField
		private long longValue;
		@ProtoField
		private int intValue;
		@ProtoField
		private float floatValue;
		@ProtoField
		private double doubleValue;
		@ProtoField
		private boolean booleanValue;

		public long getLongValue() {
			return longValue;
		}

		public void setLongValue(final long longValue) {
			this.longValue = longValue;
		}

		public int getIntValue() {
			return intValue;
		}

		public void setIntValue(final int intValue) {
			this.intValue = intValue;
		}

		public float getFloatValue() {
			return floatValue;
		}

		public void setFloatValue(final float floatValue) {
			this.floatValue = floatValue;
		}

		public double getDoubleValue() {
			return doubleValue;
		}

		public void setDoubleValue(final double doubleValue) {
			this.doubleValue = doubleValue;
		}

		public boolean isBooleanValue() {
			return booleanValue;
		}

		public void setBooleanValue(final boolean booleanValue) {
			this.booleanValue = booleanValue;
		}
	}

	@ProtoClass(ConverterProto.FieldConverterTest.class)
	public static class FieldConverterTest {

		@ProtoField(converter = TestEnumConverter.class)
		private TestEnumConverter.TestEnum enumString;
		@ProtoField(converter = DateLongConverterImpl.class)
		private Date dateLong;
		@ProtoField(converter = SetListConverterImpl.class)
		private Set<String> stringSetValue;


		public TestEnumConverter.TestEnum getEnumString() {
			return enumString;
		}

		public void setEnumString(final TestEnumConverter.TestEnum enumString) {
			this.enumString = enumString;
		}

		public Date getDateLong() {
			return dateLong;
		}

		public void setDateLong(final Date dateLong) {
			this.dateLong = dateLong;
		}

		public Set<String> getStringSetValue() {
			return stringSetValue;
		}

		public void setStringSetValue(final Set<String> stringSetValue) {
			this.stringSetValue = stringSetValue;
		}
	}

	public static class TestEnumConverter extends EnumStringConverter<TestEnumConverter.TestEnum> {

		public static enum TestEnum {
			ONE, TWO, THREE;
		}
	}

	@ProtoClass(ConverterProto.NullDefaultTest.class)
	public static class NullDefaultTest {
		@ProtoField
		private String nullString;
		@ProtoField(nullValue = AlwaysNullInspector.class, defaultValue = StringDefaultValue.class)
		private String customInspectionString;
		@ProtoField
		private PrimitiveWrapperTest defaultPrimitives;


		public String getNullString() {
			return nullString;
		}

		public void setNullString(final String nullString) {
			this.nullString = nullString;
		}

		public String getCustomInspectionString() {
			return customInspectionString;
		}

		public void setCustomInspectionString(final String customInspectionString) {
			this.customInspectionString = customInspectionString;
		}

		public PrimitiveWrapperTest getDefaultPrimitives() {
			return defaultPrimitives;
		}

		public void setDefaultPrimitives(final PrimitiveWrapperTest defaultPrimitives) {
			this.defaultPrimitives = defaultPrimitives;
		}
	}

	@ProtoClass(ConverterProto.PrimitiveTest.class)
	public static class PrimitiveWrapperTest {

		@ProtoField
		private Long longValue;
		@ProtoField
		private Integer intValue;
		@ProtoField
		private Float floatValue;
		@ProtoField
		private Double doubleValue;
		@ProtoField
		private Boolean booleanValue;

		public Long getLongValue() {
			return longValue;
		}

		public void setLongValue(final Long longValue) {
			this.longValue = longValue;
		}

		public Integer getIntValue() {
			return intValue;
		}

		public void setIntValue(final Integer intValue) {
			this.intValue = intValue;
		}

		public Float getFloatValue() {
			return floatValue;
		}

		public void setFloatValue(final Float floatValue) {
			this.floatValue = floatValue;
		}

		public Double getDoubleValue() {
			return doubleValue;
		}

		public void setDoubleValue(final Double doubleValue) {
			this.doubleValue = doubleValue;
		}

		public Boolean getBooleanValue() {
			return booleanValue;
		}

		public void setBooleanValue(final Boolean booleanValue) {
			this.booleanValue = booleanValue;
		}
	}

	@ProtoClass(ConverterProto.DomainConstructorTest.class)
	public static class DomainConstructorTest {

		@ProtoField(converter = DomainConstructorTestConverter.class, useConverterConstructorWithDomainObject = "true")
		private String msg;

		private String msg2 = "initialValue";

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getMsg2() {
			return msg2;
		}

		public void setMsg2(String msg2) {
			this.msg2 = msg2;
		}
	}

	public static class DomainConstructorTestConverter implements TypeConverter<String, String> {
		private final DomainConstructorTest domainObj;

		public DomainConstructorTestConverter(DomainConstructorTest domainObj) {
			this.domainObj = domainObj;
		}

		@Override
		public String toDomainValue(Object instance) {
		    this.domainObj.setMsg2("toDomainValue!");
		    return (String) instance;
		}

		@Override
		public String toProtobufValue(Object instance) {
			this.domainObj.setMsg2("toProtobufValue!");
			return (String) instance;
		}
	}

	public static class AlwaysNullInspector implements NullValueInspector {

		@Override
		public boolean isNull(final Object value) {
			return true;
		}
	}

	public static class StringDefaultValue implements DefaultValue {

		@Override
		public Object generateValue(final Class<?> type) {
			return "Custom default";
		}
	}

	public static class FieldResolverFactoryImpl extends AnnotatedFieldResolverFactoryImpl {

		public static final String FIELD_INT_VALUE = "intValue";
		public static final String FIELD_LONG_VALUE = "longValue";

		@Override
		public FieldResolver createResolver(final Field field, Object domain) {
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
