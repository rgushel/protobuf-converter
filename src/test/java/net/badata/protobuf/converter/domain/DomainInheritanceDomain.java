package net.badata.protobuf.converter.domain;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.proto.DomainInheritanceProto;

/**
 * @author jsjem
 * @author Roman Gushel
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
