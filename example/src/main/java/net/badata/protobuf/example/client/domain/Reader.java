package net.badata.protobuf.example.client.domain;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.example.proto.User;

/**
 * @author jsjem
 * @author Roman Gushel
 */
@ProtoClass(User.class)
public class Reader {

	@ProtoField
	private String name;
	@ProtoField
	private String password;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
