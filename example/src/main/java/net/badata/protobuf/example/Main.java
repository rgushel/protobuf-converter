package net.badata.protobuf.example;

import net.badata.protobuf.example.client.view.ClientWindow;
import net.badata.protobuf.example.server.ProtobufServer;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class Main {

	public static void main(final String[] args) throws Exception {
		if(args != null && args.length > 0 && "server".equals(args[0])) {
			new ProtobufServer().start();
		} else {
			new ClientWindow();
		}
	}
}
