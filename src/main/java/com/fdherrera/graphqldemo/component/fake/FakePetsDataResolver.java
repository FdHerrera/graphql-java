package com.fdherrera.graphqldemo.component.fake;

import com.fdherrera.graphqldemo.datasource.fake.FakePetDataSource;
import com.fdherrera.graphqldemo.generated.DgsConstants;
import com.fdherrera.graphqldemo.generated.DgsConstants.QUERY;
import com.fdherrera.graphqldemo.generated.types.Pet;
import com.fdherrera.graphqldemo.generated.types.PetFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;

@DgsComponent
@AllArgsConstructor
public class FakePetsDataResolver {
    private final FakePetDataSource dataSource;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Pets)
    public List<Pet> getPets(@InputArgument(name = QUERY.PETS_INPUT_ARGUMENT.PetFilter) PetFilter petFilter) {
        if (Objects.isNull(petFilter)) {
            return dataSource.getPets();
        }
        return dataSource.getPets()
            .stream()
            .filter(pet -> StringUtils.containsIgnoreCase(pet.getClass().getSimpleName(), petFilter.getPetType()))
            .toList();
    }

}
