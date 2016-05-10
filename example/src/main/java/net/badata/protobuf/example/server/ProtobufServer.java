package net.badata.protobuf.example.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import net.badata.protobuf.example.proto.LibraryServiceGrpc;

import java.io.IOException;

/**
 * Created by jsjem on 04.05.2016.
 *
 * @author jsjem
 */
public class ProtobufServer {

	public static final int PORT = 6789;
	private final MemoryDatabase database = new MemoryDatabase();
	private Server server;


	public void start() throws IOException, InterruptedException {
		server = ServerBuilder.forPort(PORT)
				.addService(LibraryServiceGrpc.bindService(new LibraryService(database)))
				.build()
				.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				ProtobufServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
		System.out.println("gRPC server started. Press 'Ctrl + C' for exit.");
		server.awaitTermination();
	}

	public void stop() {
		if (server != null) {
			server.shutdown();
		}
	}
}