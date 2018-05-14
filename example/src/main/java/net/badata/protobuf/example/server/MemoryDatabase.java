package net.badata.protobuf.example.server;

import net.badata.protobuf.example.server.domain.LibraryBook;
import net.badata.protobuf.example.server.domain.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class MemoryDatabase {

	private final Set<LibraryBook> books = new HashSet<>();
	private final Map<String, Reader> users = new HashMap<>();


	public void addUser(final Reader user, final String token) {
		users.put(token, user);
	}

	public Reader findUser(final String token) {
		return users.get(token);
	}

	public void putBook(final LibraryBook book) {
		if(!books.add(book) && books.remove(book)) {
			books.add(book);
		}
	}

	public boolean giveBook(final LibraryBook book, final Reader user) {
		for (LibraryBook storedBook : books) {
			if (storedBook.equals(book)) {
				return makeUnavailable(storedBook, user);
			}
		}
		return false;
	}

	private boolean makeUnavailable(final LibraryBook storedBook, final Reader user) {
		if (storedBook.isAvailable()) {
			storedBook.setAvailable(false);
			storedBook.setOwner(user);
			return true;
		}
		return false;
	}

	public List<LibraryBook> getAllBooks() {
		return new ArrayList<LibraryBook>(books);
	}
}
