package com.fdherrera.graphqldemo.datasource.fake;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.fdherrera.graphqldemo.generated.types.MobileApp;

import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;

@Component
public class FakeMobileAppDataSource extends CommonFakeSource {

    public FakeMobileAppDataSource(Faker faker) {
        super(faker);
    }

    private List<MobileApp> mobileApps;

    @PostConstruct
    private void postConstruct() {
        mobileApps = IntStream.range(0, 20)
            .mapToObj(i -> buildMobileApp())
            .collect(Collectors.toList());
    }

    private MobileApp buildMobileApp() {
        return null;
    }
}
