package com.fdherrera.graphqldemo.component.fake;

import static com.fdherrera.graphqldemo.generated.DgsConstants.QUERY_TYPE;

import com.fdherrera.graphqldemo.generated.DgsConstants.QUERY;
import com.fdherrera.graphqldemo.generated.types.MobileApp;
import com.fdherrera.graphqldemo.generated.types.MobileAppFilter;
import com.fdherrera.graphqldemo.service.MobileAppService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;


import lombok.AllArgsConstructor;

@DgsComponent
@AllArgsConstructor
public class FakeMobileAppDataResolver {
    private final MobileAppService mobileAppService;

    @DgsData(parentType = QUERY_TYPE, field = QUERY.MobileApps)
    public List<MobileApp> getMobileApps(
        @InputArgument(name = QUERY.MOBILEAPPS_INPUT_ARGUMENT.MobileAppFilter)
        MobileAppFilter filter, DataFetchingEnvironment dataFetchingEnvironment) {
        return mobileAppService.getMobileApps(filter);
    }
}
