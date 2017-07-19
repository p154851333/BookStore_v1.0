package com.monkey.bookstore.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.monkey.bookstore.po.Book;
import com.monkey.bookstore.po.Category;
public class BookDaoTest {
	private BookDao bookDao = new BookDao();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindAll() {
		Book book = new Book("111111111", "admin", 19.01, "admin", "dizhi", new Category("1", "admin"));
		bookDao.addBook(book);
	}

	@Test
	public void testFindBookByCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBookById() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBook() {
		fail("Not yet implemented");
	}

}
