package net.badata.protobuf.converter.domain;

import java.util.List;
import java.util.Map;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.proto.MappingProto;

/**
 * Created by jsjem on 26.04.2016.
 */
public class MappingDomain {

	@ProtoClass(MappingProto.MappingTest.class)
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
		private NestedTest nestedValue;
		@ProtoField(name = "stringListValue")
		private List<String> simpleListValue;
		@ProtoField
		private List<NestedTest> nestedListValue;
		@ProtoField(name = "stringMapValue")
		private Map<String, String> simpleMapValue;
		@ProtoField
		private Map<String, NestedTest> nestedMapValue;

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

		public NestedTest getNestedValue() {
			return nestedValue;
		}

		public void setNestedValue(final NestedTest nestedValue) {
			this.nestedValue = nestedValue;
		}

		public List<String> getSimpleListValue() {
			return simpleListValue;
		}

		public void setSimpleListValue(final List<String> simpleListValue) {
			this.simpleListValue = simpleListValue;
		}

		public List<NestedTest> getNestedListValue() {
			return nestedListValue;
		}

		public void setNestedListValue(final List<NestedTest> nestedListValue) {
			this.nestedListValue = nestedListValue;
		}

		public Map<String, String> getSimpleMapValue() {
			return simpleMapValue;
		}

		public void setSimpleMapValue(Map<String, String> simpleMapValue) {
			this.simpleMapValue = simpleMapValue;
		}

		public Map<String, NestedTest> getNestedMapValue() {
			return nestedMapValue;
		}

		public void setNestedMapValue(Map<String, NestedTest> nestedMapValue) {
			this.nestedMapValue = nestedMapValue;
		}
	}

	@ProtoClass(MappingProto.NestedTest.class)
	public static class NestedTest {

		@ProtoField
		private String stringValue;

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(final String stringValue) {
			this.stringValue = stringValue;
		}
	}

	@ProtoClass(MappingProto.MappingTest.class)
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

	@ProtoClass(MappingProto.MappingTest.class)
	public static class InaccessibleTest {

		@ProtoField
		private Object inaccessibleField;
		@ProtoField
		private Object protectedGetterField;

		protected Object getProtectedGetterField() {
			return protectedGetterField;
		}
	}

}
