package com.fdherrera.graphqldemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fdherrera.graphqldemo.component.fake.FakeBookDataResolver;
import com.fdherrera.graphqldemo.generated.types.Book;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import java.util.List;
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
                author{
                    name 
                    originCountry
                } 
                released {
                    year 
                    releasedCountry 
                    printedEdition
                }
            }
        }
        """;

        List<Book> actualBooks = queryExecutor.executeAndExtractJsonPath(query, "data.books");
        assertNotNull(actualBooks);
        assertEquals(20, actualBooks.size());
    }
}
