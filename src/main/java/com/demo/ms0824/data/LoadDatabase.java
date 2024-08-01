package com.demo.ms0824.data;

import com.demo.ms0824.constants.Constants;
import com.demo.ms0824.repository.HolidayRepository;
import com.demo.ms0824.repository.ToolRepository;
import com.demo.ms0824.repository.ToolTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ToolTypeRepository toolTypeRepository, ToolRepository toolRepository, HolidayRepository holidayRepository) {

        return args -> {
            log.info("Preloading Ladder {}", toolTypeRepository.save(Constants.LADDER));
            log.info("Preloading Chainsaw {}", toolTypeRepository.save(Constants.CHAINSAW));
            log.info("Preloading Jackhammer {}", toolTypeRepository.save(Constants.JACKHAMMER));
            log.info("Preloading CHNS {}", toolRepository.save(Constants.CHNS_TOOL));
            log.info("Preloading LADW {}", toolRepository.save(Constants.LADW_TOOL));
            log.info("Preloading JAKD {}", toolRepository.save(Constants.JAKD_TOOL));
            log.info("Preloading JAKR {}", toolRepository.save(Constants.JAKR_TOOL));
            log.info("Preloading Independence Day {}", holidayRepository.save(Constants.INDEPENDENCE_DAY));
            log.info("Preloading Labor Day {}", holidayRepository.save(Constants.LABOR_DAY));
        };
    }
}
