package net.badata.protobuf.converter.domain;

import net.badata.protobuf.converter.annotation.ProtoField;

import java.util.List;

/**
 * @author jsjem
 * @author Roman Gushel
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
		public Object notProtoField;

	}
}
