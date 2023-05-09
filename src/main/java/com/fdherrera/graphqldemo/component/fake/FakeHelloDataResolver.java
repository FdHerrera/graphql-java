package com.fdherrera.graphqldemo.component.fake;

import com.fdherrera.graphqldemo.datasource.fake.FakeHelloDataSource;
import com.fdherrera.graphqldemogenerated.types.Hello;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@Slf4j
@AllArgsConstructor
public class FakeHelloDataResolver {
    private final FakeHelloDataSource fakeData;

    @DgsQuery
    public List<Hello> allHellos() {
        List<Hello> hellos = fakeData.getHellos();
        log.info("Getting all Hellos: {}", hellos);
        return hellos;
    }

    @DgsQuery
    public Hello oneHello() {
        List<Hello> hellos = fakeData.getHellos();
        Hello hello = hellos.get(ThreadLocalRandom.current().nextInt(hellos.size()));
        log.info("Getting one Hello: {}", hello);
        return hello;
    }
}
