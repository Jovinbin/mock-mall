package com.mock.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhao
 * @since 2022-08-01 14:51
 */
@Component
public class DemoTask {

    @Value("${name}")
    private String name;

    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        System.out.println("name = " + name);
    }

}
