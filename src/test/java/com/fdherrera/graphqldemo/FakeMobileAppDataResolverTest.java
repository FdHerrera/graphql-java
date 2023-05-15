package com.fdherrera.graphqldemo;

import com.fdherrera.graphqldemo.generated.client.MobileAppsGraphQLQuery;
import com.fdherrera.graphqldemo.generated.client.MobileAppsProjectionRoot;
import com.fdherrera.graphqldemo.generated.types.AuthorFilter;
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
    void should() {
        AuthorFilter authorFilter = null;
        MobileAppFilter inputFilter =
            MobileAppFilter.newBuilder().name("App").version("Version").author(null).build();
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

        // queryExecutor.execute(graphqlRequest);
        queryExecutor.execute("""
            {
                    mobileApps(mobileAppFilter: {
                        name: "App" 
                        version: "Version" 
                        author : null
                    }) {
                        name
                    }
                }
            """
        );
    }
}
