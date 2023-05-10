package com.fdherrera.graphqldemo.datasource.fake;

import jakarta.annotation.PostConstruct;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

/**
 * Data source for {@link Book}s
 *
 * @author federico.herrera
 */
@Configuration
@RequiredArgsConstructor
public class FakeBookDataSource {
    private final Faker faker;

    @PostConstruct
    private void postConstruct() {
        IntStream.range(0, 20);
    }



}
