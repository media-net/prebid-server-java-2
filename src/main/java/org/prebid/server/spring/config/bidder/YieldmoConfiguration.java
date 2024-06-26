package org.prebid.server.spring.config.bidder;

import org.prebid.server.bidder.BidderDeps;
import org.prebid.server.bidder.yieldmo.YieldmoBidder;
import org.prebid.server.currency.CurrencyConversionService;
import org.prebid.server.json.JacksonMapper;
import org.prebid.server.spring.config.bidder.model.BidderConfigurationProperties;
import org.prebid.server.spring.config.bidder.util.BidderDepsAssembler;
import org.prebid.server.spring.config.bidder.util.UsersyncerCreator;
import org.prebid.server.spring.env.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import jakarta.validation.constraints.NotBlank;

@Configuration
@PropertySource(value = "classpath:/bidder-config/yieldmo.yaml", factory = YamlPropertySourceFactory.class)
public class YieldmoConfiguration {

    private static final String BIDDER_NAME = "yieldmo";

    @Bean("yieldmoConfigurationProperties")
    @ConfigurationProperties("adapters.yieldmo")
    BidderConfigurationProperties configurationProperties() {
        return new BidderConfigurationProperties();
    }

    @Bean
    BidderDeps yieldmoBidderDeps(BidderConfigurationProperties yieldmoConfigurationProperties,
                                 @NotBlank @Value("${external-url}") String externalUrl,
                                 JacksonMapper mapper,
                                 CurrencyConversionService currencyConversionService) {

        return BidderDepsAssembler.forBidder(BIDDER_NAME)
                .withConfig(yieldmoConfigurationProperties)
                .usersyncerCreator(UsersyncerCreator.create(externalUrl))
                .bidderCreator(config -> new YieldmoBidder(config.getEndpoint(), currencyConversionService, mapper))
                .assemble();
    }
}
