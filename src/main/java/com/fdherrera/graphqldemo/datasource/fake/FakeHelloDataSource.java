package com.fdherrera.graphqldemo.datasource.fake;

import com.fdherrera.graphqldemogenerated.types.Hello;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

/**
 * {@link Hello} faker.
 *
 * @author federico.herrera
 */
@Component
@RequiredArgsConstructor
public class FakeHelloDataSource {
    private final Faker faker;
    private List<Hello> hellos;

    @PostConstruct
    private void buildHellos() {
        hellos = IntStream.range(0, 20)
                     .mapToObj(i
                         -> Hello.newBuilder()
                                .randomNumber(faker.random().nextInt(5000))
                                .text(faker.company().name())
                                .build())
                     .collect(Collectors.toList());
    }

    public List<Hello> getHellos() {
        return hellos;
    }
}
