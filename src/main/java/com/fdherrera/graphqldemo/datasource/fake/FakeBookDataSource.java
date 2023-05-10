package com.fdherrera.graphqldemo.datasource.fake;

import com.fdherrera.graphqldemo.generated.types.Address;
import com.fdherrera.graphqldemo.generated.types.Author;
import com.fdherrera.graphqldemo.generated.types.Book;
import com.fdherrera.graphqldemo.generated.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

/**
 * Data source for {@link Book}s
 *
 * @author federico.herrera
 */
@Component
@RequiredArgsConstructor
public class FakeBookDataSource {
    private final Faker faker;
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

    private Author buildAuthor() {
        return Author.newBuilder()
            .name(faker.book().author())
            .originCountry(faker.address().country())
            .addresses(buildThreeAddressessAtMost())
            .build();
    }

    private ReleaseHistory buildReleaseHistory() {
        return ReleaseHistory.newBuilder()
            .releasedCountry(faker.address().country())
            .printedEdition(faker.bool().bool())
            .year(faker.number().numberBetween(1500, 2020))
            .build();
    }

    private List<Address> buildThreeAddressessAtMost() {
        return IntStream.range(0, ThreadLocalRandom.current().nextInt(1, 3))
            .mapToObj(i -> buildAddress())
            .toList();
    }

    private Address buildAddress() {
        return Address.newBuilder()
            .street(faker.address().streetName())
            .city(faker.address().city())
            .zipcode(faker.address().zipCode())
            .country(faker.address().country())
            .build();
    }
}
