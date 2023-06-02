package com.fdherrera.graphqldemo.datasource.fake;

import com.fdherrera.graphqldemo.generated.types.Cat;
import com.fdherrera.graphqldemo.generated.types.Dog;
import com.fdherrera.graphqldemo.generated.types.Pet;
import com.fdherrera.graphqldemo.generated.types.PetFoodType;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FakePetDataSource {
    private final Faker faker;
    private List<Pet> pets;

    @PostConstruct
    private void buildPets() {
        pets = IntStream.range(0, 20)
            .mapToObj(i -> {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    return Cat.newBuilder()
                        .name(faker.cat().name())
                        .breed(faker.cat().breed())
                        .registry(faker.cat().registry())
                        .food(PetFoodType.CARNIVORE)
                        .build();
                } else {
                    return Dog.newBuilder()
                        .name(faker.dog().name())
                        .breed(faker.dog().breed())
                        .coatLength(faker.dog().coatLength())
                        .size(faker.dog().size())
                        .food(PetFoodType.CARNIVORE)
                        .build();
                }
            })
            .toList();
    }

    public List<Pet> getPets() {
        return pets;
    }
}
