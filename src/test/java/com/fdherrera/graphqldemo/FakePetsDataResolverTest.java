package com.fdherrera.graphqldemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fdherrera.graphqldemo.generated.client.PetsGraphQLQuery;
import com.fdherrera.graphqldemo.generated.client.PetsProjectionRoot;
import com.fdherrera.graphqldemo.generated.types.Pet;
import com.fdherrera.graphqldemo.generated.types.PetFilter;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FakePetsDataResolverTest {
    @Autowired private DgsQueryExecutor queryExecutor;

    @Test
    void shouldReturnAllNamesWhenFilterIsNull() {
        PetFilter petFilter = null;
        PetsGraphQLQuery query = PetsGraphQLQuery.newRequest().petFilter(petFilter).build();
        PetsProjectionRoot projection = new PetsProjectionRoot().name();
        String graphqlQuery = new GraphQLQueryRequest(query, projection).serialize();

        String[] actualPetsResponse = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlQuery, "data.pets[*].name", String[].class);

        assertNotNull(actualPetsResponse);
        assertEquals(20, actualPetsResponse.length);
    }

    @Test
    void shouldReturnAllDataWhenFilterIsNull() {
        PetFilter petFilter = null;
        PetsGraphQLQuery query = PetsGraphQLQuery.newRequest().petFilter(petFilter).build();
        PetsProjectionRoot projection = new PetsProjectionRoot()
                                            .name()
                                            .onCat()
                                            .breed()
                                            .registry()
                                            .root()
                                            .onDog()
                                            .breed()
                                            .size()
                                            .coatLength()
                                            .root();
        String graphqlQuery = new GraphQLQueryRequest(query, projection).serialize();

        Pet[] actualPetsResponse =
            queryExecutor.executeAndExtractJsonPathAsObject(graphqlQuery, "data.pets", Pet[].class);

        assertNotNull(actualPetsResponse);
        assertEquals(20, actualPetsResponse.length);
    }
}
