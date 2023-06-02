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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot().author().name().root();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 1);
        Arrays.asList(actualMobileApps)
            .forEach(mobileApp
                -> assertEquals(mobileApp.getAuthor().getName(),
                    someMobileAppInDatabase.getAuthor().getName()));
    }

    @Test
    void shouldReturnAtLeastOneMobileAppWhenPlatformOfAMobileAppIsInDataSource() {
        MobileApp someMobileAppInDatabase =
            dataSource.getMobileApps().stream().findAny().orElseThrow();
        String platformInDatabase =
            someMobileAppInDatabase.getPlatform().stream().findAny().orElse("Windows");
        MobileAppFilter inputFilter =
            MobileAppFilter.newBuilder().platform(platformInDatabase).build();
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot().platform().name();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 1);
        Arrays.asList(actualMobileApps)
            .forEach(mobileApp -> assertTrue(mobileApp.getPlatform().contains(platformInDatabase)));
    }

    @Test
    void shouldReturnAtLeastOneMobileAppWhenTotalDownloadsIsInDataSource() {
        MobileApp someMobileAppInDatabase =
            dataSource.getMobileApps().stream().findAny().orElseThrow();
        Integer totalDownloadsInDB = someMobileAppInDatabase.getTotalDownloads();
        MobileAppFilter inputFilter =
            MobileAppFilter.newBuilder().minimumDownloads(totalDownloadsInDB).build();
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot().totalDownloads();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 1);
        Arrays.asList(actualMobileApps)
            .forEach(mobileApp -> assertTrue(mobileApp.getTotalDownloads() >= totalDownloadsInDB));
    }

    @Test
    void shouldReturnAtLeastOneMobileAppWhenMinimumReleaseDateIsInDataSource() {
        MobileApp someMobileAppInDatabase =
            dataSource.getMobileApps().stream().findAny().orElseThrow();
        LocalDate dateInDB = someMobileAppInDatabase.getReleaseDate();
        MobileAppFilter inputFilter =
            MobileAppFilter.newBuilder().releasedAfter(dateInDB).build();
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection = new MobileAppsProjectionRoot().releaseDate();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 1);
        Arrays.asList(actualMobileApps)
            .forEach(mobileApp -> assertTrue(mobileApp.getReleaseDate().isAfter(dateInDB) || mobileApp.getReleaseDate().isEqual(dateInDB)));
    }

    @Test
    void shouldReturnAtLeastOneMobileAppPerFilterPropertyWhenAMobileAppMatch() {
        List<MobileApp> mobileAppsInDB = dataSource.getMobileApps();
        String aNameOfAMobileAppInDB = mobileAppsInDB.get(0).getName();
        String aVersionOfAMobileAppInDB = mobileAppsInDB.get(0).getVersion();
        String aPlatformOfAMobileAppInDB = mobileAppsInDB.get(0).getPlatform().get(0);

        MobileAppFilter inputFilter = MobileAppFilter.newBuilder()
                                          .name(aNameOfAMobileAppInDB)
                                          .version(aVersionOfAMobileAppInDB)
                                          .platform(aPlatformOfAMobileAppInDB)
                                          .build();
        MobileAppsGraphQLQuery inputQuery =
            MobileAppsGraphQLQuery.newRequest().mobileAppFilter(inputFilter).build();
        MobileAppsProjectionRoot projection =
            new MobileAppsProjectionRoot().platform().name().version().platform();
        String graphqlRequest = new GraphQLQueryRequest(inputQuery, projection).serialize();

        MobileApp[] actualMobileApps = queryExecutor.executeAndExtractJsonPathAsObject(
            graphqlRequest, "data.mobileApps", MobileApp[].class);

        List<MobileApp> actualMobileAppsList = List.of(actualMobileApps);
        assertNotNull(actualMobileApps);
        assertTrue(actualMobileApps.length >= 3);
        assertAtLeastOneAppMatchesAnyOfCriteria(aNameOfAMobileAppInDB, aVersionOfAMobileAppInDB,
            aPlatformOfAMobileAppInDB, actualMobileAppsList);
    }

    private void assertAtLeastOneAppMatchesAnyOfCriteria(String aNameOfAMobileAppInDB,
        String aVersionOfAMobileAppInDB, String aPlatformOfAMobileAppInDB,
        List<MobileApp> actualMobileAppsList) {
        boolean responseContainsAMobileAppWithSameName = false;
        boolean responseContainsAMobileAppWithSameVersion = false;
        boolean responseContainsAMobileAppWithPlatform = false;

        for (MobileApp anActualMobileApp : actualMobileAppsList) {
            if (StringUtils.containsIgnoreCase(
                    anActualMobileApp.getName(), aNameOfAMobileAppInDB)) {
                responseContainsAMobileAppWithSameName = true;
            }

            if (StringUtils.containsIgnoreCase(
                    anActualMobileApp.getVersion(), aVersionOfAMobileAppInDB)) {
                responseContainsAMobileAppWithSameVersion = true;
            }

            for (String platform : anActualMobileApp.getPlatform()) {
                if (StringUtils.containsIgnoreCase(platform, aPlatformOfAMobileAppInDB)) {
                    responseContainsAMobileAppWithPlatform = true;
                }
            }
        }
        assertTrue(responseContainsAMobileAppWithSameName);
        assertTrue(responseContainsAMobileAppWithSameVersion);
        assertTrue(responseContainsAMobileAppWithPlatform);
    }
}
