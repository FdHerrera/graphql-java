package com.fdherrera.graphqldemo.datasource.fake;

import com.fdherrera.graphqldemo.generated.types.Book;
import com.fdherrera.graphqldemo.generated.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

/**
 * Data source for {@link Book}s
 *
 * @author federico.herrera
 */
@Component
public class FakeBookDataSource extends CommonFakeSource {

    public FakeBookDataSource(Faker faker) {
        super(faker);
    }

    private List<Book> books;

    @PostConstruct
    private void postConstruct() {
        books = IntStream.range(0, 20).mapToObj(i -> buildBook()).toList();
    }

    public List<Book> getBooks() {
        return books;
    }

    private Book buildBook() {
        return Book.newBuilder()
            .title(faker.book().title())
            .author(buildAuthor())
            .released(buildReleaseHistory())
            .publisher(faker.book().publisher())
            .build();
    }

    private ReleaseHistory buildReleaseHistory() {
        return ReleaseHistory.newBuilder()
            .releasedCountry(faker.address().country())
            .printedEdition(faker.bool().bool())
            .year(faker.number().numberBetween(1500, 2020))
            .build();
    }
}
