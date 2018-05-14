package net.badata.protobuf.example.client.view;

import net.badata.protobuf.example.client.ClientException;
import net.badata.protobuf.example.client.ProtobufClient;
import net.badata.protobuf.example.client.domain.Reader;
import net.badata.protobuf.example.client.domain.ReadingBook;
import net.badata.protobuf.example.server.ProtobufServer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class ClientWindow implements LoginView.OnLoginActionListener, AddBookView.OnAddBookActionListener,
		LibraryView.OnLibraryActionListener {

	private final ProtobufClient client;
	private final JFrame windowFrame;
	private final LoginView loginForm;
	private final AddBookView addBookForm;
	private final LibraryView libraryView;

	public ClientWindow() {
		client = new ProtobufClient("127.0.0.1", ProtobufServer.PORT);

		windowFrame = new JFrame("Library: Protobuf Converter Test Application");
		windowFrame.setSize(500, 500);
		windowFrame.setResizable(false);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				client.shutdown();
			}
		});

		loginForm = new LoginView(windowFrame);
		loginForm.setListener(this);

		addBookForm = new AddBookView(windowFrame);
		addBookForm.setVisible(false);
		addBookForm.setListener(this);

		libraryView = new LibraryView(windowFrame);
		libraryView.setVisible(false);
		libraryView.setListener(this);

		windowFrame.setVisible(true);
	}


	@Override
	public void onUserLogin(final String name, final String password) {
		Reader reader = new Reader();
		reader.setName(name);
		reader.setPassword(password);
		if (client.signIn(reader)) {
			loginForm.showLogoutActions();
			addBookForm.setVisible(true);
			libraryView.setVisible(true);
			updateLibraryView();
		}
	}

	private void updateLibraryView() {
		try {
			updateLibraryView(client.showAllBooks());
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}


	private void updateLibraryView(final List<ReadingBook> books) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				libraryView.updateList(books);
			}
		});
	}

	@Override
	public void onUserLogout() {
		client.logOut();
		loginForm.showLoginActions();
		addBookForm.setVisible(false);
		libraryView.setVisible(false);
	}

	@Override
	public void onAddBook(final String title, final String author, final int pages) {
		ReadingBook book = new ReadingBook();
		book.setBookId(System.currentTimeMillis());
		book.setTitle(title);
		book.setAuthor(author);
		book.setPages(pages);
		sendBook(book);
	}

	private void sendBook(final ReadingBook book) {
		try {
			updateLibraryView(client.addBook(book));
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequestBook(final ReadingBook book) {
		try {
			client.getBook(book);
			updateLibraryView();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onReturnBook(final ReadingBook book) {
		sendBook(book);
	}

	@Override
	public void onUpdateLibrary() {
		updateLibraryView();
	}
}
