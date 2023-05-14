package com.fdherrera.graphqldemo.datasource.fake;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import com.fdherrera.graphqldemo.generated.types.Address;
import com.fdherrera.graphqldemo.generated.types.Author;

import lombok.AllArgsConstructor;
import net.datafaker.Faker;

/**
 * Abstract class with common methods used by it's child classes.
 *
 * @author federico.herrera
 */
@AllArgsConstructor
public abstract class CommonFakeSource {
    protected final Faker faker;


    public Author buildAuthor() {
        return Author.newBuilder()
            .name(faker.book().author())
            .originCountry(faker.address().country())
            .addresses(buildThreeAddressessAtMost())
            .build();
    }


    public List<Address> buildThreeAddressessAtMost() {
        return IntStream.range(0, ThreadLocalRandom.current().nextInt(1, 3))
            .mapToObj(i -> buildAddress())
            .toList();
    }


    public Address buildAddress() {
        return Address.newBuilder()
            .street(faker.address().streetName())
            .city(faker.address().city())
            .zipcode(faker.address().zipCode())
            .country(faker.address().country())
            .build();
    }
}
