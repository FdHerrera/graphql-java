package com.fdherrera.graphqldemo.component.fake;

import static com.fdherrera.graphqldemo.generated.DgsConstants.QUERY_TYPE;
import static org.apache.logging.log4j.util.Strings.isBlank;

import com.fdherrera.graphqldemo.datasource.fake.FakeBookDataSource;
import com.fdherrera.graphqldemo.generated.DgsConstants.QUERY;
import com.fdherrera.graphqldemo.generated.types.Book;
import com.fdherrera.graphqldemo.generated.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Data resolver for {@link FakeBookDataSource}
 *
 * @author federico.herrera
 */
@DgsComponent
@Slf4j
@AllArgsConstructor
public class FakeBookDataResolver {
    private final FakeBookDataSource dataSource;

    @DgsData(parentType = QUERY_TYPE, field = QUERY.Books)
    public List<Book> booksByAuthorName(String author) {
        log.info("Looking for books written by: {}", author);
        if (isBlank(author)) {
            return dataSource.getBooks();
        }
        List<Book> allBooks = dataSource.getBooks();
        return allBooks.stream()
            .filter(book -> book.getAuthor().getName().toLowerCase().contains(author.toLowerCase()))
            .toList();
    }

    @DgsData(parentType = QUERY_TYPE, field = QUERY.BooksByRelease)
    public List<Book> getBooksByRelease(DataFetchingEnvironment dataFetchingEnvironment) {
        Map<String, Object> inputMap = dataFetchingEnvironment.getArgument("releasedInput");
        ReleaseHistoryInput argument =
            ReleaseHistoryInput.newBuilder()
                .year((Integer) inputMap.get("year"))
                .hasPrintedEdition((boolean) inputMap.get("hasPrintedEdition"))
                .build();
        log.info("Getting books by release: {}", argument);
        return dataSource.getBooks()
            .stream()
            .filter(book
                -> book.getReleased().getPrintedEdition().equals(argument.getHasPrintedEdition()))
            .filter(book -> book.getReleased().getYear() == argument.getYear())
            .toList();
    }
}
