/*
 * Copyright (C) 2016  BAData Creative Studio
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
import net.badata.protobuf.converter.proto.DomainInheritanceProto;

/**
 * Created by jsjem on 01.08.2016.
 *
 * @author jsjem
 */
public class DomainInheritanceDomain {
	@ProtoClass(DomainInheritanceProto.Test.class)
	public static class Test extends InheritedByTest {
		@ProtoField
		private Long ownLong;
		@ProtoField
		private double ownDouble;

		public Long getOwnLong() {
			return ownLong;
		}

		public void setOwnLong(Long ownLong) {
			this.ownLong = ownLong;
		}

		public double getOwnDouble() {
			return ownDouble;
		}

		public void setOwnDouble(double ownDouble) {
			this.ownDouble = ownDouble;
		}
	}

	public static class InheritedByTest {
		@ProtoField
		private int inheritedInt;
		@ProtoField
		private Float inheritedFloat;

		public int getInheritedInt() {
			return inheritedInt;
		}

		public void setInheritedInt(int inheritedInt) {
			this.inheritedInt = inheritedInt;
		}

		public Float getInheritedFloat() {
			return inheritedFloat;
		}

		public void setInheritedFloat(Float inheritedFloat) {
			this.inheritedFloat = inheritedFloat;
		}
	}
}
