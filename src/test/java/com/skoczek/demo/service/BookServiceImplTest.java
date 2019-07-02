package com.skoczek.demo.service;

import com.skoczek.demo.dao.BookDAO;
import com.skoczek.demo.model.Book;
import com.skoczek.demo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


public class BookServiceImplTest {

    @Mock
    private BookDAO bookDAO;

    private Book book;
    private User user;

    @InjectMocks
    private BookServiceImpl bookService;

    @Before
    public void setUp() throws Exception {

        book = new Book();
        user = new User();

        MockitoAnnotations.initMocks(this);

        book.setAuthor("Jan Kowalski");
        book.setId(1L);
        book.setTitle("Gra o tron");

        user.setFirstName("Kuba");
        user.setId(5L);

        book.setUser(user);

    }

    @Test
    public void getBooks() {

        List<Book> list = new ArrayList<>();
        list.add(book);

        when(bookDAO.getBooks()).thenReturn(list);

        List<Book> bookList = bookDAO.getBooks();

        assertEquals(1, bookList.size());
    }

    @Test
    public void saveBook() {

        bookDAO.saveBook(book);

        verify(bookDAO, times(1)).saveBook(book);
    }

    @Test
    public void deleteBook() {

        bookDAO.deleteBook(anyLong());

        verify(bookDAO, times(1)).deleteBook(anyLong());
    }

    @Test
    public void searchBookByTitle() {

        List<Book> list = new ArrayList<>();
        list.add(book);

        when(bookDAO.searchBookByTitle(anyString())).thenReturn(list);

        List<Book> bookList = bookDAO.searchBookByTitle("Gra o tron");

        assertEquals(1, bookList.size());
    }

    @Test
    public void getBookByOwner() {

        List<Book> list = new ArrayList<>();
        list.add(book);

        when(bookDAO.getBookByOwner(anyLong())).thenReturn(list);

        List<Book> bookList = bookDAO.getBookByOwner(5L);

        assertEquals(1, bookList.size());
        assertEquals("Kuba", bookList.get(0).getUser().getFirstName());
    }

    @Test
    public void findById() {

        when(bookDAO.findById(anyLong())).thenReturn(book);

        Book theBook = bookDAO.findById(1L);

        assertNotNull(theBook);
        assertEquals("Jan Kowalski", theBook.getAuthor());
    }
}