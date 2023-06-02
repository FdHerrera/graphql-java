package com.fdherrera.graphqldemo.datasource.fake;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.fdherrera.graphqldemo.generated.types.MobileApp;
import jakarta.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FakeMobileAppDataSource extends CommonFakeSource {
    private static final List<String> OS =
        List.of("Windows", "IOs", "Ubuntu", "CentOS", "Fedora", "Debian", "Mint");

    public FakeMobileAppDataSource(Faker faker) {
        super(faker);
    }

    private List<MobileApp> mobileApps;

    @PostConstruct
    private void postConstruct() {
        mobileApps = IntStream.range(0, 20).mapToObj(i -> buildMobileApp()).collect(toList());
    }

    public List<MobileApp> getMobileApps() {
        return mobileApps;
    }

    private MobileApp buildMobileApp() {
        URL url = null;
        try {
            url = new URL("https://" + faker.internet().url());
        } catch (MalformedURLException e) {
            log.error("Error building an URL for a mobile app");
        }
        return MobileApp.newBuilder()
            .appId(UUID.randomUUID().toString())
            .name(faker.name().name())
            .version(faker.app().version())
            .platform(getPlatforms())
            .author(buildAuthor())
            .releaseDate(LocalDate.now().minusDays(ThreadLocalRandom.current().nextInt(365)))
            .url(url)
            .totalDownloads(faker.number().numberBetween(0, 100_000_000))
            .build();
    }

    private List<String> getPlatforms() {
        return IntStream.range(0, 3)
            .mapToObj(i -> OS.get(ThreadLocalRandom.current().nextInt(0, OS.size())))
            .filter(s -> !Objects.isNull(s))
            .collect(toSet())
            .stream()
            .collect(toList());
    }
}
