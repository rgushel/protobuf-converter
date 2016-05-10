package net.badata.protobuf.example;

import net.badata.protobuf.example.client.view.ClientWindow;
import net.badata.protobuf.example.server.ProtobufServer;

/**
 * Created by jsjem on 04.05.2016.
 *
 * @author jsjem
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
