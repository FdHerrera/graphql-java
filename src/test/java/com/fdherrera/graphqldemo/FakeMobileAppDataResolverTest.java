package com.fdherrera.graphqldemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fdherrera.graphqldemo.datasource.fake.FakeMobileAppDataSource;
import com.fdherrera.graphqldemo.generated.client.MobileAppsGraphQLQuery;
import com.fdherrera.graphqldemo.generated.client.MobileAppsProjectionRoot;
import com.fdherrera.graphqldemo.generated.types.AuthorFilter;
import com.fdherrera.graphqldemo.generated.types.MobileApp;
import com.fdherrera.graphqldemo.generated.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FakeMobileAppDataResolverTest {
    @Autowired private DgsQueryExecutor queryExecutor;
    @Autowired private FakeMobileAppDataSource dataSource;

    @Test
    void shouldReturnAllMobileAppsWhenInputIsNull() {
        MobileAppFilter inputFilter = null;
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot()
                                                  .name()
                                                  .version()
                                                  .author()
                                                  .name()
                                                  .addresses()
                                                  .zipcode()
                                                  .country()
                                                  .street()
                                                  .city()
                                                  .root();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertEquals(20, actualMobileApps.length);
    }

    @Test
    void shouldReturnAtLeastOneMobileAppWhenNameOfAMobileAppIsInDataSource() {
        MobileApp someMobileAppInDatabase =
            dataSource.getMobileApps().stream().findAny().orElseThrow();
        MobileAppFilter inputFilter =
            MobileAppFilter.newBuilder().name(someMobileAppInDatabase.getName()).build();
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot().name();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 1);
        Arrays.asList(actualMobileApps)
            .forEach(
                mobileApp -> assertEquals(mobileApp.getName(), someMobileAppInDatabase.getName()));
    }

    @Test
    void shouldReturnAtLeastOneMobileAppWhenVersionOfAMobileAppIsInDataSource() {
        MobileApp someMobileAppInDatabase =
            dataSource.getMobileApps().stream().findAny().orElseThrow();
        MobileAppFilter inputFilter =
            MobileAppFilter.newBuilder().version(someMobileAppInDatabase.getVersion()).build();
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot().version();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 1);
        Arrays.asList(actualMobileApps)
            .forEach(mobileApp
                -> assertEquals(mobileApp.getVersion(), someMobileAppInDatabase.getVersion()));
    }

    @Test
    void shouldReturnAtLeastOneMobileAppWhenAuthorNameOfAMobileAppIsInDataSource() {
        MobileApp someMobileAppInDatabase =
            dataSource.getMobileApps().stream().findAny().orElseThrow();
        MobileAppFilter inputFilter =
            MobileAppFilter.newBuilder()
                .author(AuthorFilter.newBuilder()
                            .name(someMobileAppInDatabase.getAuthor().getName())
                            .build())
                .build();
        MobileAppsGraphQLQuery inputQuery = MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot().author().name().root();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 1);
        Arrays.asList(actualMobileApps)
            .forEach(mobileApp
                -> assertEquals(mobileApp.getAuthor().getName(), someMobileAppInDatabase.getAuthor().getName()));
    }
}
