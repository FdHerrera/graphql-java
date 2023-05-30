package com.fdherrera.graphqldemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fdherrera.graphqldemo.generated.client.MobileAppsGraphQLQuery;
import com.fdherrera.graphqldemo.generated.client.MobileAppsProjectionRoot;
import com.fdherrera.graphqldemo.generated.types.MobileApp;
import com.fdherrera.graphqldemo.generated.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FakeMobileAppDataResolverTest {
    @Autowired private DgsQueryExecutor queryExecutor;

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
}
