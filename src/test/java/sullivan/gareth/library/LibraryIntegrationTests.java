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
import org.springframework.test.context.junit4.SpringRunner;
import sullivan.gareth.library.model.Author;
import sullivan.gareth.library.model.Book;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= RANDOM_PORT)
public class LibraryIntegrationTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate testRestTemplate;

    private String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + randomServerPort + "/api";
    }

    @Test
    public void getBook_NoBooksExist() {
        // given
        String booksUrl = baseUrl + "/books";

        // when
        ResponseEntity<Book> entity = testRestTemplate.getForEntity(booksUrl, Book.class);

        // then
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(new Book(), entity.getBody());
    }

    @Test
    public void getAuthor_NoAuthorsExist() {
        // given
        String authorsUrl = baseUrl + "/authors";

        // when
        ResponseEntity<Author> entity = testRestTemplate.getForEntity(authorsUrl, Author.class);

        // then
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(new Author(), entity.getBody());
    }

}
