/*
 * Copyright (C) 2017  BAData Creative Studio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package net.badata.protobuf.converter.domain;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.proto.ResolverProto;
import net.badata.protobuf.converter.type.TypeConverter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jsjem on 10.03.2017.
 *
 * @author jsjem
 */
public class ResolverDomain {


	@ProtoClass(ResolverProto.ResolverTest.class)
	public static class Test {

		@ProtoField(name = "stringListValue", converter = CommaDelimitedStringListConverter.class)
		private String commaDelimitedStringValue;
		@ProtoField(name = "delimitedStringValue", converter = ListCommaDelimitedStringConverter.class)
		private List<String> stringList;

		public String getCommaDelimitedStringValue() {
			return commaDelimitedStringValue;
		}

		public void setCommaDelimitedStringValue(String commaDelimitedStringValue) {
			this.commaDelimitedStringValue = commaDelimitedStringValue;
		}

		public List<String> getStringList() {
			return stringList;
		}

		public void setStringList(final List<String> stringList) {
			this.stringList = stringList;
		}
	}


	public static class CommaDelimitedStringListConverter implements TypeConverter<String, List<String>> {

		@Override
		public String toDomainValue(Object instance) {
			@SuppressWarnings("unchecked")
			List<String> stringList = (List<String>) instance;
			return String.join(",", stringList);
		}

		@Override
		public List<String> toProtobufValue(Object instance) {
			String stringValue = (String) instance;
			String[] splitStrings = stringValue.split(",");
			List<String> stringList = new LinkedList<>();
			for (String s : splitStrings) {
				stringList.add(s);
			}
			return stringList;
		}

	}

	public static class ListCommaDelimitedStringConverter implements TypeConverter<List<String>, String> {

		@Override
		public List<String> toDomainValue(Object instance) {
			String stringValue = (String) instance;
			String[] splitStrings = stringValue.split(",");
			List<String> stringList = new LinkedList<>();
			for (String s : splitStrings) {
				stringList.add(s);
			}
			return stringList;
		}

		@Override
		public String toProtobufValue(Object instance) {
			@SuppressWarnings("unchecked")
			List<String> stringList = (List<String>) instance;
			return String.join(",", stringList);
		}

	}
}
