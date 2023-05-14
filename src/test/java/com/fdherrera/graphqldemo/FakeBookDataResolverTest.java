package com.fdherrera.graphqldemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fdherrera.graphqldemo.component.fake.FakeBookDataResolver;
import com.fdherrera.graphqldemo.generated.types.Author;
import com.fdherrera.graphqldemo.generated.types.Book;
import com.fdherrera.graphqldemo.generated.types.ReleaseHistory;
import com.netflix.graphql.dgs.DgsQueryExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests suite for {@link FakeBookDataResolver}
 *
 * @author federico.herrera
 */
@SpringBootTest
class FakeBookDataResolverTest {
    @Autowired private DgsQueryExecutor queryExecutor;

    @Test
    void shouldReturnAllBooksWithNonNullDataForSpecificFieldsInGraph() {
        String query = """
        {
            books(author : "") {
                title 
                publisher
                author{
                    name 
                    originCountry
                    addresses {
                        street
                        city
                        country
                        zipcode
                    }
                } 
                released {
                    year 
                    releasedCountry 
                    printedEdition
                }
            }
        }
        """;  

        Book[] booksArray = queryExecutor.executeAndExtractJsonPathAsObject(query, "data.books", Book[].class);
        List<Book> actualBooks = Arrays.asList(booksArray);
        assertNotNull(actualBooks);
        assertEquals(20, actualBooks.size());
        actualBooks.forEach(book -> {
            assertNotNull(book);
            assertNotNull(book.getTitle());
            assertNotNull(book.getPublisher());
            assertNotNull(book.getAuthor());
            Author author = book.getAuthor();
            assertNotNull(author.getName());
            assertNotNull(author.getOriginCountry());
            assertNotNull(author.getAddresses());
            author.getAddresses().forEach(address -> {
                assertNotNull(address.getStreet());
                assertNotNull(address.getCity());
                assertNotNull(address.getCountry());
            });
            if (!Objects.isNull(book.getReleased())) {
                ReleaseHistory actualRelease = book.getReleased();
                assertNotNull(actualRelease.getYear());
            }
        });
    }
}
