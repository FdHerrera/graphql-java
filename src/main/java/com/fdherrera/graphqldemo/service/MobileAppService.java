package com.fdherrera.graphqldemo.service;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import com.fdherrera.graphqldemo.datasource.fake.FakeMobileAppDataSource;
import com.fdherrera.graphqldemo.generated.types.MobileApp;
import com.fdherrera.graphqldemo.generated.types.MobileAppFilter;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return mobileApps.stream().filter(mobileApp -> filterMobileApp(filter, mobileApp)).toList();
    }

    private boolean filterMobileApp(MobileAppFilter filter, MobileApp mobileApp) {
        if (!Objects.isNull(filter.getName())) {
            if (containsIgnoreCase(mobileApp.getName(), filter.getName())) {
                return true;
            }
        }

        if (!Objects.isNull(filter.getVersion())) {
            if (containsIgnoreCase(mobileApp.getVersion(), filter.getVersion())) {
                return true;
            }
        }

        if (!Objects.isNull(filter.getAuthor())) {
            if (containsIgnoreCase(mobileApp.getAuthor().getName(), filter.getAuthor().getName())) {
                return true;
            }
        }

        if (!Objects.isNull(filter.getPlatform())) {
            if (mobileApp.getPlatform().contains(filter.getPlatform())) {
                return true;
            }
        }

        if (!Objects.isNull(filter.getMinimumDownloads())) {
            if (mobileApp.getTotalDownloads() >= filter.getMinimumDownloads()) {
                return true;
            }
        }

        if (!Objects.isNull(filter.getReleasedAfter())) {
            if (mobileApp.getReleaseDate().isAfter(filter.getReleasedAfter()) || mobileApp.getReleaseDate().isEqual(filter.getReleasedAfter())) {
                return true;
            }
        }

        if (!Objects.isNull(filter.getCategory())) {
            if (mobileApp.getCategory().equals(filter.getCategory())) {
                return true;
            }
        }
        return false;
    }
}
