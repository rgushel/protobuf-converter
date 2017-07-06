package net.badata.protobuf.converter.domain;

import java.util.List;
import java.util.Map;

import net.badata.protobuf.converter.annotation.ProtoField;

/**
 * Created by jsjem on 28.04.2016.
 */
public class IgnoreDomain {


	public static class IgnoreDataTest {

		@ProtoField
		public Object fieldName;
		@ProtoField(name = "protofield")
		public Object protoFieldName;
		@ProtoField(name = "notIgnored")
		public Object notIgnored;
		public Object notProtoField;

	}

	public static class NoIgnoreDataTest {

		@ProtoField(name = "protofield")
		public Object fieldName;
		@ProtoField
		public IgnoreDataTest ignoreClass;
		@ProtoField
		public List<IgnoreDataTest> ignoredCollection;
		@ProtoField
		public Map<String, IgnoreDataTest> ignoredMap;

		public Object notProtoField;

	}
}
