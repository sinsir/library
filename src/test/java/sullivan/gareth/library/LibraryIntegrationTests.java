package sullivan.gareth.library;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import sullivan.gareth.library.model.Author;
import sullivan.gareth.library.model.Book;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class LibraryIntegrationTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate testRestTemplate;

    private String baseUrl;
    private String booksUrl;
    private String authorsUrl;

    private static final String FIRST_NAME = "Gareth";
    private static final String LAST_NAME = "Sullivan";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + randomServerPort + "/api";

        booksUrl = baseUrl + "/books";

        authorsUrl = baseUrl + "/authors";
    }

    @Test
    public void getBook_NoBooksExist() {
        // when
        ResponseEntity<Book> entity = testRestTemplate.getForEntity(booksUrl, Book.class);

        // then
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(new Book(), entity.getBody());
    }

    @Test
    public void getAuthor_NoAuthorsExist() {
        // when
        ResponseEntity<Author> entity = testRestTemplate.getForEntity(authorsUrl, Author.class);

        // then
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(new Author(), entity.getBody());
    }

    @Test
    public void addAndGetBook() {
        // given
        Book book = new Book(TITLE, DESCRIPTION, null);

        // when
        ResponseEntity<Book> entity = testRestTemplate.postForEntity(booksUrl, book, Book.class);

        // then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());

        ResponseEntity<Book> bookResponseEntity = testRestTemplate.getForEntity(booksUrl + "/1", Book.class);
        assertEquals(HttpStatus.OK, bookResponseEntity.getStatusCode());
        assertEquals(book, bookResponseEntity.getBody());
    }

    @Test
    public void addAndGetAuthor()  {
        // given
        Author author = new Author(FIRST_NAME, LAST_NAME, null);

        // when
        ResponseEntity<Author> entity = testRestTemplate.postForEntity(authorsUrl, author, Author.class);

        // then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());

        ResponseEntity<Author> authorResponseEntity = testRestTemplate.getForEntity(authorsUrl + "/1", Author.class);
        assertEquals(HttpStatus.OK, authorResponseEntity.getStatusCode());
        assertEquals(author, authorResponseEntity.getBody());
    }

    @Test
    public void addBookLinkedToAuthor()  {
        // given
        Author author = new Author();
        author.setFirstName(FIRST_NAME);
        author.setLastName(LAST_NAME);

        Book book = new Book();
        book.setTitle(TITLE);
        book.setDescription(DESCRIPTION);
        book.setAuthor(author);

        // when
        ResponseEntity<Book> entity = testRestTemplate.postForEntity(booksUrl, book, Book.class);

        // then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());

        ResponseEntity<Book> authorResponseEntity = testRestTemplate.getForEntity(booksUrl + "/1", Book.class);
        assertEquals(HttpStatus.OK, authorResponseEntity.getStatusCode());
        assertEquals(book, authorResponseEntity.getBody());
    }
}
