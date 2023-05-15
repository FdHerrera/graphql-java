package com.fdherrera.graphqldemo.component.fake;

import com.fdherrera.graphqldemo.datasource.fake.FakeMobileAppDataSource;
import com.fdherrera.graphqldemo.generated.DgsConstants.QUERY;
import com.fdherrera.graphqldemo.generated.types.MobileApp;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;

import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.fdherrera.graphqldemo.generated.DgsConstants.QUERY_TYPE;

import java.util.List;
import java.util.Map;

@Slf4j
@DgsComponent
@AllArgsConstructor
public class FakeMobileAppDataResolver {
    private final FakeMobileAppDataSource dataSource;

    @DgsData(parentType = QUERY_TYPE, field = QUERY.MobileApps)
    public List<MobileApp> getMobileApps(DataFetchingEnvironment dataFetchingEnvironment) {
        Map<String, Object> args = dataFetchingEnvironment.getArguments();
        log.info(args.toString());
        return null;
    }
}
