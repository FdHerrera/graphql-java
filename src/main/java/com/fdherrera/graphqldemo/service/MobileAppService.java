package com.fdherrera.graphqldemo.service;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import com.fdherrera.graphqldemo.datasource.fake.FakeMobileAppDataSource;
import com.fdherrera.graphqldemo.generated.types.AuthorFilter;
import com.fdherrera.graphqldemo.generated.types.MobileApp;
import com.fdherrera.graphqldemo.generated.types.MobileAppFilter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MobileAppService {
    private final FakeMobileAppDataSource dataSource;

    public List<MobileApp> getMobileApps(MobileAppFilter filter) {
        log.debug("Getting mobile apps filtering by: {}", filter);
        List<MobileApp> mobileApps = dataSource.getMobileApps();
        if (Objects.isNull(filter)) {
            log.info("Getting all mobile apps since filter is null");
            return mobileApps;
        }
        return mobileApps.stream()
            .filter(mobileApp
                -> containsIgnoreCase(mobileApp.getName(),
                    Optional.ofNullable(filter.getName()).orElse(Strings.EMPTY)))
            .filter(mobileApp
                -> containsIgnoreCase(mobileApp.getVersion(),
                    Optional.ofNullable(filter.getVersion()).orElse(Strings.EMPTY)))
            .filter(mobileApp -> matchAuthor(mobileApp, filter.getAuthor()))
            .toList();
    }

    private boolean matchAuthor(MobileApp mobileApp, AuthorFilter authorFilter) {
        if (Objects.isNull(authorFilter)) {
            return true;
        }
        return containsIgnoreCase(mobileApp.getAuthor().getName(),
            Optional.ofNullable(authorFilter.getName()).orElse(Strings.EMPTY));
    }
}
